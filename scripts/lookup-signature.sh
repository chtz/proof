#!/bin/bash
FILE=$1
HASH=$(shasum -a 512 $FILE)	
curl -s -H "Content-Type: text/plain" -d "$HASH" https://proof-174209.appspot.com/hashes
