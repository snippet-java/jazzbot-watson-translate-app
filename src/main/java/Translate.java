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
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.language_translation.v2.LanguageTranslation;
import com.ibm.watson.developer_cloud.language_translation.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translation.v2.model.TranslationResult;

@WebServlet("/translate")
public class Translate extends HttpServlet {

    //<sessionId, credentialANDConfig>
    protected static Map<String, JsonObject> settingMap = new HashMap<String, JsonObject>(); 
	
	private String parameters = "{"
			+ "\"username\":\"\","
			+ "\"password\":\"\","
			+ "\"from\":\"english\","
			+ "\"to\":\"spanish\","
			+ "\"text\":\"hello my friend\""
			+ "}";
    

	 public static void main(String[] args) {
		 Translate translateClass = new Translate();
		 JsonObject params = new JsonParser().parse(translateClass.parameters).getAsJsonObject();
		 System.out.println(translateClass.process(params, params.get("text").getAsString()));
	 }
	
	 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		String sessionId = request.getParameter("sessionId");
		String text = request.getParameter("text");
		 
		JsonObject credConfig = settingMap.get(sessionId)==null?new JsonObject():settingMap.get(sessionId);
		
		String output = process(credConfig, text);
		
    	response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(output);	
		out.close();
     }
	 
	 private String process(JsonObject credConfig, String text) {
		 
		String output = "";
			
		try {
			LanguageTranslation service = new LanguageTranslation();
			service.setUsernameAndPassword(
					credConfig.get("username")==null?"":credConfig.get("username").getAsString(), 
					credConfig.get("password")==null?"":credConfig.get("password").getAsString());
			
			service.setEndPoint(credConfig.get("endpoint")==null?"https://watson-api-explorer.mybluemix.net/language-translation/api":credConfig.get("endpoint").getAsString());
			
			TranslationResult translationResult = service.translate(text,
					Language.valueOf(credConfig.get("from").getAsString().toUpperCase()), 
					Language.valueOf(credConfig.get("to").getAsString().toUpperCase())).execute();
			
	        JsonObject rawOutput = new JsonParser().parse(translationResult.toString()).getAsJsonObject();
	        output = rawOutput.get("translations").getAsJsonArray().get(0).getAsJsonObject().get("translation").getAsString();
	        
		} catch (Exception e) {
			output = e.getMessage();
		} 
		
		return output;
	 }
	 
	 private static final long serialVersionUID = 1L;
}
