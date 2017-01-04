package consume.librarybook;

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
            String[] args = record.split("#$#$", -1);
            if(args.length < 6)
                return false;
            itemId = args[0];
            title = args[1];
            author = args[2];
            pubInfo = args[3];
            callNum = args[4];
            type = args[5];
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

    public BookBorrowCombine(String bookBorrow, String patronMapSID, String bookTypeTransfer){

        this.bookBorrow = bookBorrow;
        this.patronMapSID = patronMapSID;
        this.bookTypeTransfer = bookTypeTransfer;
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





}
