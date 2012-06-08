package za.co.hermanus.hackathon;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class H2MapActivity extends MapActivity
{
	private static String TAG = "H2MapActivity";
	
	//Minimum update interval(ms)
	private static final long MIN_UPDATE_INTERVAL = 0;
		
	//Minumum update distance(meters)
	private static final long MIN_UPDATE_DISTANCE = 0;
	
	//
	private static final int LOC_REQUEST_CODE = 1;
	
	private static final String DELIM ="|";
	
	private static final int RED = 0;
	private static final int GREEN = 1;
	private static final int ORANGE = 2;
	private static final int BLACK = 3;
	
	private static final double BIGGEST = 2;
	private static final double AVERAGE = 1.50;
	private static final double LOW  = 1;
	
	
	  //Handle to the map controller 
    private MapController mMapController;  
    
    //Handle to the map overlay
    private PositionOverlay mPositionOverlay;
    
    //Collection of map overlays
    private List<Overlay> mMapOverlays;
    
    //
    private MapView mMapView;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		// Get a handle to the map view
		mMapView = (MapView) findViewById(R.id.mapView);

		// Get a handle to a map controller
		mMapController = mMapView.getController();

		// Set map display options
		mMapView.setTraffic(true);
		mMapView.setSatellite(false);

		// Disable built-in zoom controls
		mMapView.setBuiltInZoomControls(false);

		// Zoom into the map: Rang 1 to 21
		mMapController.setZoom(17);
		
		// Get handle to resources object
		Resources rs = getResources();

		

		// Get a handle to a list of map overlays
		mMapOverlays = mMapView.getOverlays();
		
		//Query the device location
		getDeviceLocation();
		
		
		//new AsyncWebServiceCall().execute(params);
		
		
    }
	
	/*public void should_partial_read(File baseDir) throws Exception 
	{
	
		BufferedReader br = new BufferedReader(new FileReader(new File("/heavy_users.psv")));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		br.close();
		sb.toString();
	}*/
	
	@Override
	protected boolean isRouteDisplayed() 
	{
		return false;
	}

	
	//Get the device current location
	public void getDeviceLocation() {

		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (lm != null) {
			// Gets the provider who satifies the above criteria
			String gpsProvider = LocationManager.GPS_PROVIDER;

			if (lm.isProviderEnabled(gpsProvider) == false) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				builder.setMessage("GPS is disabled on your device.\n Would you like to enable it?");
				builder.setCancelable(false);

				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								Intent locationIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivityForResult(locationIntent,
										LOC_REQUEST_CODE);
							}
						});

				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();
			} else {
				// Query the last known location
				Location location = lm.getLastKnownLocation(gpsProvider);

				// Initial location update
				updateWithLocation(location);

				// Register for location updates
				lm.requestLocationUpdates(gpsProvider, MIN_UPDATE_INTERVAL,
						MIN_UPDATE_DISTANCE, new UserLocationListener());
			}

		}
	}
	
	//Implementation of the location listener
	private class UserLocationListener implements LocationListener
	{
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){}
		
		@Override
		public void onProviderEnabled(String provider){}
		
		@Override
		public void onProviderDisabled(String provider) {
			updateWithLocation(null);
			//Toast.makeText(getApplicationContext(), "Enable GPS for this app to work", Toast.LENGTH_LONG).show();
		}
		@Override
		public void onLocationChanged(Location location) 
		{
			updateWithLocation(location);
		}
	}
	private class AsyncWebServiceCall extends AsyncTask<String, Void, Void>
	{
		@Override
		protected Void doInBackground(String... params) 
		{
			
		    try
		    {
		    	getWebData(params[0]);
			} 
			catch (Exception e) 
			{
						Log.e(TAG, "Error calling a web service", e);
						
			}
			return null;
		}
		
		
	}
    public ArrayList<Meter> parseData(String data)
    {	
    	 String [] raw =  data.split("\n");
         StringTokenizer tokenizer = null;
         ArrayList<Meter> meterData = new ArrayList<Meter>();
         
    	 for(int i = 1; i < raw.length;i++)
    	 {
    		 tokenizer =  new StringTokenizer(raw[i],DELIM);
    		 
    		 while(tokenizer.hasMoreTokens())
    		 {
    			 
    			  Location location =  new Location("");
    			  
    			  Double lon = Double.parseDouble(tokenizer.nextToken());
    			  location.setLongitude(lon.doubleValue());
    			  
    			  Double lat  = Double.parseDouble(tokenizer.nextToken());
    			  location.setLatitude(lat.doubleValue());
    			  
    			  Double rating = Double.parseDouble(tokenizer.nextToken());
    			  
    			  String address = tokenizer.nextToken();
    			  String meterid = tokenizer.nextToken();
    			  
    			  Meter meter =  new Meter(location, rating.doubleValue(), address, meterid);
    			  meterData.add(meter);
    	   }
    	 }
    	 return meterData;
    }
    
	public void getWebData(String uri) throws IOException 
	{
		Log.i(TAG, uri);
		URL url = new URL(uri);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.connect();
		
		if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) 
		{
			InputStream is = urlConnection.getInputStream();
			byte[] buf = new byte[1024];
			int len;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			is.close();
			//parseData(os.toString());
		}
		//return null;
	}
	
	//Update map with location data
	public void updateWithLocation(Location location)
	{
		
		if(location != null)
		{
			
			
			
		     String params = "latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude();
		     
		     //new AsyncWebServiceCall().execute("http://wh:wat3rDawg$@41.193.66.114:5980/e-breadboard/ebr-cgi/h2o-detective-consumers?"+ params);
		     
		     Intent intent =  getIntent(); 
		     
		     int color =  intent.getIntExtra("color", 0);
		     
		     
		     Double latitude =  location.getLatitude()*  1E6;
		     Double longitude = location.getLongitude()*  1E6;
		     
		     GeoPoint geoPoint = new GeoPoint(latitude.intValue(), longitude.intValue()); 
		     
		     // Get a handle to a drawable map marker
			 Drawable positionMarker = getResources().getDrawable(R.drawable.person);

		     // Get a handle to an itemized overlay
			 PositionOverlay positionOverlay = new PositionOverlay(positionMarker, this);
			 positionOverlay.addOverlayItem(geoPoint, null, null);
			 mMapOverlays.add(positionOverlay);
		    
			 mMapController.animateTo(geoPoint);
	    	 
			 ArrayList<Meter> data = parseData(content);
		     Iterator<Meter> it = data.iterator();
		     
		     Drawable marker = null;
		     
		    /* switch(color)
	    	  {
	    	  	// Get a handle to a drawable map marker
	    	  case RED:
		      	 marker = getResources().getDrawable(R.drawable.red);
		      	 
		      	 break;
	    	  case GREEN:
	    		 marker = getResources().getDrawable(R.drawable.green);
	    		 break;
	    	  case ORANGE:
	    		  marker = getResources().getDrawable(R.drawable.orange);
	    		  break;
	    	  case BLACK:
	    		  marker = getResources().getDrawable(R.drawable.black);
	    		  break;
	    	  default:
	    		   //Do nothing
	    		  
	    	  }*/
		     
		     while(it.hasNext())
		     {
		    	 
		    	  Meter meter = it.next();
		    	  
		    	  if(meter.getRating() >= BIGGEST && color == RED)
		    	  {
		    		  marker = getResources().getDrawable(R.drawable.red);
		    		  latitude = meter.getLocation().getLatitude() * 1E6;
			    	  longitude = meter.getLocation().getLongitude() * 1E6;
			    	 
			    	  GeoPoint destPoint = new GeoPoint(latitude.intValue(), longitude.intValue());
			    	  
			    	  positionOverlay = new PositionOverlay(marker, this);
				      positionOverlay.addOverlayItem(destPoint, null, null);
				      mMapOverlays.add(positionOverlay);  
		    	  }
		    	  else if(meter.getRating() >= AVERAGE && color == GREEN)
		    	  {
		    		  marker = getResources().getDrawable(R.drawable.green);
		    		  latitude = meter.getLocation().getLatitude() * 1E6;
			    	  longitude = meter.getLocation().getLongitude() * 1E6;
			    	 
			    	  GeoPoint destPoint = new GeoPoint(latitude.intValue(), longitude.intValue());
			    	  
			    	  positionOverlay = new PositionOverlay(marker, this);
				      positionOverlay.addOverlayItem(destPoint, null, null);
				      mMapOverlays.add(positionOverlay); 
		    		  
		    	  }
		    	  else if(meter.getRating() >= LOW && color == ORANGE)
		    	  {
		    		  marker = getResources().getDrawable(R.drawable.orange);
		    		  latitude = meter.getLocation().getLatitude() * 1E6;
			    	  longitude = meter.getLocation().getLongitude() * 1E6;
			    	 
			    	  GeoPoint destPoint = new GeoPoint(latitude.intValue(), longitude.intValue());
			    	  
			    	  positionOverlay = new PositionOverlay(marker, this);
				      positionOverlay.addOverlayItem(destPoint, null, null);
				      mMapOverlays.add(positionOverlay); 
		    		  
		    	  }
		    	  else if(meter.getRating() < LOW && color == BLACK)
		    	  {
		    		  marker = getResources().getDrawable(R.drawable.black);
		    		  latitude = meter.getLocation().getLatitude() * 1E6;
			    	  longitude = meter.getLocation().getLongitude() * 1E6;
			    	 
			    	  GeoPoint destPoint = new GeoPoint(latitude.intValue(), longitude.intValue());
			    	  
			    	  positionOverlay = new PositionOverlay(marker, this);
				      positionOverlay.addOverlayItem(destPoint, null, null);
				      mMapOverlays.add(positionOverlay); 
		    	  }
		    	  
		    	  
		     }	 
	    	
		}
		
	}
	public void plotMarker()
	{
		
	}
	public void readFile(String filename)
	{
		File directory =  Environment.getExternalStorageDirectory();
		try
		{
			File file =  new File(directory,  filename);
			FileReader fileReader =  new FileReader(file);
			BufferedReader bufferedReader =  new BufferedReader(new FileReader (new File(directory, content)));
		    String s;
		    StringBuilder builder =  new StringBuilder();
		    
			while((s = bufferedReader.readLine()) != null)
			{
				builder.append(s);
			}
			parseData(builder.toString());
		}
		catch(Exception e)
		{
			Log.e(TAG, e.getMessage(),e);
		}
	}

	String content = "LON|LAT|RATING|INFO|METER_ID||ADDRESS\n" +
	"28.202085|-25.767634|2.00|25 Exhibition Road, Hermanus|555-1234-25\n" +
	"28.198085|-25.767634|1.20|26 Exhibition Road, Hermanus|555-1234-26\n" +
	"28.200085|-25.765634|1.53|27 Exhibition Road, Hermanus|555-1234-27\n" +
	"28.200085|-25.769634|1.53|28 Exhibition Road, Hermanus|555-1234-28\n"+
	"28.200098|-25.76318||0.63|28 Exhibition Road, Hermanus|555-1234-29\n"+
	"28.201622|-25.763083|2.23|28 Exhibition Road, Hermanus|555-1234-30\n"+
	"28.199905|-25.761962|0.53|28 Exhibition Road, Hermanus|555-1234-31\n"+
	"28.200806|-25.759489|2.53|28 Exhibition Road, Hermanus|555-1234-32\n"+
	"28.200012|-25.76204|1.83|28 Exhibition Road, Hermanus|555-1234-33\n"+
	"28.201278|-25.763663|0.53|28 Exhibition Road, Hermanus|555-1234-34\n";
	
	
	/*String content = "LON|LAT|RATING|INFO|METER_ID||ADDRESS\n" +
	"31.1442|30.28776|1.53|25 Exhibition Road, Hermanus|555-1234-25\n" +
	"31.1242|30.18776|1.53|26 Exhibition Road, Hermanus|555-1234-26\n" +
	"31.1342|30.38776|1.53|27 Exhibition Road, Hermanus|555-1234-27\n" +
	"31.1642|30.08776|1.53|28 Exhibition Road, Hermanus|555-1234-28\n" +
	"31.1342|30.58776|1.53|29 Exhibition Road, Hermanus|555-1234-29\n";*/
}
