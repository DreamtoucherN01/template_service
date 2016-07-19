package com.blake.decoupler;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.rest.graphdb.GraphDatabaseFactory;

import com.blake.neo4j.Neo4jService;
import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.blake.util.share.db.dao.Info;
import com.google.gson.Gson;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class DecouplerServiceImpl implements DecouplerService {
	
	Neo4jService neo4jService;
	
	DBaccessor mongoDBAccessor;
	
	boolean run = true;
	
    private static GraphDatabaseService graphDB;  
    
    private static Index<Node> nodeIndex;  
    
    private DBCollection currentCollection;
    
    private int currentCollectionNum;

	public DecouplerServiceImpl(Neo4jService neo4jService,
			DBaccessor mongoDBAccessor) {
		
		super();
		this.neo4jService = neo4jService;
		this.mongoDBAccessor = mongoDBAccessor;
	}

	public Neo4jService getNeo4jService() {
		return neo4jService;
	}

	public void setNeo4jService(Neo4jService neo4jService) {
		this.neo4jService = neo4jService;
	}

	public DBaccessor getMongoDBAccessor() {
		return mongoDBAccessor;
	}

	public void setMongoDBAccessor(DBaccessor mongoDBAccessor) {
		this.mongoDBAccessor = mongoDBAccessor;
	}

	public JSONObject fetchDataFromMongoDB() {
		
		
		mongoDBAccessor.getDb().getCollectionNames();
		
		return null;
	}

	public void inserDataIntoNeo4j(JSONObject jo) {

		System.out.println(jo.toString());
	}

	public boolean checkDataSource() {
		
		return false;
	}

	public void serviceStart() {
		
		graphDB = GraphDatabaseFactory.databaseFor(Constants.SERVER_ROOT_URI);  
		nodeIndex = graphDB.index().forNodes(com.blake.neo4j.Neo4jService.INDEXNAME);

		while(run) {
			
			Node node = neo4jService.findNodeByName(graphDB, "currentCollectionNum");
			if(node == null) {
				
				currentCollectionNum = 0;
				currentCollection = mongoDBAccessor.getDb().getCollection("twitter_" + currentCollectionNum);
				persistCurrentCollectionNum(graphDB, nodeIndex, currentCollectionNum);
			} else {
				
				currentCollectionNum =Integer.valueOf( (String) (node.getProperty("value")) );
				currentCollection = mongoDBAccessor.getDb().getCollection("twitter_" + currentCollectionNum);
			}
			
			DBCursor cur = currentCollection.find();
		    while (cur.hasNext()) {
		    	
		    	Gson gson=new Gson();
		    	Info info = gson.fromJson( gson.toJson(cur.next()), Info.class);
		    	inserDataIntoNeo4j(JSONObject.fromObject(info.getBody()));
		    }
			
			persistCurrentCollectionNum(graphDB, nodeIndex, ++currentCollectionNum);
		}
	}

	private void persistCurrentCollectionNum(GraphDatabaseService graphDB2,
			Index<Node> nodeIndex2, int currentCollectionNum2) {

		Node node = neo4jService.findNodeByName(graphDB, "currentCollectionNum");
		neo4jService.deleteNode(node);
		HashMap<String,String> props = new HashMap<String,String>();
		props.put("name", "currentCollectionNum");
		props.put("value", String.valueOf(currentCollectionNum2));
		neo4jService.addNode(graphDB, nodeIndex, props);
	}

	public void serviceStop() {

		run = false;
	}
	
}
