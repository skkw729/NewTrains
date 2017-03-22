import java.util.ArrayList;
import java.util.List;

public class PathVia implements Path
{
	private ShortestPath first;
	private ShortestPath second;
	
	public PathVia(ShortestPath first, ShortestPath second)
	{
		this.first = first;
		this.second = second;
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
	public void print() {
		// TODO Auto-generated method stub
		
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
				routeTimes = routeTimes + "Arriving in"+s+" at "+DateTime.dateToString(second.getArriveTime(s))+"\n";
			}
		}
		
		return null;
	}
}
