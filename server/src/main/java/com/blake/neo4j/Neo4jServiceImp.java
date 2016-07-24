package com.blake.neo4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Neo4jServiceImp implements Neo4jService {

	public void checkDatabaseIsRunning() {

        WebResource resource = Client.create().resource( SERVER_ROOT_URI );  
        ClientResponse response = resource.get( ClientResponse.class );  
        System.out.println( String.format( "GET on [%s], status code [%d]", SERVER_ROOT_URI, response.getStatus() ) );  
        if (response.getStatus() == 200) {
        	
            System.out.println("连接成功!");
        } else {
        	
            System.out.println("连接失败!");
            return;
        }
        response.close(); 
	}

	public Node addNode(GraphDatabaseService graphDB, Index<Node> nodeIndex, 
			HashMap<String, String> props) {

		Transaction tx = graphDB.beginTx();
		Node node = null;
		try {  
			
			node = graphDB.createNode();  
			Iterator<Entry<String, String>> entry = props.entrySet().iterator();
			while(entry.hasNext()) {
				
				Entry<String, String> data = entry.next();
				node.setProperty(data.getKey(), data.getValue());
			}
			nodeIndex.add(node, PRIMARY_KEY, node.getProperty("name")); 
            tx.success();  
        }  
        finally { 
        	
            tx.finish();
        }  
		return node;
	}

	public void addRelationship(Node startNode, Node endNode, RelTypes relTypes) {

		if(endNode == null || startNode == null) {
			
			return;
		}
		startNode.createRelationshipTo( endNode, relTypes );  
	}

	public Traverser getFriends(Node node) {

		TraversalDescription td = Traversal.description()  
                .breadthFirst()  
                .relationships(RelTypes.FAMILIAR, Direction.OUTGOING)  
                .evaluator(Evaluators.excludeStartPosition());  
        return td.traverse(node);
	}

    public void printNodeFriends(Node node) {  

        int friendsNumbers = 0;  
        System.out.println(node.getProperty(PRIMARY_KEY) + "'s friends:");  
        for(Path friendPath: getFriends(node)) {
        	
            System.out.println("At depth " + friendPath.length() + " => "  
                    + friendPath.endNode().getProperty(PRIMARY_KEY));  
            friendsNumbers++;  
        }  
        System.out.println("Number of friends found: " + friendsNumbers);  
    }

	public void deleteNode(Node node) {

		Iterable<Relationship> relationships = node.getRelationships(RelTypes.FAMILIAR, Direction.OUTGOING);
		for(Relationship r : relationships) {
			r.delete();
		}
		node.delete();
	}

	public Node findNodeByName(GraphDatabaseService graphDB, String name) {

		Index<Node> nodeIndex = graphDB.index().forNodes(INDEXNAME);
		if(nodeIndex == null) {
			
			return null;
		}
		return nodeIndex.get(PRIMARY_KEY, name).getSingle();
	}  
}
