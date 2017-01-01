package consume.tool;

/**
 * Created by sghipr on 31/12/16.
 * 用于将大文件进行排序时的数据结构.
 * 作为基类,是一个接口.
 */
public interface SortBase extends Comparable<SortBase>{

    boolean parser(String records);

    SortBase create(SortBase o);

    int getTerm();

    String getStudentID();

    String getType();

    String getTime();

}
