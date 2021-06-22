package assignments.knn;

public class DataPreprocessing {
	private double[][] dataset;
	private double[] minimums;
	private double[] maximums;
	
	DataPreprocessing(double[][] ds){
		dataset = ds;
		minimums = new double[dataset.length-1];
		maximums = new double[dataset.length-1];
		
		for(int i=0; i<dataset.length-1; i++) {
			minimums[i] = getMin(dataset[i]);
			maximums[i] = getMax(dataset[i]);
		}
	}
	
	public double[][] standardize() {
		double[][] standardized = new double[dataset.length-1][dataset[0].length];
		
		for(int i=0; i<dataset.length-1; i++)
			for(int j=0; j<dataset[0].length; j++)
				standardized[i][j] = (double)(dataset[i][j] - minimums[i])/(maximums[i] - minimums[i]); 
		
		return standardized;
	}
	
	public double[] standardize(double[] point) {
		double[] standardized = new double[dataset.length-1];
		
		for(int i=0; i<point.length; i++) {
			standardized[i] = (double)(point[i] - minimums[i])/(maximums[i] - minimums[i]);
		}
		
		return standardized;
	}
	
	private double getMax(double[] inputArray){ 
		double maxValue = inputArray[0]; 
	    for(int i=1;i < inputArray.length;i++){ 
	      if(inputArray[i] > maxValue){ 
	         maxValue = inputArray[i]; 
	      } 
	    } 
	    return maxValue; 
	  }

	  private double getMin(double[] inputArray){ 
		double minValue = inputArray[0]; 
		
	    for(int i=1;i<inputArray.length;i++){ 
	      if(inputArray[i] < minValue){ 
	        minValue = inputArray[i]; 
	      } 
	    } 
	    return minValue; 
	  } 
	  
}
