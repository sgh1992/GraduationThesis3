package models.tool;

import java.io.File;
import java.util.HashMap;

/**
 * Created by sghipr on 2017/1/18.
 */
public class Features {


    class ConsumeClass{

        private String studentID;

        private HashMap<Integer,HashMap<String,String>> termTypeMap;

        public ConsumeClass(String studentID){
            this.studentID = studentID;
            termTypeMap = new HashMap<>();
        }

        public String getStudentID(){
            return studentID;
        }

        public void update(int term, String type, String record){

            HashMap<String,String> typeMap = null;
            if(termTypeMap.containsKey(term))
                typeMap = termTypeMap.get(term);
            else
                typeMap = new HashMap<>();

            typeMap.put(type,record);
            termTypeMap.put(term,typeMap);
        }
    }
}
