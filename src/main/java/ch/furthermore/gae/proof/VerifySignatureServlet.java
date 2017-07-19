package ch.furthermore.gae.proof;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.furthermore.gae.proof.crypto.rsa.RSA;

/**
 * 
 * <pre>
cat <<EOT > /tmp/verify-sample-request.txt
{
   "signatureBase" : "2017-07-19T12:50:31Z//this-as-a-sample-hash",
   "signature" : "i+Sy1howLpyJxRyeXpIEj8HnGYvQqY301zsfFGj1NE+ZhLGqKEbiDgiZPnJX+hOiQc5fHFBYkMDmK5qHH+bYzp4BTOILTbxcse5GyLIyA7w8OttOm5r7oGwIxSvEKWZ7XWnn8caUj6QOCn0xgkN+mi0Cp9Rbsv2mVKumzWY80b/P5cbWVpM1PaDD4okFMiOZ4VCFCJuJ09efCOkFR7NyeP6usc4xdSiYiLaZ7EjKXeMPy9KXPPQRWQGclham9uba4wX/YmSCKEAtoHJlE5NTUsZ7Vf3w3flrcT1NU6OKMUuM5//O65RMf1GHBLoG4I/eiEr02bW17esgvZUjjaIu8hWOMme5NE/hbNRejWD7aLcR3/cr0coo8VlI0380AE6z61tOSAvs/d3NTV0kF/YCucgys/B2HtlFMDlFix97Y4mEO4S+lB/UiJKAfJ2LK9TGKkSH7N8DwaJz30vfsMvCRbVIUhy7M34duYYVcQ/Bpxc63g2fBxqfjvXzPz51upcG33wq5LDuuJ48uXn5BS31ETyguPVEvkfr1G//Cq5hKqgA7ydPM5//4pbYG3GkCj/Y1CHWLeZxaOYfMfmqyu7s2zWxWdSSnBVaU6/e+tA6IeJ11pt/nyn4jtTw+RAPfTnruvbb7OkYBNZt80f54B/q8OnJTn3OB7CIshqDz62sUR8=",
   "signaturePublicKey" : "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjtcOcN0UIxVVnNGDvQNMYaueX4mr/xpeuLDNE3iR1e08jQQ3te8vv592v0T6q30wcpnecjVMbAto/9OhRV1dI44o5pdE752D9VRJ0NqEtFgXhPE1Bw15Mdip/8aqnH3QEG+7M48MuXPKmRYUcwA4idy3ZabG3lBAanK/ro4O18OpJkflu4fAWm4kIveLXFZJQAPZLN/JaizxHEocuHPm+kjEn3S/A2mmWwSd05fl3OSgqF890P/DYCWWvLJ1/nKOksMJENRmkPF+2kVTDF3acOCUzlgHGGY6MmFx6uXZVU/eOkQiS7bkpENNaxzuTWADt5e8IXSJJmnrvDRbnBLiYz3ZELb3gOmAjejejJSpz+mfLbBvoNIXSDHFTx8hVvcPKUw2BF3s3CHuyA++xm6oIpA5wbFbkNXsIVAtjDlEOu6mwOpSuLVCUiCe0Jw6r2yOn18WEzyV8vVCN2biVQ/3RwklEHIH10FdbCTqG+sWSrmHKzZeiYZ4xlxlfFdgdYgL2VsmOiSc9mFIJvAbLRTDtpXNBuP2aImyI1lKBy5LWjCa1BQO7Bu9Nd6ZkvqkLitKITWiC/Dzg9es6HVYLy45dzcFLhpJBr6HqDCPiedV70I0F4FoJioyk++gcIBjD+dIC1Z7nXa+I4/udckwV+U6nqb/JDftaWEDyxw5FsVsfD0CAwEAAQ=="
}
EOT

curl -s -H "Content-Type: text/plain" -d @/tmp/verify-sample-request.txt http://localhost:8080/verify | json_pp
{
   "verificationSignature" : "DXyUhLv90P9fJZw7anGdeiZdqxZGE8xmteQcPp/Dq65MN11KAY0DR6vtd2sFcQbUDEY2FetVHQYGhq2VWDc5LBWGHIjJwyaiibL/k5wTgYMZjHOwGemmeyj2p0fp5hnotNozJvNgYTyrTJHcm5ugZOZFkdfRXPkC1xUx3NOsY6dOq35FvWWzkSQsnKfcrmgVucdk1nuKSTVKQDpVuKAvsG9vt84zF8vQpCRO5qkZ82ZmeV4JoTe7zRSO5TGXZ2kWEGgcNfAj9eXdmU+TKD2VC/oVoC9F67Je55cpag6hIRey3CWdZPqBF9ON/wfWKlT5fkilUq0hbQhl3KMK9mkEjO+dufegeW2m+o/cqhdvA6d/q0B+AQ0Nm8ZaDJ6xSoBhk71UJ+L2W/lS9U+B1mahZw1DEKuB6lHwjCxz/OAB43054DSCtkkDiViVxO1+5dkW09V4uVQ5+ieRSiBUX1sZ557l5okuRbX88qsOOqz2Ngsh5qFbPDbeS2R2/mvRwL5uaM9uU7eM1dxCRfesMHzFLfMi31lh1DQTZBqtXMahId47IZnJjMQ36UfeJG9aAuonZXAiEpvFDND5qQ003qjoM2De4QphNzqobPkVuhfTcEzBmQz0gvaIbGuMQ0rfOn6fF7sWAxiPuY+/YgYlt5Z5REL/bM+tXxwJWpSu5Xvzz/E=",
   "verificationSignatureBasePattern" : "verificationTimestamp + '//' + verificationIsSignatureValid + '//' + signatureBase + '//' + signature + '//' + signaturePublicKey",
   "verificationIsSignatureValid" : true,
   "signature" : "i+Sy1howLpyJxRyeXpIEj8HnGYvQqY301zsfFGj1NE+ZhLGqKEbiDgiZPnJX+hOiQc5fHFBYkMDmK5qHH+bYzp4BTOILTbxcse5GyLIyA7w8OttOm5r7oGwIxSvEKWZ7XWnn8caUj6QOCn0xgkN+mi0Cp9Rbsv2mVKumzWY80b/P5cbWVpM1PaDD4okFMiOZ4VCFCJuJ09efCOkFR7NyeP6usc4xdSiYiLaZ7EjKXeMPy9KXPPQRWQGclham9uba4wX/YmSCKEAtoHJlE5NTUsZ7Vf3w3flrcT1NU6OKMUuM5//O65RMf1GHBLoG4I/eiEr02bW17esgvZUjjaIu8hWOMme5NE/hbNRejWD7aLcR3/cr0coo8VlI0380AE6z61tOSAvs/d3NTV0kF/YCucgys/B2HtlFMDlFix97Y4mEO4S+lB/UiJKAfJ2LK9TGKkSH7N8DwaJz30vfsMvCRbVIUhy7M34duYYVcQ/Bpxc63g2fBxqfjvXzPz51upcG33wq5LDuuJ48uXn5BS31ETyguPVEvkfr1G//Cq5hKqgA7ydPM5//4pbYG3GkCj/Y1CHWLeZxaOYfMfmqyu7s2zWxWdSSnBVaU6/e+tA6IeJ11pt/nyn4jtTw+RAPfTnruvbb7OkYBNZt80f54B/q8OnJTn3OB7CIshqDz62sUR8=",
   "signatureBase" : "2017-07-19T12:50:31Z//this-as-a-sample-hash",
   "verificationTimestamp" : "2017-07-19T12:56:50Z",
   "signaturePublicKey" : "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjtcOcN0UIxVVnNGDvQNMYaueX4mr/xpeuLDNE3iR1e08jQQ3te8vv592v0T6q30wcpnecjVMbAto/9OhRV1dI44o5pdE752D9VRJ0NqEtFgXhPE1Bw15Mdip/8aqnH3QEG+7M48MuXPKmRYUcwA4idy3ZabG3lBAanK/ro4O18OpJkflu4fAWm4kIveLXFZJQAPZLN/JaizxHEocuHPm+kjEn3S/A2mmWwSd05fl3OSgqF890P/DYCWWvLJ1/nKOksMJENRmkPF+2kVTDF3acOCUzlgHGGY6MmFx6uXZVU/eOkQiS7bkpENNaxzuTWADt5e8IXSJJmnrvDRbnBLiYz3ZELb3gOmAjejejJSpz+mfLbBvoNIXSDHFTx8hVvcPKUw2BF3s3CHuyA++xm6oIpA5wbFbkNXsIVAtjDlEOu6mwOpSuLVCUiCe0Jw6r2yOn18WEzyV8vVCN2biVQ/3RwklEHIH10FdbCTqG+sWSrmHKzZeiYZ4xlxlfFdgdYgL2VsmOiSc9mFIJvAbLRTDtpXNBuP2aImyI1lKBy5LWjCa1BQO7Bu9Nd6ZkvqkLitKITWiC/Dzg9es6HVYLy45dzcFLhpJBr6HqDCPiedV70I0F4FoJioyk++gcIBjD+dIC1Z7nXa+I4/udckwV+U6nqb/JDftaWEDyxw5FsVsfD0CAwEAAQ==",
   "verificationSignatureBase" : "2017-07-19T12:56:50Z//true//2017-07-19T12:50:31Z//this-as-a-sample-hash//i+Sy1howLpyJxRyeXpIEj8HnGYvQqY301zsfFGj1NE+ZhLGqKEbiDgiZPnJX+hOiQc5fHFBYkMDmK5qHH+bYzp4BTOILTbxcse5GyLIyA7w8OttOm5r7oGwIxSvEKWZ7XWnn8caUj6QOCn0xgkN+mi0Cp9Rbsv2mVKumzWY80b/P5cbWVpM1PaDD4okFMiOZ4VCFCJuJ09efCOkFR7NyeP6usc4xdSiYiLaZ7EjKXeMPy9KXPPQRWQGclham9uba4wX/YmSCKEAtoHJlE5NTUsZ7Vf3w3flrcT1NU6OKMUuM5//O65RMf1GHBLoG4I/eiEr02bW17esgvZUjjaIu8hWOMme5NE/hbNRejWD7aLcR3/cr0coo8VlI0380AE6z61tOSAvs/d3NTV0kF/YCucgys/B2HtlFMDlFix97Y4mEO4S+lB/UiJKAfJ2LK9TGKkSH7N8DwaJz30vfsMvCRbVIUhy7M34duYYVcQ/Bpxc63g2fBxqfjvXzPz51upcG33wq5LDuuJ48uXn5BS31ETyguPVEvkfr1G//Cq5hKqgA7ydPM5//4pbYG3GkCj/Y1CHWLeZxaOYfMfmqyu7s2zWxWdSSnBVaU6/e+tA6IeJ11pt/nyn4jtTw+RAPfTnruvbb7OkYBNZt80f54B/q8OnJTn3OB7CIshqDz62sUR8=//MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjtcOcN0UIxVVnNGDvQNMYaueX4mr/xpeuLDNE3iR1e08jQQ3te8vv592v0T6q30wcpnecjVMbAto/9OhRV1dI44o5pdE752D9VRJ0NqEtFgXhPE1Bw15Mdip/8aqnH3QEG+7M48MuXPKmRYUcwA4idy3ZabG3lBAanK/ro4O18OpJkflu4fAWm4kIveLXFZJQAPZLN/JaizxHEocuHPm+kjEn3S/A2mmWwSd05fl3OSgqF890P/DYCWWvLJ1/nKOksMJENRmkPF+2kVTDF3acOCUzlgHGGY6MmFx6uXZVU/eOkQiS7bkpENNaxzuTWADt5e8IXSJJmnrvDRbnBLiYz3ZELb3gOmAjejejJSpz+mfLbBvoNIXSDHFTx8hVvcPKUw2BF3s3CHuyA++xm6oIpA5wbFbkNXsIVAtjDlEOu6mwOpSuLVCUiCe0Jw6r2yOn18WEzyV8vVCN2biVQ/3RwklEHIH10FdbCTqG+sWSrmHKzZeiYZ4xlxlfFdgdYgL2VsmOiSc9mFIJvAbLRTDtpXNBuP2aImyI1lKBy5LWjCa1BQO7Bu9Nd6ZkvqkLitKITWiC/Dzg9es6HVYLy45dzcFLhpJBr6HqDCPiedV70I0F4FoJioyk++gcIBjD+dIC1Z7nXa+I4/udckwV+U6nqb/JDftaWEDyxw5FsVsfD0CAwEAAQ==",
   "verificationPublicKey" : "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjtcOcN0UIxVVnNGDvQNMYaueX4mr/xpeuLDNE3iR1e08jQQ3te8vv592v0T6q30wcpnecjVMbAto/9OhRV1dI44o5pdE752D9VRJ0NqEtFgXhPE1Bw15Mdip/8aqnH3QEG+7M48MuXPKmRYUcwA4idy3ZabG3lBAanK/ro4O18OpJkflu4fAWm4kIveLXFZJQAPZLN/JaizxHEocuHPm+kjEn3S/A2mmWwSd05fl3OSgqF890P/DYCWWvLJ1/nKOksMJENRmkPF+2kVTDF3acOCUzlgHGGY6MmFx6uXZVU/eOkQiS7bkpENNaxzuTWADt5e8IXSJJmnrvDRbnBLiYz3ZELb3gOmAjejejJSpz+mfLbBvoNIXSDHFTx8hVvcPKUw2BF3s3CHuyA++xm6oIpA5wbFbkNXsIVAtjDlEOu6mwOpSuLVCUiCe0Jw6r2yOn18WEzyV8vVCN2biVQ/3RwklEHIH10FdbCTqG+sWSrmHKzZeiYZ4xlxlfFdgdYgL2VsmOiSc9mFIJvAbLRTDtpXNBuP2aImyI1lKBy5LWjCa1BQO7Bu9Nd6ZkvqkLitKITWiC/Dzg9es6HVYLy45dzcFLhpJBr6HqDCPiedV70I0F4FoJioyk++gcIBjD+dIC1Z7nXa+I4/udckwV+U6nqb/JDftaWEDyxw5FsVsfD0CAwEAAQ=="
}
 * </pre>
 * 
 * <pre>
cat <<EOT > /tmp/verify-sample-request.txt
{
   "signatureBase" : "2017-07-19T12:50:31Z//this-as-a-sample-hash",
   "signature" : "i+Sy1howLpyJxRyeXpIEj8HnGYvQqY301zsfFGj1NE+ZhLGqKEbiDgiZPnJX+hOiQc5fHFBYkMDmK5qHH+bYzp4BTOILTbxcse5GyLIyA7w8OttOm5r7oGwIxSvEKWZ7XWnn8caUj6QOCn0xgkN+mi0Cp9Rbsv2mVKumzWY80b/P5cbWVpM1PaDD4okFMiOZ4VCFCJuJ09efCOkFR7NyeP6usc4xdSiYiLaZ7EjKXeMPy9KXPPQRWQGclham9uba4wX/YmSCKEAtoHJlE5NTUsZ7Vf3w3flrcT1NU6OKMUuM5//O65RMf1GHBLoG4I/eiEr02bW17esgvZUjjaIu8hWOMme5NE/hbNRejWD7aLcR3/cr0coo8VlI0380AE6z61tOSAvs/d3NTV0kF/YCucgys/B2HtlFMDlFix97Y4mEO4S+lB/UiJKAfJ2LK9TGKkSH7N8DwaJz30vfsMvCRbVIUhy7M34duYYVcQ/Bpxc63g2fBxqfjvXzPz51upcG33wq5LDuuJ48uXn5BS31ETyguPVEvkfr1G//Cq5hKqgA7ydPM5//4pbYG3GkCj/Y1CHWLeZxaOYfMfmqyu7s2zWxWdSSnBVaU6/e+tA6IeJ11pt/nyn4jtTw+RAPfTnruvbb7OkYBNZt80f54B/q8OnJTn3OB7CIshqDz62sUR8="
}
EOT

curl -s -H "Content-Type: text/plain" -d @/tmp/verify-sample-request.txt http://localhost:8080/verify | jq ".verificationIsSignatureValid"
true
 * </pre>
 * 
 * <pre>
<pre>
HASH=$(shasum -a 512 src/main/java/ch/furthermore/gae/proof/CreateProofServlet.java)
SIGNATURE=$(cat src/main/resources/ch/furthermore/gae/proof/CreateProofServlet.sig.json  | jq -r ".signature")
STAMP=$(cat src/main/resources/ch/furthermore/gae/proof/CreateProofServlet.sig.json  | jq -r ".signatureTimestamp")
cat <<EOT > /tmp/verify-sample-request.txt
{
  "signature": "$SIGNATURE",
  "signatureBase": "$STAMP//$HASH"
}
EOT
curl -s -H "Content-Type: text/plain" -d @/tmp/verify-sample-request.txt http://localhost:8080/verify | jq ".verificationIsSignatureValid"
 * </pre>
 * </pre>
 * 
 * <pre>
curl -s -H "Content-Type: text/plain" -d @/tmp/verify-sample-request.txt https://proof-174209.appspot.com/verify | json_pp
 * </pre>
 */
