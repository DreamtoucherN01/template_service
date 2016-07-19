package com.blake.decoupler;

import net.sf.json.JSONObject;

import com.blake.neo4j.Neo4jService;
import com.blake.util.share.DBaccessor;

public class DecouplerServiceImpl implements DecouplerService {
	
	Neo4jService neo4jService;
	
	DBaccessor mongoDBAccessor;
	
	boolean run = true;

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

		
	}

	public boolean checkDataSource() {
		
		return false;
	}

	public void serviceStart() {

		while(run) {
			
			while(checkDataSource()) {
				
				inserDataIntoNeo4j(this.fetchDataFromMongoDB());
			}
		}
	}

	public void serviceStop() {

		run = false;
	}
	
}
