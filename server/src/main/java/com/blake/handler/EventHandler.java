package com.blake.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.blake.response.UnifiedResponse;
import com.blake.util.Constants;
import com.blake.util.share.DBaccessor;
import com.blake.util.share.db.InfoAccessor;
import com.blake.util.share.db.dao.Info;
import com.blake.util.share.type.DBOperationType;


public class EventHandler{

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	
	public EventHandler(HttpServletRequest request, HttpServletResponse response) {
		
		this.request = request;
		this.response = response;
	}
	
	public void handleEvent(){
		
		BufferedReader reader = null;
		String line = null;
		StringBuffer buffer = new StringBuffer();
		
		try {
			
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			while((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		DBaccessor dbaccessor = new DBaccessor();
		InfoAccessor accessor = new InfoAccessor(dbaccessor);
		
		JSONObject jo =   JSONObject.fromObject(buffer.toString());
		String type = (String) jo.get("type");
		
		if(DBOperationType.fromString(type) == DBOperationType.insert) {
			
			JSONObject body = (JSONObject) jo.get("body");
			Info info = new Info(Constants.incre.getAndAdd(1), body.getString("text"), body.toString());
			accessor.insert(info);
			UnifiedResponse.sendSuccessResponse(response);
			return;
		} 
		
		if(DBOperationType.fromString(type) == DBOperationType.deleteall) {
			
			if(accessor.deleteAll()){
				
				UnifiedResponse.sendSuccessResponse(response);
			} else {
				
				UnifiedResponse.sendErrResponse(response, 3001);
			}
			return;
		}
		
		if(DBOperationType.fromString(type) == DBOperationType.queryall) {
			
			List<Info> infoList = accessor.findAll();
			if(infoList == null || infoList.size() == 0){
				
				UnifiedResponse.sendErrResponse(response, 2001);
			}
			UnifiedResponse.sendResponse(response, infoList);
		}
		
		if(DBOperationType.fromString(type) == DBOperationType.query) {
			
			String key = (String) jo.get("key");
			String mode = (String) jo.get("mode");
			List<Info> infoList = null;
			if(mode != null && !mode.equals("fuzzy")) {
				
				infoList = accessor.find(key, 0, 0);
			} else {
				
				infoList = accessor.fuzzyFind(key, 0, 0);
			}
			if(infoList == null || infoList.size() == 0){
				
				UnifiedResponse.sendErrResponse(response, 2001);
			}
			UnifiedResponse.sendResponse(response, infoList);
		}
	}
}
