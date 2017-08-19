package ch.furthermore.gae.proof;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.furthermore.gae.proof.crypto.rsa.RSA;

public class VerifySignatureServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		VerifySignatureRequest verifyRequest = new ObjectMapper().readValue(req.getInputStream(), VerifySignatureRequest.class);
		byte[] sig = Base64.decodeBase64(verifyRequest.getSignature());
		
		if (verifyRequest.getSignaturePublicKey() == null) {
			verifyRequest.setSignaturePublicKey(publicKey());
		}
		
		RSA pubKey = new RSA(null, Base64.decodeBase64(verifyRequest.getSignaturePublicKey()));
		
		VerifySignatureResponse verifyResp = new VerifySignatureResponse(verifyRequest);
		verifyResp.verificationIsSignatureValid = pubKey.verify(sig, IOUtils.toInputStream(verifyRequest.signatureBase, "UTF8"));
		
		calcSig(verifyResp);
		
		jsonResponse(resp, verifyResp);
	}
	
	private void calcSig(VerifySignatureResponse verification) throws IOException {
		verification.verificationPublicKey = publicKey();
		
		verification.verificationTimestamp = currentDateTime();
		verification.verificationSignatureBasePattern = "verificationTimestamp + '//' + verificationIsSignatureValid + '//' + signatureBase + '//' + signature + '//' + signaturePublicKey";
		verification.verificationSignatureBase = verification.verificationTimestamp + "//" + verification.verificationIsSignatureValid + "//" + verification.signatureBase + "//" + verification.signature + "//" + verification.signaturePublicKey;
		
		verification.verificationSignature = signature(verification.verificationSignatureBase);
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class VerifySignatureRequest {
		String signature;
		String signaturePublicKey;
		String signatureBase;

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String getSignaturePublicKey() {
			return signaturePublicKey;
		}

		public void setSignaturePublicKey(String signaturePublicKey) {
			this.signaturePublicKey = signaturePublicKey;
		}

		public String getSignatureBase() {
			return signatureBase;
		}

		public void setSignatureBase(String signatureBase) {
			this.signatureBase = signatureBase;
		}
	}
	
	public static class VerifySignatureResponse extends VerifySignatureRequest {
		boolean verificationIsSignatureValid;
		String verificationTimestamp;
		String verificationPublicKey;
		String verificationSignatureBasePattern;
		String verificationSignatureBase;
		String verificationSignature;
		
		public VerifySignatureResponse(VerifySignatureRequest req) {
			this.signaturePublicKey = req.signaturePublicKey;
			this.signatureBase = req.signatureBase;
			this.signature = req.signature;
		}

		public boolean isVerificationIsSignatureValid() {
			return verificationIsSignatureValid;
		}

		public void setVerificationIsSignatureValid(boolean verificationIsSignatureValid) {
			this.verificationIsSignatureValid = verificationIsSignatureValid;
		}

		public String getVerificationTimestamp() {
			return verificationTimestamp;
		}

		public void setVerificationTimestamp(String verificationTimestamp) {
			this.verificationTimestamp = verificationTimestamp;
		}

		public String getVerificationPublicKey() {
			return verificationPublicKey;
		}

		public void setVerificationPublicKey(String verificationPublicKey) {
			this.verificationPublicKey = verificationPublicKey;
		}

		public String getVerificationSignatureBasePattern() {
			return verificationSignatureBasePattern;
		}

		public void setVerificationSignatureBasePattern(String verificationSignatureBasePattern) {
			this.verificationSignatureBasePattern = verificationSignatureBasePattern;
		}

		public String getVerificationSignatureBase() {
			return verificationSignatureBase;
		}

		public void setVerificationSignatureBase(String verificationSignatureBase) {
			this.verificationSignatureBase = verificationSignatureBase;
		}

		public String getVerificationSignature() {
			return verificationSignature;
		}

		public void setVerificationSignature(String verificationSignature) {
			this.verificationSignature = verificationSignature;
		}
	}
}
