/*
 * TCSS 543 Group 2
 * Assignment: Network flow project
 * Program: Mesh Graph Generator
 * Apaporn Boonyaratta, Richard Hill, Quang Lu, & David Thaler
 * November 21, 2008
 */
import java.io.FileNotFoundException;
import java.io.PrintStream;
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
  private int m;
  /** The number of columns */
  private int n;
  /** The capacity limit, if random, or the capacity of each edge, if fixed. */
  private int c;
  /** The output location, either a file or System.out. */
  private PrintStream out;
  /** The random number generator */
  private Random rand;
  /** True if the edge capacities are constant. */
  private boolean constCap;

  /**
   * The run method.
   */
  public void generate() {

    // the s to first column links
    for (int i = 1; i <= m; i++) {
      out.printf("s (%d,1) %d\n", i, capacity());
    }

    // left to right links across the rows
    for (int j = 1; j <= n - 1; j++) {
      for (int i = 1; i <= m; i++) {
        line(i, j, i, j + 1, capacity());
      }
    }

    // two-way top to bottom links on the columns
    for (int j = 1; j <= n; j++) {
      for (int i = 1; i <= m - 1; i++) {
        line(i, j, i + 1, j, capacity());
        line(i + 1, j, i, j, capacity());
      }
    }

    // last column to t links
    for (int i = 1; i <= m; i++) {
      out.printf("(%d,%d) t %d\n", i, n, capacity());
    }
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
   * @return either c or a random number from 1 to c
   */
  private int capacity() {
    if (constCap) {
      return c;
    }
    // rand(c)+1 so that we can get 1 to Cmax as values.
    return rand.nextInt(c) + 1;
  }

  /**
   * Constructor for mesh generator parses the command line arguments and sets
   * the defaults. See the class comment for arguments/defaults.
   * 
   * @param args -
   *          the command line arguments. See class comment.
   */
  public MeshGenerator(String[] args) {
    rand = new Random();
    // the constant capacity flag
    if (args.length == 5 && args[4].equals("-cc")) {
      constCap = true;
    }
    // where to write the output
    if (args.length >= 4 && !args[3].equals("-cc")) {
      try {
        out = new PrintStream(args[3]);
      } catch (FileNotFoundException e) {
        System.err.println("Exception thrown on file formation: " + args[3]);
      }
    } else {
      out = System.out;
    }
    if (args.length == 4 && args[3].equals("-cc")) {
      constCap = true;
    }
    // set the capacity, it defaults to 1.
    if (args.length >= 3) {
      c = Integer.parseInt(args[2]);
    } else {
      constCap = true;
      c = 1;
    }
    // m is the rows, n the colums, 3 rows by 4 col is the default
    if (args.length >= 2) {
      n = Integer.parseInt(args[1]);
      m = Integer.parseInt(args[0]);
    } else {
      n = 4;
      m = 3;
    }
  }

  /**
   * @param args-
   *          command line args
   */
  public static void main(String[] args) {

    MeshGenerator mesh = new MeshGenerator(args);
    mesh.generate();
  }

}
