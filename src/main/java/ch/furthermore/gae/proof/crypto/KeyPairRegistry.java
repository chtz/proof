package ch.furthermore.gae.proof.crypto;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.apache.commons.codec.binary.Base64;

import ch.furthermore.gae.proof.Config;
import ch.furthermore.gae.proof.crypto.rsa.RSA;

public class KeyPairRegistry {
	private static RSA rsa;
	
	public static synchronized RSA get() {
		return rsa;
	}
	
	public static synchronized void update(Config config) {
//		try {
			String privateKey = "private.key".equals(config.getKey())
					? config.getValue()
					: ofy().load().type(Config.class).id("private.key").now().getValue();
					
			String publicKey = "public.key".equals(config.getKey())
					? config.getValue()
					: ofy().load().type(Config.class).id("public.key").now().getValue();
			
//			String privateKey = IOUtils.toString(KeyPairRegistry.class.getResourceAsStream("private.key"), "UTF8");
//			String publicKey = IOUtils.toString(KeyPairRegistry.class.getResourceAsStream("public.key"), "UTF8");
			
			rsa = new RSA(Base64.decodeBase64(privateKey), Base64.decodeBase64(publicKey));
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}
	
	static {
		update(new Config());
	}
}
