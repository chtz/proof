package ch.furthermore.gae.proof;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * $ curl -s -H "Content-Type: text/plain" -d "halli" http://localhost:8080/hashes | json_pp
 * [
 *    {
 *       "signatureBase" : "2017-07-19T11:01:45Z//halli",
 *       "signature" : "btqWAkKq2F7XpcDTikTFi/vTZDBx0LnaXYEPq3bdxxD1aBUqNaD+AaGtNF9p6Tss3z8so7EDJL3IQy3sj9KWnOhg54rGzoBRfPDb8ZemYjCjRC0h2rixBVlcWVPEhI28H0eosKiEbneg7HIuouJguP3MZIqn8Qswc82vmNnxpHjsrTszF5fnxPbL5yAYYQ1D05BooTbmkyEunqCmm2f4BdfVwm1FUXt9RGTgpfjjH8gIsAjCrHrpQgUKr6MAbK9raHBeicqx6oEquZx0CvJxCVIydeF3OdKk6RPjutiQAslHxEDDaDDMKc4vB/ExMHZSSMjU/vxS2fGNEl/gsUUr7AjHrAJn+C8g8j9Vx9PcF1V2IG5ivyvoc0p6Ao5P73qQMioNFK/JgPfUMvvcK0wdtp5kOVbCCaBXS1Y3BNq6Fz9faJQfm+vQVb9ltl5GhYmT6epO3o0F0JunMF7rZFrG6mPdhm5VFJtZ1OSYp2UvcnXBgm07cYRcV11qQrK8Mm8AnY/BYQxVUPFY24Lm2G/ts8e2TjYRRYvoRNkeoEw0br6ooBKcJ0ICf8q/XIeMOZL8HVGAlHLEqNrOuwgqYEYtwLiO65LokIfKekKJeXuzncY3aywIGvcU/RXHGSTQcInAyqrIg4miZcdgE2lEZLN9lkCtSmwFXHyQSr0pF+8nATs="
 *    }
 * ]
 * </pre>
 * 
 * <pre>
 * curl -s -H "Content-Type: text/plain" -d "first" https://proof-174209.appspot.com/hashes | json_pp
 * </pre>
 */
public class LookupProofServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String hash = requestAsString(req);
		
		List<JsonProof> proofs = new LinkedList<>();
		for (Proof proof : ofy().load().type(Proof.class).filter("hash = ", hash).list()) {
			proofs.add(new JsonProof(proof));
		}
		
		jsonResponse(resp, proofs); 
	}
}
