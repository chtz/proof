package ch.furthermore.gae.proof;

import org.apache.commons.codec.binary.Base64;

import ch.furthermore.gae.proof.crypto.KeyPairRegistry;

public class JsonIndex {
	private String publicKey;

	public JsonIndex() {
		publicKey = Base64.encodeBase64String(KeyPairRegistry.rsa.getPublicKey().getEncoded());
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
