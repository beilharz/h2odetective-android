package za.co.hermanus.hackathon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AppActivity extends Activity  implements OnItemClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_list);
		
		ListView lv =  (ListView) findViewById(R.id.listview);
	    int [] status_icons = new int[]{R.drawable.red, R.drawable.green, R.drawable.orange, R.drawable.black};
	    SimpleAdapter simpleAdapter = populateListAdpater(R.array.users,status_icons);
	    lv.setAdapter(simpleAdapter);
	    lv.setOnItemClickListener(this);
	}
	
	
	public SimpleAdapter populateListAdpater(int menuNamesArrayId, int [] menuItemIconIds)
 	{
 		SimpleAdapter simpleAdapter = null;
 	    try
        {
        	 
        	List<HashMap<String,Object>> listData =  new ArrayList<HashMap<String,Object>>();
        	String [] menuItemNames =  getResources().getStringArray(menuNamesArrayId);	
        	
        
        	for(int i = 0; i < menuItemNames.length;i++)
        	{
        		HashMap<String,Object> map = new HashMap<String,Object>();
        		map.put("status_icon", menuItemIconIds[i]);
        		map.put("status_text", menuItemNames[i]);
        		listData.add(map);
        	}
      
        	String []  mapKeys = {"status_icon","status_text"};
        	int [] viewIds = new int[]{R.id.status_icon, R.id.status_text};
        	
        	simpleAdapter = new SimpleAdapter(this, listData, R.layout.status_list_item, mapKeys, viewIds);
        	
        }
        catch(Exception e)
        {
        	simpleAdapter = null;
        	Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        	//Log.e(TAG, "Error in activity",  e);
        }
 	    return simpleAdapter;
 		
 	}


	@Override
	public void onItemClick(AdapterView<?> parent, View item, int position, long id) 
	{
		Intent intent = new Intent(this, H2MapActivity.class);
		intent.putExtra("color", position);
		startActivity(intent);
		
	}
}
