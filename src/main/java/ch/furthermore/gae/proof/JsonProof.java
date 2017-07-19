package ch.furthermore.gae.proof;

public class JsonProof {
	private String signatureBase;
	private String signature;

	public JsonProof(Proof proof) {
		signatureBase = proof.signatureBase();
		signature = proof.signature;
	}

	public String getSignatureBase() {
		return signatureBase;
	}

	public void setSignatureBase(String signatureBase) {
		this.signatureBase = signatureBase;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
