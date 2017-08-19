package ch.furthermore.gae.proof;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.furthermore.gae.proof.entity.Proof;

public class LookupProofServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String hash = requestAsString(req);
		
		jsonResponse(resp, ofy().load().type(Proof.class).filter("hash = ", hash).list()); 
	}
}
