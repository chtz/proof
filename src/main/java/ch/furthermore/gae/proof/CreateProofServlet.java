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
		Proof proof = new Proof();
		
		proof.setSignatureHash(requestAsString(req));
		if (proof.getSignatureHash().length() > 1024) {
			throw new IllegalArgumentException("Hash too big");
		}
		
		calcSig(proof);
		
		persist(proof);
		
		jsonResponse(resp, proof);
	}

	private void calcSig(Proof proof) throws IOException {
		proof.setSignaturePublicKey(publicKey());
		
		proof.setSignatureTimestamp(currentDateTime());
		
		proof.calcSignatureBase();
		proof.setSignature(signature(proof.getSignatureBase()));
	}

	private void persist(Proof proof) {
		ofy().save().entity(proof).now();
	}
}
