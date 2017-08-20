package ch.furthermore.gae.proof.crypto;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.security.KeyPair;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.googlecode.objectify.ObjectifyService;

import ch.furthermore.gae.proof.crypto.rsa.RSA;
import ch.furthermore.gae.proof.crypto.rsa.RSAKeyGenerator;
import ch.furthermore.gae.proof.entity.Config;
import ch.furthermore.gae.proof.entity.Proof;

public class ProofManager {
	private static RSA rsa;
	private static Proof proof;
	
	public static synchronized RSA getRSA() {
		return rsa;
	}
	
	public static synchronized Proof getProof() {
		return proof;
	}
	
	static String publicKey() {
		return Base64.encodeBase64String(rsa.getPublicKey().getEncoded());
	}
	
	static String privateKey() {
		return Base64.encodeBase64String(rsa.getPrivateKey().getEncoded());
	}

	public static Proof createProof(String hash) throws IOException {
		Proof proof = new Proof();
		proof.setHash(hash);
		proof.setTimestamp(currentDateTime());
		proof.setSignature(signature(proof.signatureBase()));
		proof.setPrivateKey(privateKey());
		proof.setPublicKey(publicKey());
		
		persist(proof);
		
		return proof;
	}
	
	static String currentDateTime() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
		
		return df.format(new Date());
	}
	
	static String signature(String signatureBase) throws IOException {
		return Base64.encodeBase64String(getRSA().sign(IOUtils.toInputStream(signatureBase, "UTF8")));
	}
	
	public static boolean verifyProof(Proof proof) throws IOException {
		byte[] sig = Base64.decodeBase64(proof.getSignature());
		RSA pubKey = new RSA(null, Base64.decodeBase64(proof.getPublicKey()));
		return pubKey.verify(sig, IOUtils.toInputStream(proof.signatureBase(), "UTF8"));
	}
	
	static synchronized void createIfNotExisting() {
		try {
			Config version = loadConfig("version");
			
			if (version == null) {
				KeyPair kp = RSAKeyGenerator.createKeyPair();
				rsa = new RSA(kp.getPrivate().getEncoded(), kp.getPublic().getEncoded());
				
				proof = createProof(UUID.randomUUID().toString());
				
				version = new Config();
				version.setKey("version");
				version.setValue(proof.getId());
				persist(version);
			}
			else {
				proof = loadProof(version.getValue());
				
				rsa = new RSA(Base64.decodeBase64(proof.getPrivateKey()), Base64.decodeBase64(proof.getPublicKey()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Config loadConfig(String id) {
		return ofy().load().type(Config.class).id(id).now();
	}
	
	static void persist(Config config) {
		ofy().save().entity(config).now();
	}
	
	static Proof loadProof(String id) {
		return ofy().load().type(Proof.class).id(id).now();
	}
	
	static void persist(Proof proof) {
		ofy().save().entity(proof).now();
	}
	
	static {
		ObjectifyService.register(Proof.class);
		ObjectifyService.register(Config.class);
		
		createIfNotExisting();
	}
}
