package ch.furthermore.gae.proof;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	void jsonResponse(HttpServletResponse resp, Object o) throws IOException, JsonGenerationException, JsonMappingException {
		try (Writer w = resp.getWriter()) {
			new ObjectMapper().writeValue(w, o);
		}
	}

	String requestAsString(HttpServletRequest req) throws IOException {
		try (Reader r = req.getReader()) {
			return IOUtils.toString(r);
		}
	}
}
