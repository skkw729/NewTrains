import java.util.*;
public class Train {

	private Trainline trainline;//list of stations that this train travels to
	private Timeline timeline;//arrival and departure times of this train to all destination stations
	private Date startTime;//train arrives at start time
	private Stations startStation;
	private Stations endStation;
	private static final int WAIT_TIME = 2; //Train waits 2 minutes before departing
	
	public Train(Trainline tl, Date t)
	{
		trainline = tl;
		startTime = t;
		startStation = trainline.getStationsList().get(0);
		endStation = trainline.getStationsList().get(trainline.getStationsList().size()-1);
		timeline = new Timeline();
		makeTimeline();
		
	}
	public void makeTimeline()
	{
		timeline.putArriveTime(startStation, startTime);//store the arrival time at the first station
		List<Stations> stationList = trainline.getStationsList();
		Stations from = startStation;//station that the train is travelling from
		Stations to = startStation;//station that the train is travelling to
		Date t1 = startTime;//store start time in Date object
		Calendar calendar = Calendar.getInstance();//create calendar object
		calendar.setTime(t1);//set the new calendar object to have the time stored in Date object
		calendar.add(Calendar.MINUTE, WAIT_TIME); //add wait time to the startTime
		Date departTime = calendar.getTime();//store the resulting time in a Date object
		timeline.putDepartTime(startStation, departTime); //store departureTime in hashmap
		for(int i=1; i<stationList.size(); i++)//iterate for all stations except for startStation
		{
			from = stationList.get(i-1);
			to = stationList.get(i);
			calendar.add(Calendar.MINUTE,from.getConnectionTime(to));
			t1 = calendar.getTime();
			timeline.putArriveTime(to, t1);
			calendar.add(Calendar.MINUTE, WAIT_TIME);
			departTime = calendar.getTime();
			timeline.putDepartTime(to, departTime); 
		}
	}
	/*
	 * prints the arrival/departure times for test purposes
	 */
	public void printTimeline()
	{
		for(Stations s:trainline.getStationsList())
		{
			String name = s.getName();
			Calendar time = Calendar.getInstance();//create new calendar
			time.setTime(timeline.getArriveTime(s));//set it to the arrival time at station s
			Date date = time.getTime();//new date object to store time
			System.out.println("Calling at "+name+" at "+DateTime.dateToString(date));
			
		}
	}
	public List<Stations> getStationList()
	{
		return trainline.getStationsList();
	}
	public Timeline getTimeline()
	{
		return timeline;
	}
	public Trainline getTrainline()
	{
		return trainline;
	}
	public Stations getStartStation()
	{
		return startStation;
	}
	public Stations getEndStation()
	{
		return endStation;
	}
	public boolean passStation(Stations s)
	{
		return trainline.getStationsList().contains(s);
	}
	public Stations getStationAfter(Stations s)
	{
		Stations station=null;
		if(passStation(s)&&!s.equals(endStation))
		{
			station = trainline.getStationAfter(s);
		}
		return station;
	}
	public String toString()
	{
		String startTimeString = DateTime.dateToString(startTime);
		return startTimeString+" from "+startStation.getName()+" to "+endStation.getName();
	}
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		if(!(obj instanceof Train)) return false;
		Train t = (Train) obj;
		return toString().equals(t.toString());
	}
	
}
