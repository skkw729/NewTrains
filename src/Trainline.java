import java.util.ArrayList;
import java.util.Collections;
/*
 * class to store a list of stations
 */
public class Trainline {

	private ArrayList<Stations> trainLine;
	private String name;
	private Stations startStation;
	private Stations endStation;
	
	public Trainline(String n)
	{
		trainLine = new ArrayList<Stations>();
		name = n;
	}
	public Trainline(String n, ArrayList<Stations> stations)
	{
		name = n;
		trainLine = stations;
	}
	public void addStation(Stations s)
	{
		trainLine.add(s);
	}
	public void removeStation(Stations s)
	{
		trainLine.remove(s);
	}
	public String getName()
	{
		return name;
	}
	public Stations getStartStation()
	{
		return startStation;
	}
	public Stations getEndStation()
	{
		return endStation;
	}
	public void completeTrainline()
	{
		setStartStation();
		setEndStation();	
	}
	public ArrayList<Stations> getStationsList()
	{
		return trainLine;
	}
	public Stations getStationAfter(Stations s)
	{
		int trainIndex = trainLine.indexOf(s);
		return trainLine.get(trainIndex+1);
	}
	public void setStartStation()
	{
		startStation = trainLine.get(0);
	}
	public void setEndStation()
	{
		endStation = trainLine.get(trainLine.size()-1);
	}
	/*
	 * A method to return the list of stations in reverse order
	 */
	public ArrayList<Stations> reverseLine()
	{
		ArrayList<Stations> reverseList = trainLine;
		Collections.reverse(reverseList);
		return reverseList;
	}
}
