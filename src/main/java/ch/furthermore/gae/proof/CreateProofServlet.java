package ch.furthermore.gae.proof;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.appengine.repackaged.com.google.api.client.util.Base64;

import ch.furthermore.gae.proof.crypto.KeyPairRegistry;

/**
 * <pre>
 * $ curl -s -H "Content-Type: text/plain" -d "halli" http://localhost:8080/proofs | json_pp
 * {
 *    "signatureBase" : "2017-07-19T11:22:47Z//halli",
 *    "signature" : "I6SH2SjT8m0ZW3561hYrFTX3eiWSFH7Se1IUo56Jv+95rle2Avzn/fiQRPobtVUYdXfWODV1otYqU+xSoGnR4Z0bHNM6XJY57rjFLl0uM3IyWkSOKoTzlcMV59qS0zX1V58/NgEi5PJ8uYWR1UDP/6v6Q6ALjtWefGnHEG4PapL9FfXw+VJyD41Zc0Te8x42vpqDjPtwHjbXtqp7Mrdgzg2x5Zzk2+xDgC/GhWqrvv7kOJbIDcGGFj/PyIymhmhnmZbWbJjbuWKFET/Zc6bYcfm2Givxlq3lFDnM0tS2ROu3Fwu8R1hBuDQfeIWvzEaFgjvmkiRX+pi9ZVfeCl7zKZmO7SIyMVEksgW/DMG4FxbLuu+RfzbKiuwskO85Xw91tTH8Ejba7CBry+Uf27rqXGmfzC8HE2pMJJB+ovKwk7k8r4cF9UF8SjfuqJ4otG15eheqtMHiRagdzhXIkQtFp0Rm84qaXTw+DsDMuTUsiPT3tzAzyqmr9VhFywdf2MH6UJY4GHMt6thAM6gL4DPGi745jUBV3bp0DEYjHaQ00Br+F2/Sp/Ybs2KYO1cqPUQVhXFFRQIzYrU1p+WGEcDn7lNdaR6I9jUfhJZ5KnT143YHHWUeoMWWzAcSoDsTm1/dqKp2OgDwknv30zAzzGJhm4bUrUh+RFB9x1MQ+ngahVw="
 * }
 * </pre>
 * 
 * <pre>
 * curl -s -H "Content-Type: text/plain" -d "first" https://proof-174209.appspot.com/proofs | json_pp
 * </pre>
 */
public class CreateProofServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Proof proof = new Proof();
		
		proof.hash = requestAsString(req);
		if (proof.hash.length() > 1024) {
			throw new IllegalArgumentException("Hash too big");
		}
		
		calcSig(proof);
		
		persist(proof);
		
		jsonResponse(resp, new JsonProof(proof));
	}

	private void calcSig(Proof proof) throws IOException {
		proof.timestamp = currentDateTime();
		
		proof.signature = Base64.encodeBase64String(KeyPairRegistry.rsa.sign(IOUtils.toInputStream(proof.signatureBase(), "UTF8")));
	}

	private String currentDateTime() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
		return df.format(new Date());
	}
	
	private void persist(Proof proof) {
		ofy().save().entity(proof).now();
	}
}
