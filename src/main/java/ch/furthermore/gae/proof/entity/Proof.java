package ch.furthermore.gae.proof.entity;

import java.util.UUID;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Proof {
	@Id 
	String id = UUID.randomUUID().toString();
	
	@Index 
	String signatureHash;
	
	String signatureTimestamp;
	String signature;
	String signaturePublicKey;
	String signatureBase;
	String signatureBasePattern;

	public void calcSignatureBase() {
		signatureBasePattern = "signatureTimestamp + '//' + signatureHash";
		signatureBase = signatureTimestamp + "//" + signatureHash;
	}

	public String getSignatureHash() {
		return signatureHash;
	}

	public void setSignatureHash(String signatureHash) {
		this.signatureHash = signatureHash;
	}

	public String getSignatureTimestamp() {
		return signatureTimestamp;
	}

	public void setSignatureTimestamp(String signatureTimestamp) {
		this.signatureTimestamp = signatureTimestamp;
	}

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

	public String getSignatureBasePattern() {
		return signatureBasePattern;
	}

	public void setSignatureBasePattern(String signatureBasePattern) {
		this.signatureBasePattern = signatureBasePattern;
	}
}
