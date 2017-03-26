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
	 */
	public static List<Train> createTrains(Trainline trainline)
	{
		List<Train> trains = new ArrayList<>();
		//assuming earliest train at 07:00 and latest train at 22:00		
		for(int i=7;i<23;i++)
		{
			//Create Date object to store startTime
			Date startTime = DateTime.setDate(i, 0);
			Train t = new Train(trainline, startTime);
			trains.add(t);
			if(i<22)
			{
				startTime = DateTime.setDate(i, 30);
				t = new Train(trainline, startTime);
				trains.add(t);
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
