package consume.dataClean;

import consume.tool.ConsumeRecord;
import consume.tool.SortBase;
import consume.tool.Tool;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by sghipr on 2016/12/4.
 * 针对进行初步清洗过后的消费流水数据进行按学号，消费时间进行排序.
 *
 *主要思想:小文件先排好序，然后再针对各个已经排好序的小文件利用优先队列进行排序.
 */
public class SortCleanData {

    class SortRecord implements Comparable<SortRecord>{

        private int index;
        private SortBase sortBase;

        public SortRecord(SortBase sortBase, int index){
            this.index = index;
            this.sortBase = sortBase;
        }

        public int hashCode(){
            return sortBase.hashCode() + index * 127;
        }

        public int getIndex(){
            return index;
        }

        public SortBase getSortBase(){
            return sortBase;
        }

        @Override
        public int compareTo(SortRecord o) {
            return sortBase.compareTo(o.sortBase);
        }
    }

    private String rootFile;

    private String tempDir;

    private SortBase parser;

    private static String SORTED = "sorted.csv";

    private static String removeDeuplicate = "removeDeuplicate.csv";//去重.

    public SortCleanData(String rootFile, String tempDir, SortBase parser){
        this.rootFile = rootFile;
        this.tempDir = tempDir;
        this.parser = parser;
    }

    public File sort() throws IOException {
        return sort(rootFile,tempDir,parser);
    }

    public File sortAndDup() throws IOException {
        File sortedFile = sort();
        return removeDup(sortedFile);
    }

    /**
     * 排序.
     * @param rootFile
     * @param tempDir
     * @return
     * @throws IOException
     */
    public File sort(String rootFile, String tempDir,SortBase parser) throws IOException {

        List<File> splitLists = Tool.splitFile(rootFile,tempDir,parser); //已经排好序了.
        PriorityQueue<SortRecord> priorityQueue = new PriorityQueue<>();

        List<BufferedReader> readerList = new ArrayList<>();

        BufferedReader reader = null;

        File root = new File(rootFile);

        File target = new File(root.getParent(),root.getName().substring(0,root.getName().indexOf(".")) + "_" + SORTED);

        BufferedWriter writer = new BufferedWriter(new FileWriter(target));

        for(File file : splitLists){
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            readerList.add(reader);
        }

        //ConsumeRecord parser = new ConsumeRecord();
        String record = null;
        for(int i = 0; i < readerList.size(); ++i){
            if((record = readerList.get(i).readLine()) != null){
                if(parser.parser(record))
                    priorityQueue.add(new SortRecord(parser.create(parser),i));
            }
        }
        while (!priorityQueue.isEmpty()){
            SortRecord sortRecord = priorityQueue.poll();
            writer.write(sortRecord.getSortBase().toString());
            writer.newLine();

            int index = sortRecord.getIndex();
            record = readerList.get(index).readLine();
            if(record == null){
                readerList.get(index).close();
            }
            else
                if(parser.parser(record))
                    priorityQueue.add(new SortRecord(parser.create(parser),index));
        }
        writer.close();

        Tool.deleteFiles(splitLists);//删除中间生成的文件.

        return target;
    }

    /**
     * 去重
     * @param sortedFile
     * @return
     * @throws IOException
     */
    public File removeDup(File sortedFile) throws IOException {

        File result = new File(sortedFile.getParent(),sortedFile.getName().substring(0,sortedFile.getName().indexOf(".")) + "_" + removeDeuplicate);
        BufferedWriter writer = new BufferedWriter(new FileWriter(result));

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sortedFile)));
        String str = null;
        ConsumeRecord parser = new ConsumeRecord();
        ConsumeRecord before = null;
        while ((str = reader.readLine()) != null){
            if(parser.parser(str)) {
                if (!(before != null && before.getStudentID().equals(parser.getStudentID()) &&
                        before.getTime().equals(parser.getTime()) && before.getCardNo().equals(parser.getCardNo()))) {

                    if(before != null){
                        writer.write(before.toString());
                        writer.newLine();
                    }
                }

                if(before == null)
                    before = new ConsumeRecord();
                before.update(parser);
            }
        }
        writer.write(parser.toString());
        writer.newLine();

        reader.close();
        writer.close();
        return result;
    }

    public static void main(String[] args) throws IOException {

        String file = "D:\\GraduationThesis\\consume_clean_step1.csv";
        String tempDir = "D:/GraduationThesis/consumeTemp";
        SortCleanData sortConsumeClean = new SortCleanData(file,tempDir,new ConsumeRecord());
        //sortConsumeClean.sort();
        String sortedFile = "D:/GraduationThesis/consume_clean_step1_sorted.csv";
        sortConsumeClean.removeDup(new File(sortedFile));
    }
}
