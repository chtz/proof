package ch.furthermore.gae.proof.crypto;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import ch.furthermore.gae.proof.crypto.rsa.RSA;

public class KeyPairRegistry {
	public final static RSA rsa;
	static {
		try {
			rsa = new RSA(Base64.decodeBase64(IOUtils.toString(KeyPairRegistry.class.getResourceAsStream("private.key"), "UTF8")),
					Base64.decodeBase64(IOUtils.toString(KeyPairRegistry.class.getResourceAsStream("public.key"), "UTF8")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
