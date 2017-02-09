package consume.tool;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.*;
import java.util.*;

/**
 * Created by sghipr on 2016/12/2.
 */
public class Tool {

    private static String consumePlace = "D:\\GraduationThesis\\consumePlace.txt";

    private static String graduateworkinfo = "D:\\GraduationThesis\\graduateworkinfo_safety.csv";

    private static DateTimeFormatter format1 = DateTimeFormat.forPattern("yyyyMMdd");

    private static DateTimeFormatter format2 = DateTimeFormat.forPattern("yyyyMMdd HHmmss");

    private static Locale locale =  new Locale("en");

    private static long splitSize = 10000000;

    public static HashMap<String,ConsumeNameAndType> getSimplePlace() throws IOException {

        HashMap<String,ConsumeNameAndType> simpleMap = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(consumePlace)));
        String str = null;

        while ((str = reader.readLine()) != null){

            if(str.startsWith("place"))
                continue;
            simpleMap.put(str.trim(),placeName(str.trim()));
        }
        reader.close();
        return simpleMap;
    }


    public static ConsumeNameAndType placeName(String place)  {

        String name = null;
        String type = null;//类型

        if(place.contains("水果店")) {
            name = "水果店";
            type = "fruit";
        }

        else if(place.contains("超市") || place.contains("校园二分店") || place.contains("硕士20栋校园6店")) {
            name = "超市";
            type = "supermarket";
        }

        else if(place.contains("食堂") || place.contains("二食")){
            if(place.startsWith("一食堂1F"))
                name = "一食堂1F";

            else if(place.startsWith("一食堂2F"))
                name = "一食堂2F";

            else if(place.startsWith("一食堂3F"))
                name = "一食堂3F";

            else if(place.startsWith("二食堂1F"))
                name = "二食堂1F";

            else if(place.startsWith("二食堂2F") || place.contains("二食"))
                name = "二食堂2F";

            else if(place.startsWith("二食堂3F"))
                name = "二食堂3F";

            type = "mess";
        }

        else if(place.contains("万友餐厅")) {
            name = "万友餐厅";
            type = "mess";
        }
        else if(place.contains("教职工餐厅")) {
            name = "教职工餐厅";
            type = "mess";
        }

        else if(place.contains("桂苑餐厅")) {
            name = "桂苑餐厅";
            type = "mess";
        }

        else if(place.contains("阳光餐厅")) {
            name = "阳光餐厅";
            type = "mess";
        }

        else if(place.contains("风华餐厅")) {
            name = "风华餐厅";
            type = "mess";
        }

        else if(place.contains("上海周记")){
            name = "上海周记";
            type = "snack";
        }

        else if(place.contains("百味屋")){
            name = "百味屋";
            type = "snack";
        }

        else if(place.contains("塞文斯休闲水吧")){
            name = "塞文斯休闲水吧";
            type = "snack";
        }

        else if(place.contains("汇多IP测试")){
            name = "汇多";
            type = "snack";
        }

        else if(place.contains("公交车载")){
            name = "校内公交";
            type = "driver";
        }

        else if(place.contains("成电班车") || place.contains("成电车载")){
            name = "校外公交";
            type = "driver";
        }

        else if(place.contains("中间设备")){
            name = "中间设备";
            type = "others";
        }

        else if(place.contains("主楼档案馆")){
            name = "主楼档案馆";
            type = "others";
        }

        else if(place.contains("教务处读卡器")){
            name = "教务处读卡器";
            type = "others";
        }

        else if(place.contains("医院")){
            name = "医院";
            type = "hospital";
        }

        else if(place.contains("卡库不平处理设备")){
            name = "卡库不平处理设备";
            type = "others";
        }

        else if(place.contains("游泳池")){
            name = "游戏池";
            type = "sports";
        }

        else if(place.contains("清水河物管风雨球场")){
            name = "球场";
            type = "sports";
        }

        else if(place.contains("电影院")){
            name = "电影院";
            type = "movie";
        }

        else if(place.contains("加油站")){
            name = "加油站";
            type = "oil";
        }

        else if(place.contains("文印")){
            name = "文印";
            type = "print";
        }

        else if(place.contains("图书馆")){

            if(place.contains("图书馆1F水吧")){
                name = "图书馆1F水吧";
                type = "snack";
            }

            else if(place.contains("图书馆1F电子阅览室")){
                name = "图书馆1F电子阅览室";
                type = "reading";
            }

            else if(place.contains("图书馆2F") || place.contains("图书馆POS机V23测试") || place.contains("沙河校区图书馆罚款")){
                name = "图书馆交费";
                type = "borrowing";
            }

            else if(place.contains("图书馆放映厅服务台")){
                name = "图书馆放映厅";
                type = "movie";
            }

            else if(place.contains("图书馆教材科")){
                name = "图书馆教材科";
                type = "books";
            }

            else if(place.contains("图书馆自助复印机")){
                name = "图书馆自助复印机";
                type = "print";
            }
        }

        else if(place.contains("场馆中心")){
            name = "体育馆休闲";
            type = "snack";
        }

        else if(place.contains("基础教学楼")){
            if(place.contains("基础教学楼A"))
                name = "基础教学楼A";

            else if(place.contains("基础教学楼B"))
                name = "基础教学楼B";

            else if(place.contains("基础教学楼C"))
                name = "基础教学楼C";

            type = "waterInLearning";
        }

        else if(place.contains("黑名单下载专用")){
            name = "黑名单下载专用";
            type = "snack";
        }

        else if(place.startsWith("本") || place.startsWith("欣村") || place.startsWith("沙河校区") || place.contains("5F淋064#000A9CBF")
                || place.startsWith("留学生") || place.startsWith("硕") || place.startsWith("博3")|| place.contains("沙河水站")){
            if(place.contains("淋") || place.contains("浴") ){
                name = "淋浴";
                type = "bathing";
            }
            else if(place.contains("开") || place.contains("沙河水站")){
                name = "宿舍开水";
                type = "water";
            }

            else if(place.contains("洗")){
                name = "洗衣服";
                type = "cleaning";
            }
        }

        else if(place.equals("null")){
            name = "missing";
            type = "missing";
        }

        else{
            name = "others";
            type = "others";
            System.err.println(place + " do not fit!");
        }

        return new ConsumeNameAndType(name,type);
    }


    public static boolean getYearTerm(String sid,String time,YearAndTerm yearAndTerm){

        if(sid.trim().length() < 8 || time.trim().length() != 8)
            return false;

        String year = null;
        int term = 0;
        String startTime = null;

        DateTime end = null;
        try {
            end = format1.parseDateTime(time);
        }catch (IllegalFieldValueException e){
            System.err.println(time);
            return false;
        }

        if(sid.startsWith("29") || sid.startsWith("2009")){
            startTime = "20090901";
            year = "2009";
        }

        else if(sid.startsWith("2010")){
            startTime = "20100901";
            year = "2010";
        }

        else if(sid.startsWith("2011")){
            startTime = "20110901";
            year = "2011";
        }

        else if(sid.startsWith("2012")){
            startTime = "20120901";
            year = "2012";
        }

        else if(sid.startsWith("2013")){
            startTime = "20130901";
            year = "2013";
        }

        else if(sid.startsWith("2014")){
            startTime = "20140901";
            year = "2014";
        }

        else if(sid.startsWith("2015")){
            startTime = "20150901";
            year = "2015";
        }

        else if(sid.startsWith("2016")){
            startTime="20160901";
            year = "2016";
        }

        else{
            return false;
        }

        DateTime start = format1.parseDateTime(startTime);
        int months = Months.monthsBetween(start,end).getMonths();
        term = months/6 + 1;
        if(term <= 0 || term > 8)
            return false;

        yearAndTerm.setYear(year);
        yearAndTerm.setTerm(term);

        return true;
    }

