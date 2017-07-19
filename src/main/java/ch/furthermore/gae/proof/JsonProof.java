package ch.furthermore.gae.proof;

import org.apache.commons.codec.binary.Base64;

import ch.furthermore.gae.proof.crypto.KeyPairRegistry;

public class JsonProof {
	private String signatureBase;
	private String signature;
	private String publicKey;

	public JsonProof(Proof proof) {
		signatureBase = proof.signatureBase();
		signature = proof.signature;
		publicKey = Base64.encodeBase64String(KeyPairRegistry.rsa.getPublicKey().getEncoded());
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

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
