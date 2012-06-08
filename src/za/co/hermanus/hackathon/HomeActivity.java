package za.co.hermanus.hackathon;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity 
{
	
    private static final String TAG = "HomeActivity";
    private static final String HOST = "192.168.100.5";
    private static final int PORT = 5980;
   
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen);
    }
    public void getData(View v)
    {
    	try 
    	{
    		Intent intent = new Intent(this, AppActivity.class);
    		startActivity(intent);
			Log.i(TAG, "Heavy users written successfully");
		} catch (Exception e) {
			Log.i(TAG, "Heavy user not written");
			e.printStackTrace();
		}
    	//go to map to get current location.
    	  //Intent intent = new Intent(view.getContext(), HermanusMain.class);
    	  //startActivityForResult(intent,0);
    }
    public void getHeavyUser(View v)
    {
    	
    }
    public void showData(View v)
    {
    	//go to map to get current location.
   	 // Intent intent = new Intent(view1.getContext(), HermanusMain.class);
   	  //startActivityForResult(intent,0);
   	try {
			should_partial_read(v.getContext().getCacheDir());
			Log.i(TAG, "Heavy users written successfully");
		} catch (Exception e) {
			Log.i(TAG, "Heavy user not written");
			e.printStackTrace();
		}
    }
//public void getData(File baseDir) {
//	BufferedReader br = new BufferedReader(new FileReader(new File(baseDir, "heavy_users.psv")));
//	String line;
//	StringBuilder sb = new StringBuilder();
//	while ((line = br.readLine()) != null) {
//		sb.append(line);
//		sb.append("\n");//Intent intent = new Intent(view1.getContext(), HermanusMain.class);
//  	  //startActivityForResult(intent,0);
//	}
//	br.close();
//
//	//Intent intent = new Intent(view1.getContext(), HermanusMain.class);
//	  //startActivityForResult(intent,0);
//	TextView tv = (TextView) findViewById(R.id.textView);
//	tv.setText(sb.toString());		
//	Log.i(TAG, sb.toString());
//}
    
 
    
/*private void writeData(File baseDir) throws IOException  
{
    	String params = "&latitude=" + latitude + "&longitude=" + longitude;
    	//String ip = "198.168.100.5";// "//"192.168.100.5"
    	//Log.i(TAG, "WHAT is MY ip: "+ip);
  String wc = getwebData("http://198.168.100.5:17080/ebr-vaadin-web/CgiServlet?_CONSUMER_NAME=WhCgiConsumer"+ params);
  try {
	Log.i(TAG, "get method: " + wc);
	/*String content = "LON|LAT|RATING|INFO|METER_ID|ADDRESS\n"
			+ "31.1442|30.28776|1.53|25 Exhibition Road, Hermanus|555-1234-25\n"
			+ "31.1242|30.18776|1.53|26 Exhibition Road, Hermanus|555-1234-26\n"
			+ "31.1342|30.38776|1.53|27 Exhibition Road, Hermanus|555-1234-27\n"
			+ "31.1642|30.08776|1.53|28 Exhibition Road, Hermanus|555-1234-28\n"
			+ "31.1342|30.58776|1.53|29 Exhibition Road, Hermanus|555-1234-29";*/
	/*OutputStream os = new FileOutputStream(new File(baseDir, "heavy_users.psv"));
	Log.i(TAG, baseDir.getAbsolutePath());
	os.write(wc.getBytes());
	os.close();
} catch (Exception e) {
	Log.i(TAG, "Unable to write");
}
    }*/
 
    public void should_partial_read(File baseDir) throws Exception {
    	Log.i(TAG, baseDir.getAbsolutePath());
		BufferedReader br = new BufferedReader(new FileReader(new File(baseDir, "heavy_users.psv")));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");//Intent intent = new Intent(view1.getContext(), HermanusMain.class);
      	  //startActivityForResult(intent,0);
		}
		br.close();
	
		//Intent intent = new Intent(view1.getContext(), HermanusMain.class);
  	  //startActivityForResult(intent,0);
		
	}
    
 
}
