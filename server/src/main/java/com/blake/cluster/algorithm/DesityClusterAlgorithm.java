package com.blake.cluster.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.blake.cluster.util.AlgorithmUtils;

public class DesityClusterAlgorithm {

	double Eps = 0.1;   //区域半径
    int MinPts = 10;   //密度
    
    public HashMap<Integer,HashSet<double[]>> doDBSCANAlgorithn(double[][] input, int k) {
    	
    	HashSet<double[]> kernelObjectCollection = getKernelObjectCollection(input);
    	int clusterNum = 1; //初始化聚类簇数
    	HashMap<Integer,HashSet<double[]>> group = new HashMap<Integer,HashSet<double[]>>() ;
    	while(!kernelObjectCollection.isEmpty()){
    		
    		double[] seed = kernelObjectCollection.iterator().next();
    		kernelObjectCollection.remove(seed);
    		
    		HashSet<double[]> set = getClusterByKernelElement(input,seed);
    		removeKernelElement(kernelObjectCollection, set);
    		
    		group.put(clusterNum, set);
    		clusterNum++;
    	}
		return group;
    	
    }

	private void removeKernelElement(HashSet<double[]> kernelObjectCollection,
			HashSet<double[]> set) {

		Iterator<double[]> it = set.iterator();
		while(it.hasNext()) {
			
			double[] data = it.next();
			if(kernelObjectCollection.contains(data)) {
				
				kernelObjectCollection.remove(data);
			}
		}
	}

	private HashSet<double[]> getClusterByKernelElement(double[][] input, double[] seed) {

		HashSet<double[]> set = new HashSet<double[]>();
		for(int i = 0; i< input.length ; i++) {
			
			if(AlgorithmUtils.EulcideanDistance(input[i], seed) <= Eps) {
				
				set.add(input[i]);
			}
		}
		return set;
	}

	private HashSet<double[]> getKernelObjectCollection(double[][] input) {

		HashSet<double[]> kernelCollection = new HashSet<double[]>();
		for(int i = 0; i< input.length ; i++) {
			
			int count = 0;
			for(int j = 0; j < input.length; j++) {
				
				if(AlgorithmUtils.EulcideanDistance(input[i], input[j]) <= Eps) {
					
					count++;
					if(count >= MinPts) {
						kernelCollection.add(input[i]);
						break;
					}
				}
			}
		}
		return kernelCollection;
	}
}
