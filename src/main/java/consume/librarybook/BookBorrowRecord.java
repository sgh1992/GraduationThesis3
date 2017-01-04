package consume.librarybook;

import consume.tool.SortBase;

/**
 * Created by sghipr on 4/01/17.
 */
public class BookBorrowRecord implements SortBase{

    private String patronNum;

    private String bibNum;

    private String itemNum;

    private String date;

    private String ID; //记录号

    public BookBorrowRecord(){}

    public BookBorrowRecord(BookBorrowRecord o){
        this.patronNum = o.patronNum;
        this.bibNum = o.bibNum;
        this.itemNum = o.itemNum;
        this.date = o.date;
        this.ID = o.ID;
    }

    @Override
    public boolean parser(String records) {

        String[] args = records.split(",", -1);
        if(args.length < 4)
            return false;

        return false;
    }

    @Override
    public SortBase create(SortBase o) {
        return null;
    }

    @Override
    public int getTerm() {
        return 0;
    }

    @Override
    public String getStudentID() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getTime() {
        return null;
    }

    @Override
    public int compareTo(SortBase o) {
        return 0;
    }
}
