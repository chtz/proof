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
 *       "publicKey" : "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjtcOcN0UIxVVnNGDvQNMYaueX4mr/xpeuLDNE3iR1e08jQQ3te8vv592v0T6q30wcpnecjVMbAto/9OhRV1dI44o5pdE752D9VRJ0NqEtFgXhPE1Bw15Mdip/8aqnH3QEG+7M48MuXPKmRYUcwA4idy3ZabG3lBAanK/ro4O18OpJkflu4fAWm4kIveLXFZJQAPZLN/JaizxHEocuHPm+kjEn3S/A2mmWwSd05fl3OSgqF890P/DYCWWvLJ1/nKOksMJENRmkPF+2kVTDF3acOCUzlgHGGY6MmFx6uXZVU/eOkQiS7bkpENNaxzuTWADt5e8IXSJJmnrvDRbnBLiYz3ZELb3gOmAjejejJSpz+mfLbBvoNIXSDHFTx8hVvcPKUw2BF3s3CHuyA++xm6oIpA5wbFbkNXsIVAtjDlEOu6mwOpSuLVCUiCe0Jw6r2yOn18WEzyV8vVCN2biVQ/3RwklEHIH10FdbCTqG+sWSrmHKzZeiYZ4xlxlfFdgdYgL2VsmOiSc9mFIJvAbLRTDtpXNBuP2aImyI1lKBy5LWjCa1BQO7Bu9Nd6ZkvqkLitKITWiC/Dzg9es6HVYLy45dzcFLhpJBr6HqDCPiedV70I0F4FoJioyk++gcIBjD+dIC1Z7nXa+I4/udckwV+U6nqb/JDftaWEDyxw5FsVsfD0CAwEAAQ==",
 *       "signature" : "btqWAkKq2F7XpcDTikTFi/vTZDBx0LnaXYEPq3bdxxD1aBUqNaD+AaGtNF9p6Tss3z8so7EDJL3IQy3sj9KWnOhg54rGzoBRfPDb8ZemYjCjRC0h2rixBVlcWVPEhI28H0eosKiEbneg7HIuouJguP3MZIqn8Qswc82vmNnxpHjsrTszF5fnxPbL5yAYYQ1D05BooTbmkyEunqCmm2f4BdfVwm1FUXt9RGTgpfjjH8gIsAjCrHrpQgUKr6MAbK9raHBeicqx6oEquZx0CvJxCVIydeF3OdKk6RPjutiQAslHxEDDaDDMKc4vB/ExMHZSSMjU/vxS2fGNEl/gsUUr7AjHrAJn+C8g8j9Vx9PcF1V2IG5ivyvoc0p6Ao5P73qQMioNFK/JgPfUMvvcK0wdtp5kOVbCCaBXS1Y3BNq6Fz9faJQfm+vQVb9ltl5GhYmT6epO3o0F0JunMF7rZFrG6mPdhm5VFJtZ1OSYp2UvcnXBgm07cYRcV11qQrK8Mm8AnY/BYQxVUPFY24Lm2G/ts8e2TjYRRYvoRNkeoEw0br6ooBKcJ0ICf8q/XIeMOZL8HVGAlHLEqNrOuwgqYEYtwLiO65LokIfKekKJeXuzncY3aywIGvcU/RXHGSTQcInAyqrIg4miZcdgE2lEZLN9lkCtSmwFXHyQSr0pF+8nATs=",
 *       "signatureBase" : "2017-07-19T11:01:45Z//halli"
 *    }
 * ]
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