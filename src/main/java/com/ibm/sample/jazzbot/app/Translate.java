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
		 
		JsonObject credConfig = Set.settingMap.get(sessionId)==null?new JsonObject():Set.settingMap.get(sessionId);
		String output = "";
		
		try {
			LanguageTranslator service = new LanguageTranslator();
			service.setUsernameAndPassword(
					credConfig.get("username")==null?"":credConfig.get("username").getAsString(), 
					credConfig.get("password")==null?"":credConfig.get("password").getAsString());
			
			service.setEndPoint(credConfig.get("endpoint")==null?"https://watson-api-explorer.mybluemix.net/language-translation/api":credConfig.get("endpoint").getAsString());
			
			TranslationResult translationResult = service.translate(text,
					Language.valueOf(credConfig.get("from").getAsString().toUpperCase()), 
					Language.valueOf(credConfig.get("to").getAsString().toUpperCase())).execute();
			
	        JsonObject rawOutput = new JsonParser().parse(translationResult.toString()).getAsJsonObject();
	        System.out.println("cheok: " + rawOutput);
	        output = rawOutput.get("translations").getAsJsonArray().get(0).getAsJsonObject().get("translation").getAsString();
	        System.out.println("output: " + output);
		} catch (Exception e) {
			output = e.getMessage();
		}
		
    	response.setContentType("html/text");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
    }
}
