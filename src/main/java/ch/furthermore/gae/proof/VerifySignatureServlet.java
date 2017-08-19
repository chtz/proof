package ch.furthermore.gae.proof;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.furthermore.gae.proof.crypto.rsa.RSA;
import ch.furthermore.gae.proof.entity.Proof;

public class VerifySignatureServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Proof proof = new ObjectMapper().readValue(req.getInputStream(), Proof.class);
		
		byte[] sig = Base64.decodeBase64(proof.getSignature());
		RSA pubKey = new RSA(null, Base64.decodeBase64(proof.getPublicKey()));
		
		VerifySignatureResponse verifyResp = new VerifySignatureResponse();
		verifyResp.setSignatureValid(pubKey.verify(sig, IOUtils.toInputStream(proof.signatureBase(), "UTF8")));
		
		jsonResponse(resp, verifyResp);
	}
	
	public static class VerifySignatureResponse {
		private boolean signatureValid;

		public boolean isSignatureValid() {
			return signatureValid;
		}

		public void setSignatureValid(boolean signatureValid) {
			this.signatureValid = signatureValid;
		}
	}
}
