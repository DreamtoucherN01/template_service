package com.blake.neo4j.util;

import org.neo4j.graphdb.Node;

public class GeoFilter extends Filter {

	@Override
	public boolean doFilter(Node node) {
		
		if(node.hasProperty("geo") && null != node.getProperty("geo")){
			
			return true;
		}
		return false;
	}

}
