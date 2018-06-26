package com.android.bjl.http;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.android.bjl.util.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 通讯实体类
 * Created by zhangzz@bjrrtx.com on 2016/11/11.
 */
public class HttpCommon {
    private static final String TAG = "HttpCommon";
    private Activity pActivity;
    private OkHttpClient.Builder builder;
    private OkHttpClient mOkHttpClient;
    private HttpEventCallBack pNetCallBack; // 回调
    private String pMac = "";
    private String pfilePath = ""; // 下载文件路径，包含文件名

    public final static long CONNECT_TIMEOUT = 60;
    public final static long READ_TIMEOUT = 100;
    public final static long WRITE_TIMEOUT = 60;

    public HttpCommon(Activity activity, HttpEventCallBack mNetCallBack) {
        this.pActivity = activity;
        this.pNetCallBack = mNetCallBack;
        builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);//设置读取超时时间
        // 添加证书
        List<InputStream> certificates = new ArrayList<>();
        List<byte[]> certs_data = NetConfig.getCertificatesData();

        // 将字节数组转为数组输入流
        if (certs_data != null && !certs_data.isEmpty()) {
            for (byte[] bytes : certs_data) {
                certificates.add(new ByteArrayInputStream(bytes));
            }
        }


        SSLSocketFactory sslSocketFactory = getSocketFactory(certificates);
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory);
        }

        // 设定SSL传输证书 TODO 生产需要设定此参数
//        setCertificates(new ByteArrayInputStream(Constant.MESSAGE_SSL.getBytes()));
    }

    /**
     * @param map
     * @param action
     * @param httpTaskId
     */
    public void post(Map<String, String> map, String action, final int httpTaskId) {
        Log.i(TAG, "报文:请求： " + map.toString());
        builder.cookieJar(new CookieJar() {
            PersistentCookieStore cookieStore = new PersistentCookieStore(pActivity);

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    for (Cookie item : cookies) {
                        cookieStore.add(url, item);
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies;
            }
        });
        //创建okHttpClient对象
        mOkHttpClient = builder.build();
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
//        builder.add("v","1.0");
//        builder.add("month","10");
//        builder.add("day","1");
//        builder.add("key","a7c3e8e7c78a7d6f28efa7abb1130f95");

        //创建一个Request
        final Request request = new Request.Builder()
                .url(Constant.SERVER_URL.concat(action)) // 服务器地址
                .post(builder.build())
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String jsonStr = response.body().string();
//                String key = "";
//                try {
//                    key = new JSONObject(pMac).optString("mac").substring(0, 8);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                }
//                // 成功直接返回
//
//                String strResponse = null;
//                try {
//                    strResponse = new String(DesUtil.getInstance().decrypt(BASE64Encoder.decode(jsonStr.getBytes()), key.getBytes(), key.getBytes()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                }
//                Log.i(TAG, "请求接收报文: " + strResponse);
//                pNetCallBack.onHttpSuccess(httpTaskId, strResponse);

                Log.i(TAG, "报文:接收： " + jsonStr);
                pNetCallBack.onHttpSuccess(httpTaskId, jsonStr);
            }
        });
    }


