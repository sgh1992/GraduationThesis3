package consume.dataClean;

import consume.tool.ConsumeRecord;
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
public class SortConsumeClean {

    class SortRecord implements Comparable<SortRecord>{

        private int index;

        private ConsumeRecord consumeRecord;

        public SortRecord(ConsumeRecord consumeRecord, int index){
            this.index = index;
            this.consumeRecord = consumeRecord;
        }

        public int hashCode(){
            return consumeRecord.hashCode() + index * 127;
        }

        public int getIndex(){
            return index;
        }

        public ConsumeRecord getConsumeRecord(){
            return consumeRecord;
        }

        @Override
        public int compareTo(SortRecord o) {
            return consumeRecord.compareTo(o.consumeRecord);
        }
    }

    private String rootFile;

    private String tempDir;

    private static String SORTED = "sorted.csv";

    public SortConsumeClean(String rootFile, String tempDir){

        this.rootFile = rootFile;
        this.tempDir = tempDir;
    }

    public File sort() throws IOException {
        return sort(rootFile,tempDir);
    }

    public File sort(String rootFile, String tempDir) throws IOException {

        List<File> splitLists = Tool.splitFile(rootFile,tempDir); //已经排好序了.
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

        ConsumeRecord parser = new ConsumeRecord();
        String record = null;
        for(int i = 0; i < readerList.size(); ++i){
            if((record = readerList.get(i).readLine()) != null){
                if(parser.parser(record))
                    priorityQueue.add(new SortRecord(new ConsumeRecord(parser.getStudentID(),parser.getType(),parser.getPlace(),
                            parser.getCardNo(),parser.getTime(),parser.getAmount(),parser.getBalance(),parser.getTerm(),parser.getYear()),i));
            }
        }
        while (!priorityQueue.isEmpty()){
            SortRecord sortRecord = priorityQueue.poll();
            writer.write(sortRecord.getConsumeRecord().toString());
            writer.newLine();

            int index = sortRecord.getIndex();
            record = readerList.get(index).readLine();
            if(record == null){
                readerList.get(index).close();
            }
            else
                if(parser.parser(record))
                    priorityQueue.add(new SortRecord(new ConsumeRecord(parser.getStudentID(),parser.getType(),parser.getPlace(),
                            parser.getCardNo(),parser.getTime(),parser.getAmount(),parser.getBalance(),parser.getTerm(),parser.getYear()),index));
        }
        writer.close();

        Tool.deleteFiles(splitLists);//删除中间生成的文件.

        return target;
    }

    public static void main(String[] args) throws IOException {

        String file = "D:\\GraduationThesis\\consume_clean_step1.csv";
        String tempDir = "D:/GraduationThesis/consumeTemp";
        SortConsumeClean sortConsumeClean = new SortConsumeClean(file,tempDir);
        sortConsumeClean.sort();

    }
}
