import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestPath extends Timeline implements Path 
{
	private List<Stations> stations;
	private Map<Stations, Train> train;
	private Map<Stations, Date> transfer;
	public ShortestPath() 
	{
		super();
		train = new HashMap<>();
		transfer = new HashMap<>();
		stations = new ArrayList<>();
	}
	public int getNumberOfTransfers()
	{
		return transfer.size();
	}
	public List<Stations> getTransferStations()
	{
		List<Stations> transferStations = new ArrayList<>();
		if(!transfer.isEmpty())
		{
			for(Stations s:stations)
			{
				Date d = transfer.get(s);
				if(d!=null)
				{
					transferStations.add(s);
				}
			}
		}
		return transferStations;
	}
	public Map<Stations, Train> getTrainMap()
	{
		return train;
	}
	public Train getTrain(Stations s)
	{
		return train.get(s);
	}
	public void putTrain(Stations s, Train t)
	{
		train.put(s, t);
	}
	public Date getTransferTime(Stations s)
	{
		return transfer.get(s);
	}
	public void putTransferStation(Stations s, Date time)
	{
		transfer.put(s, time);
	}
	public void setStations(List<Stations> list)
	{
		stations = list;
	}
	public List<Stations> getStations()
	{
		return stations;
	}
	public void print()
	{
		System.out.println(getRoute());
		System.out.println(getRouteTimes());
		System.out.println(getTransfers());
	}
	public String getRoute()
	{
		String route = "The fastest route is: ";
		for(Stations s:stations)
		{
			route = route + s.getCode()+" ";
		}
		return route;
	}
	public String getRouteTimes()
	{
		Stations departureStation = stations.get(0);
		Stations lastStation = stations.get(stations.size()-1);
		String routeTimes = "Departing from "+departureStation+" at "+DateTime.dateToString(super.getDepartTime(departureStation))+"\n";
		for(Stations s:stations)
		{
			if(s!=departureStation)
			{
				if(s!=(lastStation))
				{
					routeTimes = routeTimes+"Arriving in "+s+" at "+DateTime.dateToString(super.getArriveTime(s))+"\n";
				}
				else
				{
					routeTimes = routeTimes+"Arriving in "+s+" at "+DateTime.dateToString(super.getArriveTime(s));
				}
			}
		}
		return routeTimes;
	}
	public String getTransfers()
	{
		String transfers = "";
		if(!transfer.isEmpty())
		{
			transfers = transfers+"This route has "+transfer.size()+" change(s)\n";
			for(Stations s: stations)
			{
				Date transferTime = transfer.get(s);
				if(transferTime!=null)
				{
					transfers = transfers+"Transfer at "+s+" departing at "+DateTime.dateToString(transferTime)+"\n";
				}
			}
		}
		int minutes = DateTime.dateDiff(super.getArriveTime(stations.get(stations.size()-1)), super.getDepartTime(stations.get(0)));
		transfers = transfers+"Total travel time: "+minutes+" minutes.\n";
		return transfers;
	}
}
