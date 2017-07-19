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
 * <pre>
 * $ cat <<EOT >> /tmp/verify-sample-request.txt
 * {
 *    "signatureBase" : "2017-07-19T11:52:35Z//halli",
 *    "signature" : "dPsQ+tXs0sFJwS8Tk0ogL6JG+XfhEAH1Iv59j+LRjjjrbGGL+IDxW++YMxE2EtdbCs8uXirDl2nY+9d7f257Xpym9Eh7QfNu66jTDYoeet507bgrRe2FEHDqrU463/op2yDQkUUbI7NipQWCFggyXkRHwXKSeUSgWvH3yo6cjeq+T1/fR3d6/jGio6gblCefFpxGOq0OcmTWUNQTsYgqekNz+XGeF1Z24XTOAWJ6MUvg56wr173AEKtKoWx3MpW1gdBFAup1e3A6tQaIiWKqeYFAvLlBDO/82jRhbpxH8QjnWP0OD5rqbja/Wig84AhUayY6crmb9UGdx5PV5UNShXIBDhxmhs3nNc7tQJuaZkkq09whodYvcyRKu3tL2lUJY1RvkYmgFW9l174RXXEyPE7g17l/Im7sP2GtiC5oORsysvm7eb1KQY5bF7rgi1irg8bg3+wqO46WVy0Bqv8j6Ogi2ozhAx3LhCnHXTKaJ5JUKluQeidfBtNeVvLzCd1CGpM4LFp0+8umvBiNGlhWomTkNwTmSvYA6LOika5OM1fd1I75XyZBS5YOUIqmD+XJtlNYBFOIo1/RlR9vFJDPCWbhkajqwZJDC2lKJ2bHKrLqNcEWcngon1yJr9i2utpPybdPmJ66hwxx6CZkkrfzx1px1wByCjsJpy59y+OhmNA=",
 *    "publicKey" : "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjtcOcN0UIxVVnNGDvQNMYaueX4mr/xpeuLDNE3iR1e08jQQ3te8vv592v0T6q30wcpnecjVMbAto/9OhRV1dI44o5pdE752D9VRJ0NqEtFgXhPE1Bw15Mdip/8aqnH3QEG+7M48MuXPKmRYUcwA4idy3ZabG3lBAanK/ro4O18OpJkflu4fAWm4kIveLXFZJQAPZLN/JaizxHEocuHPm+kjEn3S/A2mmWwSd05fl3OSgqF890P/DYCWWvLJ1/nKOksMJENRmkPF+2kVTDF3acOCUzlgHGGY6MmFx6uXZVU/eOkQiS7bkpENNaxzuTWADt5e8IXSJJmnrvDRbnBLiYz3ZELb3gOmAjejejJSpz+mfLbBvoNIXSDHFTx8hVvcPKUw2BF3s3CHuyA++xm6oIpA5wbFbkNXsIVAtjDlEOu6mwOpSuLVCUiCe0Jw6r2yOn18WEzyV8vVCN2biVQ/3RwklEHIH10FdbCTqG+sWSrmHKzZeiYZ4xlxlfFdgdYgL2VsmOiSc9mFIJvAbLRTDtpXNBuP2aImyI1lKBy5LWjCa1BQO7Bu9Nd6ZkvqkLitKITWiC/Dzg9es6HVYLy45dzcFLhpJBr6HqDCPiedV70I0F4FoJioyk++gcIBjD+dIC1Z7nXa+I4/udckwV+U6nqb/JDftaWEDyxw5FsVsfD0CAwEAAQ=="
 * }
 * EOT
 * 
 * $ curl -s -H "Content-Type: text/plain" -d @/tmp/verify-sample-request.txt http://localhost:8080/verify | json_pp
 * {
 *    "signatureBase" : "2017-07-19T11:52:35Z//halli",
 *    "publicKey" : "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjtcOcN0UIxVVnNGDvQNMYaueX4mr/xpeuLDNE3iR1e08jQQ3te8vv592v0T6q30wcpnecjVMbAto/9OhRV1dI44o5pdE752D9VRJ0NqEtFgXhPE1Bw15Mdip/8aqnH3QEG+7M48MuXPKmRYUcwA4idy3ZabG3lBAanK/ro4O18OpJkflu4fAWm4kIveLXFZJQAPZLN/JaizxHEocuHPm+kjEn3S/A2mmWwSd05fl3OSgqF890P/DYCWWvLJ1/nKOksMJENRmkPF+2kVTDF3acOCUzlgHGGY6MmFx6uXZVU/eOkQiS7bkpENNaxzuTWADt5e8IXSJJmnrvDRbnBLiYz3ZELb3gOmAjejejJSpz+mfLbBvoNIXSDHFTx8hVvcPKUw2BF3s3CHuyA++xm6oIpA5wbFbkNXsIVAtjDlEOu6mwOpSuLVCUiCe0Jw6r2yOn18WEzyV8vVCN2biVQ/3RwklEHIH10FdbCTqG+sWSrmHKzZeiYZ4xlxlfFdgdYgL2VsmOiSc9mFIJvAbLRTDtpXNBuP2aImyI1lKBy5LWjCa1BQO7Bu9Nd6ZkvqkLitKITWiC/Dzg9es6HVYLy45dzcFLhpJBr6HqDCPiedV70I0F4FoJioyk++gcIBjD+dIC1Z7nXa+I4/udckwV+U6nqb/JDftaWEDyxw5FsVsfD0CAwEAAQ==",
 *    "signature" : "dPsQ+tXs0sFJwS8Tk0ogL6JG+XfhEAH1Iv59j+LRjjjrbGGL+IDxW++YMxE2EtdbCs8uXirDl2nY+9d7f257Xpym9Eh7QfNu66jTDYoeet507bgrRe2FEHDqrU463/op2yDQkUUbI7NipQWCFggyXkRHwXKSeUSgWvH3yo6cjeq+T1/fR3d6/jGio6gblCefFpxGOq0OcmTWUNQTsYgqekNz+XGeF1Z24XTOAWJ6MUvg56wr173AEKtKoWx3MpW1gdBFAup1e3A6tQaIiWKqeYFAvLlBDO/82jRhbpxH8QjnWP0OD5rqbja/Wig84AhUayY6crmb9UGdx5PV5UNShXIBDhxmhs3nNc7tQJuaZkkq09whodYvcyRKu3tL2lUJY1RvkYmgFW9l174RXXEyPE7g17l/Im7sP2GtiC5oORsysvm7eb1KQY5bF7rgi1irg8bg3+wqO46WVy0Bqv8j6Ogi2ozhAx3LhCnHXTKaJ5JUKluQeidfBtNeVvLzCd1CGpM4LFp0+8umvBiNGlhWomTkNwTmSvYA6LOika5OM1fd1I75XyZBS5YOUIqmD+XJtlNYBFOIo1/RlR9vFJDPCWbhkajqwZJDC2lKJ2bHKrLqNcEWcngon1yJr9i2utpPybdPmJ66hwxx6CZkkrfzx1px1wByCjsJpy59y+OhmNA=",
 *    "valid" : true
 * } 
 * </pre>
 * 
 * <pre>
 * curl -s -H "Content-Type: text/plain" -d @/tmp/verify-sample-request.txt https://proof-174209.appspot.com/verify | json_pp
 * </pre>
 */
public class VerifySignatureServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonProof verifyRequest = new ObjectMapper().readValue(req.getInputStream(), JsonProof.class);
		byte[] sig = Base64.decodeBase64(verifyRequest.getSignature());
		RSA pubKey = new RSA(null, Base64.decodeBase64(verifyRequest.getPublicKey()));
		
		JsonVerifiedProof verifyResp = new JsonVerifiedProof(verifyRequest);
		verifyResp.valid = pubKey.verify(sig, IOUtils.toInputStream(verifyRequest.signatureBase, "UTF8"));
		
		jsonResponse(resp, verifyResp);
	}
}
