package com.blake.response;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blake.util.share.db.dao.Info;

public class UnifiedResponse {
	
	final static Logger logger = LoggerFactory.getLogger(UnifiedResponse.class);

	public static void sendResponse(HttpServletResponse response,
			List<Info> infoList, int page , boolean done) {

		JSONObject result =  new JSONObject();
		result.put("result", true);
		result.put("err", 0);
		result.put("data", JSONArray.fromObject(infoList)); 
		result.put("ver", 1);
		result.put("page", page);
		
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is {}" , result.toString());
		}
		response.setContentLength(result.toString().getBytes().length);
		try {
			
			response.getWriter().write(result.toString());
			response.getWriter().close();
			return;
		} catch (IOException e) {
			
			logger.error("catch IOEXception : {}", e);
		}
	}
	
	public static void sendResponse(HttpServletResponse response,
			List<Info> infoList) {

		JSONObject result =  new JSONObject();
		result.put("result", true);
		result.put("err", 0);
		result.put("data", JSONArray.fromObject(infoList)); 
		result.put("ver", 1);
		
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is {}" , result.toString());
		}
		response.setContentLength(result.toString().getBytes().length);
		try {
			
			response.getWriter().write(result.toString());
			response.getWriter().close();
			return;
		} catch (IOException e) {
			
			logger.error("catch IOEXception : {}", e);
		}
	}

	public static void sendErrResponse(HttpServletResponse response, int errcode) {

		String respStr = "{\"result\":false,\"err\":1001,\"ver\":1,\"desc\":\"check your search key or narrow search range\"}";
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is {}" , respStr);
		}
		response.setContentLength(respStr.getBytes().length);
		try {
			
			response.getWriter().write(respStr);
			response.getWriter().close();
			return;
		} catch (IOException e) {
			
			logger.error("catch IOEXception : {}", e);
		}
	}

	public static void sendSuccessResponse(HttpServletResponse response) {

		String respStr = "{\"result\":true,\"err\":0,\"ver\":1}";
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is {}" , respStr);
		}
		response.setContentLength(respStr.getBytes().length);
		try {
			
			response.getWriter().write(respStr);
			response.getWriter().close();
			return;
		} catch (IOException e) {
			
			logger.error("catch IOEXception : {}", e);
		}
	}

}
