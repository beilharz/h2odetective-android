package za.co.hermanus.hackathon;

import android.location.Location;

public class Meter 
{
	private Location location;
	private double rating;
	private String address;
	private String meterid;
	
	public Meter()
	{	
	}
	public Meter(Location location, double rating, String address,
			String meterid) {
		super();
		this.location = location;
		this.rating = rating;
		this.address = address;
		this.meterid = meterid;
	}
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMeterid() {
		return meterid;
	}

	public void setMeterid(String meterid) {
		this.meterid = meterid;
	}	
}
