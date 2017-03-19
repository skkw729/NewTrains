import java.util.*;
public class CreatePathVia 
{
	CreatePath2 path1;
	CreatePath2 path2;
	List<Nodes> fullPath;
	public CreatePathVia(TrainSchedule schedule, Date when, Stations from, Stations via, Stations to)
	{
		path1 = new CreatePath2(schedule, when, from, via);
		path1.startDijkstras();
		fullPath = path1.getPath();
		Date arriveTimeVia = path1.getArriveTime(path1.getEndNode());
		path2 = new CreatePath2(schedule, arriveTimeVia, via, to);
		path2.startDijkstras();
		fullPath.remove(fullPath.size()-1);
		fullPath.addAll(path2.getPath());
	}
	public void printPath()
	{
		path1.printPath(path1.getPath());
		path2.printPath(path2.getPath());
	}
}
