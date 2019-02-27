package com.example.admin.musicclassroom.Utils

import com.squareup.okhttp.Callback
import com.squareup.okhttp.FormEncodingBuilder
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request

/**
 * Created by Administrator on 2017/6/21 0021.
 */

object HttpUtils {

    //    val SERVER_URL = "http://101.201.236.65/"
//    val BASE_URL= "http://101.201.236.65/"
    val SERVER_URL = "http://tj-mj.cn"

    val BASE_URL= "http://tj-mj.cn/"
    val SEND_MIDI = SERVER_URL+"/push/pushTagByMusicId"
    val SEND_EX_NAME = SERVER_URL+"/push/pushTagByCourse"
    val LOGIN_TEACHER = SERVER_URL+"/login/login/teacher"
    val CHECK_UPDATE = SERVER_URL+"/login/version/get"
    val SOUND = SERVER_URL+"/push/pushTagByInstruction"

    val READ_GRADE = SERVER_URL+"/musicsystem/grade/getUnit"

    val READ_COURSE = SERVER_URL+"/musicsystem/course/getAllCourse"

    val READ_MIDI = SERVER_URL+"/musicsystem/grade/getAllMusic"
    val READ_COURSRDETIAL = SERVER_URL+"/musicsystem/course/getCourse"
    val ALLLISTEN = SERVER_URL+"/musicsystem/music/getAllListen"
    val ALLMUSIC = SERVER_URL+"/musicsystem/music/getAllMusic"
    val DETAILSBYNAME = SERVER_URL+"/musicsystem/music/getDetails"


    /*
    val ALLINFO = "http://tj-mj.cn/musicsystem/music/getAllInfo"
    val READ_MESSAGE = "http://101.201.236.65/test/introduce/selectIntroduce"
    //    val READ_COURSE = "http://101.201.236.65/test/unitCourse/findCourses"
//    val READ_COURSE1 = "http://101.201.236.65/test/unitCourse/findCourses"
    val READ_MUSIC = "http://101.201.236.65/test/music/selectAll"
    val READ_EXERCISES = "http://101.201.236.65/test/exercises/selectAll"
    val READ_MUSICAL = "http://101.201.236.65/test/musical/selectMusical"
    val READ_THEORY = "http://101.201.236.65/test/theory/selectTheory"
    val READ_VIDEO = "http://101.201.236.65/test/video/selectAll"
    */
    private val client = OkHttpClient()

    fun sendRequestToServer(url: String, params: Map<String, String>, callback: Callback) {
        val builder = FormEncodingBuilder()
        if(params.isNotEmpty()){
            val keys = params.keys
            for (param in keys) {
                builder.add(param, params[param])
            }
        }else{
            builder.add("","")
        }
        val body = builder.build()
        val request = Request.Builder().url(url).post(body).build()
        client.newCall(request).enqueue(callback)
    }

    fun showAddress(type: String): String {
        val result = when (type) {
            "all" -> SERVER_URL
            "login" -> LOGIN_TEACHER
            "update" -> CHECK_UPDATE
            "sendMidi" -> SEND_MIDI
            else -> "没有对应的地址"
        }
        return result
    }

}