import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
 
/**
 * the PreflowPush class implements the PreoflowPush algorithm
 * 
 * @author Todd Robbins & Brandon Lioce
 */
public class PreflowPush {
	public static ResidualGraph graph; // the remaining graph after sorting
	public double maxFlow; // the max flow value
	public List<Vertex> vertices = null; 
	public SimpleGraph simpleGraph;
	/**
	 *
	 * find the max-flow of the simple graph
	 *
	 * @param simpleGraph the simple graph
	 * @return max flow
	 */
	public PreflowPush(SimpleGraph simpleGraph) {
		graph = new ResidualGraph();
		this.simpleGraph = simpleGraph;
		this.maxFlow = maxFlow(); //pre flow push max flow  
		}
	
	/**
	 * Initialize the new graphs Vertexes 
	 * @param simpleGraph
	 * @return 
	 * @return
	 */
	private HashMap<String, ResidualVertex> addVertices(SimpleGraph simpleGraph) {
		
        Iterator<?> vertices = simpleGraph.vertices();
        HashMap<String,ResidualVertex> vertexList = new HashMap<String,ResidualVertex>();
        int noOfVertices = simpleGraph.numVertices();
        while(vertices.hasNext())
        {
            Vertex v = (Vertex) vertices.next();
            ResidualVertex rv = new ResidualVertex(v.getName());
            vertexList.put(String.valueOf(rv.getName()), rv);
            if(rv.getName().equalsIgnoreCase("s"))
            {
                
                rv.setHeight(noOfVertices);
            }
            else
            {
                rv.setHeight(0);
            }
            graph.insertVertex(rv);
        }
        
        return vertexList;
	}
	
	
	
	/**
	 * Initialize the new graphs Edges 
	 * @param simpleGraph
	 * @param vertexList
	 */
	   private static void addEdges(SimpleGraph g,HashMap<String,ResidualVertex> vertexList){
	        Iterator<?> edges = g.edges();
	        while(edges.hasNext())
	        {
	            Edge e = (Edge) edges.next();
	            Vertex v = e.getFirstEndpoint();
	            Vertex w = e.getSecondEndpoint();
	            ResidualVertex rv = vertexList.get(v.getName());
	            ResidualVertex rw = vertexList.get(w.getName());
	            double edgeCapacity = (double) e.getData();
	            ResidualEdge edge;
	            if(rv.getName().equalsIgnoreCase("s"))
	            {
	                edge = new ResidualEdge(rw, rv, edgeCapacity);
	                rw.setExcess(edge.getCapacity());
	                int index = graph.vertexList.indexOf(rw);
	                graph.vertexList.remove(index);
	                graph.vertexList.addLast(rw);
	                graph.insertEdge(edge, rw);
	            }
	            else
	            {
	                edge = new ResidualEdge(rv, rw, edgeCapacity);
	                graph.insertEdge(edge, rv);
	            }
	        }
	    }
	
	
	/**
	 * Calculates the max flow of new "cut" graph
	 * 
	 * @param simpleGraph
	 * @return
	 */
	public double maxFlow() {
		ResidualVertex residualVertex;
		HashMap<String,ResidualVertex> vertexList=addVertices(simpleGraph); //add nodes
		addEdges(simpleGraph, vertexList); // add edges 
		LinkedList<?> vertices = graph.vertexList; //list of verticies in our new graph
		double maxFlow = 0; 
		while ((residualVertex = getAvailableNode()) != null) {

			ResidualEdge edge = findMinHeight(residualVertex); // unused node. 
			if (edge != null) {
				push(residualVertex, edge);
			} else {
				relabel(residualVertex);
			}

		}
		
		/**
		 * add excess
		 */
		for (int i = 0; i < vertices.size(); i++) {
			ResidualVertex vertex = (ResidualVertex) vertices.get(i);
			if (vertex.getName().equalsIgnoreCase("t")) {
				maxFlow = vertex.getExcess();
			}
		}
		return maxFlow;
	}
	
	/**
	 * Returns active node
	 * 
	 * @return an active node
	 */
	private ResidualVertex getAvailableNode() {
		Iterator<?> vertices = graph.vertices();
		while(vertices.hasNext()) {
			ResidualVertex vertex = (ResidualVertex) vertices.next(); 
			if (!vertex.getName().equalsIgnoreCase("s") && !vertex.getName().equalsIgnoreCase("t") && vertex.getExcess() > 0) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * get the Minimum Height Adjacent to the Vertex
	 * @param residualVertex
	 * @return
	 */
	private ResidualEdge findMinHeight(ResidualVertex residualVertex) {
		
		List<ResidualEdge> edges = residualVertex.nextEdgeList;
		ResidualVertex w2;
		ResidualEdge finalEdge = null;
		double minHeight = residualVertex.getHeight();
		
		for (ResidualEdge edge : edges) {
			ResidualEdge residualEdge = edge;
			w2 = residualEdge.getSecondPoint();
			if (minHeight > w2.getHeight()) {
				minHeight = w2.getHeight();
				finalEdge = residualEdge;
			}
		}
		return finalEdge;
	}
	
	/**
	 * Push flow
	 * @param residualVertex
	 * @param forwardEdge
	 */
	private void push(ResidualVertex residualVertex, ResidualEdge forwardEdge) {
		
		ResidualVertex w2 = forwardEdge.getSecondPoint();
		double excessAvailable = residualVertex.getExcess(); //
		double canPush = forwardEdge.getCapacity();// 40
		double pushValue = (excessAvailable < canPush) ? excessAvailable : canPush; // 
		int index = 0; 
		if (pushValue == canPush) {
			graph.removeEdge(forwardEdge);
		} else {
			forwardEdge.setCapacity(forwardEdge.getCapacity() - pushValue);
	        int indexForward = graph.edgeList.indexOf(forwardEdge);
	        graph.edgeList.remove(indexForward);
	        graph.edgeList.addLast(forwardEdge);
		}
		
		residualVertex.setExcess(residualVertex.getExcess() - pushValue);
		
		/*
		 * set new vertex
		 * 
		 */
        index = graph.vertexList.indexOf(residualVertex);
        graph.vertexList.remove(index);
        graph.vertexList.addLast(residualVertex);
        
		ResidualEdge backward = graph.getEdge(w2, residualVertex);
		
		if (backward == null) {
			backward = new ResidualEdge(w2, residualVertex, pushValue);
			graph.insertEdge(backward, w2);
		} else {
			backward.setCapacity(backward.getCapacity() + pushValue);
	        index = graph.edgeList.indexOf(backward);
	        graph.edgeList.remove(index);
	        graph.edgeList.addLast(backward);
		}

		w2.setExcess(w2.getExcess() + pushValue);
        index = graph.vertexList.indexOf(w2);
        graph.vertexList.remove(index);
        graph.vertexList.addLast(w2);
	}
	
	/** 
	 * Relabel height of new graph
	 * @param residualVertex
	 */
	private void relabel(ResidualVertex residualVertex) {
		
		List<ResidualEdge> nextEdges = residualVertex.nextEdgeList;
		double minHeight = residualVertex.getHeight();
		
		for (int i = 0; i < nextEdges.size(); i++) {
			
			ResidualEdge residualEdge = nextEdges.get(i);
			ResidualVertex endPoint = residualEdge.getSecondPoint();
			
			if (minHeight > endPoint.getHeight()) {
				minHeight = endPoint.getHeight();
			}
		}

		residualVertex.setHeight(minHeight + 1);
        int index = graph.vertexList.indexOf(residualVertex);
        graph.vertexList.remove(index);
        graph.vertexList.addLast(residualVertex);
	}
}
