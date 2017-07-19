package ch.furthermore.gae.proof.crypto.tools;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import ch.furthermore.gae.proof.crypto.rsa.RSAKeyGenerator;

public class KeyPairGenerator {
	public static void main(String[] args) throws IOException {
		KeyPair kp = RSAKeyGenerator.createKeyPair();
		FileUtils.write(new File("src/main/resources/ch/furthermore/gae/proof/crypto/private.key"), 
				Base64.encodeBase64String(kp.getPrivate().getEncoded()), "UTF8");
		FileUtils.write(new File("src/main/resources/ch/furthermore/gae/proof/crypto/public.key"), 
				Base64.encodeBase64String(kp.getPublic().getEncoded()), "UTF8");
	}
}
