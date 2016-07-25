package com.blake.cluster;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import com.blake.cluster.algorithm.KMeansClusterAlgorithm;
import com.blake.neo4j.Neo4jService;
import com.blake.neo4j.Neo4jServiceImp;
import com.blake.neo4j.Neo4jService.RelTypes;
import com.blake.neo4j.util.Filter;
import com.blake.neo4j.util.GeoFilter;

public class ClusterController {
	
	public static void doNEO4JClusterByDistance(GraphDatabaseService graphDB, Neo4jService neo){
		
		@SuppressWarnings("deprecation")
		Iterable<Node> node1 = graphDB.getAllNodes();
		Iterator<Node> it = node1.iterator();
		while(it.hasNext()) {
			
			Node node = it.next();
			Iterable<Relationship> relationships = node.getRelationships(RelTypes.CLOSER, Direction.INCOMING);
			for(Relationship r : relationships) {
				r.delete();
			}
		}
		
		Filter filter = new GeoFilter();
		HashSet<Node> nodes = ((Neo4jServiceImp) neo).getAllNode(graphDB, filter);
		
		double[][] input = new double[nodes.size()][];
		HashMap<double[],Node> mapper =  new HashMap<double[],Node>();
		int i = 0;
		for(Node node : nodes) {
			
			String geoStr = (String) node.getProperty("geo");
			if(null == geoStr) {
				
				System.out.println("geoStr error");
			}
			String [] split = geoStr.split(",");
				
			double[] geo = {Double.parseDouble(split[0].substring(split[0].indexOf("=")+1)),
					Double.parseDouble(split[1].substring(split[1].indexOf("=") +1, split[1].length()-1))};
			
			input[i] = geo;
			mapper.put(geo, node);
			i++;
		}
		
		KMeansClusterAlgorithm k = new KMeansClusterAlgorithm();
		HashMap<Integer,HashSet<double[]>> group = k.doKMeansAlgorithn(input, 5);
		
		HashMap<Integer,HashSet<Node>>  nodeGroup = new HashMap<Integer,HashSet<Node>> ();
		
		Iterator<Entry<Integer, HashSet<double[]>>> entry = group.entrySet().iterator();
		
		while(entry.hasNext()) {
			
			Entry<Integer, HashSet<double[]>> item = entry.next();
			
			HashSet<double[]> geo = item.getValue();
			for(double[] tmp : geo) {
				
				if(nodeGroup.get(item.getKey()) != null) {
					
					nodeGroup.get(item.getKey()).add(mapper.get(tmp));
					
				} else {
					
					HashSet<Node> tmp1 = new HashSet<Node>();
					tmp1.add(mapper.get(tmp));
					nodeGroup.put(item.getKey(), tmp1);
					
				}
			}
		}
		
		Iterator<Entry<Integer, HashSet<Node>>> entry1 = nodeGroup.entrySet().iterator();
		while(entry1.hasNext()) {
			
			Entry<Integer, HashSet<Node>> info = entry1.next();
			HashSet<Node> value = info.getValue();
			Node center = null ;int count=0;
			for(Node no : value) {
				
				if(count == 0) {
					
					center = no;
					
				} else {
					
					neo.addRelationship(no, center, RelTypes.CLOSER);
				}
				count++;
			}
		}
	}
	
	public static void doNEO4JReteetCluster(GraphDatabaseService graphDB, Neo4jService neo4jService) {
		
		@SuppressWarnings("deprecation")
		Iterable<Node> nodeiter = graphDB.getAllNodes();
		Iterator<Node> it = nodeiter.iterator();
		while(it.hasNext()) {
			
			Node node = it.next();
			System.out.print(" " + node.getProperty("name") );
			if( !checkVisited(node)) {
				
				node.setProperty("visited", "1");
			} else {
				
				continue;
			}
			getReteetedValue(node, neo4jService, graphDB);
			System.out.println(" ");
		}
		
	}
	
	public static void doNEO4JReteetCluster(GraphDatabaseService graphDB, Neo4jService neo4jService, Node node) {
		
		if( !checkVisited(node)) {
			
			node.setProperty("visited", "1");
		} else {
			
			return;
		}
		getReteetedValue(node, neo4jService, graphDB);
	}

	private static void getReteetedValue(Node node, Neo4jService neo4jService, GraphDatabaseService graphDB) {

		if(node.hasProperty("retweetedname")) {
			
			Node find = neo4jService.findNodeByName(graphDB, (String) node.getProperty("retweetedname"));
			neo4jService.addRelationship(node, find, Neo4jService.RelTypes.RETREET);
			if(null != find && !checkVisited(find)) {
				
				find.setProperty("visited", "1");
				getReteetedValue(find, neo4jService, graphDB);
			}
		} 
	}
	
	static boolean checkVisited(Node node){
		
		if(node.hasProperty("visited") && node.getProperty("visited").equals("1")) {
			
			return true;
		}
		return false;
	}

}
