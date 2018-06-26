package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.bjl.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by john on 2018/4/28.
 */

public class WebViewActivity extends BaseActivity {
    private WebView webView;
    String title, content;
    int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        init();
    }


    private void init() {
        type = getIntent().getIntExtra("type", -1);
        webView = (WebView) findViewById(R.id.wb_webview);
        title = getIntent().getStringExtra("title");
//        title="网页";
        content = getIntent().getStringExtra("content");
        content = getNewContent(content);
        if (type == 5) {
            showTitleLeftTvAndImgAndRightTv(title, "返回", R.mipmap.back, "评论", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(WebViewActivity.this,CLDEPLActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("id",getIntent().getStringExtra("id"));
                    startActivity(intent);
                }
            });
        } else {
            showTitleLeftTvAndImg(title, "返回", R.mipmap.back, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
//        //WebView加载web资源
//        webView.loadUrl(content);
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
// plan_html.loadData(planweb,"text/html", "utf-8");//在我这里这个显示是乱码，所以用下面这种
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true); // 显示放大缩小
        settings.setSupportZoom(true); // 可以缩放
    }


    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public static String getNewContent(String htmltext) {
        try {
            if (htmltext.contains("img")) {
                htmltext = htmltext.replaceAll("src=\"/glc_admin/static/upload/image", "src=\"http://47.98.226.156/glc_admin/static/upload/image");
            }
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

}
