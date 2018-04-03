/**
 * Written by Ed Hong UWT Feb. 19, 2003.
 * Modified by Donald Chinn May 14, 2003.
 * Modified by Donald Chinn December 11, 2003.
 */

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * A class that can read a graph (in a specific format) from a file.
 * 
 * @author edhong
 * @version 0.0
 */
public class GraphInput {

    /**
     * Load graph data from a text file via user interaction.
     * This method asks the user for a directory and path name.
     * It returns a hashtable of (String, Vertex) pairs.
     * newgraph needs to already be initialized.
     * @param newgraph  a simple graph
     * @returns a hash table of (String, Vertex) pairs
     */
    public static Hashtable LoadSimpleGraph(SimpleGraph newgraph) {
        System.out.print("Please enter the full path and file name for the input data: ");
        String userinput;
        userinput = KeyboardReader.readString();
        return LoadSimpleGraph(newgraph, userinput);
    }

    /**
     * Load graph data from a text file.
     * The format of the file is:
     * Each line of the file contains 3 tokens, where the first two are strings
     * representing vertex labels and the third is an edge weight (a double).
     * Each line represents one edge.
     * 
     * This method returns a hashtable of (String, Vertex) pairs.
     * 
     * @param newgraph  a graph to add edges to. newgraph should already be initialized
     * @param pathandfilename  the name of the file, including full path.
     * @returns  a hash table of (String, Vertex) pairs
     */
    public static Hashtable LoadSimpleGraph(SimpleGraph newgraph, String pathandfilename){
        BufferedReader  inbuf = InputLib.fopen(pathandfilename);
        System.out.println("Opened " + pathandfilename + " for input.");
        String  line = InputLib.getLine(inbuf); // get first line
        StringTokenizer sTok;
        int n, linenum = 0;
        Hashtable table = new Hashtable();
        SimpleGraph sg = newgraph;

        while (line != null) {
            linenum++;
            sTok = new StringTokenizer(line);
            n = sTok.countTokens();
            if (n==3) {
                Double edgedata;
                Vertex v1, v2;
                String v1name, v2name;

                v1name = sTok.nextToken();
                v2name = sTok.nextToken();
                edgedata = new Double(Double.parseDouble(sTok.nextToken()));
                v1 = (Vertex) table.get(v1name);
                if (v1 == null) {
//                      System.out.println("New vertex " + v1name);
                        v1 = sg.insertVertex(null, v1name);
                        table.put(v1name, v1);
                }
                v2 = (Vertex) table.get(v2name);
                if (v2 == null) {
//                      System.out.println("New vertex " + v2name);
                    v2 = sg.insertVertex(null, v2name);
                    table.put(v2name, v2);
                }
//              System.out.println("Inserting edge (" + v1name + "," + v2name + ")" + edgedata);
                sg.insertEdge(v1,v2,edgedata, null);
            }
            else {
                System.err.println("Error:invalid number of tokens found on line " +linenum+ "!");
                return null;
            }
            line = InputLib.getLine(inbuf);
        }

        InputLib.fclose(inbuf);
        System.out.println("Successfully loaded "+ linenum + " lines. ");
        return table;
    }


    /**
     * Code to test the methods of this class.
     */
    public static void main (String args[]) {
          SimpleGraph G;
          G = new SimpleGraph();
          //LoadSimpleGraph(G, args[0]);
          LoadSimpleGraph(G, "./src/FixedDegree/testVertexNumber/10v-5o-25min-200max.txt");
          
          //FordFulkerson
          long startTime1 = System.nanoTime();
          FordFulkerson ff = new FordFulkerson(G);
          double maxFlow1 = ff.getMaxFlow();
          long endTime1 = System.nanoTime();
          long totalTime1 = (endTime1 - startTime1) / 1000;
          System.out.println("FordFulkerson: The max flow value is: " +  maxFlow1);
          System.out.println("FordFulkerson: The running time is: " +  totalTime1 + " microsecond");
          
          //ScalingFordFulkerson
          long startTime2 = System.nanoTime();
          ScalingFordFulkerson sff = new ScalingFordFulkerson(G);
          double maxFlow2 = sff.getMaxFlow();
          long endTime2 = System.nanoTime();
          long totalTime2 = (endTime2 - startTime2) / 1000;
          System.out.println("ScalingFordFulkerson: The max flow value is: " +  maxFlow2);
          System.out.println("ScalingFordFulkerson: The running time is: " +  totalTime2 + " microsecond");
          
          //PreflowPush
          long startTime3 = System.nanoTime();
          PreflowPush pp = new PreflowPush(G);
          double maxFlow3 = pp.maxFlow;
          long endTime3 = System.nanoTime();
          long totalTime3 = (endTime3 - startTime3) / 1000;
          System.out.println("PreflowPush: The max flow value is: " +  maxFlow3);
          System.out.println("PreflowPush: The running time is: " +  totalTime3 + " microsecond");
          
    }
}