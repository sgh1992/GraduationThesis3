package consume.tool;

/**
 * Created by sghipr on 2016/12/4.
 */
public class YearAndTerm {

    private String year; //入学学年

    private int term; //学期

    public YearAndTerm(){}

    public YearAndTerm(String year, int term){
        this.year = year;
        this.term = term;
    }

    public String getYear(){
        return year;
    }

    public int getTerm(){
        return term;
    }

    public void setYear(String year){
        this.year = year;
    }

    public void setTerm(int term){
        this.term = term;
    }

    public String toString(){
        return year + "," + term;
    }


}
