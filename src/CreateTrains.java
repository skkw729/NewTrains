import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
 * A class storing methods used in the creation of trains and trainlines
 */
public class CreateTrains 
{
	/*
	 * Creates trains for both directions of a given trainline
	 * frequency of 1 every 30minutes
	 * creates trains for current day and tomorrow
	 */
	public static List<Train> createTrains(Trainline trainline)
	{
		List<Train> trains = new ArrayList<>();
		Date now = new Date();
		//assuming earliest train at 07:00 and latest train at 22:00		
		for(int i=7;i<23;i++)
		{
			//Create Date object to store startTime
			Date startTime = DateTime.setDate(i, 0);
			Date tomorrow = DateTime.getTomorrow(now);
			Date startTimeTomorrow = DateTime.setDate(i, 0, tomorrow);
			Train t = new Train(trainline, startTime);
			Train t2 = new Train(trainline, startTimeTomorrow);
			trains.add(t);
			trains.add(t2);
			if(i<22)
			{
				startTime = DateTime.setDate(i, 30);
				startTimeTomorrow = DateTime.setDate(i, 30, tomorrow);
				t = new Train(trainline, startTime);
				t2 = new Train(trainline, startTimeTomorrow);
				trains.add(t);
				trains.add(t2);
			}
		}
		return trains;
	}
	public static List<Train> createTrains(Trainline trainline, Date when)
	{
		List<Train> trains = new ArrayList<>();
		//assuming earliest train at 07:00 and latest train at 22:00		
		for(int i=7;i<23;i++)
		{
			//Create Date object to store startTime
			Date startTime = DateTime.setDate(i, 0, when);
			Date tomorrow = DateTime.getTomorrow(when);
			Date startTimeTomorrow = DateTime.setDate(i, 0, tomorrow);
			Train t = new Train(trainline, startTime);
			Train t2 = new Train(trainline, startTimeTomorrow);
			trains.add(t);
			trains.add(t2);
			if(i<22)
			{
				startTime = DateTime.setDate(i, 30, when);
				startTimeTomorrow = DateTime.setDate(i, 30, tomorrow);
				t = new Train(trainline, startTime);
				t2 = new Train(trainline, startTimeTomorrow);
				trains.add(t);
				trains.add(t2);
			}
		}
		return trains;
	}
	public static void addReverseConnection(Trainline trainline)
	{
		List<Stations> stations = trainline.getStationsList();
		addReverseConnection(stations);
	}
	public static void addReverseConnection(List<Stations> stations)
	{
		for(Stations s:stations)
		{
			for(Stations s2:stations)
			{
				int i = s.getConnectionTime(s2);
				if(i!=-1)//-1 means no connection between stations s and s2
				{
					s2.addConnection(s, s.getConnectionTime(s2));
				}
			}
		}
	}
	/*
	 * returns the reverse order of a given trainline
	 */
	public static Trainline reverseLine(Trainline line)
	{
		Trainline reverseLine = new Trainline(line.getEndStation()+" to "+line.getStartStation());
		List<Stations> reverseList = line.reverseLine();
		for(Stations s:reverseList)
		{
			reverseLine.addStation(s);
		}
		reverseLine.completeTrainline();
		return reverseLine;
	}
	
}
