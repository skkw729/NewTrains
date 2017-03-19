import java.util.*;
/*
 * A class to store arrival/departure times
 */
public class Timeline 
{
	private HashMap<Stations,Date> arriveTime;
	private HashMap<Stations, Date> departTime;
	
	public Timeline()
	{
		arriveTime = new HashMap<>();
		departTime = new HashMap<>();
	}
	public void putArriveTime(Stations s, Date d)
	{
		arriveTime.put(s, d);
	}
	public void putDepartTime(Stations s, Date d)
	{
		departTime.put(s, d);
	}
	public Date getArriveTime(Stations s)
	{
		return arriveTime.get(s);
	}
	public Date getDepartTime(Stations s)
	{
		return departTime.get(s);
	}
}

