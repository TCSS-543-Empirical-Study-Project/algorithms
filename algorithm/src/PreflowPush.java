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
		// this.maxFlow = maxFlow(); //pre flow push max flow
	}

	/**
	 * Calculates the max flow of new "cut" graph
	 * 
	 * @param simpleGraph
	 * @return
	 */
	public double getMaxFlow() {
		ResidualVertex residualVertex;
		addVertices(simpleGraph); // add nodes
		addEdges(simpleGraph); // add edges
		LinkedList<?> vertices = graph.vertexList; // list of vertices in our new graph
		this.maxFlow = 0;
		while ((residualVertex = getAvailableNode()) != null) {

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
				this.maxFlow = vertex.getExcess();
			}
		}

		return maxFlow;
	}

/*	*//**
	 * Initialize the new graphs Vertexes
	 * 
	 * @param simpleGraph
	 * @return
	 * @return
	 *//*
	private HashMap<String, ResidualVertex> addVertices(SimpleGraph simpleGraph) {

		Iterator<?> vertices = simpleGraph.vertices();
		HashMap<String, ResidualVertex> vertexList = new HashMap<String, ResidualVertex>();

		while (vertices.hasNext()) {
			Vertex vertex = (Vertex) vertices.next();
			ResidualVertex residualVertex = new ResidualVertex(vertex.getName());
			vertexList.put(String.valueOf(residualVertex.getName()),
					residualVertex);
			if (residualVertex.getName().equalsIgnoreCase("s")) {
				residualVertex.setHeight(simpleGraph.numVertices()); // source node height equals total number of nodes
			} else {
				residualVertex.setHeight(0); // initial height of all other nodes
			}
			graph.insertVertex(residualVertex);
		}
		return vertexList;
	}*/
	
	private void addVertices(SimpleGraph sGraph) {
		List<Vertex> vertices = sGraph.vertexList;
		for (int i = 0; i < vertices.size(); i++) {
			ResidualVertex v = new ResidualVertex((vertices.get(i)).getName());
			if (v.getName().equalsIgnoreCase("s")) {
				v.setHeight(sGraph.numVertices());
			} else {
				v.setHeight(0);
			}
			graph.insertVertex(v);
		}
	}

/*	*//**
	 * Initialize the new graphs Edges
	 * 
	 * @param simpleGraph
	 * @param vertexList
	 *//*
	private static void addEdges(SimpleGraph g, HashMap<String, ResidualVertex> vertexList) {
		Iterator<?> edges = g.edges();
		while (edges.hasNext()) {
			Edge edge = (Edge) edges.next();
			Vertex v = edge.getFirstEndpoint();
			Vertex w = edge.getSecondEndpoint();
			double edgeCapacity = (double) edge.getData();

			ResidualVertex rv = vertexList.get(v.getName());
			ResidualVertex rw = vertexList.get(w.getName());
			ResidualEdge residualEdge;

			if (rv.getName().equalsIgnoreCase("s")) {
				residualEdge = new ResidualEdge(rw, rv, edgeCapacity);
				rw.setExcess(residualEdge.getCapacity());
				int index = graph.vertexList.indexOf(rw);
				graph.vertexList.remove(index);
				graph.vertexList.addLast(rw);
				graph.insertEdge(residualEdge, rw);
			} else {
				residualEdge = new ResidualEdge(rv, rw, edgeCapacity);
				graph.insertEdge(residualEdge, rv);
			}
		}
	}*/
	
	/**
	 * Initialize the new graphs Edges
	 * 
	 * @param simpleGraph
	 * @param vertexList
	 */
	private void addEdges(SimpleGraph sGraph) {
		List<Edge> edges = sGraph.edgeList;
		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i);
			Vertex v1 = e.getFirstEndpoint();
			Vertex u1 = e.getSecondEndpoint();
			ResidualVertex v2 = null;
			ResidualVertex u2 = null;
			Iterator<?> vertices = graph.vertices();
			while (vertices.hasNext()) {
				ResidualVertex curV = (ResidualVertex) vertices.next();
				if (curV.getName().equalsIgnoreCase((String) v1.getName())) {
					v2 = curV;
				}
				if (curV.getName().equalsIgnoreCase((String) u1.getName())) {
					u2 = curV;
				}
			}
			if (v2.getName().equalsIgnoreCase("s")) {
				ResidualEdge edge = new ResidualEdge(u2, v2, (double) e.getData());
				u2.setExcess(edge.getCapacity());
				int index = graph.vertexList.indexOf(u2);
				graph.vertexList.remove(index);
				graph.vertexList.addLast(u2);
				graph.insertEdge(edge, u2);
			} else {
				ResidualEdge edge = new ResidualEdge(v2, u2, (double) e.getData());
				graph.insertEdge(edge, v2);
			}
		}
	}

	/**
	 * Returns active node
	 * 
	 * @return an active node
	 */
	private ResidualVertex getAvailableNode() {
		Iterator<?> vertices = graph.vertices();
		while (vertices.hasNext()) {
			ResidualVertex vertex = (ResidualVertex) vertices.next();
			if (!vertex.getName().equalsIgnoreCase("s")
					&& !vertex.getName().equalsIgnoreCase("t")
					&& vertex.getExcess() > 0) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * get the Minimum Height Adjacent to the Vertex
	 * 
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
	 * 
	 * @param residualVertex
	 * @param forward
	 */
	private void push(ResidualVertex residualVertex, ResidualEdge forward) {

		ResidualVertex w2 = forward.getSecondPoint();
		double excessAvailable = residualVertex.getExcess(); 
		double canPush = forward.getCapacity();
		double pushValue = (excessAvailable < canPush) ? excessAvailable : canPush; //
		int index = 0;
		
		/*
		 * push to forward edge.
		 */
		if (pushValue == canPush) {
			graph.removeEdge(forward);
		} else {
			forward.setCapacity(forward.getCapacity() - pushValue);
			int indexForward = graph.edgeList.indexOf(forward);
			graph.edgeList.remove(indexForward);
			graph.edgeList.addLast(forward);
		}
		residualVertex.setExcess(residualVertex.getExcess() - pushValue);
		index = graph.vertexList.indexOf(residualVertex);
		graph.vertexList.remove(index);
		graph.vertexList.addLast(residualVertex);
		
		/*
		 * push to backward edge. 
		 */
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
	 * 
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
