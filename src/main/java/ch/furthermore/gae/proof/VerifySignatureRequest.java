package ch.furthermore.gae.proof;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifySignatureRequest {
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
