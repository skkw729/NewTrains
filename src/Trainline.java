import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
 * class to store a list of stations
 */
public class Trainline {

	private List<Stations> trainLine;
	private String name;
	private Stations startStation;
	private Stations endStation;
	
	public Trainline()
	{
		trainLine = new ArrayList<>();
		name = "";
	}
	public Trainline(List<Stations> stations)
	{
		name = "";
		trainLine = stations;
	}
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
	public void setName(String n)
	{
		name = n;
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
	public List<Stations> getStationsList()
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
	public List<Stations> reverseLine()
	{
		List<Stations> reverseList = new ArrayList<>();//make a new list to avoid side effects
		reverseList.addAll(trainLine);
		Collections.reverse(reverseList);
		return reverseList;
	}
}
