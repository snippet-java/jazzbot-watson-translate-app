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
		
		JsonObject configCred = new JsonObject();
		configCred.addProperty("username", "".equals(username)?"":username);
		configCred.addProperty("password", "".equals(password)?"":password);
		configCred.addProperty("from", "".equals(from)?"":from);
		configCred.addProperty("to", "".equals(to)?"":to);
    	
		settingMap.put(sessionId, configCred);
		
		String output = "SET operation successful";
		
    	response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
    }
}
