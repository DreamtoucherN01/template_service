package com.blake.dataprocessor.weibo;

import java.util.HashMap;
import java.util.HashSet;

public interface WeiboDataProcessor {
	
	double[][] getInput(String path);
	
	HashMap<Integer,HashSet<double[]>> getClusterResult(double[][] data, int clusterNum);
	
	HashMap<Integer,String> getStatisticData(HashMap<Integer,HashSet<double[]>> result, HashMap<String,double[]> mapper);
	
	void flushData(String path, HashMap<Integer,HashSet<double[]>> data);

}
