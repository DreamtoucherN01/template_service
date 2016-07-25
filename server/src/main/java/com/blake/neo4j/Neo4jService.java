package com.blake.neo4j;

import java.util.HashMap;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Traverser;

import com.blake.util.Constants;

public interface Neo4jService {
	
	final String SERVER_ROOT_URI = Constants.SERVER_ROOT_URI;
	
	final String PRIMARY_KEY = "name";  
	
	final String INDEXNAME = "name";  
	
    static enum RelTypes implements RelationshipType {  
    	
        FAMILIAR,  
        UNFAMILIAR,
        GROUP,
        RETREET,
        CLOSER
    }  
	
	void checkDatabaseIsRunning();
	
	Node addNode(GraphDatabaseService graphDB, Index<Node> nodeIndex, HashMap<String,String> props);

	void addRelationship( Node startNode, Node endNode, RelTypes relTypes ) ;
	
	Traverser getFriends(final Node node);
	
	void deleteNode(Node node);
	
	Node findNodeByName(GraphDatabaseService graphDB, String name);
}
