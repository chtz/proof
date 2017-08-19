package ch.furthermore.gae.proof.crypto;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.security.KeyPair;

import org.apache.commons.codec.binary.Base64;

import com.googlecode.objectify.Key;

import ch.furthermore.gae.proof.crypto.rsa.RSA;
import ch.furthermore.gae.proof.crypto.rsa.RSAKeyGenerator;
import ch.furthermore.gae.proof.entity.Config;

public class KeyPairRegistry {
	private static RSA rsa;
	
	public static synchronized RSA get() {
		return rsa;
	}
	
	public static synchronized void createIfNotExisting() {
		try {
			Config privateKey = ofy().load().type(Config.class).id("private.key").now();
			Config publicKey = ofy().load().type(Config.class).id("public.key").now();
			
			if (privateKey == null || publicKey == null) {
				KeyPair kp = RSAKeyGenerator.createKeyPair();
				
				privateKey = new Config();
				privateKey.setKey("private.key");
				privateKey.setValue(Base64.encodeBase64String(kp.getPrivate().getEncoded()));
				persist(privateKey);
				
				publicKey = new Config();
				publicKey.setKey("public.key");
				publicKey.setValue(Base64.encodeBase64String(kp.getPublic().getEncoded()));
				persist(publicKey);
				
				rsa = new RSA(kp.getPrivate().getEncoded(), kp.getPublic().getEncoded());
			}
			else {
				String privateKeyEnc = privateKey.getValue();
				String publicKeyEnc = publicKey.getValue();
				
				rsa = new RSA(Base64.decodeBase64(privateKeyEnc), Base64.decodeBase64(publicKeyEnc));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static boolean objectExists(String id, Class<?> objectClass) {
		return ofy().load().filterKey(Key.create(objectClass, id)).keys().first() != null;
	}
	
	static void persist(Config config) {
		ofy().save().entity(config).now();
	}
	
	static {
		createIfNotExisting();
	}
}
