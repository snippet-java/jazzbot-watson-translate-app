package com.ibm.sample.jazzbot.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;

@WebServlet("/translate")
public class Translate extends HttpServlet {
    private static final long serialVersionUID = 1L;
	
	 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		String sessionId = request.getParameter("sessionId");
		String text = request.getParameter("text");
		 
		JsonObject credConfig = Set.settingMap.get(sessionId);
		
		JsonObject output = new JsonObject();
		
		try {
			LanguageTranslator service = new LanguageTranslator();
			service.setUsernameAndPassword(credConfig.get("username").getAsString(), credConfig.get("password").getAsString());
			
			TranslationResult translationResult = service.translate(text,
					Language.valueOf(credConfig.get("from").getAsString().toUpperCase()), Language.valueOf(credConfig.get("to").getAsString().toUpperCase())).execute();
			
	        output = new JsonParser().parse(translationResult.toString()).getAsJsonObject();
		} catch (Exception e) {
			output.addProperty("err", e.getMessage());
		}
		
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
    }
}
