import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
public class Nodes 
{
	private String code;
    private HashMap<Nodes, Integer> connections;
    private boolean solved;
    private int temporaryValue;
    private int finalValue;
    private HashMap<Nodes, ArrayList<Train>> trainConnections;
    private HashMap<Nodes, ArrayList<Date>> connectionTimes;
    Nodes preNode;
    /**
     * Constructor for objects of class Nodes
     */
    public Nodes(String theName)
    {
        code = theName;
        solved = false;
        temporaryValue=0;
        finalValue=-1;
        connections = new HashMap<>();
        trainConnections = new HashMap<>();
        connectionTimes=new HashMap<>();
        preNode = null;
    }
    public ArrayList<Date> getConnectionTimes(Nodes n)
    {
    	return connectionTimes.get(n);
    }
    public void putConnectionTimes(Nodes n, ArrayList<Date> times)
    {
    	connectionTimes.put(n, times);
    }
    /**
     * A method to set the name of the node
     */
    public void setName(String input)
    {
        code = input;
    }
    public String getName()
    {
        return code;
    }
    
    /**
     * A method to set the connections of this node
     */
    public void setConnections(Nodes node, int distance)
    {
        connections.put(node, distance);
    }
    
    /**
     * A method to return the distance to a given node.
     * If no direct connection exists, then it returns -1.
     */
    public int getDistance(Nodes node)
    {
        int answer=-1;
        if(connections.get(node)!=null)
        {
            answer=connections.get(node);
        }
        
        return answer;
    }
    /**
     * returns the final value of a node
     */
    public int getFinalValue()
    {
        return finalValue;
    }
    /**
     * set the final value of a node
     */
    public void setFinalValue(int value)
    {
        finalValue = value;
    }
    /**
     * return the temporary value of a node
     */
    public int getTemporaryValue()
    {
        return temporaryValue;
    }
    /**
     * set the temporary value of a node
     */
    public void setTemporaryValue(int value)
    {
        temporaryValue = value;
    }
    /**
     * check if a node is solved
     * @return true if the node is solved or false if unsolved
     */
    public boolean getSolved()
    {
        boolean answer = solved;
        return answer;
    }
    /**
     * set the node as solved
     */
    public void setSolved(boolean input)
    {
        solved =input;
    }
    /**
     * set the preceeding node as the specified node
     */
    public void setPreNode(Nodes node)
    {
        preNode = node;
    }
    /**
     * returns the preceeding node if present else returns null
     */
    public Nodes getPreNode()
    {
        return preNode;
    }
    public void setTrains(Nodes n, ArrayList<Train> t)
    {
    	trainConnections.put(n, t);
    }
    public ArrayList<Train> getTrains(Nodes n)
    {
    	return trainConnections.get(n);
    }
    public String toString()
    {
    	return code;
    }
    public boolean equals(Object obj)
    {
    	if(this==obj) return true;
    	if(!(obj instanceof Nodes)) return false;
    	Nodes n = (Nodes) obj;
    	return code.equals(n.getName());
    }
    public int hashCode()
    {
    	int hc = 19;
    	return 29*hc+code.hashCode();
    }
}

