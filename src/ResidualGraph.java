import java.util.*;

/**
 * A class that represents a residual graph.
 * 
 * @author Mengting
 * @version 0.0
 */
public class ResidualGraph {
	LinkedList<ResidualEdge> edgeList;
	LinkedList<ResidualVertex> vertexList;
	public ResidualGraph() {
		this.edgeList = new LinkedList<>();
		this.vertexList = new LinkedList<>();
	}
	
	public Iterator<?> vertices() {
        return vertexList.iterator();
    }
	
	public Iterator<?> edges() {
        return edgeList.iterator();
    }
	
	public Iterator<?> nextEdgeList(ResidualVertex v) {
        return v.nextEdgeList.iterator();
    }
	
	public ResidualVertex aVertex() {
        if (vertexList.size() > 0)
            return vertexList.getFirst();
        else
            return null;
    }
	
	public void insertVertex(ResidualVertex a) {
		this.vertexList.add(a);
	}
	
	public void insertEdge(ResidualEdge edge, ResidualVertex start) {
		this.edgeList.add(edge);
		start.nextEdgeList.add(edge);
	}
	
	public void removeEdge(ResidualEdge edge) {
		ResidualVertex start = edge.getFirstPoint();
		edgeList.remove(edge);
		start.nextEdgeList.remove(edge);
		moveToEnd(start);
	}
	
	public ResidualEdge getEdge(ResidualVertex start, ResidualVertex end) {
		Iterator<?> iterator = nextEdgeList(start);
		ResidualEdge result;
		while (iterator.hasNext()) {
			result = (ResidualEdge) iterator.next();
			if (result.getSecondPoint().getName().equals(end.getName())) {
				return result;
			}
		}
		return null;
	}
	

	
	public ResidualVertex getSource() {
		for (int i = 0; i < vertexList.size(); i++) {
			ResidualVertex v = vertexList.get(i);
			if (vertexList.get(i).getName().equalsIgnoreCase("s")) {
				return vertexList.get(i);
			}
		}
		return null;
	}
	
	public void moveToEnd(ResidualEdge edge) {
		int index = edgeList.indexOf(edge);
		edgeList.remove(index);
		edgeList.add(edge);
	}
	
	public void moveToEnd(ResidualVertex vertex) {
		int index = vertexList.indexOf(vertex);
		vertexList.remove(index);
		vertexList.add(vertex);
	}
	
	public LinkedList<ResidualVertex> findPath(ResidualVertex v, LinkedList<ResidualVertex> path) {
		if (!v.isVisited()) {
			v.setVisited();
			path.add(v);
			moveToEnd(v);
		}
		if (v.hasUnvisitedNeigh()) {
			ResidualVertex neigh = v.getNeigh();
			if (!neigh.getName().equalsIgnoreCase("t")) {
				findPath(neigh, path);
			} else {
				path.add(neigh);
			}
		} else {
			path.remove(v);
			if (path.size() != 0) {
				v = path.getLast();
				findPath(v, path);
			}
		}
		return path;
	}
}
