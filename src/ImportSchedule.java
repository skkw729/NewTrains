import java.io.*;
import java.util.*;
public class ImportSchedule 
{
	public static TrainSchedule importSchedule() throws FileNotFoundException
	{
		Scanner stations;
		Scanner connections;
		Scanner trainlines;
		TrainSchedule schedule = new TrainSchedule();
			
		stations = new Scanner(new FileReader("data/stations.csv"));
		connections = new Scanner(new FileReader("data/connections.csv"));
		trainlines = new Scanner(new FileReader("data/trainlines.csv"));
		
		//stations
		List<Stations> stationList = new ArrayList<Stations>();
		stations.nextLine();//skip the first line of column titles
		while(stations.hasNext())
		{
			Scanner line = new Scanner(stations.nextLine()).useDelimiter(",");
			String name = line.next();
			String code = line.next();
			Stations station = new Stations(name,code);
			stationList.add(station);	
		}
		stations.close();
		//connections
		connections.nextLine();//skip the first line of column titles
		while(connections.hasNext())
		{
			Scanner line = new Scanner(connections.nextLine()).useDelimiter(",");
			String from = line.next();
			String to = line.next();
			int minutes = Integer.parseInt(line.next());
			Stations fromStation = null;
			Stations toStation = null;
			
			//match String to Station using Station code
			for(Stations s:stationList)
			{
				if(from.equals(s.getCode()))
				{
					fromStation = s;
				}
				else if(to.equals(s.getCode()))
				{
					toStation = s;
				}
			}
			if(fromStation==null || toStation==null)
				throw new IllegalStateException("null stations found in connections");
			fromStation.addConnection(toStation, minutes);
			
		}
		connections.close();
		//trainlines
		List<Trainline> trainlineList = new ArrayList<>();
		
		while(trainlines.hasNext())
		{
			Trainline trainline = new Trainline();
			Scanner line = new Scanner(trainlines.nextLine()).useDelimiter(",");
			while(line.hasNext())
			{
				String station = line.next();
				Stations stationRef = null;
				//match station using station code
				for(Stations s:stationList)
				{
					if(s.getCode().equals(station))
					{
						stationRef = s;
					}
				}
				if(stationRef==null) {
					throw new IllegalStateException("null station found in trainlines");
				}
				trainline.addStation(stationRef);
			}
			//all stations have been added to the trainline
			trainline.completeTrainline();//initialise start and end stations
			trainline.setName(trainline.getStartStation()+" to "+trainline.getEndStation());
			trainlineList.add(trainline);
		}
		//add trainlines to schedule to import all information
		for(Trainline t: trainlineList)
		{
			schedule.addTrainline(t);
		}
		return schedule;
		

	}
}
