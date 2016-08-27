package com.blake.cluster.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class AlgorithmUtils {
	
	public static double EulcideanDistance(double[] is, double[] is2) {

		double dist = 0 ;
		for(int i = 0; i < is.length; i++) {
			
			dist += Math.pow(is[i] - is2[i], 2);
		}
		return Math.sqrt(dist);
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
}
