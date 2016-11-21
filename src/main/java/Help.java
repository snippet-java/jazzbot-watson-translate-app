import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

@WebServlet("/help")
public class Help extends HttpServlet {
    private static final long serialVersionUID = 1L;
	
	 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		JsonArray output = new JsonArray();
		output.add("set username <USERNAME> - set the username of translator service");
		output.add("set password <PASSWORD> - set the password of translator service");
		output.add("set endpoint <ENDPOINT> - set the endpoint of translator service");
		output.add("set from <LANGUAGE> - set the language to translate from (e.g English, Spanish, French, Italian, Portuguese)");
		output.add("set to <LANGUAGE> - set the language to translate to");
		output.add("translate <TEXT> - return the translation result");
    	
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
    }
}
