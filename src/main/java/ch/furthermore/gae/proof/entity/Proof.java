package ch.furthermore.gae.proof.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Proof {
	@JsonIgnore @Id 
	private String id = UUID.randomUUID().toString();
	
	@Index 
	private String hash;
	
	private String timestamp;
	private String signature;
	
	@Ignore
	private String publicKey;

	public String signatureBase() {
		return timestamp + "/" + hash;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String signatureHash) {
		this.hash = signatureHash;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String signatureTimestamp) {
		this.timestamp = signatureTimestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
