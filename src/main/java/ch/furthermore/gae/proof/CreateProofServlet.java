package ch.furthermore.gae.proof;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.furthermore.gae.proof.entity.Proof;

public class CreateProofServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String hash = requestAsString(req);
		
		if (hash.length() > 1024) {
			throw new IllegalArgumentException("Hash too big");
		}
		
		Proof proof = new Proof();
		proof.setHash(hash);
		proof.setTimestamp(currentDateTime());
		proof.setSignature(signature(proof.signatureBase()));
		proof.setPublicKey(publicKey());
		
		ofy().save().entity(proof).now();
		
		jsonResponse(resp, proof);
	}
}
