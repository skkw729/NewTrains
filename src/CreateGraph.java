import java.util.*;
public class CreateGraph 
{
	TrainSchedule schedule;
	ArrayList<Train> trains;
	ArrayList<Stations> stations;
	ArrayList<Nodes> nodes;
	Nodes startNode;
	Nodes endNode;
	Date when;
	public CreateGraph(TrainSchedule s, Date time, Stations from, Stations to)
	{
		schedule = s;
		trains = schedule.getTrains();
		//trains = getTrainsAfter(schedule.getTrains(), time);
		stations = getStations();
		nodes = new ArrayList<>();
		when = time;
		makeNodes();
		startNode = stationToNode(from);
		endNode = stationToNode(to);
		//setStartNodeConnections(); //wait time currently not accounted for
		setNodeConnections();
		
	}
	public Nodes getStartNode()
	{
		return startNode;
	}
	public Nodes getEndNode()
	{
		return endNode;
	}
	public void makeNodes()
	{
		for(Stations s:stations)
		{
			nodes.add(new Nodes(s.getCode()));
		}
	}
	public void setNodeConnections()
	{
		for(Nodes n:nodes)
		{
			for(int i=0; i<nodes.size();i++)
			{
				if(!n.equals(nodes.get(i)))
				{
					//convert node to station
					Stations from = nodeToStation(n);
					Stations to = nodeToStation(nodes.get(i));
					int minTime = getMinTimeTaken(from, to);
					//add connection between 2 nodes using minTimeTaken()
					if(minTime!=-1)
					{
						//Train t = getTrainMinTimeTaken(from, to);
						//n.setTrain(nodes.get(i), t);
						n.setConnections(nodes.get(i), minTime);
					}
				}
			}
		}
	}
	public void setStartNodeConnections()
	{
		Stations start = nodeToStation(startNode);
		for(Stations s:stations)
		{
			if(start.getConnectionTime(s)!=-1)
			{
				int connectionTimeAdjusted = getStartMinTime(start, s);
				//Train t = getStartTrain(start, s);
				startNode.setConnections(stationToNode(s),connectionTimeAdjusted);
				//startNode.setTrain(stationToNode(s), t);
			}
		}
	}
	//returns the trains that depart the specified station after the given time
	public ArrayList<Train> getTrainsAfter(ArrayList<Train> trains,Stations s, Date time)
	{
		ArrayList<Train> trainsAfterTime = new ArrayList<>();
		for(Train t:trains)
		{
			if(!t.getTimeline().getDepartTime(s).before(time))
			{
				trainsAfterTime.add(t);
			}
		}
		return trainsAfterTime;
	}
	public ArrayList<Train> getTrains()
	{
		return trains;
	}
	/*
	 * returns -1 if the connection doesn't exist between from and to stations
	 */
	public int getMinTimeTaken(Stations from, Stations to)
	{
		int minTime=-1;
		
		if(from.getConnectionTime(to)!=-1)
		{
			for(Train t: trains)
			{
				if(t.getStationAfter(from)!=null && t.getStationAfter(from).equals(to))
				{
					Date fromArriveTime = t.getTimeline().getArriveTime(from);
					Date toArriveTime = t.getTimeline().getArriveTime(to);
					long timeDiff = toArriveTime.getTime() - fromArriveTime.getTime();
					int millisecToMinute = (int) (timeDiff/(1000*60));
					if(minTime==-1 || millisecToMinute<minTime)
					{
						minTime = millisecToMinute;
					}
				}
			}
		}
		return minTime;
	}
	public Train getTrainMinTimeTaken(Stations from, Stations to)
	{
		Train train=null;
		int minTime=-1;
		
		if(from.getConnectionTime(to)!=-1)
		{
			for(Train t: trains)
			{
				if(t.getStationAfter(from)!=null && t.getStationAfter(from).equals(to))
				{
					Date fromArriveTime = t.getTimeline().getArriveTime(from);
					Date toArriveTime = t.getTimeline().getArriveTime(to);
					long timeDiff = toArriveTime.getTime() - fromArriveTime.getTime();
					int millisecToMinute = (int) (timeDiff/(1000*60));
					if(minTime==-1 || millisecToMinute<minTime)
					{
						train = t;
						minTime = millisecToMinute;
					}
				}
			}
		}
		return train;
	}
	/*return the time taken to reach the next station from the startTrain
	 * taking into consideration the current time
	 */
	public int getStartMinTime(Stations from, Stations to)
	{
		//Train train=null;
		int minTime=-1;
		for(Train t:trains)
		{
			if(t.getStationAfter(from)!=null && t.getStartStation().equals(to))
			{
				Date fromArriveTime = t.getTimeline().getArriveTime(from);
				long waitToArrive = fromArriveTime.getTime() - when.getTime();
				int waitToArriveMin = (int)waitToArrive/(60*1000);
				Date toArriveTime = t.getTimeline().getArriveTime(to);
				//if train passes the stations in the correct order
					long timeDiff = toArriveTime.getTime() - fromArriveTime.getTime();
					int millisecondToMinutes = (int) (timeDiff/(60*1000));
					int totalMinutes = waitToArriveMin + millisecondToMinutes;
					if(minTime==-1||totalMinutes<minTime)
					{
						//train = t;
						minTime = totalMinutes;
					}

			}
		}
		return minTime;
	}
	public Train getStartTrain(Stations from, Stations to)
	{
		Train train=null;
		int minTime=-1;
		for(Train t:trains)
		{
			if(t.getStationAfter(from)!=null && t.getStartStation().equals(to))
			{
				Date fromArriveTime = t.getTimeline().getArriveTime(from);
				long waitToArrive = fromArriveTime.getTime() - when.getTime();
				int waitToArriveMin = (int)waitToArrive/(60*1000);
				Date toArriveTime = t.getTimeline().getArriveTime(to);
				//if train passes the stations in the correct order
					long timeDiff = toArriveTime.getTime() - fromArriveTime.getTime();
					int millisecondToMinutes = (int) (timeDiff/(60*1000));
					int totalMinutes = waitToArriveMin + millisecondToMinutes;
					if(minTime==-1||totalMinutes<minTime)
					{
						train = t;
						minTime = totalMinutes;
					}

			}
		}
		return train;
	}
	public ArrayList<Train> getTrainPath(ArrayList<Nodes> nodePath)
	{
		ArrayList<Train> trainPath = new ArrayList<>();
		for(int i=0; i<nodePath.size()-2;i++)
		{
			Stations from  = nodeToStation(nodePath.get(i));
			Stations to = nodeToStation(nodePath.get(i+1));
			trainPath.add(getTrainMinTimeTaken(from, to));
		}
		return trainPath;
	}

	public Nodes stationToNode(Stations s)
	{
		String code = s.getCode();
		Nodes n = null;
		for(Nodes node:nodes)
		{
			if(node.getName().equals(code))
			{
				n = node;
			}
		}
		return n;
	}
	public Stations nodeToStation(Nodes n)
	{
		String code = n.getName();
		Stations s=null;
		for(Stations station:stations)
		{
			if(station.getCode().equals(code))
			{
				s = station;
			}
		}
		return s;
	}
	public ArrayList<Stations> getStations()
	{
		return schedule.getStationsList();
	}
	public ArrayList<Nodes> getNodes() 
	{
		return nodes;
	}
	public void setStart(Nodes node) 
	{
		startNode = node;
	}
	public void setEndNode(Nodes node)
	{
		endNode = node;
	}
}
