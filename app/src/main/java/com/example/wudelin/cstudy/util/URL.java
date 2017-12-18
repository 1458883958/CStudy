package com.example.wudelin.cstudy.util;

/**
 * Created by wudelin on 2017/11/24.
 */

public class URL {
    //服务器ip
    private static final String HTTP_URL = "http://114.67.224.207:8080/Okhttp/";

    //获取章节内容
    public static final String HTTP_URL_CONTENT = HTTP_URL+"Req_c?l_name=";
    //public static final String u = "http://114.67.224.207:8080/Okhttp/Upda_i?username=";

    //获取答案
    public static final String HTTP_URL_ANSWER = HTTP_URL+"Answer?l_name=";

    //获取题目
    public static final String HTTP_URL_PROBLEM = HTTP_URL+"Problen?l_name=";

    //获取给个章节题目数
    public static final String HTTP_URL_PROBLEM_LENGTH = HTTP_URL+"Req_length?l_name=";

    //获取视频
    public static final String HTTP_URL_VEDIO = "http://114.67.224.207:8080/Vedio/3_";

    //注册
    public static final String HTTP_URL_REG = HTTP_URL+"Reg?";

    //更新分数和查询分数
    public static final String HTTP_URL_UPD_SCORE = HTTP_URL+"Upd_score?username=";
    public static final String HTTP_URL_REQ_SCORE = HTTP_URL+"Req_score?username=";

    //更新进度和查询进度
    public static final String HTTP_URL_UPD_PRO = HTTP_URL+"Upda_i?username=";
    public static final String HTTP_URL_REQ_PRO = HTTP_URL+"Req_i?username=";
}
