#!/bin/bash
FILE=$1
SIG_FILE=$1.sig.json	
echo Current hash: $(shasum -a 512 $FILE)
echo Signed hash : $(cat $SIG_FILE | jq -r ".hash")
curl -s -H "Content-Type: text/plain" -d @$SIG_FILE https://proof-174209.appspot.com/verify
