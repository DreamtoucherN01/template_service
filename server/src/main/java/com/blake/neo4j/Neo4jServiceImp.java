package com.blake.neo4j;

import java.util.HashMap;
import java.util.HashSet;
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

import com.blake.neo4j.util.Filter;
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
               .depthFirst()
                .relationships(RelTypes.RETREET, Direction.INCOMING)  
                .evaluator(Evaluators.excludeStartPosition());  
        return td.traverse(node);
	}

    public int printNodeFriends(Node node) {  

        int friendsNumbers = 0;  
        System.out.println(node.getProperty(PRIMARY_KEY) + "'s friends:");  
        for(Path friendPath: getFriends(node)) {
        	
            System.out.println("At depth " + friendPath.length() + " => "  
                    + friendPath.endNode().getProperty(PRIMARY_KEY));  
            friendsNumbers++;  
        }  
        System.out.println("Number of friends found: " + friendsNumbers);  
        return friendsNumbers;
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
	
	public Traverser traversalFriends(Node node, RelTypes reltypes, Direction direction) {

		TraversalDescription td = Traversal.description()  
				.breadthFirst()
                .relationships(reltypes, direction)  
                .evaluator(Evaluators.all());  
        return td.traverse(node);
	}
	
	public HashSet<Node> getRelationGroup(Node node) {  
		
		return getRelationGroup( node,  RelTypes.RETREET,  Direction.INCOMING, null);
	}

	public HashSet<Node> getRelationGroup(Node node, RelTypes reltypes) {  
		
		return getRelationGroup( node,  reltypes,  Direction.INCOMING, null);
	}
	
	public HashSet<Node> getRelationGroup(Node node, RelTypes reltypes, Direction direction) {  
		
		return getRelationGroup( node,  reltypes,  direction, null);
	}
	
    public HashSet<Node> getRelationGroup(Node node, RelTypes reltypes, Direction direction, Filter filter) {  

        HashSet<Node> nodeSet = new HashSet<Node>();
        System.out.println(node.getProperty(PRIMARY_KEY) + "'s friends:");  
        for(Path friendPath: traversalFriends(node, reltypes, direction)) {
        	
        	if(null == filter || filter.doFilter(node)) {
        		
        		nodeSet.add(friendPath.endNode());
        	}
            
        }  
        return nodeSet;
    }
    
    public HashSet<Node> getAllNode(GraphDatabaseService graphDB) {
    	
    	return getAllNode(graphDB, null);
    }
    
    @SuppressWarnings("deprecation")
	public HashSet<Node> getAllNode(GraphDatabaseService graphDB, Filter filter) {
    	
    	HashSet<Node> nodeSet = new HashSet<Node>();
		Iterable<Node> nodes = graphDB.getAllNodes();
		Iterator<Node> it = nodes.iterator();
		while(it.hasNext()) {
			
			Node node = it.next();
			if(null == filter || filter.doFilter(node)) {
        		
        		nodeSet.add(node);
        	}
		}
		return nodeSet;
    }
	
}
