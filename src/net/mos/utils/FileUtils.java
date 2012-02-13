package net.mos.utils;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

public class FileUtils {

	
	public static final String TAG = "FileUtils";
	WebView webView;
	private Handler mHandler = new Handler();
	
	public FileUtils(WebView view)
	{
		webView = view;
	}
	
    
	//检查sdCard是否存在
	public boolean checkSdcardExists(){		
    	String sdCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sdCardStatus.equals(Environment.MEDIA_MOUNTED)){
			status = true;
		}else
			status = false;
		return status;
	}
	
	public String getRoot(){
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	
	public void getListName(String path){
		File file = new File(path);
		if (!file.exists()) {
//			return null;
		}
		File[] files = file.listFiles();
		JSONArray jsonArray = new JSONArray();
		try {
			for (int i = 0; i < files.length; i++) {	
				File file2 = files[i];
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", file2.getName());
				jsonObject.put("aPath", file2.getAbsolutePath());
				jsonArray.put(i, jsonObject);
			}
			JSONObject parentObject = new JSONObject();
			parentObject.put("name", "/..");
			parentObject.put("aPath", file.getParentFile().getAbsolutePath());
			jsonArray.put(0, parentObject);
			Log.i(TAG, jsonArray.toString());
		} catch (JSONException e) {			
			e.printStackTrace();
		}
	    webView.loadUrl("javascript:refreshList('"+jsonArray.toString()+"')");  
	}
}
