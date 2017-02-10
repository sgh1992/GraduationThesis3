package models.model;


import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.ZeroR;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.Standardize;

import java.util.Enumeration;
import java.util.Random;

public class ModelMethod {
	
	private int m_NumFolds = 5;
	
	private  Classifier[] m_Models = null;
	
	private int m_NumModels = 5;
	
	private String[] m_RemoveAttrs = null;
	
	private double[] m_AccuracyRate = null;

	private double[] mircoF1 = null;

	private double[] macroF1 = null;
			
	public ModelMethod(int numModels){
		
		//初始化操作.
		m_NumModels = numModels;
		
		m_RemoveAttrs = new String[m_NumModels];
		
		m_Models = new Classifier[m_NumModels];

		mircoF1 = new double[numModels];
		macroF1 = new double[numModels];
		
		for(int i = 0; i < m_NumModels; i++)
			m_Models[i] = new ZeroR();
	}
	
	public ModelMethod(){
		
	}
	
	//设置每个model的具体分类器.包括分类器的具体类型，需要删除的属性字段.
	public void setModel(Classifier classifier, int index, String remove){
		
		m_Models[index] = classifier;
		
		m_RemoveAttrs[index] = remove;
	}
	
	//这个设置
	public void setNumFolds(int numFolds){
		
		m_NumFolds = numFolds;
	}
	
	
	/**
	 * 交叉验证选择最佳模型.
	 * @param dataFile
	 * @throws Exception
	 */
	public void CVModelSelection(String dataFile) throws Exception{
		
		Instances data = getInstances(dataFile);
		
		m_AccuracyRate = new double[m_NumModels];

				
		//注意，交叉验证针对离散型数据，要先进行分层处理.
		data.setClassIndex(data.numAttributes() - 1);//前提是要先设置类别.

		//首先对数据进行标准化处理.
		data = standardNise(data);

		data.stratify(m_NumFolds);
		data.setClassIndex(-1);//分层之后，要将类别重置为-1，方便后续处理.
		
		Random random = new Random(17);
		
		for(int numFold = 0; numFold < m_NumFolds; numFold++){			
			Instances train = data.trainCV(m_NumFolds, numFold, random);
			Instances test = data.testCV(m_NumFolds, numFold);
			System.out.println("Iteration " + numFold + " ...");
		    for(int i = 0; i < m_NumModels; i++){

		    	Instances trainFilter = removeAttrs(train, m_RemoveAttrs[i]);
		    	Instances testFilter = removeAttrs(test, m_RemoveAttrs[i]);
		    	
		    	Classifier classifier = Classifier.makeCopy(m_Models[i]);
		    	classifier.buildClassifier(trainFilter);
		    	
		    	Enumeration<Instance> enumTests = testFilter.enumerateInstances();

				int[][] confuseMatrix = new int[trainFilter.numClasses()][trainFilter.numClasses()];//混淆矩阵.
		    	
		    	double accuracy = 0;
		    	while(enumTests.hasMoreElements()){
		    		
		    		Instance inst = enumTests.nextElement();
		    		
		    		String acutalLabel = inst.stringValue(inst.classIndex());

		    		double predict = classifier.classifyInstance(inst);

					confuseMatrix[(int)inst.classValue()][(int)predict] += 1;
		    		
		    		String predictLabel = trainFilter.classAttribute().value((int)predict);
		    		
		    		if(acutalLabel.equals(predictLabel))
		    			accuracy += 1;		    		
		    		
		    	}

				macroF1[i] += macroF1(confuseMatrix);
				mircoF1[i] += microF1(confuseMatrix);

		    	accuracy /= testFilter.numInstances();
		    	m_AccuracyRate[i] += accuracy;

				System.out.println("Model " + i);
		    }
		}
		
		//每个模型的最终准确率.
		for(int i = 0; i < m_NumModels; i++){			
			m_AccuracyRate[i] /= m_NumFolds;
			mircoF1[i] /= m_NumFolds;
			macroF1[i] /= m_NumFolds;

			System.out.println("--- Model " + i + "---");

			System.out.println(" accuracyRate is: " + m_AccuracyRate[i]);
			System.out.println(" microF1: " + mircoF1[i]);
			System.out.println(" macroF1: " + macroF1[i]);

			System.out.println();
		}
		
	}

	/**
	 * 根据混淆矩阵来计算 macroF1值.
	 * @param confuseMatrix
	 * @return
     */
	public double macroF1(int[][] confuseMatrix){

		double sumF1 = 0.0;
		for(int label = 0; label < confuseMatrix.length; ++label){
			double samples = 0.0;
			for(int j = 0; j < confuseMatrix[0].length; ++j)
				samples += confuseMatrix[label][j];
			double recall = samples == 0.0 ? 0.0 : confuseMatrix[label][label]/samples;

			double TPAndFP = 0.0;
			for(int j = 0; j < confuseMatrix.length; ++j)
				TPAndFP += confuseMatrix[j][label];

			double precision = TPAndFP == 0.0 ? 0.0 : confuseMatrix[label][label]/TPAndFP;

			double f1 = recall + precision == 0 ? 0 : 2*precision*recall/(precision + recall);
			sumF1 += f1;
		}
		return sumF1/confuseMatrix.length;
	}

	/**
	 * 根据混淆矩阵来计算出 microF1的值.
	 * @param confuseMatrix
	 * @return
     */
	public double microF1(int[][] confuseMatrix){

		double TP = 0.0;
		double FP = 0.0;
		double FN = 0.0;

		for(int label = 0; label < confuseMatrix.length; ++label){
			TP += confuseMatrix[label][label];

			for(int j = 0; j < confuseMatrix.length && j != label; ++j)
				FP += confuseMatrix[j][label];

			for(int j = 0; j < confuseMatrix[0].length && j != label; ++j)
				FN += confuseMatrix[label][j];
		}

		double precision = (TP + FP) == 0.0 ? 0.0 : TP/(TP + FP);
		double recall = (TP + FN) == 0.0 ? 0.0 : TP/(TP + FN);

		return recall + precision == 0.0 ? 0.0 : 2*precision*recall/(precision + recall);
	}
	
	public Instances removeAttrs(Instances data, String removeStrs) throws Exception{
		
		Remove remove = new Remove();
		remove.setAttributeIndices(removeStrs);
		remove.setInputFormat(data);
		
		data = Filter.useFilter(data, remove);
		
		if(data.classIndex() == -1)
			data.setClassIndex(data.numAttributes() - 1);
		return data;
	}

	public Instances standardNise(Instances data) throws Exception {

		Standardize standardize = new Standardize();
		standardize.setInputFormat(data);

		data = Filter.useFilter(data,standardize);

		if(data.classIndex() == -1)
			data.setClassIndex(data.numAttributes() - 1);
		return data;
	}

	public Instances getInstances(String dataFile) throws Exception{
		
		DataSource source = new DataSource(dataFile);
		Instances data = source.getDataSet();
		return data;
	}
}