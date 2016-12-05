package consume.tool;

/**
 * Created by sghipr on 2016/12/3.
 */
public class ConsumeNameAndType {

    private String name;

    private String type;

    public ConsumeNameAndType(String name, String type){
        this.name = name;
        this.type = type;
    }

    public int hashCode(){
        return name.hashCode() + type.hashCode();
    }


    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public String toString(){
        return name + "," + type;
    }


}
