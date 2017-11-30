import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * the PreflowPush class implements the PreoflowPush algorithm
 * 
 * @author Todd Robbins & Brandon Lioce
 */
public class PreflowPush {
	public static ResidualGraph graph; // the remaining graph after sorting
	public double maxFlow; // the max flow value

	/**
	 *
	 * find the max-flow of the simple graph
	 *
	 * @param simpleGraph the simple graph
	 * @return max flow
	 */
	public PreflowPush(SimpleGraph simpleGraph) {
		graph = new ResidualGraph();
		this.maxFlow = 0; // initial max flow value is 0
		this.maxFlow = calculatemaxFlow(simpleGraph);
	}
	
	/**
	 * Initialize the new graphs Vertexes 
	 * @param simpleGraph
	 * @return
	 */
	private static HashMap<String, ResidualVertex> initializeResidualGraphVertex(SimpleGraph simpleGraph) {
		
		/*
		 * grab original graphs vertices 
		 */
		Iterator<?> vertices = simpleGraph.vertices(); 
		HashMap<String, ResidualVertex> vertexList = new HashMap<String, ResidualVertex>();
		int numVertices = simpleGraph.numVertices();
		
		/*
		 * insert them into our new graph  
		 */
		while (vertices.hasNext()) {
			
			Vertex vertex = (Vertex) vertices.next();
			ResidualVertex residualVertex = new ResidualVertex(vertex.getName());
			vertexList.put(String.valueOf(residualVertex.getName()), residualVertex);
			
			if (residualVertex.getName().equalsIgnoreCase("s")) {
				residualVertex.setHeight(numVertices);
			} else {
				residualVertex.setHeight(0);
			}
			graph.insertVertex(residualVertex);
		}

		return vertexList;

	}
	
	/**
	 * Initialize the new graphs Edges 
	 * @param simpleGraph
	 * @param vertexList
	 */
	private static void initializeResidualGraphEdge(SimpleGraph simpleGraph, HashMap<String, ResidualVertex> vertexList) {
		
		/*
		 * grab original graphs edges  
		 */
		Iterator<?> edges = simpleGraph.edges();
		
		while (edges.hasNext()) {
			Edge edge = (Edge) edges.next();
			
			Vertex v = edge.getFirstEndpoint(); //first endpoint of this edge. label the same as Ed Hong 
			Vertex w = edge.getSecondEndpoint(); // the second endpoint of this edge. label the same as Ed Hong 
			
			/*
			 * (Reference: *--*
			 */
			ResidualVertex endPointOne = vertexList.get(v.getName());
			ResidualVertex endPointTwo = vertexList.get(w.getName());
			
			double capacity = (double) edge.getData();
			ResidualEdge residualEdge;
			
			if (endPointOne.getName().equalsIgnoreCase("s")) {
				residualEdge = new ResidualEdge(endPointTwo, endPointOne, capacity);
				endPointTwo.setExcess(residualEdge.getCapacity());
				graph.newVertex(endPointTwo);
				graph.insertEdge(endPointTwo, endPointOne, residualEdge);
			} else {
				residualEdge = new ResidualEdge(endPointOne, endPointTwo, capacity);
				graph.insertEdge(endPointOne, endPointTwo, residualEdge);
			}
		}
	}
	
	/**
	 * Calculates the max flow
	 * @param simpleGraph 
	 * @return
	 */
	private static double calculatemaxFlow(SimpleGraph simpleGraph) {
		
		HashMap<String, ResidualVertex> vertexList = initializeResidualGraphVertex(simpleGraph);
		initializeResidualGraphEdge(simpleGraph, vertexList);
		ResidualVertex residualVertex;
		
		while ((residualVertex = graph.getActiveNode()) != null) {

			ResidualEdge e = getMinHeightAdjacentVertex(residualVertex);
			if (e != null) {
				push(residualVertex, e);
			} else {
				relabel(residualVertex);
			}

		}

		return graph.getExcessSink();
	}
	
	/**
	 * get the Minimum Height Adjacent to the Vertex
	 * @param residualVertex
	 * @return
	 */
	private static ResidualEdge getMinHeightAdjacentVertex(ResidualVertex residualVertex) {
		
		LinkedList<ResidualEdge> edges = residualVertex.outgoingEdge;
		ResidualVertex endpointTwo;
		ResidualEdge fe = null;
		double minHeight = residualVertex.getHeight();
		
		for (ResidualEdge edge : edges) {
			ResidualEdge e = edge;
			endpointTwo = e.getOtherEnd();
			if (minHeight > endpointTwo.getHeight()) {
				minHeight = endpointTwo.getHeight();
				fe = e;
			}
		}
		return fe;
	}
	
	/**
	 * Push flow
	 * @param residualVertex
	 * @param forward
	 */
	private static void push(ResidualVertex residualVertex, ResidualEdge forward) {
		
		ResidualVertex endPointTwo = forward.getOtherEnd();
		double excessAvailable = residualVertex.getExcess(); //
		double canPush = forward.getCapacity();// 40
		double pushValue = (excessAvailable < canPush) ? excessAvailable : canPush; // 
		
		if (pushValue == canPush) {
			graph.removeEdge(forward);
		} else {
			forward.setCapacity(forward.getCapacity() - pushValue);
			graph.newEdge(forward);
		}
		
		residualVertex.setExcess(residualVertex.getExcess() - pushValue);
		graph.newVertex(residualVertex);
		ResidualEdge backward = graph.getEdge(endPointTwo, residualVertex);
		
		if (backward == null) {
			backward = new ResidualEdge(endPointTwo, residualVertex, pushValue);
			graph.insertEdge(endPointTwo, residualVertex, backward);
		} else {
			backward.setCapacity(backward.getCapacity() + pushValue);
			graph.newEdge(backward);
		}

		endPointTwo.setExcess(endPointTwo.getExcess() + pushValue);
		graph.newVertex(endPointTwo);
	}
	
	/**
	 * Relabel height of new graph
	 * @param residualVertex
	 */
	private static void relabel(ResidualVertex residualVertex) {
		
		LinkedList<ResidualEdge> outgoingEdges = residualVertex.outgoingEdge;
		double minHeight = residualVertex.getHeight();
		
		for (int i = 0; i < outgoingEdges.size(); i++) {
			
			ResidualEdge residualEdge = outgoingEdges.get(i);
			ResidualVertex endPoint = residualEdge.getOtherEnd();
			
			if (minHeight > endPoint.getHeight()) {
				minHeight = endPoint.getHeight();
			}
		}

		residualVertex.setHeight(minHeight + 1);
		graph.newVertex(residualVertex);
	}
}
