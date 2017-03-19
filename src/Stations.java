import java.util.*;
public class Stations
{
	private String name;
	private String code;
	private HashMap<Stations,Integer> connections;	
	public Stations(String name, String code)
	{
		this.name = name;
		this.code = code;
		connections = new HashMap<>();
	}
	/*
	 * A method that adds a connecting station and the time taken to get there in minutes
	 */
	public void addConnection(Stations s, int minutes)
	{
		connections.put(s, minutes);
	}
	public void removeConnection(Stations s)
	{
		connections.remove(s);
	}
	public String getName()
	{
		return name;
	}
	public String getCode()
	{
		return code;
	}
	public void setName(String n)
	{
		name=n;
	}
	public void setCode(String c)
	{
		code=c;
	}
	/*
	 * A method to get the time in minutes to reach the given station
	 * returns null if it doesn't connect
	 */
	public int getConnectionTime(Stations s)
	{
		if(connections.get(s)!=null)
		{
			return connections.get(s);
		}
		else 
		{
			return -1;
		}
	}
	public boolean equals(Stations s)
	{
		return s.getCode().equals(this.code);
	}
	public String toString()
	{
		return name;
	}
	
}
