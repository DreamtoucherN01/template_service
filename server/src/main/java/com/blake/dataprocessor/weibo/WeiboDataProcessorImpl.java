package com.blake.dataprocessor.weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import com.blake.cluster.algorithm.KMeansClusterAlgorithm;

public class WeiboDataProcessorImpl implements WeiboDataProcessor{
	
	
	HashMap<String,double[]> mapper = new HashMap<String,double[]>();
	
	public HashMap<String, double[]> getMapper() {
		
		return mapper;
	}

	double input[][] = null;

	public double[][] getInput(String path) {

		File file = new File(path);
		if(!file.isDirectory()) {
			
			System.out.println("path is not correct");
			return null;
		}
		File[] list = file.listFiles();
		for(File cur:list) {
			
			if(cur.isDirectory()) {
				
				continue;
			}
			BufferedReader reader = null;
	        try {
	        	
	        	reader = new BufferedReader(new InputStreamReader (new FileInputStream(cur),"UTF-8"));
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) {

	            	String[] items = tempString.split("\\$");
	            	if(items.length < 6) {
	            		
	            		continue;
	            	}
	            	double []tmp = { Double.valueOf(items[4]),  Double.valueOf(items[5])}; 
	            	mapper.put(items[2], tmp);
	            }
	            reader.close();
	        } catch (IOException e) {
	        	
	            e.printStackTrace();
	        } finally {
	        	
	            if (reader != null) {
	                try {
	                	
	                    reader.close();
	                } catch (IOException e1) {
	                	
	                	e1.printStackTrace();
	                }
	            }
	        }
		}
		input = new double[mapper.size()][];
		Iterator<Entry<String, double[]>> entry = mapper.entrySet().iterator();
		int num = 0;
		while(entry.hasNext()) {
			
			input[num++] = entry.next().getValue();
		}
		return input;
	}

	public HashMap<Integer, HashSet<double[]>> getClusterResult(double[][] data, int clusterNum) {

		if(null == data) {
			
			return null;
		}
		KMeansClusterAlgorithm k = new KMeansClusterAlgorithm();
		return k.doKMeansAlgorithn(data, clusterNum);
	}

	public HashMap<Integer, String> getStatisticData(
			HashMap<Integer, HashSet<double[]>> result,
			HashMap<String, double[]> mapper) {

		HashMap<Integer, String> response = new HashMap<Integer, String>();
		HashMap<String, double[]> data;
		if(null != mapper) {
			
			data = mapper;
		} else {
			
			if(null != this.getMapper()) {
				
				data = this.getMapper();
			} else {
				
				return null;
			}
		}
		
		
		return null;
	}

	public void flushData(String path, HashMap<Integer,HashSet<double[]>> entry) {

		File road = new File(path);
		if(!road.exists()) {
			
			road.mkdir();
		}
		File file = new File(road + "/output.txt");
		
		FileWriter  fos;
		try {
			
			fos = new FileWriter (file);
	        BufferedWriter bw=new BufferedWriter(fos); 
	        
	        Iterator<Entry<Integer, HashSet<double[]>>> data = entry.entrySet().iterator();
	        while(data.hasNext()) {
	        	
	        	Entry<Integer, HashSet<double[]>> info = data.next();
	        	bw.write("cluster " + info.getKey());
	        	bw.newLine();
				HashSet<double[]> value = (HashSet<double[]>) info.getValue();
				for(double[] a: value) {
					
					bw.write("		  " + Arrays.toString(a));
		        	bw.newLine();
				}
	        }
	        bw.flush();
	        bw.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
        
	}

}
