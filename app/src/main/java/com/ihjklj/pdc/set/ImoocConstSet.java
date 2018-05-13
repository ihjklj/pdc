package com.ihjklj.pdc.set;

/**
 * Created by Administrator on 2018/5/10.
 */

public class ImoocConstSet {

    //课程信息请求地址
    public static final String IMOOC_URL = "http://47.98.153.250:30001/imooc/"; //默认地址，获取的是最近一天的数据
    public static final String IMOOC_KEY = "http://47.98.153.250:30001/imooc/keyflag/"; //根据条件获取数据

    //设置图表的数据展示来源对象：
    public static final String IMOOC_LABEL_CN = "慕课网数据";    //展示中文
    public static final String IMOOC_LABEL_EN = "imooc course"; //展示英文

    //从服务器下载的课程数据的排序规则如下：
    public static final int IMOOC_COURSE_TYPE_NET = 0;  //按照爬虫从网站爬到的顺序展示
    public static final int IMOOC_COURSE_TYPE_NUM = 1;  //按照课程学习的人数展示
    public static final int IMOOC_COURSE_TYPE_UP = 2;   //按照近期课程的上升热度展示
    public static final int IMOOC_COURSE_TYPE_DOWN = 3; //按照近期课程的下降速度展示

    //设置表Y轴的最大和最小值（如果Y轴波动不大建议设置以下，界面的效果可以统一，如果波动很大就不建议设置）：
    public static final float IMOOC_Y_MAX = 100f;   //最大值
    public static final float IMOOC_Y_MIN = 0f;     //最小值
}
