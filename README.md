# proof

```
curl -s -H "Content-Type: text/plain" -d "($ shasum -a 512 ./temp/test.txt)" https://proof-174209.appspot.com/proofs > temp/test.txt.sig.json
```

```
cat temp/test.txt.sig.json | jq ". | {signature,signatureTimestamp}"
```

```
{
  "signature": "WW01oKT0a4pgblbKM46SRYIV8CN3xQhwwSNTDs0msvrrKK50bdUY3iQEAkpcCmQTpJj2Z07RpJqIMswNQDHJZiv0vJATRc0p1U+x2sYLQm9un/zFoDgKxvw6HZ5AcysuBdwD3gSdeAWWgw8ngqrCSAopvp1WQ2VtsT7tHSDNAEcuC6sQpcaQMm0p96EI+kTEry33n6ldi4kI0zd0k6iJ/EJDDlEMeFJFWD1LjvEcE8MKeK+qU8PsSYAaCFvqptFk+5shFb9Hwsc1N++wFfv5hGDCfPMB7B5xB+pOjq+8VXtUNc8XCRKoBVUZn5JCiQ37etS6TLOF0V1gYVaNO1WEBry8cbdPZTkRztFIk7r55toLFSBaXKC/yr9UoSvA2DZggI8WPtx91oOMmpl1duT+2dfHe0i/RUL+JXN6yFQvloJP7uab+tQp/B3djPyYBMI9M93MZcGRftTgqZyxLMpc1jiGFfCyDk/tqrmSw8ZU3xZB1behmEBkJ3Q7rCtEHUqIAM2Q9WnbodpVuL5fWnHSvsfpSy6hNmrcnZy9JOXiqD1fDi49rxQcpglCj+oF8NrAwNair16ERfaK+CCEjPIMvO+iSYpWXQWmGZ8CAglzrT7OZvKBoZkL/Fv7+VrG+uTFzpLFGbgcDS1cy8CwSbzxp4HIjQD6eb9qJ0Vnn1q6IGI=",
  "signatureTimestamp": "2017-07-19T15:07:01Z"
}
```

```
SIGNATURE="WW01oKT0a4pgblbKM46SRYIV8CN3xQhwwSNTDs0msvrrKK50bdUY3iQEAkpcCmQTpJj2Z07RpJqIMswNQDHJZiv0vJATRc0p1U+x2sYLQm9un/zFoDgKxvw6HZ5AcysuBdwD3gSdeAWWgw8ngqrCSAopvp1WQ2VtsT7tHSDNAEcuC6sQpcaQMm0p96EI+kTEry33n6ldi4kI0zd0k6iJ/EJDDlEMeFJFWD1LjvEcE8MKeK+qU8PsSYAaCFvqptFk+5shFb9Hwsc1N++wFfv5hGDCfPMB7B5xB+pOjq+8VXtUNc8XCRKoBVUZn5JCiQ37etS6TLOF0V1gYVaNO1WEBry8cbdPZTkRztFIk7r55toLFSBaXKC/yr9UoSvA2DZggI8WPtx91oOMmpl1duT+2dfHe0i/RUL+JXN6yFQvloJP7uab+tQp/B3djPyYBMI9M93MZcGRftTgqZyxLMpc1jiGFfCyDk/tqrmSw8ZU3xZB1behmEBkJ3Q7rCtEHUqIAM2Q9WnbodpVuL5fWnHSvsfpSy6hNmrcnZy9JOXiqD1fDi49rxQcpglCj+oF8NrAwNair16ERfaK+CCEjPIMvO+iSYpWXQWmGZ8CAglzrT7OZvKBoZkL/Fv7+VrG+uTFzpLFGbgcDS1cy8CwSbzxp4HIjQD6eb9qJ0Vnn1q6IGI="
STAMP="2017-07-19T15:07:01Z"
REQUEST_FILE=/tmp/sample-request.json
cat <<EOT > $REQUEST_FILE
{
  "signature": "$SIGNATURE",
  "signatureBase": "$STAMP//($ shasum -a 512 ./temp/test.txt)"
}
EOT
curl -s -H "Content-Type: text/plain" -d @$REQUEST_FILE https://proof-174209.appspot.com/verify > temp/test.txt.sig.verify.json
```

```
cat temp/test.txt.sig.verify.json | jq ".verificationIsSignatureValid"
```

```
true
```
