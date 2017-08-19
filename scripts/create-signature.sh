#!/bin/bash
FILE=$1
SIG_FILE=$1.sig.json
HASH=$(shasum -a 512 $FILE)	
curl -s -H "Content-Type: text/plain" -d "$HASH" https://proof-174209.appspot.com/proofs | tee $SIG_FILE
