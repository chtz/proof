package ch.furthermore.gae.proof;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.ObjectifyService;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static { 
		ObjectifyService.register(Proof.class); 
	}
	
	protected void jsonResponse(HttpServletResponse resp, Object o) throws IOException, JsonGenerationException, JsonMappingException {
		try (PrintWriter w = resp.getWriter()) {
			new ObjectMapper().writeValue(w, o);
		}
	}

	protected String requestAsString(HttpServletRequest req) throws IOException {
		try (InputStream in = req.getInputStream()) {
			return IOUtils.toString(in, "UTF8");
		}
	}
}
