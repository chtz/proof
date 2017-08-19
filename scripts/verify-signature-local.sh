#!/bin/bash
FILE=$1
SIG_FILE=$1.sig.json	
echo Current hash: $(shasum -a 512 $FILE)
echo Signed hash : $(cat temp/bar.txt.sig.json | jq -r ".hash")
curl -s -H "Content-Type: text/plain" -d @$SIG_FILE http://localhost:8080/verify
