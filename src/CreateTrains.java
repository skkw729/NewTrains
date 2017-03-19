import java.util.ArrayList;
import java.util.Date;
/*
 * A class storing methods used in the creation of trains
 */
public class CreateTrains 
{
	/*
	 * Creates trains for both directions of a given trainline
	 * frequency of 1 every 30minutes
	 */
	public static ArrayList<Train> createTrains(Trainline trainline)
	{
		ArrayList<Train> trains = new ArrayList<>();
		//assuming earliest train at 07:00 and latest train at 22:00		
		for(int i=7;i<23;i++)
		{
			//Create Date object to store startTime
			Date startTime = DateTime.setDate(i, 0);
			Train t = new Train(trainline, startTime);
			//reverse connection time not implemented yet
//			Trainline reverse = new Trainline(trainline.getName()+" south", trainline.reverseLine());
//			Train tReverse = new Train(reverse, startTime);
			trains.add(t);
//			trains.add(tReverse);
			if(i<22)
			{
				startTime = DateTime.setDate(i, 30);
				t = new Train(trainline, startTime);
//				tReverse = new Train(reverse, startTime);
				trains.add(t);
//				trains.add(tReverse);
			}
		}
		return trains;
	}
	public static void addReverseConnection(Trainline trainline)
	{
		ArrayList<Stations> stations = trainline.getStationsList();
		addReverseConnection(stations);
	}
	public static void addReverseConnection(ArrayList<Stations> stations)
	{
		for(Stations s:stations)
		{
			for(Stations s2:stations)
			{
				try
				{
					s2.addConnection(s, s.getConnectionTime(s2));
				}
				catch (NullPointerException e){}
			}
		}
	}
	/*
	 * returns the reverse order of a given trainline
	 */
	public static Trainline reverseLine(Trainline line)
	{
		Trainline reverseLine = new Trainline(line.getName()+" South");
		for(int i =0; i<line.getStationsList().size(); i++)
			{
				reverseLine.addStation(line.getStationsList().get(line.getStationsList().size()-1-i));
			}
		reverseLine.completeTrainline();
		return reverseLine;
	}
	
}
