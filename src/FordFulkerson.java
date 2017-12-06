import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FordFulkerson 
{
	public ResidualGraph graph;  //the residual graph
	public double maxFlow;  //the max flow value
	public ResidualVertex source;
	public SimpleGraph sGraph;
	
	public FordFulkerson(SimpleGraph sGraph) {
		this.sGraph = sGraph;
	}
	public double getMaxFlow() {
		//long startTime = System.nanoTime();
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
		
		
			addEdges(sGraph);
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
			//long endTime = System.nanoTime();
			//long duration = (endTime - startTime);
			//System.out.println(duration);
		
		return maxFlow;
	}
	
	private void addVertices(SimpleGraph sGraph) {
		List<Vertex> vertices = sGraph.vertexList;
		for (int i = 0; i < vertices.size(); i++) {
			ResidualVertex v = new ResidualVertex((vertices.get(i)).getName());
			graph.insertVertex(v);
		}
	}
	
	
	private void addEdges(SimpleGraph sGraph) {
		List<Edge> edges = sGraph.edgeList;
		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i);			
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
