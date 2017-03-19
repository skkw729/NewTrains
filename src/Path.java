import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Path extends Timeline 
{
	private List<Stations> stations;
	private Map<Stations, Train> train;
	private Map<Stations, Date> transfer;
	public Path() 
	{
		super();
		train = new HashMap<>();
		transfer = new HashMap<>();
		stations = new ArrayList<>();
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
}
