package ch.furthermore.gae.proof;

public class VerifySignatureResponse extends VerifySignatureRequest {
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