//    public static boolean getYearTerm(String sid,String time,YearAndTerm yearAndTerm,DateTimeFormatter format1){
//
//        if(sid.trim().length() < 8)
//            return false;
//
//        String year = null;
//        int term = 0;
//        String startTime = null;
//
//        DateTime end = null;
//        try {
//            end = format1.parseDateTime(time);
//        }catch (IllegalFieldValueException e){
//            System.err.println(time);
//            return false;
//        }
//
//        if(sid.startsWith("29") || sid.startsWith("2009")){
//            startTime = "20090901";
//            year = "2009";
//        }
//
//        else if(sid.startsWith("2010")){
//            startTime = "20100901";
//            year = "2010";
//        }
//
//        else if(sid.startsWith("2011")){
//            startTime = "20110901";
//            year = "2011";
//        }
//
//        else if(sid.startsWith("2012")){
//            startTime = "20120901";
//            year = "2012";
//        }
//
//        else if(sid.startsWith("2013")){
//            startTime = "20130901";
//            year = "2013";
//        }
//
//        else if(sid.startsWith("2014")){
//            startTime = "20140901";
//            year = "2014";
//        }
//
//        else if(sid.startsWith("2015")){
//            startTime = "20150901";
//            year = "2015";
//        }
//
//        else if(sid.startsWith("2016")){
//            startTime="20160901";
//            year = "2016";
//        }
//
//        else{
//            return false;
//        }
//
//        try {
//            DateTime start = format1.parseDateTime(startTime);
//            int months = Months.monthsBetween(start,end).getMonths();
//            term = months/6 + 1;
//            if(term <= 0)
//                return false;
//        }catch (Exception e){
//            return false;
//        }
//
//
//        yearAndTerm.setYear(year);
//        yearAndTerm.setTerm(term);
//
//        return true;
//    }

    public static String getDayOfWeek(String time){

        return format1.parseDateTime(time).dayOfWeek().getAsText(locale);
    }

    public static List<File> splitFile(String file,String targetDir, SortBase parser) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String str = null;

        List<File> splitList = new ArrayList<>();

        int fileNum = 0;

        File targetRoot = new File(targetDir);
        if(targetRoot.exists()){
            for(File file1 : targetRoot.listFiles())
                file1.delete();
        }

        File targetFile = new File(targetDir,fileNum + ".csv");
        splitList.add(targetFile);

        if(!targetFile.getParentFile().exists())
            targetFile.getParentFile().mkdirs();

        BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
        List<SortBase> recordList = new ArrayList<>();
        //ConsumeRecord parser = new ConsumeRecord();

        long num = 1;
        while ((str = reader.readLine()) != null){

            if(num % splitSize == 0){
                Collections.sort(recordList);//每次分裂小文件的时候都需要将之排好序
                for(SortBase record : recordList){
                    writer.write(record.toString());
                    writer.newLine();
                }
                fileNum++;
                writer.close();
                targetFile = new File(targetDir,fileNum + ".csv");
                splitList.add(targetFile);
                writer = new BufferedWriter(new FileWriter(targetFile));

                //clean
                recordList.clear();
                num = 1;
            }

            if(parser.parser(str)){
                recordList.add(parser.create(parser));
                num++;
            }
        }

        //注意最后一次的数据要写进去
        Collections.sort(recordList);//每次分裂小文件的时候都需要将之排好序
        for(SortBase record : recordList){
            writer.write(record.toString());
            writer.newLine();
        }
        writer.close();
        recordList.clear();

        reader.close();

        System.err.println("Split File Over!");

        return splitList;
    }

    public static void deleteFiles(List<File> fileList){

        for(File file : fileList)
            file.delete();
    }

    public static int hoursBetween(String start, String end){
        DateTime startTime = format2.parseDateTime(start);
        DateTime endTime = format2.parseDateTime(end);
        return Hours.hoursBetween(startTime,endTime).getHours();
    }

    public static HashMap<String,String> getStudentWork() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(graduateworkinfo)));

        String str = null;

        GraduateWorkParser parser = new GraduateWorkParser();

        HashMap<String,String> workMap = new HashMap<>();

        while ((str = reader.readLine()) != null){
            if(parser.parser(str)) {
                String work = parser.getWork().equals("保研") ? "录研" : parser.getWork();
                if(work.equals("出国出境"))
                    work = "出国深造";
                workMap.put(parser.getStudentID(), work);
            }
        }
        return workMap;
    }

    public static File addWork(String file) throws IOException {

        File root = new File(file);

        File target = new File(root.getParent(),root.getName().substring(0,root.getName().indexOf(".")) + "_addWork.csv");

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(root)));

        BufferedWriter writer = new BufferedWriter(new FileWriter(target));

        String str = null;

        HashMap<String,String> workMap = getStudentWork();


        while ((str = reader.readLine()) != null){
            String studentID = str.split(",", -1)[0];
            if(studentID.startsWith("studentID")){
                writer.write(str + "," + "work");
                writer.newLine();
                continue;
            }
            if(workMap.containsKey(studentID)){
                writer.write(str + "," + workMap.get(studentID));
                writer.newLine();
            }
        }
        reader.close();
        writer.close();
        return target;
    }

    public static boolean isSampleDay(String time1, String time2){

        String[] args1 = time1.split(" ", -1);
        String[] args2 = time2.split(" ", -1);
        if(args1.length < 2 || args2.length < 2)
            return false;
        else
            return args1[0].equals(args2[0]);
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) throws Exception {

//        HashMap<String,ConsumeNameAndType> consumeMap = getSimplePlace();
//        for(Map.Entry<String,ConsumeNameAndType> entry : consumeMap.entrySet()){
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }

//        YearAndTerm yearAndTerm = new YearAndTerm();
//        getYearTerm("2016080901", "20170901",yearAndTerm);
//        System.out.println(yearAndTerm);

        String file = "D:\\GraduationThesis\\consume_clean_step1.csv";
        String targetDir = "D:/GraduationThesis/consumeTemp";
        splitFile(file,targetDir,new ConsumeRecord());

    }
}
