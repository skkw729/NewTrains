import java.util.*;
public class CreatePath 
{
	CreateGraph graph;
	ArrayList<Nodes> nodePath; 
	public CreatePath(CreateGraph graph)
	{
		this.graph = graph;
		nodePath = new ArrayList<>();
	}
	 /**
     * Sets the final value of the startNode to 0 and updates the tempValue of all connecting nodes
     */
    private void updateTempValue()
    {
        Nodes start = graph.getStartNode();
        int updatedValue=0;
        for (Nodes nodes : graph.getNodes())
        {
            
            if(start.getDistance(nodes)!=-1 && nodes.getSolved()==false)
            {
                updatedValue = start.getDistance(nodes);
                if(nodes.getTemporaryValue()<= nodes.getTemporaryValue()+updatedValue)
                {
                    nodes.setTemporaryValue(updatedValue);
                }
                
            }
        }
        start.setSolved(true);
        start.setFinalValue(0);
    }
    /**
     * Updates the tempValue of all connecting nodes and sets the specified node as solved
     */
    private void updateNodeValue(Nodes node)
    {
        int updatedValue=0;
        for (Nodes nodes : graph.getNodes())
        {
            if(node.getDistance(nodes)!=-1 && nodes.getSolved()==false)//if a connection exists
            {
                updatedValue = node.getDistance(nodes)+node.getTemporaryValue();
                if(nodes.getTemporaryValue()==0||nodes.getTemporaryValue()>updatedValue)//if no tempValue exists or if new tempValue would be lower than current
                {
                    nodes.setTemporaryValue(updatedValue);//update tempValue
                    nodes.setPreNode(node);
                }
            }
        }
        node.setSolved(true);//Mark this node as solved
        node.setFinalValue(node.getTemporaryValue());//set the final value of this node as its tempValue
    }
    /**
     * Prints tempValue and finalValue of all nodes
     */
    public void printTempValue()
    {
        for(Nodes nodes:graph.getNodes())
        {
            System.out.println(nodes.getName() + " TempValue: "+nodes.getTemporaryValue()+" Final: "+nodes.getFinalValue());
        }
    }
    /**
     * A method that returns an unsolved node with the lowest temporary value
     */
    private Nodes getNextNode()
    {
        Nodes next = null;
        int value=0;
        for(Nodes nodes:graph.getNodes())
        {
            if(nodes.getSolved()==false && nodes.getTemporaryValue()>0)
            {
                if(value==0)
                {
                    value = nodes.getTemporaryValue();
                }
                if(value>nodes.getTemporaryValue())
                {
                    value=nodes.getTemporaryValue();
                }
            }
        }
        for(Nodes nodes:graph.getNodes())
        {
            if(nodes.getSolved()==false && nodes.getTemporaryValue()==value)
            {
                next = nodes;
            }
        }
        return next;
    }
    /**
     * A method to set the final value of a node equal to the temporary value of
     * the next unsolved node with the lowest temporary value.
     */
//    private void setFinalValue(Nodes node)
//    {
//        Nodes next = getNextNode();
//        node.setSolved(true);
//        node.setTemporaryValue(next.getTemporaryValue());
//        node.setFinalValue(node.getTemporaryValue());
//    }
    /**
     * A method to print the shortest path from a startNode to an endNode
     */
    public void printPath()
    {
        //take end node, find its preNode and add it to the list
        Nodes next = graph.getEndNode();
        ArrayList<Nodes> path = new ArrayList<>();
        path.add(next);
        while(next.getPreNode()!=null)
        {
                path.add(next.getPreNode());
                next = next.getPreNode();
        }
        path.add(graph.getStartNode());
        System.out.println("The shortest path takes " + graph.getEndNode().getFinalValue()+" minutes");
        System.out.print("Take the path: ");
        Collections.reverse(path);
        for(Nodes n:path)
        {
        	System.out.print(n+" ");
        }
        nodePath = path;
//        System.out.println("Take the trains: ");
//        System.out.println(graph.getStartNode().getTrain(path.get(path.size()-2)));
//        for(int i=path.size()-2;i>0;i--)
//        {
//            System.out.println(path.get(i).getTrain(path.get(i-1)));
//        }
//        System.out.println("");
    }
    /**
     * A method to perform Dijkstra's algorithm
     */
    public void method()
    {
        updateTempValue();
        while(!graph.getEndNode().getSolved())
        {
            Nodes next = getNextNode();
            updateNodeValue(next);
        }
    }
    /**
     * A method to set the startNode
     */
    public void setBeginning(String nodeName)
    {
        for(Nodes node : graph.getNodes())
        {
            if(node.getName().equals(nodeName))
            {
                graph.setStart(node);
            }
        }
    }
    /**
     * A method to set the endNode
     */
    public void setEnd(String nodeName)
    {
        for(Nodes node : graph.getNodes())
        {
            if(node.getName().equals(nodeName))
            {
                graph.setEndNode(node);
            }
        }
    }
    /**
     * A method to reset all nodes to their original state and values 
     */
    public void clearValue()
    {
        for(Nodes node : graph.getNodes())
        {
            node.setSolved(false);
            node.setTemporaryValue(0);
            node.setFinalValue(0);
            node.setPreNode(null);
        }
    }
    public ArrayList<Nodes> getNodePath()
    {
    	return nodePath;
    }
}


