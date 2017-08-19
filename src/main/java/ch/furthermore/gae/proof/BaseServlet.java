package ch.furthermore.gae.proof;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.ObjectifyService;

import ch.furthermore.gae.proof.crypto.KeyPairRegistry;
import ch.furthermore.gae.proof.entity.Config;
import ch.furthermore.gae.proof.entity.Proof;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static { 
		ObjectifyService.register(Proof.class);
		ObjectifyService.register(Config.class);
	}
	
	String signature(String signatureBase) throws IOException {
		return Base64.encodeBase64String(KeyPairRegistry.get().sign(IOUtils.toInputStream(signatureBase, "UTF8")));
	}

	String publicKey() {
		return Base64.encodeBase64String(KeyPairRegistry.get().getPublicKey().getEncoded());
	}

	String currentDateTime() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
		
		return df.format(new Date());
	}
	
	void jsonResponse(HttpServletResponse resp, Object o) throws IOException, JsonGenerationException, JsonMappingException {
		try (PrintWriter w = resp.getWriter()) {
			new ObjectMapper().writeValue(w, o);
		}
	}

	String requestAsString(HttpServletRequest req) throws IOException {
		try (InputStream in = req.getInputStream()) {
			return IOUtils.toString(in, "UTF8");
		}
	}
}
