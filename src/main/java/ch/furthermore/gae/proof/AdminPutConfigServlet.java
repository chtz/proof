package ch.furthermore.gae.proof;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.furthermore.gae.proof.crypto.KeyPairRegistry;

public class AdminPutConfigServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.getWriter().write("<form method=\"post\">Key: <input type=\"text\" name=\"key\" /><br/>Value: <input type=\"value\" name=\"value\" /><br/><input type=\"submit\" /></form>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Config config = new Config();
		config.setKey(req.getParameter("key"));
		config.setValue(req.getParameter("value"));
		
		persist(config);
		
		KeyPairRegistry.update(config);
		
		jsonResponse(resp, config);
	}

	private void persist(Config config) {
		ofy().save().entity(config).now();
	}
}
