import java.util.*;
public class CreatePath2 
{
	CreateGraph2 graph;
	List<Stations> solvedNodes;
	List<Stations> unsolvedNodes;
	Map<Stations,Date> bestArriveTime;
	Map<Stations,Train> bestTrain;
	Stations endNode;
	Stations startNode;
	Date time;
	public CreatePath2(TrainSchedule schedule, Date when, Stations from, Stations to)
	{
		graph = new CreateGraph2(schedule, from, to);
		solvedNodes = new ArrayList<>();
		unsolvedNodes = graph.getStations();
		endNode = to;
		startNode = from;
		bestArriveTime = new HashMap<>();
		bestTrain = new HashMap<>();
		time = when;
	}
	public Stations getEndNode()
	{
		return endNode;
	}
	public Stations getStartNode()
	{
		return startNode;
	}
	public void setStartTime(Stations from, Date when)
	{
		bestArriveTime.put(startNode, when);
	}
	/*
	 *Returns the node with the best arrive time from the unsolved nodes list.
	 *Adds it to the solved nodes and removes it from unsolved nodes. 
	 */
	public Stations getNextNode()
	{
		Date bestTime = null;
		Stations node = null;
		for(Stations n:unsolvedNodes)
		{
			Date d = bestArriveTime.get(n);
			if(d!=null)
			{
				if(bestTime==null || d.before(bestTime))
				{
					bestTime = d;
					node = n;
				}
			}
		}
		solvedNodes.add(node);
		unsolvedNodes.remove(node);
		return node;
	}
	/*
	 * Returns a list of neighbour nodes from the unsolved nodes
	 * Returns an empty list if no neighbours exist
	 */
	public List<Stations> getNeighbourNodes(Stations from)
	{
		List<Stations> neighbours = new ArrayList<>();
		for(Stations to:unsolvedNodes)
		{
			List<Train> directTrains = graph.getTrainConnections(from).get(to);
			if(directTrains!=null&&!directTrains.isEmpty())
			{
				neighbours.add(to);
			}
		}
		return neighbours;
	}
	public void updateNeighbourNodes(Stations from)
	{
		List<Stations> neighbours = getNeighbourNodes(from);
		for(Stations neighbour:neighbours)
		{
			Date bestTime = null;//best arrival time
			Train train = null;//the train giving the best arrival time
			List<Train> trains = graph.getTrainConnections(from).get(neighbour);//list of direct trains from station to neighbour node
			for(Train t:trains)
			{
				Date arriveTime = t.getTimeline().getArriveTime(neighbour);//arrival time of the train at neighbour
				Date departTime = t.getTimeline().getDepartTime(from);//depart time of the train from n
				if(departTime.after(bestArriveTime.get(from)))//if the depart time is after the arrival time at n
				{
					if(bestTime==null||arriveTime.before(bestTime))
					{
						bestTime = arriveTime;
						train = t;
					}
				}
			}
			if(bestArriveTime.get(neighbour)==null||bestArriveTime.get(neighbour).after(bestTime))//check if new time is better than one already stored
			{
				bestArriveTime.put(neighbour, bestTime);//store earliest arrival time
				bestTrain.put(neighbour, train);//store best train for arrival time
				graph.setPreStation(neighbour, from);//store node n coming before the neighbour node
			}
		}
	}
	public void startDijkstras()
	{
		graph.setTrainConnections();
		setStartTime(startNode, time);
		while(!unsolvedNodes.isEmpty())
		updateNeighbourNodes(getNextNode());
	}
	public Path getPath()
	{
		Path path = new Path();
		
		//list of stations on the path
		List<Stations> stations = new ArrayList<>();
		stations.add(endNode);
		Stations nextStation = graph.getPreStation(endNode);
		while(nextStation!=startNode)
		{
			stations.add(nextStation);
			nextStation = graph.getPreStation(nextStation);
		}
		stations.add(startNode);
		Collections.reverse(stations);
		path.setStations(stations);//store list in the path
		
		bestTrain.put(startNode, bestTrain.get(stations.get(1)));//set train from start station
		//store arrival/depart times and train at each station
		for(Stations s:stations)
		{
			Date arrivalTime = bestArriveTime.get(s);
			Date departTime = bestTrain.get(s).getTimeline().getDepartTime(s);
			Train theTrain = bestTrain.get(s);
			path.putArriveTime(s, arrivalTime);
			path.putDepartTime(s, departTime);
			path.putTrain(s, theTrain);
		}
		
		//calculate stations to transfer at and associated departTime
		Train trainFrom;
		Train trainAfter;
		for(int i=1;i<stations.size();i++)
		{
			trainFrom = bestTrain.get(stations.get(i-1));
			trainAfter = bestTrain.get(stations.get(i));
			if(!trainFrom.equals(trainAfter))
			{
				Stations transferStation = stations.get(i-1);
				Date departTime = trainAfter.getTimeline().getDepartTime(transferStation);
				path.putTransferStation(transferStation, departTime);
			}
		}
		
		return path;
	}
}