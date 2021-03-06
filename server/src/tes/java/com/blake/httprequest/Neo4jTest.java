package com.blake.httprequest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.rest.graphdb.GraphDatabaseFactory;

import com.blake.cluster.ClusterController;
import com.blake.neo4j.Neo4jService;
import com.blake.neo4j.Neo4jServiceImp;

public class Neo4jTest {

    private static final String DB_PATH = "testgraph.db";  
    private static final String PRIMARY_KEY = "name";  
    private static GraphDatabaseService graphDB;  
    private static Index<Node> nodeIndex;  
    private static long startNodeId;  

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("starting test");
		
//        RestAPI api = new RestAPIFacade("http://192.168.185.59:7474/");
//        System.out.println("API created");
//        final RestCypherQueryEngine engine = new RestCypherQueryEngine(api);
//        System.out.println("engine created");
        
		graphDB = GraphDatabaseFactory.databaseFor("http://192.168.185.59:7474/db/data");  
		nodeIndex = graphDB.index().forNodes(com.blake.neo4j.Neo4jService.INDEXNAME);
		Transaction tx = graphDB.beginTx(); 
		Neo4jService neo = new Neo4jServiceImp();
		
//		HashMap props = new HashMap();
//		props.put("name", "jack");
//		props.put("age", "22");
//		Node jack = neo.addNode(graphDB,nodeIndex, props);
//		
//		props = new HashMap();
//		props.put("name", "gg");
//		props.put("age", "21");
//		Node gg = neo.addNode(graphDB,nodeIndex, props);
//		
//		props = new HashMap();
//		props.put("name", "qq");
//		props.put("age", "23");
//		Node qq = neo.addNode(graphDB,nodeIndex, props);
//		
//		neo.addRelationship(jack, gg, com.blake.neo4j.Neo4jService.RelTypes.FAMILIAR);
//		neo.addRelationship(qq, gg, com.blake.neo4j.Neo4jService.RelTypes.FAMILIAR);
//		neo.addRelationship(jack, qq, com.blake.neo4j.Neo4jService.RelTypes.FAMILIAR);
		
//		Node node = neo.findNodeByName(graphDB, "Fireground");
		
//		ClusterController.doNEO4JReteetCluster(graphDB, neo);
		
		ClusterController.doNEO4JClusterByDistance(graphDB, neo);
		
		
//		System.out.println("count is " + count);
		
//		@SuppressWarnings("deprecation")
//		ExecutionEngine engine = new ExecutionEngine(graphDB);
//		ExecutionResult result = engine.execute("MATCH p=()-[r:RETREET]->() RETURN p LIMIT 1000" ,""); 
//		System.out.println(result.toString()); 
		
		
		
		
		
//		if(node != null) {
//			System.out.println("find : " + node.getProperty("text"));
//		}
//		
//		Iterable<Node> node1 = graphDB.getAllNodes();
//		Iterator<Node> it = node1.iterator();
//		while(it.hasNext()) {
//			
//			Node node = it.next();
//			Iterable<Relationship> relationships = node.getRelationships(RelTypes.RETREET, Direction.OUTGOING);
//			for(Relationship r : relationships) {
//				r.delete();
//			}
//			node.delete();
//		}
//	        ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.logger(DB_PATH));  
//	      ExecutionResult result = engine.execute(  
//	              "start n=node:nodes(name=\"" + name + "\") "  
//	              + "match n-[:KNOWS*..]->f "  
//	              + "return distinct f, f.name");  
//	        ExecutionResult result = engine.execute("start n=node(500000) return n;");  
//		neo.printNodeFriends(graphDB.getNodeById(52));
        try {  
              
            tx.success();  
        }  
        finally {  
            tx.finish();  
        }  
        
        System.out.println("test finished");
        System.exit(0);
//		GraphDatabaseService graphDb = new RestGraphDatabase("http://192.168.185.59:7474/"); 
//		Transaction tx = graphDb.beginTx();  
//		Map<String,Object> props=new HashMap<String, Object>();  
//		props.put("id",100);  
//		props.put("name","firstNode");  
//		Node node = graphDb.createNode(props);  
//	 	tx.success();  
//	 	tx.finish();  
	}
	
	public static void prdoubleGroupOut(HashMap<Integer, HashSet<Node>> group) {

		Iterator<Entry<Integer, HashSet<Node>>> entry = group.entrySet().iterator();
		while(entry.hasNext()) {
			
			Entry<Integer, HashSet<Node>> info = entry.next();
			System.out.println("cluster " + info.getKey());
			HashSet<Node> value = info.getValue();
			for(Node no : value) {
				
				System.out.println("		  " + no);
			}
		}
	}

}
