package net.mos.webFile;

import java.io.File;

import net.mos.utils.FileUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {
	 private static final String LOG_TAG = "WebViewDemo";  
	    private WebView mWebView;  
	    private Handler mHandler = new Handler();  
	    private FileUtils fs;
	    
	    
	    @Override  
	    public void onCreate(Bundle icicle) {  
	        super.onCreate(icicle);  	        
	        requestWindowFeature(Window.FEATURE_NO_TITLE);	
	        
	        setContentView(R.layout.main);  
	        mWebView = (WebView) findViewById(R.id.webView);  
	        fs = new FileUtils(mWebView);
	  
	        WebSettings webSettings = mWebView.getSettings();  
	        webSettings.setSavePassword(false);  
	        webSettings.setSaveFormData(false);  
	        // 下面的一句话是必须的，必须要打开javaScript不然所做一切都是徒劳的  
	        webSettings.setJavaScriptEnabled(true);  
	        webSettings.setSupportZoom(false);  	  
	  
	        mWebView.setWebChromeClient(new MyWebChromeClient());  
	  
	        // 看这里用到了 addJavascriptInterface 这就是我们的重点中的重点  
	        mWebView.addJavascriptInterface(fs, "FileUtils");
	  

//	        mWebView.loadUrl("http://10.0.2.2/webapp/filelist.html"); 
	        mWebView.loadUrl("file:///android_asset/www/filelist.html");
	    }
	    
	  
//	    // 这是他定义由 addJavascriptInterface 提供的一个Object  
//	    final class DemoJavaScriptInterface {  
//	        DemoJavaScriptInterface() {
//	        }  
//	  
//	        /** 
//	         * This is not called on the UI thread. Post a runnable to invoke 
//	         * 这不是呼吁界面线程。发表一个运行调用 
//	         * loadUrl on the UI thread. 
//	         * loadUrl在UI线程。 
//	         */  
//	        public void clickOnAndroid() {        // 注意这里的名称。它为clickOnAndroid(),注意，注意，严重注意  
//	            mHandler.post(new Runnable() {  
//	                public void run() {  
//	                    // 此处调用 HTML中的javaScript函数  
//	                    mWebView.loadUrl("javascript:wave('dfsaf')");  
//	                }  
//	            });  
//	        }  
//	    }  
	// 线下的代码可以不看，调试用的  
	///////////////////////////////////////////////////////////////////////////////////////////////////  
	    /** 
	     * Provides a hook for calling "alert" from javascript. Useful for 
	     * 从javascript中提供了一个叫“提示框” 。这是很有用的 
	     * debugging your javascript. 
	     *  调试你的javascript。 
	     */
	    final class MyWebChromeClient extends WebChromeClient {  
	        @Override  
	        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {  
	            Log.d(LOG_TAG, message);  
	            result.confirm();  
	            
	            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
	            								.create();
	            alertDialog.setMessage(message);
	            alertDialog.show();
	            
	            return true;  
	        }  
	    }  
}
