#!/bin/bash
FILE=$1
HASH=$(shasum -a 512 $FILE)

SIG_FILE=$1.sig.json	
SIGNATURE=$(cat $SIG_FILE  | jq -r ".signature")
STAMP=$(cat $SIG_FILE | jq -r ".signatureTimestamp")

REQUEST_FILE=$(mktemp)
cat <<EOT > $REQUEST_FILE
{
  "signature": "$SIGNATURE",
  "signatureBase": "$STAMP//$HASH"
}
EOT
SIGNATURE_VALID=$(curl -s -H "Content-Type: text/plain" -d @$REQUEST_FILE https://proof-174209.appspot.com/verify | jq ".verificationIsSignatureValid")
rm $REQUEST_FILE

echo Signature date of $FILE is valid: $SIGNATURE_VALID
