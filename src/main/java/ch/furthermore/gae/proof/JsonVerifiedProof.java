package ch.furthermore.gae.proof;

public class JsonVerifiedProof extends JsonProof {
	boolean valid;

	public JsonVerifiedProof(JsonProof req) {
		this.publicKey = req.publicKey;
		this.signatureBase = req.signatureBase;
		this.signature = req.signature;
	}
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}