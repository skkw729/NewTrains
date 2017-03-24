import java.util.*;
public class CreatePath 
{
	TrainSchedule schedule;
	CreateGraph graph;
	List<Stations> solvedNodes;
	List<Stations> unsolvedNodes;
	Map<Stations,Date> bestArriveTime;
	Map<Stations,Train> bestTrain;
	public CreatePath(TrainSchedule schedule)
	{
		this.schedule = schedule;
		graph = new CreateGraph(schedule);
		solvedNodes = new ArrayList<>();
		unsolvedNodes = new ArrayList<>();
		unsolvedNodes.addAll(graph.getStations());//copy of arraylist (changes will be made to this list)
		bestArriveTime = new HashMap<>();
		bestTrain = new HashMap<>();
	}
	private void clearGraph()
	{
		graph = new CreateGraph(schedule);
		unsolvedNodes = new ArrayList<>();
		unsolvedNodes.addAll(graph.getStations());//copy of arraylist
		solvedNodes = new ArrayList<>();
		bestArriveTime = new HashMap<>();
		bestTrain = new HashMap<>();
	}
	/*
	 * Match a string station code to a station
	 * returns null if no match can be found
	 */
	private Stations getStation(String stationCode)
	{
		Stations station = null;
		for(Stations s:graph.getStations())
		{
			if(s.getCode().equals(stationCode))
			{
				station = s;
			}
		}
		return station;
	}
	/*
	 * Initialise the arrival time at the starting station
	 */
	private void setStartTime(Stations from, Date when)
	{
		bestArriveTime.put(from, when);
	}
	/*
	 *Returns the node with the best arrive time from the unsolved nodes list.
	 *Adds it to the solved nodes and removes it from unsolved nodes. 
	 */
	private Stations getNextNode()
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
	private List<Stations> getNeighbourNodes(Stations from)
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
	/*
	 * calculate the best arrival time for nodes adjacent to the given station 
	 */
	private void updateNeighbourNodes(Stations from)
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
	/*
	 * Calculates the shortest path from the given station to all other stations, using trains that depart after the given time
	 */
	private void startDijkstras(Stations from, Date when)
	{
		graph.setTrainConnections();
		setStartTime(from, when);
		while(!unsolvedNodes.isEmpty())
		updateNeighbourNodes(getNextNode());
	}
	public Path getPath(String startStationCode, String endStationCode, Date when)
	{
		Stations start = getStation(startStationCode);
		Stations end = getStation(endStationCode);
		return getPath(start, end, when);
	}
	/*
	 * returns a Path for given start station, end station and depart time
	 */
	public Path getPath(Stations start, Stations end, Date when)
	{
		if(start==null || end==null || when==null) throw new IllegalArgumentException("Null parameters detected");
		ShortestPath path = new ShortestPath();
		startDijkstras(start, when);
		//list of stations on the path
		List<Stations> stations = new ArrayList<>();
		stations.add(end);
		Stations nextStation = graph.getPreStation(end);
		while(nextStation!=start)
		{
			stations.add(nextStation);
			nextStation = graph.getPreStation(nextStation);
		}
		stations.add(start);
		Collections.reverse(stations);
		path.setStations(stations);//store list in the path
		
		bestTrain.put(start, bestTrain.get(stations.get(1)));//set train from start station
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
	public Path getPathVia(String startStationCode, String endStationCode, String viaStationCode, Date when)
	{
		Stations start = getStation(startStationCode);
		Stations end = getStation(endStationCode);
		Stations via = getStation(viaStationCode);
		return getPathVia(start, end, via, when);
	}
	public Path getPathVia(Stations start, Stations end, Stations via, Date when)
	{
		if(start==null || end==null || via==null || when==null) throw new IllegalArgumentException("Null parameters detected");
		Path path=null;
		Path first = getPath(start, via, when);
		Date arriveAtVia = bestArriveTime.get(via);
		clearGraph();
		Path second = getPath(via, end, arriveAtVia);
		if(first instanceof ShortestPath && second instanceof ShortestPath)
		{
			path = new PathVia(first, second, via);
		}
		else throw new IllegalArgumentException("ShortestPaths expected, actual parameters were: "+first.getClass() + " and "+second.getClass());
		return path;
	}
}