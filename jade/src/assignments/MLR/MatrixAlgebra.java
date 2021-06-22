package assignments.MLR;
import java.util.Arrays;

public class MatrixAlgebra implements Solver{
	
	public double[] calculateCoefficients(double[][] dataset) {
		double coefficients[] = new double[3];
		double[][] x = new double[dataset[0].length][dataset.length-1];
		double[][] y = new double[dataset[0].length][1];
		double[][] xPrime = Arrays.copyOfRange(dataset, 0, dataset.length-1);
		double[][] XpX;
		double[][] XpY;
		double[][] invertedXpX;
		double[][] beta;
		
		for(int i=0; i<dataset[0].length; i++)
			y[i][0] = dataset[3][i];
		
		// Add a column of 1's
		for(int i=0; i<dataset[0].length; i++)
			dataset[0][i] = 1.0;
		
		// Compute XPrime's transpose
		for(int i=0; i<dataset[0].length; i++) {
			for(int j=0; j<dataset.length-1; j++) {
				x[i][j] = xPrime[j][i];
			}
		}
		
		XpX = multiplyMatrices(xPrime, x);
		invertedXpX = invert(XpX);
		XpY = multiplyMatrices(xPrime, y);
		beta = multiplyMatrices(invertedXpX, XpY);
		
		
		for(int i=0; i<beta.length; i++)
			coefficients[i] = beta[i][0];
		
		return coefficients;
	}
	
	
	public double predict(double[] coef, double[] input) {
		double prediction = 0.0;
		prediction = coef[0] + coef[1]*input[0] + coef[2]*input[1];
		return prediction;
	}
	
	
	double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
	    double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

	    for (int row = 0; row < result.length; row++) {
	        for (int col = 0; col < result[row].length; col++) {
	            result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
	        }
	    }

	    return result;
	}
	
	double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
	    double cell = 0;
	    for (int i = 0; i < secondMatrix.length; i++) {
	        cell += firstMatrix[row][i] * secondMatrix[i][col];
	    }
	    return cell;
	}
	
	
	public static double[][] invert(double a[][]) 
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
 // Transform the matrix into an upper triangle
        gaussian(a, index);
 
 // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                    	    -= a[index[j]][i]*b[index[i]][k];
 
 // Perform backward substitutions
        for (int i=0; i<n; ++i) 
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) 
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) 
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
 
// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.
 
    public static void gaussian(double a[][], int index[]) 
    {
        int n = index.length;
        double c[] = new double[n];
 
 // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
 // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
 // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
 
   // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	
            {
                double pj = a[index[i]][j]/a[index[j]][j];
 
 // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
 // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }
	
	
}
