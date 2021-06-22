package assignments.MLR;

public class Cramer implements Solver{
	
	public double[] calculateCoefficients(double[][] dataset) {
		double coefficients[] = new double[3];
		
		double sumX1=0.0, sumX2=0.0, sqrSumX1=0.0, sqrSumX2=0.0, sumX1X2=0.0, sumY=0.0, sumX1Y=0.0, sumX2Y=0.0;

	    for(double x1 : dataset[1]) {
	    	sumX1 += x1;
	    	sqrSumX1 += Math.pow(x1, 2);
	    }
	    	
	    for(double x2 : dataset[2]) {
	    	sumX2 += x2;
	    	sqrSumX2 += Math.pow(x2, 2);
	    }
	    	
	    for(double y : dataset[3]) 
	    	sumY += y;
	  
	    
	    for(int i=0; i<dataset[1].length; i++) {
	    	sumX1X2 += (dataset[1][i] * dataset[2][i]);
	    	sumX1Y += (dataset[1][i] * dataset[3][i]);
	    	sumX2Y += (dataset[2][i] * dataset[3][i]);
	    }
	    
	    double sysDet=0.0, b0Det=0.0, b1Det=0.0, b2Det=0.0;
	    double n = (double)dataset[0].length;
	    
	    
	    sysDet = n*sqrSumX1*sqrSumX2+sumX1*sumX1X2*sumX2+sumX2*sumX1*sumX1X2
	    		-sumX2*sqrSumX1*sumX2-n*sumX1X2*sumX1X2-sqrSumX2*sumX1*sumX1;
	    
	    b0Det = sumY*sqrSumX1*sqrSumX2+sumX1*sumX1X2*sumX2Y+sumX2*sumX1Y*sumX1X2 
	    		-sumX2Y*sqrSumX1*sumX2-sumX1X2*sumX1X2*sumY-sqrSumX2*sumX1Y*sumX1;
	    
	    b1Det = n*sumX1Y*sqrSumX2+sumX1*sumX2Y*sumX2+sumX2*sumY*sumX1X2
	    		-sumX2*sumX1Y*sumX2-sumX1X2*sumX2Y*n-sqrSumX2*sumY*sumX1;
	    
	    b2Det = n*sqrSumX1*sumX2Y+sumX1*sumX1Y*sumX2+sumY*sumX1*sumX1X2
	    		-sumY*sqrSumX1*sumX2-n*sumX1Y*sumX1X2-sumX1*sumX1*sumX2Y;
	    
	    coefficients[0] = (double)b0Det/sysDet;
	    coefficients[1] = (double)b1Det/sysDet;
	    coefficients[2] = (double)b2Det/sysDet;
	    
		return coefficients;
	}
	
	public double predict(double[] coef, double[] input) {
		double prediction = 0.0;
		prediction = coef[0] + coef[1]*input[0] + coef[2]*input[1];
		return prediction;
	}
	
}
