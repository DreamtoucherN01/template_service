package com.blake.neo4j;

import java.util.HashMap;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Traverser;

public interface Neo4jService {
	
	final String SERVER_ROOT_URI = "http://192.168.185.59:7474/db/data/";
	
	final String PRIMARY_KEY = "name";  
	
	final String INDEXNAME = "name";  
	
    static enum RelTypes implements RelationshipType {  
    	
        FAMILIAR,  
        UNFAMILIAR 
    }  
	
	void checkDatabaseIsRunning();
	
	Node addNode(GraphDatabaseService graphDB, Index<Node> nodeIndex, HashMap<String,String> props);

	void addRelationship( Node startNode, Node endNode, RelTypes relTypes ) ;
	
	Traverser getFriends(final Node node);
	
	void deleteNode(Node node);
	
	Node findNodeByName(GraphDatabaseService graphDB, String name);
}
