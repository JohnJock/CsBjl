package com.android.bjl.http;

/**
 * Created by john on 2018/4/11.
 */

public interface HttpEventCallBack {
    public enum HttpRetId {
        EHttpRecvHeader, EHttpResSucceeded, EHttpResCanceled, EHttpResFailed, EHttpResTimerOut, EHttpResException, EHttpBadGATE
    };

    /**
     * 成功请求数据时调用
     */
    public void onHttpSuccess(int requestId, Object content);

    /**
     * 失败时调用
     *
     * @param requestId
     * @param httpStatus
     */
    public void onHttpFail(int requestId, HttpRetId httpStatus, String msg);

    /**
     * 接收到数据时调用
     *  @param requestId
     * @param nDataLen
     * @param totalLength
     */
    public void OnHttpReceived(int requestId, long nDataLen, long totalLength);

}
