package com.blake.neo4j;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.io.fs.FileUtils;

public class DBInit {

	private static final String DB_PATH = "testgraph.db";  
    private static final String PRIMARY_KEY = "name";  
    private GraphDatabaseService graphDB;  
    private Index<Node> nodeIndex;  
    private long startNodeId;  
	
	public void initDb() {
		
		graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(new File(DB_PATH));  
        nodeIndex = graphDB.index().forNodes("nodes");  
        registerShutdownHook(graphDB); 
	}
	
	private static void registerShutdownHook(final GraphDatabaseService graphDB) {  
        Runtime.getRuntime().addShutdownHook(new Thread() {  
            @Override  
            public void run() {  
                graphDB.shutdown();  
            }  
        });  
    }  
	
	private void clearDB() {  
		
        try {  
        	
            FileUtils.deleteRecursively(new File(DB_PATH));  
        }  
        catch(IOException e) { 
        	
            throw new RuntimeException(e);  
        }  
    }  
      
    private void shutdown() {  
        graphDB.shutdown();  
    }  
}
