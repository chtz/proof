package ch.furthermore.gae.proof;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
curl -s -H "Content-Type: text/plain" -d "this-as-a-sample-hash" http://localhost:8080/proofs | json_pp
{
   "signatureBase" : "2017-07-19T12:50:31Z//this-as-a-sample-hash",
   "signatureHash" : "this-as-a-sample-hash",
   "signatureTimestamp" : "2017-07-19T12:50:31Z",
   "signature" : "i+Sy1howLpyJxRyeXpIEj8HnGYvQqY301zsfFGj1NE+ZhLGqKEbiDgiZPnJX+hOiQc5fHFBYkMDmK5qHH+bYzp4BTOILTbxcse5GyLIyA7w8OttOm5r7oGwIxSvEKWZ7XWnn8caUj6QOCn0xgkN+mi0Cp9Rbsv2mVKumzWY80b/P5cbWVpM1PaDD4okFMiOZ4VCFCJuJ09efCOkFR7NyeP6usc4xdSiYiLaZ7EjKXeMPy9KXPPQRWQGclham9uba4wX/YmSCKEAtoHJlE5NTUsZ7Vf3w3flrcT1NU6OKMUuM5//O65RMf1GHBLoG4I/eiEr02bW17esgvZUjjaIu8hWOMme5NE/hbNRejWD7aLcR3/cr0coo8VlI0380AE6z61tOSAvs/d3NTV0kF/YCucgys/B2HtlFMDlFix97Y4mEO4S+lB/UiJKAfJ2LK9TGKkSH7N8DwaJz30vfsMvCRbVIUhy7M34duYYVcQ/Bpxc63g2fBxqfjvXzPz51upcG33wq5LDuuJ48uXn5BS31ETyguPVEvkfr1G//Cq5hKqgA7ydPM5//4pbYG3GkCj/Y1CHWLeZxaOYfMfmqyu7s2zWxWdSSnBVaU6/e+tA6IeJ11pt/nyn4jtTw+RAPfTnruvbb7OkYBNZt80f54B/q8OnJTn3OB7CIshqDz62sUR8=",
   "signatureBasePattern" : "signatureTimestamp + '//' + signatureHash",
   "signaturePublicKey" : "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjtcOcN0UIxVVnNGDvQNMYaueX4mr/xpeuLDNE3iR1e08jQQ3te8vv592v0T6q30wcpnecjVMbAto/9OhRV1dI44o5pdE752D9VRJ0NqEtFgXhPE1Bw15Mdip/8aqnH3QEG+7M48MuXPKmRYUcwA4idy3ZabG3lBAanK/ro4O18OpJkflu4fAWm4kIveLXFZJQAPZLN/JaizxHEocuHPm+kjEn3S/A2mmWwSd05fl3OSgqF890P/DYCWWvLJ1/nKOksMJENRmkPF+2kVTDF3acOCUzlgHGGY6MmFx6uXZVU/eOkQiS7bkpENNaxzuTWADt5e8IXSJJmnrvDRbnBLiYz3ZELb3gOmAjejejJSpz+mfLbBvoNIXSDHFTx8hVvcPKUw2BF3s3CHuyA++xm6oIpA5wbFbkNXsIVAtjDlEOu6mwOpSuLVCUiCe0Jw6r2yOn18WEzyV8vVCN2biVQ/3RwklEHIH10FdbCTqG+sWSrmHKzZeiYZ4xlxlfFdgdYgL2VsmOiSc9mFIJvAbLRTDtpXNBuP2aImyI1lKBy5LWjCa1BQO7Bu9Nd6ZkvqkLitKITWiC/Dzg9es6HVYLy45dzcFLhpJBr6HqDCPiedV70I0F4FoJioyk++gcIBjD+dIC1Z7nXa+I4/udckwV+U6nqb/JDftaWEDyxw5FsVsfD0CAwEAAQ=="
}
 * </pre>
 * 
 * <pre>
HASH=$(shasum -a 512 src/main/java/ch/furthermore/gae/proof/CreateProofServlet.java)	
curl -s -H "Content-Type: text/plain" -d "$HASH" http://localhost:8080/proofs | jq ". | {signature,signatureTimestamp}" | tee src/main/resources/ch/furthermore/gae/proof/CreateProofServlet.sig.json
{
  "signature": "SZYFMjMwn1yYAaOxl/hgGP/8JAfAe4n/+L867zl0zz5UmX2BcZtlDBiYYlLgEIvfiywRPopLsI9EmZklOBwhrgtTrOxkN6sG2EBWQEJbru6ZKyiyyWlM2TlfQT7VuTANtVdqeFG6YNIw2y/Qrbgfo8F0C8Hz3AUBhmuqBpEQWWQIxozXOWixApqGCozb4H8PE6uwa0JF4R7S2L7KyuN5YzzVuOd735rSxxsRYRNhXRNATVWJbh0KWiGph1qoXRYN/VGpSlWy5Djq9DvMVcG3f1+nhkCguicRP064NmQF47EmncLmw5XYau8bMbryt2RTmXMjDRoiajTJzN/+Sn8JHrZuKazywm7NdPrw5ZGBvdj7mivd3fYDYdkaHfRULxX31kb3WKmWWGTPQACipnCw0IWrSi8GB9jlfSfr7INflr+Tpm+PV9+mw3y3UszK5cXvAct9WMF0m3Mou+TQiSujbdRLc5ehtLi1dloaABp2BBtwjD7HKtQ2/GVAqAlCzSveGQqIIMQU5D9RIFfOeA0xjJqivF3QGI+dVVKJatfmK3KW0pXUrqK/GzRp067rzcGEdgmgjO7CPl1PgsfFMvvHkeCs/G0+kAev1EJ9q/yigFv0k1jKnwJiv8cZhHrkpELQ7m5cStmCotoyH2JzrVdQlltdQ9TBLZFVD/rGI7Rgyao=",
  "signatureTimestamp": "2017-07-19T13:30:55Z"
}
 * </pre>
 * 
 * <pre>
curl -s -H "Content-Type: text/plain" -d "first" https://proof-174209.appspot.com/proofs | json_pp
 * </pre>
 */
public class CreateProofServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Proof proof = new Proof();
		
		proof.signatureHash = requestAsString(req);
		if (proof.signatureHash.length() > 1024) {
			throw new IllegalArgumentException("Hash too big");
		}
		
		calcSig(proof);
		
		persist(proof);
		
		jsonResponse(resp, proof);
	}

	private void calcSig(Proof proof) throws IOException {
		proof.signaturePublicKey = publicKey();
		
		proof.signatureTimestamp = currentDateTime();
		
		proof.calcSignatureBase();
		proof.signature = signature(proof.signatureBase);
	}

	private void persist(Proof proof) {
		ofy().save().entity(proof).now();
	}
}