//    /**
//     * @param map
//     * @param url
//     * @param request
//     */
//    public void post(Map map, String url, int request) {
//        JSONObject jsonObject = new JSONObject(map);
//        try {
//            jsonObject.put("formData", "debug");
//            jsonObject.put("FAPView", "json");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        post(request, Constant.SERVER_URL.concat(url)
//                , jsonObject.toString());
//
////        Log.i(TAG, "请求报文: "+jsonObject.toString());
//    }

    /**
     * 数据传输 POST
     *
     * @param httpTaskId 返回执行回调的taskId
     * @param url        请求的URL
     * @param strParam   JSON字符串格式参数
     */
    public void post(final int httpTaskId, final String url, String strParam) {
        Log.i(TAG, "请求报文: " + strParam);
        Log.i(TAG, "请求接口: " + url);
        // 对传输数据进行加密
//        strParam = enData(strParam);
        builder.cookieJar(new CookieJar() {
            PersistentCookieStore cookieStore = new PersistentCookieStore(pActivity);

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    for (Cookie item : cookies) {
                        cookieStore.add(url, item);
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies;
            }
        });
        //创建okHttpClient对象
        mOkHttpClient = builder.build();
        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("formData", strParam);
//        builder.add("FAPSessionId", PrefUtils.getString(pActivity, "sessionId", ""));
        builder.add("v", "1.0");
        builder.add("month", "10");
        builder.add("day", "1");
        builder.add("key", "a7c3e8e7c78a7d6f28efa7abb1130f95");
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url) // 服务器地址
                .post(builder.build())
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                String jsonStr = response.body().string();
//                String key = "";
//                try {
//                    key = new JSONObject(pMac).optString("mac").substring(0, 8);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                }
//                // 成功直接返回
//
//                String strResponse = null;
//                try {
//                    strResponse = new String(DesUtil.getInstance().decrypt(BASE64Encoder.decode(jsonStr.getBytes()), key.getBytes(), key.getBytes()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                }
//                Log.i(TAG, "请求接收报文: " + strResponse);
//                pNetCallBack.onHttpSuccess(httpTaskId, strResponse);
                pNetCallBack.onHttpSuccess(httpTaskId, response.body().string());
            }
        });
    }


    public void uploadFile(final int httpTaskId, final String url, String strParam, final String filePath) {
        builder.cookieJar(new CookieJar() {
            PersistentCookieStore cookieStore = new PersistentCookieStore(pActivity);

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    for (Cookie item : cookies) {
                        cookieStore.add(url, item);
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies;
            }
        });
        //创建okHttpClient对象
        mOkHttpClient = builder.build();
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加一个文本表单参数
        multipartBuilder.addFormDataPart("method", "upload上传");

        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            multipartBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        }

        // 对传输数据进行加密
//        strParam = enData(strParam);
        multipartBuilder.addFormDataPart("formData", strParam);
        //  multipartBuilder.addFormDataPart("FAPSessionId", SessionDataPlugin.getSessionId());
        //构造文件上传时的请求对象Request
        Request request = new Request.Builder().url(url).post(multipartBuilder.build()).build();

        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                String jsonStr = response.body().string();
