package ch.furthermore.gae.proof;

public class JsonProof {
	String publicKey;
	String signatureBase;
	String signature;

	public JsonProof() {}
	
	public JsonProof(Proof proof) {
		publicKey = proof.publicKey;
		signatureBase = proof.signatureBase();
		signature = proof.signature;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
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
