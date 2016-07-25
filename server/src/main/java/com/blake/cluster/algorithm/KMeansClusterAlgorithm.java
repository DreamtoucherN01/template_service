package com.blake.cluster.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class KMeansClusterAlgorithm {
	
	private double MAX_TIME = 100;
	
	private double sampleInput[][];
	
	private double sampleOutput[][];

	public double[][] getSampleInput() {
		return sampleInput;
	}

	public void setSampleInput(double[][] sampleInput) {
		this.sampleInput = sampleInput;
	}

	public double[][] getSampleOutput() {
		return sampleOutput;
	}

	public void setSampleOutput(double[][] sampleOutput) {
		this.sampleOutput = sampleOutput;
	}
	
	public HashMap<Integer,HashSet<double[]>> doKMeansAlgorithn(double[][] input, int k) {
		
		HashMap<Integer,HashSet<double[]>> group = null ;
		double params[][];
		
		if(null == input || input.length == 0) {
			
			if(null == sampleInput || sampleInput.length == 0) {
				
				System.out.println("please input sample");
				return null;
			} else {
				
				params = sampleInput;
			}
		} else {
			
			params = input;
		}
		
		sampleOutput = new double[k][];
		//init initial means vector
		for(int i=0 ; i< k; i++) {
			
			sampleOutput[i] = params[i];
		}
		
		int time = -1;
		while(time++ < MAX_TIME) {
		
			if(time > 0) {
				
				sampleOutput = getSampleOutput(group, sampleOutput ,k);
			}
			
			if(sampleOutput == null) {
				
				break;
			}
			
			group = new HashMap<Integer,HashSet<double[]>>();
			
			for(int i=0; i < params.length ; i++) {
				
				double max = -1;int num = -1;
				
				for(int j = 0 ; j < k; j ++) {
					
					double dist = EulcideanDistance(params[i],sampleOutput[j]);
					
					if(max == -1 || dist < max) {
						
						max = dist;
						num = j;
					}
				}
				
				if(group.get(num) != null) {
					
					group.get(num).add(params[i]);
					
				} else {
					
					HashSet<double[]> set = new HashSet<double[]>();
					set.add(params[i]);
					group.put(num, set);
					
				}
			}
			
//			prdoubleGroupOut(group, time);
		}
		
		return group;
	}

	private double[][] getSampleOutput(HashMap<Integer, HashSet<double[]>> group, double[][] sampleOutput2, int k) {

		double[][] sampleOutput = new double[k][];
		Iterator<Entry<Integer, HashSet<double[]>>> entry = group.entrySet().iterator();
		while(entry.hasNext()) {
			
			Entry<Integer, HashSet<double[]>> info = entry.next();
			HashSet<double[]> value = info.getValue();
			double meansX = 0.0;
			double meansY = 0.0;
			for(double[] a: value) {
				
					
					meansX += a[0];
					meansY += a[1];
			}
			sampleOutput[info.getKey()] = new double[2];
			sampleOutput[info.getKey()][0] = meansX/value.size();
			sampleOutput[info.getKey()][1] = meansY/value.size();
		}
		
		if(CheckSimilarity(sampleOutput, sampleOutput2)) {
			
			return null;
		}
		return sampleOutput;
	}

	private boolean CheckSimilarity(double[][] sampleOutput,
			double[][] sampleOutput2) {

		double totalDist = 0.0;
		for(int i = 0 ;i < sampleOutput.length; i++) {
			
			totalDist += EulcideanDistance(sampleOutput[i],sampleOutput2[i]);
		}
		if(totalDist < 0.0001) {
			
			return true;
		}
		return false;
	}

	public void prdoubleGroupOut(HashMap<Integer, HashSet<double[]>> group , double time) {

		System.out.println("group info when iterator " + time + " times");
		Iterator<Entry<Integer, HashSet<double[]>>> entry = group.entrySet().iterator();
		while(entry.hasNext()) {
			
			Entry<Integer, HashSet<double[]>> info = entry.next();
			System.out.println("cluster " + info.getKey());
			HashSet<double[]> value = info.getValue();
			for(double[] a: value) {
				
				System.out.println("		  " + Arrays.toString(a));
			}
		}
	}

	private double EulcideanDistance(double[] is, double[] is2) {

		double dist = 0 ;
		for(int i = 0; i < is.length; i++) {
			
			dist += Math.pow(is[i] - is2[i], 2);
		}
		return Math.sqrt(dist);
	}

}
