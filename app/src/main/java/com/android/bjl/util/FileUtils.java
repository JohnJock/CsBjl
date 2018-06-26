
package com.android.bjl.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtils {

    private final static String TAG = "FileUtils";

    private String SDCardRoot;

    public FileUtils() {
        // 得到当前外部存储设备的目录
        SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 在SD卡上创建文件
     * 
     * @throws IOException
     */
    public File createFileInSDCard(String fileName, String dir) throws IOException {
        File file = new File(SDCardRoot + dir + File.separator + fileName);
        System.out.println("file---->" + file);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     * 
     */
    public File creatSDDir(String dir) {
        File dirFile = new File(SDCardRoot + dir + File.separator);
        System.out.println(dirFile.mkdirs());
        return dirFile;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public boolean isFileExist(String fileName, String path) {
        File file = new File(SDCardRoot + path + File.separator + fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public File write2SDFromInput(String path, String fileName, InputStream input) {

        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = createFileInSDCard(fileName, path);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            int temp;
            while ((temp = input.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    // ====

    public static InputStream getInputStreamFromPath(String path) {

        File file = new File(path);
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException", e);

        }

        return inputStream;
    }

    public static void writeBytes(String path, String content) {

        if (!CommonUtil.isEmpty(content)) {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(path);
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                Log.e(TAG, "FileNotFoundException", e);
            } catch (IOException e) {
                Log.e(TAG, "IOException", e);
            }
        }

    }

    public static void copyFile(File f1, File f2) throws Exception {
        int length = 2097152;
        FileInputStream in = new FileInputStream(f1);
        FileOutputStream out = new FileOutputStream(f2);
        byte[] buffer = new byte[length];
        while (true) {
            int ins = in.read(buffer);
            if (ins == -1) {
                in.close();
                out.flush();
                out.close();
                return;
            } else
                out.write(buffer, 0, ins);
        }
    }
    
    /**
     * 复制某个目录下的文件到另一个目录下
     * @param fromPath
     * @param toPath
     * @throws Exception
     */
    public static void copyFloder(String fromPath, String toPath) throws Exception {
    	File fileFromPath=new File(fromPath);
    	File fileToPath=new File(toPath);
    	if(!fileToPath.exists()){
    		fileToPath.mkdirs();
    	}
    	
    	if(fileFromPath.exists()&&fileFromPath.isDirectory()){
    		String[] fileFromList=fileFromPath.list();
    		if(!fromPath.endsWith("/")){
    			fromPath=fromPath+"/";
    		}
    		if(!toPath.endsWith("/")){
    			toPath=toPath+"/";
    		}
    		
    		for(int i=0;i<fileFromList.length;i++){
    			copyFile(new File(fromPath+fileFromList[i]), new File(toPath+fileFromList[i]));
    		}
    	}
    }

    /**
     * Method Name：copyAssetFile Description：从项目asset文件夹里复制文件到某个位置
     * 
     * @param fromFilePath
     * @throws IOException
     */
    public static void copyAssetFile(String fromFilePath, String toFilePath, Context context)
            throws IOException {
//        int length = 2097152;
    	int length=1024;
        InputStream in = context.getAssets().open(fromFilePath);
        FileOutputStream out = new FileOutputStream(new File(toFilePath));
        byte[] buffer = new byte[length];
        boolean a=true;
        while (a) {
            int ins = in.read(buffer);
            if(ins!=-1){
            	out.write(buffer, 0, ins);
            }else{
            	a=false;
            }   
        }
        
        in.close();
        out.flush();
        out.close();
    }

    public static String getFileString(String filePath) {
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                String allContent = "";
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String content = null;
                while ((content = bufferedReader.readLine()) != null) {
                    allContent += content;
                }
                read.close();
                return allContent;
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return "";

    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 删除单个文件
     * 
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static void saveBitmap(Activity activity, String fileName, Bitmap bitmap) {

        File f = new File(activity.getFilesDir(), fileName);
        try {
            f.createNewFile();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method Name：getFilePath Description：获取工程文件目录
     * data/data/com.rrtx.rrtxandroid/files
     * @param activity Creator：muzhengjun Create DateTime：2013-10-08
     */
    public static String getFilePath(Activity activity) {
        String zipPath = activity.getFilesDir().toString();
        return zipPath;
    }

    /**
     * Method Name：createActivityMKdirs Description：在工程文件目录下创建文件
     * 
     * @param filePath：文件完整路径
     * @param activity Creator：muzhengjun Create DateTime：2013-10-15
     * @throws IOException
     */
    public static File createActivityMKdirs(String filePath, Activity activity) throws IOException {
        File file = null;
        String publicPath = FileUtils.getFilePath(((Activity) activity)) + "/";
        int directoryIndex = 0;
        if (filePath.length() > (publicPath.length() + 1)) {
            int index = filePath.indexOf("/", publicPath.length());
            while (index != -1) {
                if (index != -1) {
                    directoryIndex = index;
                }
                index = filePath.indexOf("/", index + 1);
            }

            String directoryPath = filePath.substring(0, directoryIndex);
            File fileDir = new File(directoryPath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        file = new File(filePath);
        if (file != null && !file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    /**
     * 图片转base64文件流
     * @param bitmap
     * @return
     */
    public static String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * base64文件流转图片
     * @param string
     * @return
     */
    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {

            byte[] bitmapArray;

            bitmapArray = Base64.decode(string, Base64.DEFAULT);

            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 压缩图片
     * @param bitmap 原图片Bitmap
     * @param toFile 目标图片路径
     * @param width 要锁后宽度
     * @param height 压缩后高度
     * @param quality 保真度 0 ：不清晰 - 100清晰
     */
    public static void transImage(Bitmap bitmap, String toFile, int width, int height, int quality)
    {
        try
        {
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            // 缩放图片的尺寸
            float scaleWidth = (float) width / bitmapWidth;
            float scaleHeight = (float) height / bitmapHeight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 产生缩放后的Bitmap对象
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            // save file
            File myCaptureFile = new File(toFile);
            FileOutputStream out = new FileOutputStream(myCaptureFile);
            if(resizeBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)){
                out.flush();
                out.close();
            }
            if(!bitmap.isRecycled()){
                bitmap.recycle();//记得释放资源，否则会内存溢出
            }
            if(!resizeBitmap.isRecycled()){
                resizeBitmap.recycle();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) return false;
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        // 创建目录失败返回false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
