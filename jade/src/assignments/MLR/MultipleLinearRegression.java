/**
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 * 
 * GNU Lesser General Public License
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */

package assignments.MLR;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MultipleLinearRegression extends Agent {

  private double x1, x2; 

  public static double[][] dataset = {
   		{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17},
   		{41.9, 43.4, 43.9, 44.5, 47.3, 47.5, 47.9, 50.2, 52.8, 53.2, 56.7, 57.0, 63.5, 65.3, 71.1, 77.0, 77.8},
   		{29.1, 29.3, 29.5, 29.7, 29.9, 30.3, 30.5, 30.7, 30.8, 30.9, 31.5, 31.7, 31.9, 32.0, 32.1, 32.5, 32.9},
   		{251.3, 251.3, 248.3, 267.5, 273.0, 276.5, 270.3, 274.9, 285.0, 290.0, 297.0, 302.5, 304.5, 309.3, 321.7, 330.7, 349.0}
  };


  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");

    RegressionGUI gui = new RegressionGUI(this);
    gui.showGui();
  } 

  public void updateX(final double x1R, final double x2R) {
    addBehaviour(new OneShotBehaviour() {
      public void action() {
        x1 = x1R;
        x2 = x2R;
        addBehaviour(new MatrixAlgebraPrediction(x1, x2));
        addBehaviour(new CramerPrediction(x1, x2));
      }
    } );
  }

  class CramerPrediction extends OneShotBehaviour {
    private double[] predictors;

    CramerPrediction(double val1, double val2){
    	predictors = new double[2];
    	predictors[0] = val1;
    	predictors[1] = val2;
    }
   
    public void action() {
      System.out.println("Cramer Method: ");
      
      Solver cramerMethod = new Cramer();
		double[] coef = cramerMethod.calculateCoefficients(dataset);
		double prediction = cramerMethod.predict(coef, predictors);
      
      System.out.println("y = b0+b1(x1)+b2(x2)");
      System.out.println("y = " + coef[0] + "+" + coef[1] + "(" + predictors[0] + ")" + "+" + coef[2] + "(" + predictors[1] + ")");
      System.out.println("y = " + prediction);

    }
  }

   
  class MatrixAlgebraPrediction extends OneShotBehaviour {
    private double[] predictors;

    MatrixAlgebraPrediction(double val1, double val2){
    	predictors = new double[2];
    	predictors[0] = val1;
    	predictors[1] = val2;
    }
   
    public void action() {
      System.out.println("Matrix Algebra Method: ");
      
      Solver algebraMethod = new MatrixAlgebra();
		double[] coef = algebraMethod.calculateCoefficients(dataset);
		double prediction = algebraMethod.predict(coef, predictors);
      
      System.out.println("y = b0+b1(x1)+b2(x2)");
      System.out.println("y = " + coef[0] + "+" + coef[1] + "(" + predictors[0] + ")" + "+" + coef[2] + "(" + predictors[1] + ")");
      System.out.println("y = " + prediction);

    }

  }

}
