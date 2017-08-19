package ch.furthermore.gae.proof;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import ch.furthermore.gae.proof.crypto.KeyPairRegistry;

public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IndexResponse response = new IndexResponse();
		response.setPublicKey(Base64.encodeBase64String(KeyPairRegistry.get().getPublicKey().getEncoded()));
		response.setTimestamp(currentDateTime());
		
		jsonResponse(resp, response);
	}
	
	public static class IndexResponse {
		private String publicKey;
		private String timestamp;

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}
	}
}
