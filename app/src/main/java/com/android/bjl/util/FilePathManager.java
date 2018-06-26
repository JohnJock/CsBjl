
package com.android.bjl.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;


/**
 * 路径管理类
 * Description: 文件路径管理类
 */
public class FilePathManager {
    private static FilePathManager instance;

    private Context mContext;

    private String publicPath;

    private String WEB_WWW = "www";

    private String WEB_ZIPS_PATH = "webZips";

    private String WEB_ICON_PATH = "iocn";

    /** 临时存放目录 */
    private String WEB_TEMP_PATH = "temp";

    /** 头像等图片存放目录 */
    private String WEB_IMG_PATH = "img";


    private FilePathManager(Context context) {
        mContext = context;
        publicPath = FileUtils.getFilePath(((Activity) mContext));
    }
    public static FilePathManager getIntance(Context context) {
        if (null == instance) {
            instance = new FilePathManager(context);
        }
        return instance;
    }

    public String getPublicPath() {
        return publicPath;
    }

    /**
     * 跳转页面（默认是）file:///data/data/com.example.hello/files" + "/www/"
     * @param fileName
     * @return
     */
    public String getTurnPage(String fileName) {
        if (fileName != null) {
            return "file://" + publicPath.concat("/www/").concat(fileName);
        }else {
            return "";
        }

    }
    public String getIconZipPath(String subMenuId) {
        return publicPath + "/" + WEB_ICON_PATH + "/" + subMenuId + ".zip";
    }

    public String getWebZips() {
        return publicPath + "/www/" + WEB_ZIPS_PATH + "/";
    }
    public String getWebZipPath(String subMenuId) {
        return publicPath + "/www/" + WEB_ZIPS_PATH + "/" + subMenuId + ".zip";
    }
    public String getWebPath() {
        return publicPath;
    }
    public String getWwwPath() {
        return publicPath + "/" + WEB_WWW + "/";
    }

    /**
     * 获取临时图片存放路径
     * @return
     */
    public String getImgPath() {
        return publicPath + "/www/" + WEB_TEMP_PATH + "/" + WEB_IMG_PATH + "/";
    }

    /**
     * 获取临时存放路径
     * @return
     */
    public String getTempPath() {
        return publicPath + "/www/" + WEB_TEMP_PATH;
    }

    /**
     * 获取HTML临时图片存放路径
     * @return
     */
    public static String getHtmlPath(String attar) {
        String htmlString="0";
        if (!TextUtils.isEmpty(attar)) {
            htmlString="../temp/img/".concat(attar).concat(".png");
        }
        return htmlString;
    }
}
