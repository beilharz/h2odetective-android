package za.co.hermanus.hackathon;

import java.util.ArrayList;

import android.content.Context;

import android.graphics.drawable.Drawable;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class PositionOverlay extends ItemizedOverlay<OverlayItem> 
{

	Context mContext;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>(); 
	
	public PositionOverlay(Drawable defaultMarker, Context context)
	{  
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	
	//Add a marker overlay to the map
	public void addOverlayItem(GeoPoint geoPoint, String markerText, String snippet)
	{
		if(mOverlays.size() > 0)
		{
			  clearItems(); 
		}
		mOverlays.add(new OverlayItem(geoPoint, markerText, snippet));    
		populate();
		
	}
	public void clearItems()
	{
		mOverlays.clear();
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int index) 
	{
		return mOverlays.get(index);
	}

	
	@Override
	public int size() 
	{
		return mOverlays.size();
	}

	//Handle a tap event on the map marker
	@Override
	protected boolean onTap(int index)
	{    
		return false;
	}

}
