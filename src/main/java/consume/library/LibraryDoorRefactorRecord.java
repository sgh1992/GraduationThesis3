package consume.library;

import consume.tool.SortBase;

/**
 * Created by sghipr on 31/12/16.
 */
public class LibraryDoorRefactorRecord implements SortBase{

    private String studentID;

    private String name;

    private String status;

    private String passTime;

    private String college;

    private String year;

    private String term;

    @Override
    public boolean parser(String records) {

        String[] args = records.split(",", -1);
        if(args.length < 7)
            return false;


        return false;
    }

    @Override
    public int compareTo(SortBase o) {
        return 0;
    }
}
