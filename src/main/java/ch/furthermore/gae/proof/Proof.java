package ch.furthermore.gae.proof;

import java.util.UUID;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Proof {
	@Id String id = UUID.randomUUID().toString();
	@Index String hash;
	String timestamp;
	String signature;
	String publicKey;

	public String signatureBase() {
		return timestamp + "//" + hash;
	}
}
