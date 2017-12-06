import java.io.File;
import java.io.IOException;

public class tcss543 {

	public static void main(String[] args) throws IOException {
		tcss543 test = new tcss543();
		if (args.length > 0) {
			SimpleGraph G = new SimpleGraph();
			GraphInput.LoadSimpleGraph(G, args[0]);
			FordFulkerson ff = new FordFulkerson(G);
			ScalingFordFulkerson sff = new ScalingFordFulkerson(G);
			PreflowPush pp = new PreflowPush(G);
			test.run(ff, sff, pp);
		} else {
			test.testPerformance();
			// throw new IOException("please input a text file");
		}
	}

	// input the graph file to test three algorithm
	public void run(FordFulkerson ff, ScalingFordFulkerson sff, PreflowPush pp) {
		System.out.println("Test three algorithms for 10 times given an input file");
		System.out.println("-----------------------------------------------------------");

		// Ford Fulkerson Algorithm
		runAlgorithm("Ford Fulkerson Algorithm", ff, sff, pp);

		// Scaling Ford Fulkerson Algorithm
		runAlgorithm("Scaling Ford Fulkerson Algorithm", ff, sff, pp);

		// Preflow Push Algorithm
		 runAlgorithm("Preflow Push Algorithm", ff, sff, pp);
	}

	public static void runAlgorithm(String algorithm, FordFulkerson ff, ScalingFordFulkerson sff, PreflowPush pp) {
		int times = 5;
		long[] runtime = new long[times];
		long start = 0;
		double maxFlow = 0;
		System.out.println("->Test for " + algorithm);
		for (int i = 0; i < times; i++) {
			start = System.currentTimeMillis();
			if (algorithm.equals("Ford Fulkerson Algorithm")) {
				maxFlow = ff.getMaxFlow();
			} else if (algorithm.equals("Scaling Ford Fulkerson Algorithm")) {
				maxFlow = sff.getMaxFlow();
			} else {
				maxFlow = pp.getMaxFlow();
			}
			runtime[i] = System.currentTimeMillis() - start;
		}
		long result = getAve(runtime);
		System.out.println(algorithm + ": Max flow: " + maxFlow);
		System.out.println(algorithm + ": Running time: " + result + " millisecond");
	}

	public static long getAve(long[] runtime) {
		long sum = 0;
		int length = runtime.length;
		for (int i = 0; i < length; i++) {
			sum += runtime[i];
		}
		return sum / length;
	}

	// use our test files to test three algorithm
	public void testPerformance() {
		System.out.println("Test three algorithms for 5 times based on testcases we created");
		System.out.println("-----------------------------------------------------------");
		String[] graphTypes = { "./src/Bipartite", "./src/FixedDegree", "./src/Mesh", "./src/Random" };
		for (int i = 0; i < graphTypes.length; i++) {
			File folder = new File(graphTypes[i]);
			File[] testScenarios = folder.listFiles();
			for (int j = 0; j < testScenarios.length; j++) {
				File[] testcases = testScenarios[j].listFiles();
				for (int k = 0; k < testcases.length; k++) {
					SimpleGraph G = new SimpleGraph();
					GraphInput.LoadSimpleGraph(G, testcases[k].getPath());
					FordFulkerson ff = new FordFulkerson(G);
					ScalingFordFulkerson sff = new ScalingFordFulkerson(G);
					PreflowPush pp = new PreflowPush(G);
					System.out.println("----------------************test for" + testcases[k].getPath()
							+ "************----------------");
					// Ford Fulkerson Algorithm
					runAlgorithm("Ford Fulkerson Algorithm", ff, sff, pp);

					// Scaling Ford Fulkerson Algorithm
					runAlgorithm("Scaling Ford Fulkerson Algorithm", ff, sff, pp);

					// Preflow Push Algorithm
					 runAlgorithm("Preflow Push Algorithm", ff, sff, pp);
				}
			}
		}
	}
}
