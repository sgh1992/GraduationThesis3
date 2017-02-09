package consume.librarybook;

import consume.dataClean.SortCleanData;

import java.io.File;
import java.io.IOException;

/**
 * Created by sghipr on 2017/1/5.
 * 数据排序.
 */
public class LibraryBookSorted {


    public File bookBorrowSorted(String rootFile, String tempDir) throws IOException {

        SortCleanData sortCleanData = new SortCleanData(rootFile, tempDir, new BookBorrowRecordRefactor());
        return sortCleanData.sort();
    }

    public static void main(String[] args) throws IOException {


        String rootFile = "D:\\GraduationThesis\\bookBorrowRefactor.csv";
        String tempDir = "D:/GraduationThesis/libraryTemp";
        LibraryBookSorted bookSorted = new LibraryBookSorted();
        bookSorted.bookBorrowSorted(rootFile,tempDir);
    }

}
