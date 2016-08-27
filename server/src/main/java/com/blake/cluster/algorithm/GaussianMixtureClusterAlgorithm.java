package com.blake.cluster.algorithm;

import java.util.HashMap;
import java.util.HashSet;

import Jama.Matrix;

public class GaussianMixtureClusterAlgorithm {
	
	public int round = 20;
	public int component = 10;
	int []a =  new int[component];
	int u[][] = new int[component][];
	Matrix m[] = new Matrix[component];
	
	public HashMap<Integer,HashSet<double[]>> doGaussianMixtureAlgorithn(double[][] input, int k) {
		
		
		while(loopCondition()) {
			
			
			round--;
		}
		
		
		return null;
		
	}

	private boolean loopCondition() {

		if(round == 0) {
			
			return true;
			
		} else {
		
			return false;
			
		}
	}

}