//                String key = "";
//                try {
//                    key = new JSONObject(pMac).optString("mac").substring(0, 8);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                }
//                // 成功直接返回
//                try {
//                    pNetCallBack.onHttpSuccess(httpTaskId, new String(DesUtil.getInstance().decrypt(BASE64Encoder.decode(jsonStr.getBytes()), key.getBytes(), key.getBytes())));
//                } catch (Exception e) {
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                    e.printStackTrace();
//                }
                pNetCallBack.onHttpSuccess(httpTaskId, response.body().string());
            }
        });
    }

    /**
     * 多张图片上传  反馈
     *
     * @param httpTaskId
     * @param url
     * @param map
     * @param filePath
     */
    public void uploadFile(final int httpTaskId, final String url, Map<String, String> map, String[] keyPath, String[] filePath) {
        Log.i(TAG, "报文:请求： " + map.toString());
        builder.cookieJar(new CookieJar() {
            PersistentCookieStore cookieStore = new PersistentCookieStore(pActivity);

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    for (Cookie item : cookies) {
                        cookieStore.add(url, item);
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies;
            }
        });
        //创建okHttpClient对象
        mOkHttpClient = builder.build();
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加一个文本表单参数
        multipartBuilder.addFormDataPart("method", "upload上传");

        if (filePath != null && filePath.length > 0) {
            for (int i = 0; i < filePath.length; i++) {
                File file = new File(filePath[i]);
                multipartBuilder.addFormDataPart(keyPath[i], file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }


        // 对传输数据进行加密
//        strParam = enData(strParam);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        // multipartBuilder.addFormDataPart("FAPSessionId", SessionDataPlugin.getSessionId());
        //构造文件上传时的请求对象Request
        Request request = new Request.Builder().url(Constant.SERVER_URL.concat(url)).post(multipartBuilder.build()).build();

        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                String jsonStr = response.body().string();
//                String key = "";
//                try {
//                    key = new JSONObject(pMac).optString("mac").substring(0, 8);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                }
//                // 成功直接返回
//                try {
//                    pNetCallBack.onHttpSuccess(httpTaskId, new String(DesUtil.getInstance().decrypt(BASE64Encoder.decode(jsonStr.getBytes()), key.getBytes(), key.getBytes())));
//                    String str = new String(DesUtil.getInstance().decrypt(BASE64Encoder.decode(jsonStr.getBytes()), key.getBytes(), key.getBytes()));
//                    Log.i(TAG, "onResponse: " + str);
//                } catch (Exception e) {
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                    e.printStackTrace();
//                }
                Log.i(TAG, "报文:接收： " + jsonStr);
                pNetCallBack.onHttpSuccess(httpTaskId, jsonStr);
            }
        });
    }


    /**
     * 多张图片上传  反馈
     *
     * @param httpTaskId
     * @param url
     * @param strParam
     * @param filePath
     */
    public void uploadFile(final int httpTaskId, final String url, String strParam, String[] keyPath, String[] filePath) {
        builder.cookieJar(new CookieJar() {
            PersistentCookieStore cookieStore = new PersistentCookieStore(pActivity);

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    for (Cookie item : cookies) {
                        cookieStore.add(url, item);
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies;
            }
        });
        //创建okHttpClient对象
        mOkHttpClient = builder.build();
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加一个文本表单参数
        multipartBuilder.addFormDataPart("method", "upload上传");

        if (filePath != null && filePath.length > 0) {
            for (int i = 0; i < filePath.length; i++) {
                File file = new File(filePath[i]);
                multipartBuilder.addFormDataPart(keyPath[i], file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            }
        }


        // 对传输数据进行加密
//        strParam = enData(strParam);
        multipartBuilder.addFormDataPart("formData", strParam);
        // multipartBuilder.addFormDataPart("FAPSessionId", SessionDataPlugin.getSessionId());
        //构造文件上传时的请求对象Request
        Request request = new Request.Builder().url(url).post(multipartBuilder.build()).build();

        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

//                String jsonStr = response.body().string();
//                String key = "";
//                try {
//                    key = new JSONObject(pMac).optString("mac").substring(0, 8);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                }
//                // 成功直接返回
//                try {
//                    pNetCallBack.onHttpSuccess(httpTaskId, new String(DesUtil.getInstance().decrypt(BASE64Encoder.decode(jsonStr.getBytes()), key.getBytes(), key.getBytes())));
//                    String str = new String(DesUtil.getInstance().decrypt(BASE64Encoder.decode(jsonStr.getBytes()), key.getBytes(), key.getBytes()));
//                    Log.i(TAG, "onResponse: " + str);
//                } catch (Exception e) {
//                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯成功，解析报文失败！");
//                    e.printStackTrace();
//                }
                pNetCallBack.onHttpSuccess(httpTaskId, response.body().string());
            }
        });
    }


    /**
     * 下载文件 downloadFile
     *
     * @param httpTaskId 返回执行回调的taskId
     * @param url        请求的URL
     * @param strParam   JSON字符串格式参数
     * @param filePath   文件存放路径（包含文件名）
     */
    public void downloadFile(final int httpTaskId, final String url, String strParam, String filePath) {
        this.pfilePath = filePath;
        // 对传输数据进行加密
//        strParam = enData(strParam);
        builder.cookieJar(new CookieJar() {
            PersistentCookieStore cookieStore = new PersistentCookieStore(pActivity);

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    for (Cookie item : cookies) {
                        cookieStore.add(url, item);
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies;
            }
        });
        //创建okHttpClient对象
        mOkHttpClient = builder.build();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("formData", strParam);
        builder.add("FAPView", "STREAM");
        //  builder.add("FAPSessionId", SessionDataPlugin.getSessionId());
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url) // 服务器地址
                .post(builder.build())
                .header("Accept-Encoding", "identity")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.d("downloadFile开始接受文件：", pfilePath);
                InputStream is = null;
                byte[] buf = new byte[10240];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    // 如果读取的不是文件
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(pfilePath);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        //将下载的进度回调
//                        pNetCallBack.OnHttpReceived(httpTaskId,total,file.length());
                    }
                    fos.flush();
                    Log.d("downloadFile", pfilePath.concat("文件下载成功"));
                    pNetCallBack.onHttpSuccess(httpTaskId, "ok");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("downloadFile", pfilePath.concat("出现异常"));
                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * 在UpdateFile中下载Zip文件位置添加downloadZip
     *
     * @param httpTaskId 返回执行回调的taskId
     * @param url        请求的URL
     * @param strParam   JSON字符串格式参数
     * @param filePath   文件存放路径（包含文件名）
     */
    public void downloadZip(final int httpTaskId, final String url, String strParam, String filePath) {
        this.pfilePath = filePath;
        // 对传输数据进行加密
//        strParam = enData(strParam);
        builder.cookieJar(new CookieJar() {
            PersistentCookieStore cookieStore = new PersistentCookieStore(pActivity);

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    for (Cookie item : cookies) {
                        cookieStore.add(url, item);
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies;
            }
        });
        //创建okHttpClient对象
        mOkHttpClient = builder.build();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("formData", strParam);
        builder.add("FAPView", "STREAM");
        //  builder.add("FAPSessionId", SessionDataPlugin.getSessionId());
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url) // 服务器地址
                .post(builder.build())
                .header("Accept-Encoding", "identity")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                pfilePath = Environment.getExternalStorageDirectory().getPath() + pfilePath;
                Log.d("downloadFile开始接受文件：", pfilePath);
                InputStream is = null;
                byte[] buf = new byte[10240];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    // 如果读取的不是文件
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(pfilePath);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        //将下载的进度回调
//                        pNetCallBack.OnHttpReceived(httpTaskId,total,file.length());
                    }
                    fos.flush();
                    Log.d("downloadFile", pfilePath.concat("文件下载成功"));
                    pNetCallBack.onHttpSuccess(httpTaskId, "ok");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("downloadFile", pfilePath.concat("出现异常"));
                    pNetCallBack.onHttpFail(httpTaskId, HttpEventCallBack.HttpRetId.EHttpResFailed, "通讯异常，请检查网络！");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

