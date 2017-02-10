package models.model;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;

public class ClassifierTest {
	
	private String m_removeStr = null;

	private String dataFile;

	public ClassifierTest(String m_removeStr, String dataFile){
		this.m_removeStr = m_removeStr;
		this.dataFile = dataFile;
	}
	
	public void modelRun() throws Exception{
		
		String removeStr = "";

		int numModels = 4;
		
		ModelMethod method = new ModelMethod(numModels);
		
		//Model 0
		RandomForest randomForest = new RandomForest();
		randomForest.setMaxDepth(5);
		randomForest.setNumTrees(30);
		randomForest.setNumFeatures(9);
		method.setModel(randomForest, 0, removeStr);

		//Model 1
		REPTree repTree = new REPTree();
		method.setModel(repTree, 1, removeStr);

		//Model 2
		AdaBoostM1 adaBoostM1 = new AdaBoostM1();
		adaBoostM1.setClassifier(new DecisionStump());
		method.setModel(adaBoostM1,2,removeStr);

		//Model 3
		SMO smo = new SMO();
		method.setModel(smo,3,removeStr);

		//Model 4
//		MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
//		method.setModel(multilayerPerceptron,4,removeStr);

//		//Model 3
//		Logistic logistic = new Logistic();
//		logistic.setMaxIts(1000);
//		method.setModel(logistic,5,removeStr);

		
		method.CVModelSelection(dataFile);
		System.out.println("CVSelection is Over!");
		
	}

	public static void main(String[] args) throws Exception {

		String removeStr = "1";
		String dataFile = "D:\\GraduationThesis\\combineFeatures1_2_3_4_addWork.csv";

		ClassifierTest classifierTest = new ClassifierTest(removeStr,dataFile);
		classifierTest.modelRun();
	}
}