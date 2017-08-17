#!/bin/bash
FILE=$1
HASH=$(shasum -a 512 $FILE)	
curl -s -H "Content-Type: text/plain" -d "$HASH" http://localhost:8080/hashes | json_pp
