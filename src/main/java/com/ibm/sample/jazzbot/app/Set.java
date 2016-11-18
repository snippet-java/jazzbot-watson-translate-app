package com.ibm.sample.jazzbot.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

@WebServlet("/set")
public class Set extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //<sessionId, credentialANDConfig>
    protected static Map<String, JsonObject> settingMap = new HashMap<String, JsonObject>(); 
	
	 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		String sessionId = request.getParameter("sessionId");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String endpoint = request.getParameter("endpoint");
		
		JsonObject configCred = settingMap.get(sessionId) == null?new JsonObject():settingMap.get(sessionId);
		if(username != null)
			configCred.addProperty("username", username);
		if(password != null)
			configCred.addProperty("password", password);
		if(from != null)
			configCred.addProperty("from", from);
		if(to != null)
			configCred.addProperty("to", to);
		if(endpoint != null)
			configCred.addProperty("endpoint", endpoint);
    	
		settingMap.put(sessionId, configCred);
		String output = "SET operation successful";
		
    	response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
    }
}
