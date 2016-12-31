package consume.library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sghipr on 2016/12/31.
 */
public class LibraryDoorCombine {

    private String libraryDoor2009_2012 = "D:\\GraduationThesis\\librarydoor2009_2012.txt";

    private String libraryDoor2013_2014 = "D:\\GraduationThesis\\librarydoor2013_2014.txt";

    private String libraryDoor2009_2014 = "librarydoor2009_2014_refactor.csv";

    public File combine() throws IOException {
        return combine(libraryDoor2009_2012,libraryDoor2013_2014);
    }

    public File combine(String libraryDoor2009_2012, String libraryDoor2013_2014) throws IOException {

        File door2009_2012 = new File(libraryDoor2009_2012);

        File door2013_2014 = new File(libraryDoor2013_2014);

        File combineFile = new File(door2009_2012.getParent(),libraryDoor2009_2014);

        BufferedReader reader = null;
        BufferedWriter writer = new BufferedWriter(new FileWriter(combineFile));

        List<File> fileList = new ArrayList<>();

        fileList.add(door2009_2012);
        fileList.add(door2013_2014);
        String charset = "gb2312";

        for(File file : fileList){
            if(file.getName().equals("librarydoor2013_2014.txt"))
                charset = "UTF-8";
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),charset));
            String str = null;
            while ((str = reader.readLine()) != null){
                writer.write(str);
                writer.newLine();
            }
            reader.close();
        }

        writer.close();
        return combineFile;
    }

    public static void main(String[] args) throws IOException {

        LibraryDoorCombine doorCombine = new LibraryDoorCombine();
        doorCombine.combine();
    }


}
