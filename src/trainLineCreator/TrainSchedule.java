package trainLineCreator;

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
	private void importStationList(Trainline line)
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
	/*
	 * removes a trainline from the schedule
	 */
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
	/*
	 * adds a train to a schedule
	 */
	public void addTrain(Train t)
	{
		trains.add(t);
		updateSchedule();
	}
	/*
	 * removes a train from a schedule
	 */
	public void remove(Train t)
	{
		trains.remove(t);
		updateSchedule();
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
	 * remakes the stationSchedule hashmap using the trains list
	 */
	private void updateSchedule()
	{
		for(Stations s:stationsList)
		{
			List<Train> trainlist = getTrains(s);
			stationSchedule.put(s, trainlist);
		}
	}
	/*
	 * list of trains that pass the given station
	 */
	public List<Train> getStationSchedule(Stations s)
	{
		return stationSchedule.get(s);
	}
	/*
	 * list of arrival times of trains that pass this station
	 */
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
		}
		updateSchedule();
	}
	public void createTrains(Date when)
	{
		for(Trainline lines:trainlines)
		{
			CreateTrains.addReverseConnection(lines);//add reverse connectionTimes
			List<Train> trains = CreateTrains.createTrains(lines, when);
			this.trains.addAll(trains);
		}
		updateSchedule();
	}
	public List<Stations> getStationsList()
	{
		return stationsList;
	}
	public Stations getStation(String code)
	{
		for(Stations s:stationsList)
		{
			if(s.getCode().equals(code))
				return s;
		}
		return null;
	}
}
