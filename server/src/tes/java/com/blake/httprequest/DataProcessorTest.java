package com.blake.httprequest;

import java.util.HashMap;
import java.util.HashSet;

import com.blake.cluster.algorithm.KMeansClusterAlgorithm;
import com.blake.dataprocessor.weibo.WeiboDataProcessor;
import com.blake.dataprocessor.weibo.WeiboDataProcessorImpl;

public class DataProcessorTest {

	public static void main(String[] args) {

		WeiboDataProcessor po = new WeiboDataProcessorImpl();
		double[][] data = po.getInput("E:\\myinfo\\chenxu\\data");
		KMeansClusterAlgorithm k = new KMeansClusterAlgorithm();
		HashMap<Integer,HashSet<double[]>> group = k.doKMeansAlgorithn(data, 6);
//		k.prdoubleGroupOut(group, 11);
		po.flushData("E:\\myinfo\\chenxu\\data\\output", group);
	}

}
