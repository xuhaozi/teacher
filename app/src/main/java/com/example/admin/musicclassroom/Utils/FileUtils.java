package com.example.admin.musicclassroom.Utils;




import java.io.File;


/**
 * Created by Administrator on 2017/6/12.
 */

public class FileUtils {

    public static String subName(String fileName) {
        return fileName.substring(0, fileName.indexOf(","));
    }

    public static String subText(String fileName) {
        return fileName.substring(0, fileName.indexOf("."));
    }

    public static String[] splitString(String fileName, String suffix) {
        return fileName.split(suffix);
    }
    public static File fileRelative(File file, String relative) {
        String parent = file.getParentFile().getAbsolutePath();
        String fileName = file.getName();
        String name = fileName.substring(0, fileName.indexOf(","));
        String relativeName = parent + "/" + name + "." + relative;
        return new File(relativeName);
    }


    public static String getFileName(String path) {
        String substring = null;
        if (path!=null){
            substring= path.substring(path.lastIndexOf("/") + 1);
        }
        return substring;
    }

    public static void DeleteFile(String course) {

    }
}
