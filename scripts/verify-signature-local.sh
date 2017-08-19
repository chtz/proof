#!/bin/bash
FILE=$1
SIG_FILE=$1.sig.json	
echo Current hash: $(shasum -a 512 $FILE)
echo Signed hash: $(cat $SIG_FILE | jq -r ".hash")
echo Current public key: $(curl -s http://localhost:8080/ | jq -r ".publicKey")
echo Signature public key: $(cat $SIG_FILE | jq -r ".publicKey")
curl -s -H "Content-Type: text/plain" -d @$SIG_FILE http://localhost:8080/verify
