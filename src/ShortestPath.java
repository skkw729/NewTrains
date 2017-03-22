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
		System.out.print("The best route is: ");
		for(Stations s:stations)
		{
			System.out.print(s.getCode()+" ");
		}
		System.out.println("");
		System.out.println("Departing from "+stations.get(0)+" at "+DateTime.DateToString(super.getDepartTime(stations.get(0))));
		for(int i=1;i<stations.size();i++)
		{
			Stations s = stations.get(i);
			System.out.println("Arriving in "+s+" at "+DateTime.DateToString(super.getArriveTime(s)));
		}
		if(!transfer.isEmpty())
		{
			System.out.println("This route has "+transfer.size()+" change(s)");
			for(Stations s: stations)
			{
				Date transferTime = transfer.get(s);
				if(transferTime!=null)
				{
					System.out.println("Transfer at "+s+" departing at "+DateTime.DateToString(transferTime));
				}
			}
		}
		int minutes = DateTime.dateDiff(super.getArriveTime(stations.get(stations.size()-1)), super.getDepartTime(stations.get(0)));
		System.out.println("Total travel time: "+minutes+" minutes.");
	}
	public String getRoute()
	{
		return null;
	}
	public String getRouteTimes()
	{
		return null;
	}
	/*
	 * combines two paths into a single path
	 */
//	public Path mergePath(Path otherPath)
//	{
//		Stations lastStation = stations.get(stations.size()-1);//last station of the first path
//		Stations firstStation = otherPath.getStations().get(0);//first station of the second path
//		Stations viaStation = null;
//		if(lastStation.equals(firstStation))//if both stations are the same, then set it to be the viaStation 
//		{
//			viaStation = lastStation;
//		}
//		else throw new IllegalStateException("Connecting stations do not match");
//		stations.addAll(otherPath.getStations());//combine list of stations
//		stations.remove(viaStation);//remove duplicate viaStation (if present)
//		train.putAll(otherPath.getTrain());
//		//set best train for arrival at viaStation
//		//handle the case where stations are visited more than once
//		return null;
//	}
}
