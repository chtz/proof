package ch.furthermore.gae.proof;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

/**
 * <pre>
 * $ curl -H "Content-Type: text/plain" -d "halli" http://localhost:8080/register/get/200/willi/wuff
 * registered |get|200|/willi/wuff|5|
 * $ curl -v http://localhost:8080/willi/wuff
 * < Content-Type: text/plain
 * < Content-Length: 5
 * halli
 * 
 * $ curl -H "Content-Type: text/plain" -d "halli" https://proof-174209.appspot.com//register/get/200/willi/wuff
 * registered |get|200|/willi/wuff|5|
 * $ curl https://proof-174209.appspot.com//willi/wuff
 * halli
 * </pre>
 */
public class EchoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static { 
		ObjectifyService.register(Response.class); 
	}
	
	private void updateResponse(String method, String path, Response r) {
		r.methodPath = methodPath(method, path);
		
		ofy().save().entity(r).now();
	}

	private String methodPath(String method, String path) {
		return method + ":" + path;
	}

	private Response lookupResponse(String method, String path) {
		return ofy().load().type(Response.class).id(methodPath(method, path)).now();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Matcher m = Pattern.compile("\\/register\\/(.+?)\\/(.+?)\\/(.+)").matcher(req.getServletPath());

		if (!m.matches()) {
			Response r = lookupResponse("post", req.getServletPath());
			
			resp.setStatus(r.code);
			resp.setContentType(r.type);
			resp.setContentLength(r.content.length);
			resp.getOutputStream().write(r.content);
		} else {
			String method = m.group(1);
			String code = m.group(2);
			String path = "/" + m.group(3);

			Response r = lookupResponse(method.toLowerCase(), path);
			if (r == null) {
				r = new Response();
			}

			r.code = Integer.parseInt(code);
			r.type = req.getContentType();

			BufferedInputStream in = new BufferedInputStream(req.getInputStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			for (int l = in.read(buffer); l != -1; l = in.read(buffer)) {
				out.write(buffer, 0, l);
			}

			r.content = out.toByteArray();

			updateResponse(method, path, r);
			
			resp.getWriter().println("registered |" + method + "|" + code + "|" + path + "|" + r.content.length + "|"); // DEBUG
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Response r = lookupResponse("get", req.getServletPath());
		
		resp.setStatus(r.code);
		resp.setContentType(r.type);
		resp.setContentLength(r.content.length);
		resp.getOutputStream().write(r.content);
	}
}
