package com.blake.util.share.type;

public enum DBOperationType {

	insert("insert"),
	delete("delete"),
	deleteall("deleteall"),
	update("update"),
	query("query"),
	queryall("queryall");
	
	private String operation;

	private DBOperationType(String operation) {
		
		this.operation = operation;
	}
	
	public static DBOperationType fromString(String operation) {
		
		for(DBOperationType dboperation : DBOperationType.values()) {
			
			if(dboperation.operation.equals(operation)) {
				
				return dboperation;
			}
		}
		
		throw new IllegalArgumentException();
	}
}
