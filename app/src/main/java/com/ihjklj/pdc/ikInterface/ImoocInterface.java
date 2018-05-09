package com.ihjklj.pdc.ikInterface;

import com.ihjklj.pdc.model.ImoocCourse;
import java.util.List;

/**
 * Created by Administrator on 2018/5/9.
 */

public interface ImoocInterface {

    public List<ImoocCourse> getCourse(String data);
    public int getFailed();
}
