package ch.furthermore.gae.proof;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.furthermore.gae.proof.entity.Proof;

public class LookupProofServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String hash = requestAsString(req);
		
		jsonResponse(resp, lookupProofs(hash)); 
	}

	public static List<Proof> lookupProofs(String hash) {
		return ofy().load().type(Proof.class).filter("hash = ", hash).list();
	}
}
