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

package assignments.ann;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Scanner;

import java.lang.Math;
import java.util.Arrays;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class DeepLearning extends Agent {

  public static int[][] dataset = {
      { // RIGHT TURN
        -1,
        0,0,1,0,0,  
        0,0,1,1,0,  
        1,1,1,1,1,  
        1,0,1,1,0,  
        1,0,1,0,0,
        1,1
      },
      {
        -1,
        0,0,1,0,0,
        0,0,0,1,0,
        1,1,1,1,1,
        1,0,0,1,0,
        1,0,1,0,0,
        1,1
      },
      {
        -1,
        0,0,1,1,0,
        0,0,0,1,1,
        1,1,1,1,1,
        1,0,0,1,1,
        1,0,1,1,0,
        1,1
      },
      { // LEFT TURN
        -1,
        0,0,1,0,0,
        0,1,1,0,0,
        1,1,1,1,1,
        0,1,1,0,1,
        0,0,1,0,1,
        0,0
      },
      {
        -1,
        0,0,1,0,0,
        0,1,0,0,0,
        1,1,1,1,1,
        0,1,0,0,1,
        0,0,1,0,1,
        0,0
      },
      {
        -1,
        0,1,1,0,0,
        1,1,0,0,0,
        1,1,1,1,1,
        1,1,0,0,1,
        0,1,1,0,1,
        0,0
      }
  };
  

  public static int[][] testingDataset = {
      { // RIGHT TURN
        -1,
        0,0,1,1,0,  
        0,0,1,1,0,  
        1,1,1,1,1,  
        1,0,1,1,0,  
        1,0,1,0,0
      },
      {
        -1,
        0,0,1,0,0,
        0,0,0,1,1,
        1,1,1,1,1,
        1,0,0,1,0,
        1,0,1,0,0
      },
      {
        -1,
        0,0,1,1,0,
        0,0,0,1,1,
        1,1,1,1,1,
        1,0,1,1,1,
        1,0,1,1,0
      },
      { // LEFT TURN
        -1,
        1,0,1,0,0,
        0,1,1,0,0,
        1,1,1,1,1,
        0,1,1,0,1,
        0,0,1,0,1
      },
      {
        -1,
        0,0,1,0,0,
        0,1,0,0,1,
        1,1,1,1,1,
        0,1,0,0,1,
        0,0,1,0,1
      },
      {
        -1,
        0,1,1,0,0,
        1,1,1,0,0,
        1,1,1,1,1,
        1,1,0,0,1,
        0,1,1,0,1
      }
  };

  private static int inputSignals = 25;
  private static int hiddenNeurons = 8;
  private static int outputNeurons = 2;
  private static int maxEpochs = 1000;
  private static double errorRange = 0.02;
  public static double learningCoef = 0.2;
  
  private static double[][][] weights;



  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");

    addBehaviour(new NeuralNetwork());
  } 

  class NeuralNetwork extends OneShotBehaviour {

    public void action() {
      System.out.println("Neural Network Behaviour: ");
      
      initializeNN();
      ForwardPass fp = new ForwardPass();
      BackwardPass bp = new BackwardPass();
      
      int epoch = 0;
      double errorsSum=0.0, errorsProm=1;

      // TRAINING 
      while (errorsProm > errorRange && epoch < maxEpochs) {
        errorsSum = 0.0;
        for(int i=0; i<dataset.length; i++) {
          double[] hlOutput = fp.hiddenLayerOutput(Arrays.copyOfRange(dataset[i], 0, dataset[i].length-2), weights[0]);
          double[] output = fp.calculateNNOutput(Arrays.copyOfRange(dataset[i], 0, dataset[i].length-2), weights);
          double[] errors = new double[outputNeurons];
          
          for(int j=0; j<outputNeurons; j++) {
            errors[j] = dataset[i][dataset[i].length-2+j] - output[j];
            errorsSum += Math.pow(errors[j], 2);
          }
          bp.updateWeights(Arrays.copyOfRange(dataset[i], 0, dataset[i].length-2), hlOutput, output, weights, errors);
        }
        
        errorsProm = (double)(errorsSum/(dataset.length*outputNeurons));
        epoch++;
        System.out.println(errorsProm);
      }
      
      System.out.println("\nEpochs: " + epoch);
      System.out.println();
      
      // TESTING
      for(int i=0; i<testingDataset.length; i++) {
        double[] output = fp.calculateNNOutput(testingDataset[i], weights);
        System.out.println(Arrays.toString(output));
        System.out.println(Arrays.toString(binarizeOutput(output)));
      }
    }


    private void initializeNN() {
      weights = new double[2][][]; // Two layers + input layer
      weights[0] = new double[hiddenNeurons][inputSignals+1]; // Hidden layer weights
      weights[1] = new double[outputNeurons][hiddenNeurons+1];// Output layer weights
      
      for(int p=0; p<weights.length; p++)
        for(int i=0; i<weights[p].length; i++)
          for(int j=0; j<weights[p][i].length; j++)
            weights[p][i][j] = Math.random();
          
    }
    
    private double[] binarizeOutput(double []output) {
      double[] binerized = new double[output.length];
        
          for(int i=0; i<output.length; i++) {
            if (output[i] > 0.5) binerized[i] = 1;
            else binerized[i] = 0;
          }
          
      return binerized;
    }

  }

}
