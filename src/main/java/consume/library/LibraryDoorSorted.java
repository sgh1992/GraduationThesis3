package consume.library;

import consume.dataClean.SortCleanData;

import java.io.File;
import java.io.IOException;

/**
 * Created by sghipr on 2017/1/1.
 */
public class LibraryDoorSorted {

    public File sort(String rootFile,String tempDir, LibraryDoorWithSIDRecord parser) throws IOException {

        SortCleanData sortCleanData = new SortCleanData(rootFile, tempDir, parser);
        return sortCleanData.sort();
    }

    public static void main(String[] args) throws IOException {

        String rootFile = "D:\\GraduationThesis\\librarydoorWithStudentID.csv";
        String tempFile = "D:\\GraduationThesis\\libraryTemp";

        LibraryDoorSorted doorSorted = new LibraryDoorSorted();
        doorSorted.sort(rootFile,tempFile,new LibraryDoorWithSIDRecord());
    }



}
