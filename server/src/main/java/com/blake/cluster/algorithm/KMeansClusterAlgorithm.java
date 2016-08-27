package com.blake.cluster.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.blake.cluster.util.AlgorithmUtils;
import com.blake.util.Constants;

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
		
		//检查输入
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
		
		//选取随机样本作为分类标准
		sampleOutput = new double[k][];
		
		//init initial means vector
		HashSet<Integer> duplicationCheck =  new HashSet<Integer>();
		for(int i=0 ; i< k; i++) {
			
			int find = -1;
			while(find == -1) {
				
				find = (int) (Math.random() * (params.length -1));
				if(duplicationCheck.contains(find)) {
					
					find = -1;
					continue;
				} else {
					
					duplicationCheck.add(find);
				}
				
			}
			sampleOutput[i] = params[find];
		}
		duplicationCheck = null;
		
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
//			double meansX = 0.0;
//			double meansY = 0.0;
//			for(double[] a: value) {
//					
//					meansX += a[0];
//					meansY += a[1];
//			}
//			sampleOutput[info.getKey()] = new double[2];
//			sampleOutput[info.getKey()][0] = meansX/value.size();
//			sampleOutput[info.getKey()][1] = meansY/value.size();
			double[] tmp = getCenterPointvalue(value);
			if(tmp == null){
				
				return null;
			}
			sampleOutput[info.getKey()] = tmp;
		}
		
//		if(CheckSimilarity(sampleOutput, sampleOutput2)) {
//			
//			return null;
//		}
		return sampleOutput;
	}

	private double[] getCenterPointvalue(HashSet<double[]> value) {

		List<double[]> groupData = new ArrayList<double[]>();
		Iterator<double[]> it = value.iterator();
		while (it.hasNext())
		{
			groupData.add(it.next());
		}
		double maxValue = Double.MIN_VALUE;
		double minValue = Double.MAX_VALUE;
		double longX[] = new double[2];
		double longY[] = new double[2];
		double shortX[] = new double[2];
		double shortY[] = new double[2];
		
//		for(double[] data1 : groupData) {
		for(int i=0; i<groupData.size() ; i++ ) {
			
//			for(double[] data2 : groupData) {
			for(int j=i+1; j<groupData.size() ; j++ ) {
				
				double distance = AlgorithmUtils.EulcideanDistance(groupData.get(i), groupData.get(j));
				if(distance > maxValue) {
					longX = groupData.get(i);
					longY = groupData.get(j);
				}
				if(distance < minValue) {
					
					shortX = groupData.get(i);
					shortY = groupData.get(j);
				}
			}
			
		}
		double[] centerCalculate = new double[2];
		centerCalculate[0] = ((longX[0] + shortX[0])/2 + (longY[0] + shortY[0])/2)/2;
		centerCalculate[1] = ((longX[1] + shortX[1])/2 + (longY[1] + shortY[1])/2)/2;
		boolean find =  false;
		double[] myval = null;
		double threshold = 0.001;
		while(!find) {
			
			for(double[] data1 : groupData) {
				
				if( AlgorithmUtils.EulcideanDistance(data1, centerCalculate) < threshold) {
					
					find = true;
					myval = data1;
				}
			}
			threshold += 0.005;
		}
		return myval;
	}

	@SuppressWarnings("unused")
	private boolean CheckSimilarity(double[][] sampleOutput,
			double[][] sampleOutput2) {

		double totalDist = 0.0;
		for(int i = 0 ;i < sampleOutput.length; i++) {
			
			totalDist += EulcideanDistance(sampleOutput[i],sampleOutput2[i]);
		}
		if(totalDist < Constants.similarityThreshold) {
			
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

class myComparer implements Comparator{

	public int compare(Object o1, Object o2) {

		
		return 0;
	}
	
	
}
