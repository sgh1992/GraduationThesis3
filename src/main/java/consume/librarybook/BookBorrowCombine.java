package consume.librarybook;

import consume.tool.Tool;
import consume.tool.YearAndTerm;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.*;
import java.util.HashMap;

/**
 * Created by sghipr on 4/01/17.
 * 将图书借阅记录中的patronNum转换类studentID
 * 同时根据itemNum得出对应的bookType.
 * studentID,itemID,date,bookType,year,term
 */
public class BookBorrowCombine {

    private String bookBorrow;

    private String patronMapSID;

    private String bookTypeTransfer;

    private static String COMBINE = "bookBorrowRefactor.csv";

    class BookInfoRecord{

        private String itemId;

        private String title;

        private String author;

        private String pubInfo;

        private String callNum;

        private String type;

        public boolean parser(String record){
            String[] args = record.split("(#\\$#\\$)", -1);
            if(args.length < 6)
                return false;
            itemId = args[0].trim();
            title = args[1].trim();
            author = args[2].trim();
            pubInfo = args[3].trim();
            callNum = args[4].trim();
            type = args[5].trim();
            if(type.length() == 0)
                return false;
            return true;
        }

        public String getItemId(){
            return itemId;
        }

        public String getType(){
            return type;
        }

        public String toString(){
            return itemId + "," + title + "," + author + "," + pubInfo + "," + callNum + "," + type;
        }

    }

    class PatronStudentIDRecord{

        private String patronNum;

        private String studentID;

        private String userName;

        public boolean parser(String record){
            String[] args = record.split(",", -1);
            if(args.length < 3)
                return false;
            patronNum = args[0];
            studentID = args[1];
            userName = args[2];
            return true;
        }

        public String getPatronNum(){
            return patronNum;
        }

        public String getStudentID(){
            return studentID;
        }

        public String toString(){
            return patronNum + "," + studentID + "," + userName;
        }

    }

    class BookBorrowRecord {

        private String patronNum;

        private String bibNum;

        private String itemNum;

        private String date;

        private String ID; //记录号

        public BookBorrowRecord() {
        }

        public BookBorrowRecord(BookBorrowRecord o) {
            this.patronNum = o.patronNum;
            this.bibNum = o.bibNum;
            this.itemNum = o.itemNum;
            this.date = o.date;
            this.ID = o.ID;
        }

        public boolean parser(String record) {
            String[] args = record.split(",", -1);
            if (args.length < 5)
                return false;

            patronNum = args[0];
            bibNum = args[1];
            itemNum = args[2];
            date = args[3];
            ID = args[4];
            return true;
        }

        public String getPatronNum() {
            return patronNum;
        }

        public String getBibNum() {
            return bibNum;
        }

        public String getItemNum() {
            return itemNum;
        }

        public String getDate() {
            return date;
        }

        public String toString() {
            return patronNum + "," + bibNum + "," + itemNum + "," + date;
        }
    }



    public BookBorrowCombine(String bookBorrow, String patronMapSID, String bookTypeTransfer){

        this.bookBorrow = bookBorrow;
        this.patronMapSID = patronMapSID;
        this.bookTypeTransfer = bookTypeTransfer;
    }

    public File combine() throws IOException {

        File root = new File(bookBorrow);

        File result = new File(root.getParent(),COMBINE);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(root)));

        BufferedWriter writer = new BufferedWriter(new FileWriter(result));

        String str = null;

        //DateTimeFormatter format1 = DateTimeFormat.forPattern("yyyyMMdd");

        BookBorrowRecord parser = new BookBorrowRecord();

        HashMap<String,String> bookTypeMap = getBookTypeMap(bookTypeTransfer);
        HashMap<String,String> patronMap = getPatronMap(patronMapSID);

        StringBuilder resultBuilder = new StringBuilder();

        YearAndTerm yearAndTerm = new YearAndTerm();

        int missNums = 0;
        int totalNums = 0;

        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){
                String type = bookTypeMap.get(parser.getItemNum());
                String studentID = patronMap.get(parser.getPatronNum());
                if(type == null || studentID == null) {
                    if(type == null)
                        missNums++;
                    continue;
                }
                String time = parser.getDate().replace("-","");
                resultBuilder.append(studentID).append(",");
                resultBuilder.append(parser.getItemNum()).append(",");
                resultBuilder.append(time).append(",");
                resultBuilder.append(type).append(",");
                if(Tool.getYearTerm(studentID,time,yearAndTerm)){
                    resultBuilder.append(yearAndTerm.getYear()).append(",");
                    resultBuilder.append(yearAndTerm.getTerm());
                    writer.write(resultBuilder.toString());
                    writer.newLine();
                }
            }
            resultBuilder.setLength(0);
            totalNums++;
        }

        reader.close();
        writer.close();
        System.err.println("missing Nums: " + missNums);
        System.err.println("total Nums: " + totalNums);
        return result;
    }



    public HashMap<String,String> getBookTypeMap(String bookTypeTransfer) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(bookTypeTransfer)));

        BookInfoRecord parser = new BookInfoRecord();

        HashMap<String,String> bookTypeMap = new HashMap<>();

        String str = null;

        while ((str = reader.readLine()) != null){
            if(parser.parser(str.trim())){
                if(!bookTypeMap.containsKey(parser.getItemId()))
                    bookTypeMap.put(parser.getItemId(),parser.getType());
            }
        }

        reader.close();
        return bookTypeMap;
    }

    public HashMap<String,String> getPatronMap(String patronMapSID) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(patronMapSID)));

        PatronStudentIDRecord parser = new PatronStudentIDRecord();

        String str = null;

        HashMap<String,String> patronMap = new HashMap<>();

        while ((str = reader.readLine()) != null){
            if(parser.parser(str)){
                patronMap.put(parser.getPatronNum(),parser.getStudentID());
            }
        }
        reader.close();
        return patronMap;
    }

    public static void main(String[] args) throws IOException {


        String bookborrow = "D:\\GraduationThesis\\bookborrow.csv";
        String bookInfo = "D:\\GraduationThesis\\chinaStandardBookTransfer.csv";
        String librarybookMap = "D:\\GraduationThesis\\librarybook_sidmap.csv";
        BookBorrowCombine borrowCombine = new BookBorrowCombine(bookborrow,librarybookMap,bookInfo);
        borrowCombine.combine();
    }
}
