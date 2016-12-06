package consume.analysize;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sghipr on 2016/12/6.
 *
 * 一个顶层的接口.
 * 用于处理不同的时间周期内的时间段的划分.
 */
public interface Cycle {

    List<String> getInteral();

    /**
     * 获得在一个周期内划分的时间段.
     * @return
     */
    List<String> divide();

    /**
     * 根据消费的时间点来获得对应的时间间隔.
     * @param time
     * @return
     */
    String getInteral(String time);
}