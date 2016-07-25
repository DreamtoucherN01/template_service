package com.blake.decoupler;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.rest.graphdb.GraphDatabaseFactory;

import com.blake.cluster.ClusterController;
import com.blake.dataprocessor.backup.BackupData;
import com.blake.dataprocessor.twitter.TwitterDataProcessor;
import com.blake.neo4j.Neo4jService;
import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.blake.util.share.db.dao.Info;
import com.google.gson.Gson;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DecouplerServiceImpl implements DecouplerService ,Runnable {
	
	Logger logger = Logger.getLogger(DecouplerServiceImpl.class);
	
	Neo4jService neo4jService;
	
	DBaccessor mongoDBAccessor;
	
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

	public void inserDataIntoNeo4j(JSONObject treets) {

		
		HashMap<String,String> props = TwitterDataProcessor.getProperties(treets);
		Node node = neo4jService.addNode(graphDB, nodeIndex, props);
		ClusterController.doNEO4JReteetCluster(graphDB, neo4jService, node);
	}

	public boolean checkDataSource() {
		
		return false;
	}

	public void serviceStart() {
		
		logger.info("start transtering data to neo4j database");
		
		if(Constants.neo4jrun) {
			
			logger.info("wait for the last operation finish");
			return;
		} else {
			
			Constants.neo4jrun = true;
		}
		graphDB = GraphDatabaseFactory.databaseFor(Constants.SERVER_ROOT_URI);  
		nodeIndex = graphDB.index().forNodes(com.blake.neo4j.Neo4jService.INDEXNAME);
		
		Node node = neo4jService.findNodeByName(graphDB, "currentCollectionNum");
		if(node == null) {
			
			currentCollectionNum = 0;
			currentCollection = mongoDBAccessor.getDb().getCollection("twitter_" + currentCollectionNum);
			persistCurrentCollectionNum(graphDB, nodeIndex, currentCollectionNum);
		} else {
			
			currentCollectionNum =Integer.valueOf( (String) (node.getProperty("value")) );
			currentCollection = mongoDBAccessor.getDb().getCollection("twitter_" + currentCollectionNum);
		}
		
		logger.info("NAME : " + currentCollection.getName() + " COUNT : " + currentCollection.getCount());
		if(currentCollection.getCount() < Constants.maxItemNumberPerCollection) {
			
			logger.info("data is not ready, please wait");
			if(currentCollection.getCount() == 0) {
				
				logger.info("check back up data");
				currentCollection = mongoDBAccessor.getDb().getCollection("twitter_" + currentCollectionNum + "_bak");
				if(currentCollection.getCount() == Constants.maxItemNumberPerCollection) {
					
					logger.info("using back up data");
					DBCursor cur = currentCollection.find();
				    while (cur.hasNext()) {
				    	
				    	DBObject next = cur.next();
				    	Gson gson=new Gson();
				    	Info info = gson.fromJson( gson.toJson(next), Info.class);
				    	inserDataIntoNeo4j(JSONObject.fromObject(info.getBody()));
				    }
					persistCurrentCollectionNum(graphDB, nodeIndex, ++currentCollectionNum);
				}
			}
			
		} else {
			
			
			DBCursor cur = currentCollection.find();
		    while (cur.hasNext()) {
		    	
		    	DBObject next = cur.next();
		    	Gson gson=new Gson();
		    	Info info = gson.fromJson( gson.toJson(next), Info.class);
		    	inserDataIntoNeo4j(JSONObject.fromObject(info.getBody()));
		    }
		    BackupData.backupData(mongoDBAccessor, currentCollectionNum);
			persistCurrentCollectionNum(graphDB, nodeIndex, ++currentCollectionNum);
		}
		
		Constants.neo4jrun = false;
		
		logger.info("transtering data to neo4j finished");
	}

	private void persistCurrentCollectionNum(GraphDatabaseService graphDB2,
			Index<Node> nodeIndex2, int currentCollectionNum2) {

		Node node = neo4jService.findNodeByName(graphDB, "currentCollectionNum");
		if(node != null) {
			
			neo4jService.deleteNode(node);
		}
		
		HashMap<String,String> props = new HashMap<String,String>();
		props.put("name", "currentCollectionNum");
		props.put("value", String.valueOf(currentCollectionNum2));
		neo4jService.addNode(graphDB, nodeIndex, props);
	}

	public void run() {

		this.serviceStart();
	}
	
}
