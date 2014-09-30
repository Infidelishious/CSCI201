package edu.usc.ianglow;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event {
	String title, location;
	Calendar start, end;
	
	public Event(String title, String location, Calendar start, Calendar end)
	{
		this.title = title;
		this.location = location;
		this.start = start;
		this.end = end;
	}
	
	public String toString()
	{
		SimpleDateFormat format1 = new SimpleDateFormat("h:mm a");
		
		return "" + title + " at "
				+ location + " from "
				+ format1.format(start.getTime()) + " - "
				+ format1.format(end.getTime());
	}
	
	public String toCSVLine()
	{
		SimpleDateFormat format1 = new SimpleDateFormat("h:mm:ss a");
		
		return "" + start.get(Calendar.YEAR) + ","
				+ new DateFormatSymbols().getMonths()[start.get(Calendar.MONTH) - 1] + ","
				+ start.get(Calendar.DAY_OF_MONTH) + ","
				+ title + ","
				+ location + ","
				+ format1.format(start.getTime()) + ","
				+ format1.format(end.getTime()) + "\n";
	}
	

}
