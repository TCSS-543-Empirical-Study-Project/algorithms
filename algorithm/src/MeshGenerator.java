/*
 * TCSS 543 Group 2
 * Assignment: Network flow project
 * Program: Mesh Graph Generator
 * Apaporn Boonyaratta, Richard Hill, Quang Lu, & David Thaler
 * November 21, 2008
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * This class generates text files representing mesh graph flow networks. The
 * mesh graphs have edges from s to each node in the first column, from each
 * internal node to the node on its right, both ways between every internal node
 * and the nodes above and below it, and from the last column nodes to the sink.
 * 
 * The file format is the standard TCSS 343/543 format of 'first vertex' 'second
 * vertex' 'capacity'. The program takes command line arguments. They are: [# of
 * rows] [# of columns][capacity or maximum capacity][filename][-cc flag]. All
 * arguments are optional. If you enter no arguments, you get a 3 x 4 mesh with
 * capacity of 1 on all edges, printed to System.out. The arguments are:
 * 
 * #rows/columns - self-explanatory...defaults to 3x4 if no arguments are given
 * 
 * capacity - defaults to 1(fixed) if <3 arguments. Otherwise random on the
 * range 1 to capacity, unless '-cc' set.
 * 
 * filename - the name of file to write to. Defaults to System.out if <4
 * parameters (or if -cc is last parameter)
 * 
 * -cc flag...With at least the first three parameters specified, ending the
 * line with '-cc' will cause edge capacities to have a constant value of c.
 * 
 * @author TCSS 543 group 2: Apaporn Boonyaratta, Richard Hill, Quang Lu, &
 *         David Thaler
 * @version November 21, 2008
 */
public class MeshGenerator {

	/** The number of rows */
	private static int rows;
	/** The number of columns */
	private static int columns;
	/** The minimum capacity */
	private static int minCap;
	/** The capacity limit, if random, or the capacity of each edge, if fixed. */
	private static int maxCap;
	/** The output location, either a file or System.out. */
	private static PrintWriter out;
	/** The random number generator */
	private static Random rand;
	/** The user directory. */
	private static String directory;
	/** The file out name. */
	private static String fileName;

	/**
	 * The run method.
	 */
	public void generate() {

		// the s to first column links
		for (int i = 1; i <= rows; i++) {
			out.printf("s (%d,1) %d\n", i, capacity());
		}

		// left to right links across the rows
		for (int j = 1; j <= columns - 1; j++) {
			for (int i = 1; i <= rows; i++) {
				line(i, j, i, j + 1, capacity());
			}
		}

		// two-way top to bottom links on the columns
		for (int j = 1; j <= columns; j++) {
			for (int i = 1; i <= rows - 1; i++) {
				line(i, j, i + 1, j, capacity());
				line(i + 1, j, i, j, capacity());
			}
		}

		// last column to t links
		for (int i = 1; i <= rows; i++) {
			out.printf("(%d,%d) t %d\n", i, columns, capacity());
			//System.out.println("(%d,%d) t %d\n", i, columns, capacity());
		}
		
		System.out.println("\n\nOutput is created at: \t" + directory + "\\" + fileName);
		out.close();
	}

	/**
	 * Utility method to generate one line of output at an interior node in the
	 * graph. The line reads 'first node' 'second node' capacity , where the nodes
	 * are represented as (row #, column #). This method should be called with the
	 * correct capacity for this node; it doesn't generate them.
	 * 
	 * @param i1 -
	 *          first node row #
	 * @param j1-
	 *          first node column #
	 * @param i2-
	 *          second node row #
	 * @param j2-
	 *          second node column #
	 * @param cap -
	 *          the capacity entry
	 */
	private void line(int i1, int j1, int i2, int j2, int cap) {
		out.printf("(%d,%d) (%d,%d) %d\n", i1, j1, i2, j2, cap);
	}

	/**
	 * Utility method to generate edge capacities for mesh graph generator. These
	 * are constant with value c if the constCap flag is set, random on the range
	 * from 1 to c otherwise.
	 * 
	 * @return Capacity between minCap and maxCap.
	 */
	private int capacity() {
		return rand.nextInt(maxCap) + minCap;
	}

	/**
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.print("Enter number of rows: ");
		rows = BipartiteGraph.GetInt();
		
		System.out.print("Enter number of columns: ");
		columns = BipartiteGraph.GetInt();
		
		System.out.print("Enter minimum edge capacity: ");
		minCap = BipartiteGraph.GetInt();
		
		System.out.print("Enter maximum edge capacity: ");
		maxCap = BipartiteGraph.GetInt();
		
		System.out.print("Enter a name for the file (do not type \".txt\"): ");
		fileName = BipartiteGraph.GetString();
		
		directory = System.getProperty("user.dir");
		out = new  PrintWriter(new FileWriter(new File(directory, fileName)));
		
		MeshGenerator mesh = new MeshGenerator();
		rand = new Random();
		mesh.generate();
	}
}
