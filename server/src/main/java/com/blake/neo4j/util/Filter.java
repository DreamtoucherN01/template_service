package com.blake.neo4j.util;

import org.neo4j.graphdb.Node;

public abstract class Filter {
	
	public abstract boolean doFilter(Node node);

}
