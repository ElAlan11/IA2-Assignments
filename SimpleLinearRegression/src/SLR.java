import java.util.Scanner;

public class SLR {
	
	public static void benettonCase() {
		int[][] dataset = {{1,2,3,4,5,6,7,8,9},{651,762,856,1063,1190,1298,1421,1440,1518},{23,26,30,34,43,48,52,57,58}};
		double b0=0, b1=0, yHat=0;
	    int n=0;
	    
	    Scanner sc = new Scanner(System.in);
	    n = sc.nextInt();
	    
	    double sumY=0, sumX2=0, sumX=0, sumXY=0;
	    
	    for(int y : dataset[1])
	    	sumY += y;
	    
	    for(int x : dataset[2])
	    	sumX2 += Math.pow(x, 2);
	    
	    for(int x : dataset[2])
	    	sumX += x;
	    
	    for(int i=0; i<dataset[1].length; i++)
	    	sumXY += dataset[2][i] * dataset[1][i];
	    
	    b0 = (sumY*sumX2-sumX*sumXY)/(n*sumX2-Math.pow(sumX, 2));
	    b1 = (n*sumXY-sumX*sumY)/(n*sumX2-Math.pow(sumX, 2));
	    yHat = b0 + b1*n;
	    
	    System.out.println("y = b0+b1(x)");
	    System.out.println("y = " + b0 + "+" + b1 + "(" + n + ")");
	    System.out.println("y = " + yHat);
	}
	
	public static void main(String[] argc) {
		benettonCase();
	}
}
