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

	/**
	 *
	 * find the max-flow of the simple graph
	 *
	 * @param simpleGraph the simple graph
	 * @return max flow
	 */
	public PreflowPush(SimpleGraph simpleGraph) {
		graph = new ResidualGraph();
		this.maxFlow = calculatemaxFlow(simpleGraph); //pre flow push max flow  
		}
	
	/**
	 * Calculates the max flow
	 * 
	 * @param simpleGraph
	 * @return
	 */
	private static double calculatemaxFlow(SimpleGraph simpleGraph) {
		ResidualVertex residualVertex;
		addVertices(simpleGraph); //add nodes
		addEdges(simpleGraph); // add edges 
		LinkedList<?> vertices = graph.vertexList; //list of verticies in our new graph
		double maxFlow = 0; 
		while ((residualVertex = getActiveNode()) != null) {

			ResidualEdge edge = findMinHeight(residualVertex);
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
	 * Initialize the new graphs Vertexes 
	 * @param simpleGraph
	 * @return
	 */
	private static void addVertices(SimpleGraph simpleGraph) {
		
		List<Vertex> vertices = simpleGraph.vertexList;
		int numVertices = simpleGraph.numVertices();

		for (int i = 0; i < vertices.size(); i++) {
			ResidualVertex vertex = new ResidualVertex((vertices.get(i)).getName());
			if (vertex.getName().equalsIgnoreCase("s")) {
				vertex.setHeight(numVertices);
			} else {
				vertex.setHeight(0);
			}
			graph.insertVertex(vertex);
		}
	}
	
	/**
	 * Initialize the new graphs Edges 
	 * @param simpleGraph
	 * @param vertexList
	 */
	private static void addEdges(SimpleGraph simpleGraph) {
		List<Edge> edges = simpleGraph.edgeList;
		for (int i = 0; i < edges.size(); i++) {
				Edge edge = edges.get(i);
				Vertex v1 = edge.getFirstEndpoint(); //first endpoint of this edge. label the same as Ed Hong 
				Vertex w1 = edge.getSecondEndpoint(); // the second endpoint of this edge. label the same as Ed Hong 
				
				Iterator<?> vertices = graph.vertices();
				ResidualVertex v2 = null;
				ResidualVertex w2 = null;
				
				while (vertices.hasNext()) {
					ResidualVertex currVertex = (ResidualVertex) vertices.next();
					if (currVertex.getName().equalsIgnoreCase((String) v1.getName())) {
						v2 = currVertex;
					}
					if (currVertex.getName().equalsIgnoreCase((String) w1.getName())) {
						w2 = currVertex;
					}
				}
				ResidualEdge residualEdge = new ResidualEdge(v2, w2, (double) edge.getData());
				graph.insertEdge(residualEdge, v2);
		}
	}
	
	/**
	 * Returns active node
	 * 
	 * @return an active node
	 */
	private static ResidualVertex getActiveNode() {
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
	private static ResidualEdge findMinHeight(ResidualVertex residualVertex) {
		
		LinkedList<ResidualEdge> edges = residualVertex.outgoingEdge;
		ResidualVertex endpointTwo;
		ResidualEdge finalEdge = null;
		double minHeight = residualVertex.getHeight();
		
		for (ResidualEdge edge : edges) {
			ResidualEdge residualEdge = edge;
			endpointTwo = residualEdge.getOtherEnd();
			if (minHeight > endpointTwo.getHeight()) {
				minHeight = endpointTwo.getHeight();
				finalEdge = residualEdge;
			}
		}
		return finalEdge;
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
