package com.blake.cluster;

import java.util.Iterator;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import com.blake.neo4j.Neo4jService;

public class ClusterController {
	
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
		
		System.out.print(" " + node.getProperty("name") );
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
