package consume.library;

import consume.analysize.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by sghipr on 2017/1/1.
 */
public class LibraryDoorRegularity {

    public static String LibraryStyle = "librarydoor";

    public File getLDRegularityFile(String libraryDoorDataClean, Cycle cycle) throws IOException {

        StudentRegularity studentRegularity = new StudentRegularity(libraryDoorDataClean, cycle, new LibraryDoorWithSIDRecord(), LibraryStyle);
        return studentRegularity.regularity();
    }

    /**
     * Test
     * @param args
     */
    public static void main(String[] args) throws IOException {

        LibraryDoorRegularity ldRegularity = new LibraryDoorRegularity();
        String libraryDoorDataClean = "D:\\GraduationThesis\\librarydoorWithStudentID_sorted.csv";

        File result = ldRegularity.getLDRegularityFile(libraryDoorDataClean, new WeekCycle());

    }


}
