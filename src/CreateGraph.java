import java.util.*;
public class CreateGraph 
{
	private TrainSchedule schedule;
	private ArrayList<Stations> stations;
	private Map<Stations, Map<Stations, List<Train>>> trainConnections;//for a given station, returns a list of trains that connect it to any other given station
	private Map<Stations, Stations> preStation;//the station that preceeds the given station
	public CreateGraph(TrainSchedule schedule)
	{
		this.schedule = schedule;
		stations = schedule.getStationsList();
		trainConnections = new HashMap<>();
		preStation = new HashMap<>();
	}
	public void clearGraph()
	{
		preStation = new HashMap<>();
		trainConnections = new HashMap<>();
	}
	public List<Stations> getStations()
	{
		return stations;
	}
	public void setTrainConnections()
	{
		for(Stations from:stations)
		{
			for(Stations to:stations)
			{
				if(!from.equals(to))
				{
					if(from.getConnectionTime(to)!=-1)//if the connection exists
					{
						List<Train> directTrains  = getDirectTrains(from,to);
						if(!directTrains.isEmpty())
						{
							Map<Stations, List<Train>> connections = getTrainConnections(from);//update the currently stored connection data if one exists
							if(connections!=null) 
							{
								connections.put(to, directTrains);
							}
							else//otherwise create a new hashmap and store the connection data
							{
								connections = new HashMap<>();
								connections.put(to, directTrains);
							}
							trainConnections.put(from, connections);
						}
					}
				}
			}
		}
	}
	/*
	 * list of trains such that the destination station is the station immediately after the departure station
	 */
	public ArrayList<Train> getDirectTrains(Stations from, Stations to)
	{
		ArrayList<Train> trains = schedule.getTrains(from);//all trains that pass through departure station
		ArrayList<Train> directTrains = new ArrayList<>();
		for(Train t:trains)
		{
			if(t.getStationAfter(from)!=null && t.getStationAfter(from).equals(to))
			{
				directTrains.add(t);
			}
		}
		return directTrains;
	}
	public Map<Stations, List<Train>> getTrainConnections(Stations s)
	{
		return trainConnections.get(s);
	}
	public Stations getPreStation(Stations s)
	{
		return preStation.get(s);
	}
	public void setPreStation(Stations station, Stations pre)
	{
		preStation.put(station, pre);
	}
}
