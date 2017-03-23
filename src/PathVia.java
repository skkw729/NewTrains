import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PathVia implements Path
{
	private ShortestPath first;
	private ShortestPath second;
	private Stations via;
	
	public PathVia(ShortestPath first, ShortestPath second,Stations via)
	{
		this.first = first;
		this.second = second;
		this.via = via;
	}
	public List<Stations> getStations()
	{
		List<Stations> stationList = new ArrayList<>(); 
		stationList.addAll(first.getStations());
		stationList.remove(second.getStations().get(0));
		stationList.addAll(second.getStations());
		return stationList;
	}
	@Override
	public void print() 
	{
		System.out.println(getRoute());
		System.out.println(getRouteTimes());
		System.out.println(getTransfers());
		
	}
	@Override
	public String getRoute() {
		String route = "The fastest route is: ";
		for(Stations s:getStations())
		{
			route= route+s.getCode()+" ";
		}
		return route;
	}
	@Override
	public String getRouteTimes() 
	{
		Stations departureStation = first.getStations().get(0);
		String routeTimes = "Departing from "+departureStation+" at "+DateTime.dateToString(first.getDepartTime(departureStation))+"\n";
		for(Stations s:first.getStations())
		{
			if(s!=departureStation)
			{
				routeTimes = routeTimes+"Arriving in "+s+" at "+DateTime.dateToString(first.getArriveTime(s))+"\n";
			}
		}
		Stations firstStationOfSecondPath = second.getStations().get(0);
		for(Stations s:second.getStations())
		{
			if(s!=firstStationOfSecondPath)
			{
				routeTimes = routeTimes + "Arriving in "+s+" at "+DateTime.dateToString(second.getArriveTime(s))+"\n";
			}
		}
		return routeTimes;
	}
	public String getTransfers()
	{
		int numberOfTransfers = first.getNumberOfTransfers()+second.getNumberOfTransfers();
		Stations transferStation=null;
		if(!first.getTrain(via).equals(second.getTrain(via)))
		{
			numberOfTransfers++;
			transferStation = via;
		}
		String transfers = "This route has "+numberOfTransfers+" change(s)\n";
		for(Stations s:first.getStations())
		{
			Date transferTime = first.getTransferTime(s);
			if(transferTime!=null)
			{
				transfers = transfers+"Transfer at "+s+" departing at "+DateTime.dateToString(transferTime)+"\n";
			}
		}
		if(transferStation !=null)
		{
			Date transferTime = second.getDepartTime(transferStation);
			transfers = transfers+"Transfer at "+transferStation+" departing at "+DateTime.dateToString(transferTime)+"\n";
		}
		for(Stations s:second.getStations())
		{
			Date transferTime = second.getTransferTime(s);
			if(transferTime!=null)
			{
				transfers = transfers+"Transfer at "+s+" departing at "+DateTime.dateToString(transferTime)+"\n";
			}
		}
		return transfers;
	}
}
