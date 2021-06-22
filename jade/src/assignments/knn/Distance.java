package assignments.knn;
import java.lang.Math;
import java.util.Arrays;

public class Distance {
	
	public static double[] euclidean(double[][] dataset, double[] unknownPoint) {
		double[] distances = new double[dataset[0].length];
		
		for(int i=0; i<dataset[0].length; i++) {
			double[] point = new double[2];
			point[0] = dataset[0][i];
			point[1] = dataset[1][i];
					
			distances[i] = twoPointsDistance(point, unknownPoint);
		}
		
		return distances;
	}
	
	public static int[] closerElements(double[] distances, int k) {
		int[] closerIndexes = new int[k];
		double[] sortedDistances = distances.clone();
		Arrays.sort(sortedDistances);
		
		for(int i=0; i<k; i++) {
			int j=0;
			while (j < distances.length) {
	            if (distances[j] == sortedDistances[i]) {
	                closerIndexes[i] = j;
	                break;
	            }
	            j++;
	        }
		}
		
		return closerIndexes;
	}
	
	private static double twoPointsDistance(double[] p1, double[] p2) {
		return Math.sqrt( Math.pow((p2[0]-p1[0]), 2) + Math.pow((p2[1]-p1[1]), 2));
	}
	
}
