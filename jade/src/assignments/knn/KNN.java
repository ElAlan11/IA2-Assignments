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

package assignments.knn;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Scanner;
import java.util.Arrays;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class KNN extends Agent {

  private double x1, x2; 

  public static double[][] dataset = {
      {158.0, 158.0, 158.0, 160.0, 160.0, 163.0, 163.0, 160.0, 163.0, 165.0, 165.0, 165.0, 168.0, 168.0, 168.0, 170.0, 170.0, 170.0},
      {58.0,   59.0,  63.0,  59.0,  60.0,  60.0,  61.0,  64.0,  64.0,  61.0,  62.0,  65.0,  62.0,  63.0,  66.0,  63.0,  64.0,  68.0},
      {0.0,     0.0,   0.0,   0.0,   0.0,   0.0,   0.0,   1.0,   1.0,   1.0,   1.0,   1.0,   1.0,   1.0,   1.0,   1.0,   1.0,   1.0} // 0 for M; 1 for L
  };
  
//  public static double[][] dataset = {
//      {0.125, 0.375, 0.625, 0.00, 0.375, 0.8, 0.075, 0.5,  1.0,  0.7,  0.325},
//      {0.11,  0.21,  0.31,  0.01, 0.50,  0.0, 0.38,  0.22, 0.41, 1.00, 0.65},
//      {0.0,    0.0,   0.0,   0.0,  0.0,  0.0,  1.0,   1.0,  1.0,  1.0,  1.0}
//  };

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");

    KnnGUI gui = new KnnGUI(this);
    gui.showGui();
  } 

  public void updateX(final double x1R, final double x2R) {
    addBehaviour(new OneShotBehaviour() {
      public void action() {
        x1 = x1R;
        x2 = x2R;
        addBehaviour(new KNNClasiffication(x1, x2));
      }
    } );
  }

  class KNNClasiffication extends OneShotBehaviour {
    private double[] predictors;
    private int k = 3;

    KNNClasiffication(double val1, double val2){
    	predictors = new double[2];
    	predictors[0] = val1;
    	predictors[1] = val2;
    }
   
    public void action() {
      System.out.println("KNN Classification: ");
      
      DataPreprocessing processor = new DataPreprocessing(dataset);
      double[][] stdDataset = processor.standardize();
      
      double[] distances = Distance.euclidean(stdDataset, processor.standardize(predictors));
      int[] closerElemInd = Distance.closerElements(distances, k);
      int dataClass = classify(closerElemInd, k);
      
      // System.out.println(Arrays.toString(closerElemInd));
      
      if(dataClass == 0)
        System.out.println("T-Shirt Size = M");
      else
        System.out.println("T-Shirt Size = L");

    }


    private int classify(int[] closerElements, int k){
    int class1freq = 0, class2freq = 0;
    
      for(int i=0; i<closerElements.length; i++) {
        if(dataset[2][closerElements[i]] == 0)
          class1freq++;
        else
          class2freq++;
      } 
      
      if (class1freq > class2freq)
        return 0;
      else
        return 1;
    }

  }

}
