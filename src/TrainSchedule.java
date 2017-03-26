import java.util.*;
public class TrainSchedule 
{
	private Map<Stations, List<Train>> stationSchedule;//stores the list of trains arriving at each station
	private List<Trainline> trainlines;
	private List<Stations> stationsList;
	private List<Train> trains;
	
	public TrainSchedule()
	{
		stationSchedule = new HashMap<>();
		stationsList = new ArrayList<>();
		trainlines = new ArrayList<>();
		trains = new ArrayList<>();
	}
	/*
	 * add stations from a trainline to the list in this class
	 */
	public void importStationList(Trainline line)
	{
		//add new stations to stationList in schedule
		for(Stations s:line.getStationsList())
		{
			if(!stationsList.contains(s))
			{
				stationsList.add(s);
			}
		}
	}
	public List<Train> getTrains()
	{
		return trains;
	}
	public void addTrainline(Trainline line)
	{
		importStationList(line);//updates the stationList when adding a new trainline
		if(!trainlines.contains(line))
			{
				trainlines.add(line);
			}
		//adds the reverse trainline
		Trainline reverseLine = CreateTrains.reverseLine(line);
		importStationList(reverseLine);
		if(!trainlines.contains(reverseLine))
		{
			trainlines.add(reverseLine);
		}
	}
	public boolean removeTrainline(Trainline line)
	{
		if(trainlines.contains(line))
		{
			return trainlines.remove(line);
		}
		else
		{
			return false;
		}
	}
	public void addTrain(Train t)
	{
		trains.add(t);
		updateSchedule(t);
	}
	public void remove(Train t)
	{
		trains.remove(t);
	}
	/*
	 * returns a list of trains that travel through a given station
	 */
	public List<Train> getTrains(Stations s)
	{
		ArrayList<Train> trainList = new ArrayList<>();
		for(Train t:trains)
		{
			if(t.getStationList().contains(s))
			{
				trainList.add(t);
			}
		}
		return trainList;
	}
	/*
	 * remakes the stationSchedule hashmap
	 */
	public void updateSchedule(Train t)
	{
		for(Stations s:stationsList)
		{
			List<Train> trainlist = getTrains(s);
			stationSchedule.put(s, trainlist);
		}
	}
	public List<Train> getStationSchedule(Stations s)
	{
		return stationSchedule.get(s);
	}
	public List<Date> getArrivalTimes(Stations s)
	{
		List<Date> arrivalTimes = new ArrayList<>();
		for(Train train:trains)
		{
			if(train.passStation(s))
			{
				Date arrivingAt = train.getTimeline().getArriveTime(s);
				arrivalTimes.add(arrivingAt);
			}
		}
		return arrivalTimes;
	}
	/*
	 * prints arrival times of trains that pass the given station - used for testing
	 */
	public void printStationSchedule(Stations s)
	{
		for(Train t:trains)
		{
			if(t.passStation(s))
			{
				System.out.println(t+" arrives in " + s + " at "+DateTime.dateToString(t.getTimeline().getArriveTime(s)));
			}
		}
		
	}
	public void createTrains()
	{
		for(Trainline lines:trainlines)
		{
			CreateTrains.addReverseConnection(lines);//add reverse connectionTimes
			List<Train> trains = CreateTrains.createTrains(lines);
			this.trains.addAll(trains);
			for(Train t:trains)
			{
				updateSchedule(t);
			}
		}
	}
	public List<Stations> getStationsList()
	{
		return stationsList;
	}
}
