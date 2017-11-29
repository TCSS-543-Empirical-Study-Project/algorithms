import java.util.*;
/**
 * A class that represents the residual vertex for the graph
 * 
 * @author Mengting
 * @version 0.0
 */
public class ResidualVertex {
	public List<ResidualEdge> nextEdgeList; //the next connected edge of this residual vertex
	private String name; //the name of the residual vertex
	private boolean visited; //if this residual vertex is visited
	
	/**
     * allows you to setup the name of the residual vertex
     * the initial value for visited is false
     * @param name  the name of the residual vertex
     */
	public ResidualVertex(Object name) {
		this.name = String.valueOf(name);
		this.visited = false;
		this.nextEdgeList = new LinkedList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited() {
		this.visited = true;
	}
	
	public void setUnvisited() {
		this.visited = false;
	}
	
	public boolean hasUnvisitedNeigh() {
		for (int i = 0; i < nextEdgeList.size(); i++) {
			ResidualEdge e = nextEdgeList.get(i);
			ResidualVertex v = e.getSecondPoint();
			if (!v.isVisited()) {
				return true;
			}
		}
		return false;
	}
	
	public ResidualVertex getNeigh() {
		for (int i = 0; i < nextEdgeList.size(); i++) {
			ResidualEdge e = nextEdgeList.get(i);
			ResidualVertex v = e.getSecondPoint();
			if (!v.isVisited()) {
				return v;
			}
		}
		return null;
	}
}
