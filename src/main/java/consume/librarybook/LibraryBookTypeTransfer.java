package consume.librarybook;

import consume.databases.DB;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by sghipr on 2017/1/4.
 * 根据每册书的callNum字段，用中图法来获得对应的图书类别.
 */
public class LibraryBookTypeTransfer {

    private static String chinaBookClassic = "D:\\GraduationThesis\\chinaStandardBookClassic.csv";

    private static String transferResult = "chinaStandardBookTransfer.csv";

    private static String splitLetter = "#$#$";

    public LibraryBookTypeTransfer(){
    }

    public File typeTransfer() throws IOException, SQLException {
        return typeTransfer(chinaBookClassic);
    }

    public File typeTransfer(String chinaBookClassic) throws SQLException, IOException {

        File root = new File(chinaBookClassic);

        File result = new File(root.getParent(),transferResult);

        BufferedWriter writer = new BufferedWriter(new FileWriter(result));

        DB db = new DB();

        String sqlString = "select itemNum, title, author, pubInfo, callNum from librarybookinfo";

        ResultSet rSet = db.executeQuery(sqlString);

        HashMap<String,String> typeMap = getBookTypeMap(chinaBookClassic);

        String longText = getLongName(chinaBookClassic);

        int missNums = 0;
        int totalNums = 0;

        StringBuilder builder = new StringBuilder();

        while (rSet.next()){

            String itemNum = rSet.getString("itemNum");
            String title = rSet.getString("title");
            String author = rSet.getString("author");
            String pubInfo = rSet.getString("pubInfo");
            String callNum = rSet.getString("callNum");

            totalNums++;

            String type = getType(callNum,longText,typeMap);
            if(type == null){
                missNums++;
                continue;
            }

            builder.append(itemNum).append(splitLetter);
            builder.append(title).append(splitLetter);
            builder.append(author).append(splitLetter);
            builder.append(pubInfo).append(splitLetter);
            builder.append(callNum).append(splitLetter);
            builder.append(type);

            writer.write(builder.toString());
            writer.newLine();

            builder.setLength(0);
        }

        rSet.close();
        db.close();
        writer.close();

        System.err.println("missNums: " + missNums);
        System.err.println("totalNums: " + totalNums);

        return result;
    }

    public String getType(String callNum, String longText, HashMap<String,String> typeMap){

        String typeNum = null;

        if(callNum.trim().length() >= 3){
            String cur = callNum.substring(0,3);
            if(longText.indexOf(cur) != -1){
                typeNum = cur;
            }
            else{
                cur = callNum.substring(0,2);
                if(longText.indexOf(cur) != -1)
                    typeNum = cur;
            }
        }
        else if(callNum.trim().length() == 2){
            if(longText.indexOf(callNum.trim()) != -1)
                typeNum = callNum.trim();
        }

        if(typeNum == null)
            return null;

        else
            return typeMap.get(typeNum);
    }



    public String getLongName(String chinaBookClassic) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(chinaBookClassic)));
        String str = null;

        StringBuilder longBuilder = new StringBuilder();

        while ((str = reader.readLine()) != null){

            String[] args = str.split(",", -1);
            String type = args[0];
            longBuilder.append(type).append(",");
        }

        reader.close();
        return longBuilder.substring(0,longBuilder.length() - 1).toString();
    }

    public HashMap<String,String> getBookTypeMap(String chinaBookClassic) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(chinaBookClassic)));
        String str = null;

        HashMap<String,String> types = new HashMap<>();

        while ((str = reader.readLine()) != null){

            String[] args = str.split(",", -1);
            String callNum = args[0];
            String type = args[1];

            if(!types.containsKey(callNum))
                types.put(callNum,type);
        }

        reader.close();
        return types;
    }

    public static void main(String[] args) throws IOException, SQLException {

        LibraryBookTypeTransfer libraryBookTypeTransfer = new LibraryBookTypeTransfer();
        File result = libraryBookTypeTransfer.typeTransfer();
    }
}
