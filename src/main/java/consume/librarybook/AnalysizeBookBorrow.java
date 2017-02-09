package consume.librarybook;

import java.io.*;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by sghipr on 2017/1/5.
 * 统计每个学生在每个学期中，在各类图书中借阅的次数.
 */
public class AnalysizeBookBorrow {

    class  BookBorrowTerms{

        private String studentID;

        private int term;

        private HashMap<String,Integer> counts;

        public BookBorrowTerms(String studentID, int term){
            this.studentID = studentID;
            this.term = term;
            counts = new HashMap<>();
        }

        public String getStudentID(){
            return studentID;
        }

        public int getTerm(){
            return term;
        }

        public boolean contains(String studentID, int term){
            return this.studentID.equals(studentID) && this.term == term;
        }

        public void update(String bookType){
            int count = counts.containsKey(bookType) ? counts.get(bookType) : 0;
            counts.put(bookType,count + 1);
        }

        public String toString(TreeSet<String> bookTypeSet){

            StringBuilder result = new StringBuilder();
            result.append(studentID).append(",");
            result.append(term).append(",");
            result.append("librarybook").append(",");
            for(String bookType : bookTypeSet){
                int count = counts.containsKey(bookType) ? counts.get(bookType) : 0;
                result.append(count).append(",");
            }

            return result.substring(0,result.length() - 1).toString();
        }
    }

    /**
     * 中图分类法.
     */
    class ChinaBookType{

        private String callNums;

        private String type;

        public boolean parser(String record){
            String[] args = record.split(",", -1);
            if(args.length < 2)
                return false;
            this.callNums = args[0];
            this.type = args[1];
            return true;
        }

        public String getCallNums(){
            return callNums;
        }

        public String getType(){
            return type;
        }

        public String toString(){
            return callNums + "," + type;
        }
    }



    private String bookborrows;

    private String bookTypes;

    private static String bookBorrowAnalysize = "bookBorrowAnalysize.csv";

    public AnalysizeBookBorrow(String bookborrows, String bookTypes){
        this.bookborrows = bookborrows;
        this.bookTypes = bookTypes;
    }

    public File analysize() throws IOException {

        TreeSet<String> bookTypeSet = getBookTypeSet(bookTypes);

        File root = new File(bookborrows);

        File result = new File(root.getParent(),bookBorrowAnalysize);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(root)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(result));

        BookBorrowRecordRefactor parser = new BookBorrowRecordRefactor();

        BookBorrowTerms termObject = null;

        String str = null;
        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){
                if(termObject == null){
                    termObject = new BookBorrowTerms(parser.getStudentID(),parser.getTerm());
                }
                if(!termObject.contains(parser.getStudentID(),parser.getTerm())){
                    writer.write(termObject.toString(bookTypeSet));
                    writer.newLine();

                    termObject = new BookBorrowTerms(parser.getStudentID(),parser.getTerm());
                }
                termObject.update(parser.getType());
            }
        }

        writer.write(termObject.toString(bookTypeSet));
        writer.newLine();
        reader.close();
        writer.close();
        return result;
    }

    public TreeSet<String> getBookTypeSet(String bookTypes) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(bookTypes)));
        String str = null;
        TreeSet<String> bookTypeSet = new TreeSet<>();

        ChinaBookType parser = new ChinaBookType();

        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){
                bookTypeSet.add(parser.getType());
            }
        }

        reader.close();
        return bookTypeSet;
    }

    public static void main(String[] args) throws IOException {

        String bookBorrows = "D:\\GraduationThesis\\bookBorrowRefactor_sorted.csv";
        String bookTypes = "D:\\GraduationThesis\\chinaStandardBookClassic.csv";

        AnalysizeBookBorrow analysizeBookBorrow = new AnalysizeBookBorrow(bookBorrows,bookTypes);
        File result = analysizeBookBorrow.analysize();
    }
}
