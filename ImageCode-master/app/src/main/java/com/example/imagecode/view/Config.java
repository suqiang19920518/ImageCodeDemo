package com.example.imagecode.view;

import android.graphics.Color;

/**
 * @author: sq
 * @date: 2017/9/7
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: 用于对验证码控件参数的配置
 */
public class Config {

    // 点数设置
    public static final int POINT_NUM = 50;
    // 线段数设置
    public static final int LINE_NUM = 2;
    //设置画布背景颜色
    public static final int COLOR_CANVAS = Color.GRAY;
    //设置直线、文字颜色
    public static final int COLOR_TEXT = Color.BLACK;
    //设置边框颜色
    public static final int COLOR_BORDER = Color.parseColor("#CCCCCC");
    //设置文字宽度（小）
    public static final int SMALL_WIDTH_TEXT = 20;
    //设置文字宽度（大）
    public static final int LARGER_WIDTH_TEXT = 25;
    //画笔字体大小变动幅度
    public static final int[] TEXT_SIZE_RANGE = {40, 42, 44, 45};

}
