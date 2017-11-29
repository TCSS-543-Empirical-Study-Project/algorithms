import java.io.*;
import java.util.*;


public class BipartiteGraph
{
	public static void main(String[] args) throws Exception
	{
		int n, m, maxCapacity, i, j, minCapacity;
		double maxProbability, value, x;
		System.out.println("\n\n---------------------------------------------------");
		System.out.print("Enter number of nodes on the source side: \t");
		n = GetInt();
		System.out.print("Enter number of nodes on the sink side: \t");
		m = GetInt();
		System.out.print("Enter max probability: \t\t\t\t");
		maxProbability = GetReal();
		if(maxProbability > 1)
		{
			System.out.println("Max probability should be less than or equal to 1");
			return;
		}
		System.out.print("Enter minimum capacity: \t\t\t");
		minCapacity = GetInt();
		System.out.print("Enter maximum capacity: \t\t\t");
		maxCapacity = GetInt();
		String directory = System.getProperty("user.dir");
		System.out.print("Enter the output file name: \t\t\t");
		String fileName = GetString() + ".txt";	
		System.out.println("---------------------------------------------------\n");

		try
		{
			PrintWriter outFile = new  PrintWriter(new FileWriter(new File(directory, fileName)));

			double[][] edge = new double[n][m];
			for(i=0; i<n; i++)
			{
				for(j=0; j<m; j++)
				{
					value = Math.random();
					if(value <= maxProbability)
						edge[i][j] = value;
					else
						edge[i][j] = 0;
				}
			}
			
			System.out.println("-----------------------------------------");
			System.out.println("\tSource\tSink\tCapacity");
			System.out.println("-----------------------------------------");			

			//computing the edges out of source
			for (i = 0; i < n; i++)
			{
				x=Math.random();
				//Compute a capacity in range of [minCapacity, maxCapacity]
				value = Math.floor(minCapacity + (x * (maxCapacity - minCapacity + 1)));
				System.out.println("\t" + "s" + "\tl" + (i + 1) + "\t" + (int)value);
				outFile.println("\t" + "s" + "\tl" + (i + 1) + "\t" + (int)value);
			}
			for(i=0; i<n; i++)
			{
				for(j=0; j<m; j++)
				{				
					if(edge[i][j] > 0)
					{
						edge[i][j] = Math.floor(minCapacity + (edge[i][j] * (maxCapacity - minCapacity + 1)));
						System.out.println("\tl"+ (i+1) + "\tr" + (j+1) + "\t" + (int)edge[i][j]);
						//computing for the vertices between source and sink and writing them to the output file
						outFile.println("\tl"+ (i+1) + "\tr" + (j+1) + "\t" + (int)edge[i][j]);
					}
				}
			}
			//computing the edges into the sink
			for (j=0; j < m; j++)
			{
				x=Math.random();
				value = Math.floor(minCapacity + (x * (maxCapacity - minCapacity + 1)));
				System.out.println("\tr" + (j+1) + "\t" + "t" + "\t" + (int)value);
				outFile.println("\tr" + (j + 1) + "\t" + "t" + "\t" + (int)value);
			}

			System.out.println("\n\nOutput is created at: \t" + directory + "\\" + fileName);
			outFile.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}

	//helper functions
	public static String GetString() throws IOException 
	{
		BufferedReader stringIn = new BufferedReader (new
			InputStreamReader(System.in));
		return  stringIn.readLine();
	}

	public static int GetInt() throws IOException 
	{
		String aux = GetString();
		return Integer.parseInt(aux);
	}

	public static double GetReal() throws IOException 
	{
		String  aux = GetString();
		Double d  = new Double(aux);
		return  d.doubleValue() ;
	}
}