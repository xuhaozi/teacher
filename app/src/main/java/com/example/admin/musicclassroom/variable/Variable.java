package com.example.admin.musicclassroom.variable;


import com.example.admin.musicclassroom.entity.CourseVo;
import com.example.admin.musicclassroom.entity.LoginInfoVo;

import java.util.List;

public class Variable {

//    public static String accessaddress = "http://192.168.199.120:8080/musicsystem/";
    public static String accessaddress = "http://tj-mj.cn/teacher/";

    public static String accessaddress_img = "http://tj-mj.cn/";
    public static LoginInfoVo loginInfoVo;
    public static CourseVo course;

    //登录
    public static String address_Login = accessaddress + "user/login";

    //自动登录
    public static String address_Automatic_logon = accessaddress + "user/auto";

    //获取课程首页浏览历史
    public static String address_history = accessaddress + "course/head";

    //获取课程详情
    public static String address_detail = accessaddress + "course/detail";

    //获取视频列表
    public static String address_video = accessaddress + "course/video";

    //获取乐理列表
    public static String address_theory = accessaddress + "course/theory";

    //获取习题
    public static String address_exercise = accessaddress + "course/exercise";

    //.获取课程乐器
    public static String address_musical = accessaddress + "course/musical";

    //.通过国家获取音乐风格
    public static String address_musicall = accessaddress + "music/all";

    //通过乐器名搜索乐器
    public static String address_searchmusical = accessaddress + "search/musical";

    //通过音乐家名称搜索音乐家
    public static String address_searchmusician = accessaddress + "search/musician";

    //通过国家获取音乐家
    public static String address_musician = accessaddress + "musician/all";


    //通过国家获取乐器
    public static String address_musical_all = accessaddress + "musical/all";

    //获取乐器的详细信息
    public static String address_musical_info = accessaddress + "musical/info";
    //获取音乐家的详细信息
    public static String address_musician_info = accessaddress + "musician/info";


    //获取国家获取音乐类型
    public static String address_music_all = accessaddress + "music/all";


    //获取音乐风格详情
    public static String address_music_info = accessaddress + "music/info";

    //获取所有乐理
    public static String address_theory_all = accessaddress + "theory/all";


    //获取乐理详情
    public static String address_theory_info = accessaddress + "theory/info";


}