//    // 报文加密
//    private String enData(String strParam) {
//        Log.d("加密前报文：", strParam);
//
//
//        // 进行加密事项
//        pMac = MacUtils.handleMac(strParam);
//        // 生成随机数
//        String random = String.valueOf((int) (89999999 * Math.random() + 10000000));
//        byte[] stream = BASE64Encoder.encode(pMac.getBytes()).getBytes();
//        byte[] desKey = random.getBytes();
//        byte[] encrypt = new byte[0];
//        try {
//            encrypt = DesUtil.getInstance().encrypt(stream, desKey, desKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.d("加密", "随机数 random=" + random + "," + random.length());
//        Log.d("随机数对原文进行", "随机数 random=" + random + "," + random.length());
//        // 对随机数进行RSA加密
//        String keyDes = RSACerPlus.getInstance(Constant.MESSAGE_PUBLIC_KEY, this.pActivity).doEncrypt(BASE64Encoder.encode(random.getBytes()));
//        Log.d("随机数RSA加密", keyDes + "," + keyDes.length());
//        // 返回加密字符串
//        String nvpValue = "E" + keyDes.length() + keyDes + BASE64Encoder.encode(encrypt);
//        Log.d("加密后报文", nvpValue);
//        return nvpValue;
//    }

    /**
     * 增加SSL支持
     *
     * @param certificates
     */
    private void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            mOkHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                    .build();
//            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加证书
     *
     * @param certificates
     */
    private static SSLSocketFactory getSocketFactory(List<InputStream> certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            try {
                for (int i = 0, size = certificates.size(); i < size; ) {
                    InputStream certificate = certificates.get(i);
                    String certificateAlias = Integer.toString(i++);
                    keyStore.setCertificateEntry(certificateAlias, certificateFactory
                            .generateCertificate(certificate));
                    if (certificate != null)
                        certificate.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