public class VerifySignatureServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		VerifySignatureRequest verifyRequest = new ObjectMapper().readValue(req.getInputStream(), VerifySignatureRequest.class);
		byte[] sig = Base64.decodeBase64(verifyRequest.getSignature());
		
		if (verifyRequest.getSignaturePublicKey() == null) {
			verifyRequest.setSignaturePublicKey(publicKey());
		}
		
		RSA pubKey = new RSA(null, Base64.decodeBase64(verifyRequest.getSignaturePublicKey()));
		
		VerifySignatureResponse verifyResp = new VerifySignatureResponse(verifyRequest);
		verifyResp.verificationIsSignatureValid = pubKey.verify(sig, IOUtils.toInputStream(verifyRequest.signatureBase, "UTF8"));
		
		calcSig(verifyResp);
		
		jsonResponse(resp, verifyResp);
	}
	
	private void calcSig(VerifySignatureResponse verification) throws IOException {
		verification.verificationPublicKey = publicKey();
		
		verification.verificationTimestamp = currentDateTime();
		verification.verificationSignatureBasePattern = "verificationTimestamp + '//' + verificationIsSignatureValid + '//' + signatureBase + '//' + signature + '//' + signaturePublicKey";
		verification.verificationSignatureBase = verification.verificationTimestamp + "//" + verification.verificationIsSignatureValid + "//" + verification.signatureBase + "//" + verification.signature + "//" + verification.signaturePublicKey;
		
		verification.verificationSignature = signature(verification.verificationSignatureBase);
	}
}
