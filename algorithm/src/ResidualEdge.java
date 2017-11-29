import java.util.*;

/**
 * A class that represents the residual edge for the graph
 * 
 * @author Mengting
 * @version 0.0
 */
public class ResidualEdge {
	private double capacity;   //the capacity of the residual edge
	private ResidualVertex v;
	private ResidualVertex u;
	
    /**
     * allows you to setup the vertex v, w, flow and capacity value of the residual edge
     * @param v  one end of the edge
     * @param u  another end of the edge
     * @param capacity  the value of capacity of this edge
     */
	public ResidualEdge(ResidualVertex v, ResidualVertex u, double capacity) {
		this.v = v;
		this.u = u;
		this.capacity = capacity;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public void setCapacity(double value) {
		this.capacity = value;
	}
	
	public ResidualVertex getFirstPoint() {
		return v;
	}
	
	public ResidualVertex getSecondPoint() {
		return u;
	}

}
