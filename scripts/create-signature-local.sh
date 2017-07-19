#!/bin/bash
FILE=$1
SIG_FILE=$1.sig.json
HASH=$(shasum -a 512 $FILE)	
curl -s -H "Content-Type: text/plain" -d "$HASH" http://localhost:8080/proofs | jq ". | {signature,signatureTimestamp}" > $SIG_FILE

echo Signature date of $FILE is $(cat $SIG_FILE | jq -r ".signatureTimestamp")
