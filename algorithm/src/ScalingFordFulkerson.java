import java.util.*;

/**
 * A class that represents the scaling Ford Fulkerson algorithm
 * 
 * @author Mengting
 * @version 0.0
 */
public class ScalingFordFulkerson {
	public ResidualGraph graph;  //the residual graph
	public double maxFlow;  //the max flow value
	public ResidualVertex source;
	
	
	//constructor of ScalingFordFulkerson class
	public double ScalingFordFulkerson(SimpleGraph sGraph) {
		this.graph = new ResidualGraph();
		//initial max flow value is 0
		this.maxFlow = 0;   
		//add all vertex of input graph into vertex list of residual graph
		addVertices(sGraph);
//		for (int i = 0; i < graph.vertexList.size(); i++) {
//			System.out.println(graph.vertexList.get(i).getName());
//		}
		
		this.source = graph.getSource();
		//get the max capacity for the edge out of source
		double maxSourceCap = getMaxSourceCap(sGraph); 
		//calculate the delta which is the largest power of 2 and no large than the max capacity out of source
		int delta = getDelta(maxSourceCap);
		//System.out.println("Delta: " + delta + " maxSourceCap: " + maxSourceCap + " ");
		while (delta >= 1) {
			addEdges(sGraph, delta);
			//System.out.println(delta);
//			for (int i = 0; i < graph.edgeList.size(); i++) {
//				System.out.println(graph.edgeList.get(i).getFirstPoint().getName() + " " + graph.edgeList.get(i).getSecondPoint().getName() + " " + graph.edgeList.get(i).getCapacity());
//			} 
			//find the path from source to target
			List<ResidualVertex> path;
			//update the residual graph
			while ((path = findSTpath()).size() != 0) {
				double minValue = getMinCap(path);
				updateResidualGraph(path, minValue);
				maxFlow += minValue;
			}
			//update delta
			delta = delta / 2;
		}
		return maxFlow;
	}
	
	private void addVertices(SimpleGraph sGraph) {
		List<Vertex> vertices = sGraph.vertexList;
		for (int i = 0; i < vertices.size(); i++) {
			ResidualVertex v = new ResidualVertex((vertices.get(i)).getName());
			graph.insertVertex(v);
		}
	}
	
	private void addEdges(SimpleGraph sGraph, int delta) {
		List<Edge> edges = sGraph.edgeList;
		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i);
			if ((double)e.getData() >= delta && (double)e.getData() < delta * 2) {
				Vertex v1 = e.getFirstEndpoint();
				Vertex u1 = e.getSecondEndpoint();
				ResidualVertex v2 = null;
				ResidualVertex u2 = null;
				Iterator vertices = graph.vertices();
				while (vertices.hasNext()) {
					ResidualVertex curV = (ResidualVertex) vertices.next();
					if (curV.getName().equalsIgnoreCase((String) v1.getName())) {
						v2 = curV;
					}
					if (curV.getName().equalsIgnoreCase((String) u1.getName())) {
						u2 = curV;
					}
				}
				ResidualEdge edge = new ResidualEdge(v2, u2, (double) e.getData());
				graph.insertEdge(edge, v2);
			}
		}
	}
	
	private double getMaxSourceCap(SimpleGraph sg) {
		double max = 0;
		Iterator vertices = sg.vertices();
		while (vertices.hasNext()) {
			Vertex v = (Vertex)vertices.next();
			if (v.getName().toString().equalsIgnoreCase("s")) {
				List<Edge> edges = v.incidentEdgeList;
				for (int i = 0; i < edges.size(); i++) {
					max = Math.max(max, (double)edges.get(i).getData());
				}
			}
		}
		return max;
	}
	
	private int getDelta(double max) {
		int delta = 1;
		while (delta * 2 <= max) {
			delta *= 2;
		}
		return delta;
	}
	
	
	private LinkedList<ResidualVertex> findSTpath() {
		//source.setVisited();
		LinkedList<ResidualVertex> path = new LinkedList<>();
		path = graph.findPath(source, path);
		updateVisitedStatus();
		source.setUnvisited();
		graph.moveToEnd(source);
		return path;
	}
	
	private void updateVisitedStatus() {
		Iterator vertices = graph.vertices();
		while (vertices.hasNext()) {
			ResidualVertex v = (ResidualVertex) vertices.next();
			v.setUnvisited();
		}
	} 
	private double getMinCap(List<ResidualVertex> path) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < path.size() - 1; i++) {
			ResidualVertex v = path.get(i);
			ResidualVertex u = path.get(i + 1);
			ResidualEdge e = graph.getEdge(v, u);
			//System.out.println(v.getName() + " " + u.getName());
//			System.out.println(e.getCapacity());
			min = Math.min(min, e.getCapacity());
		}
		return min;
	}
	
	private void updateResidualGraph(List<ResidualVertex> path, double minValue) {
		for (int i = 0; i < path.size() - 1; i++) {
			ResidualVertex v = path.get(i);
			ResidualVertex u = path.get(i + 1);
			
			ResidualEdge forward = graph.getEdge(v, u);
			if (forward.getCapacity() == minValue) {
				graph.removeEdge(forward);
			} else {
				forward.setCapacity(forward.getCapacity() - minValue);
				graph.moveToEnd(forward);
			}
			
			ResidualEdge backward = graph.getEdge(u, v);
			if (backward == null) {
				backward = new ResidualEdge(u, v, minValue);
				graph.insertEdge(backward, u);
			} else {
				backward.setCapacity(backward.getCapacity() + minValue);
				graph.moveToEnd(backward);
			}
		}
	}
	
}
