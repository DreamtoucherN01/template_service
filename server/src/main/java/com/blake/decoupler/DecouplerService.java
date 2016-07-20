package com.blake.decoupler;

import net.sf.json.JSONObject;

public interface DecouplerService {

	void serviceStart();
	
	JSONObject fetchDataFromMongoDB() ;
	
	void inserDataIntoNeo4j(JSONObject jo);
	
	boolean checkDataSource();
}
