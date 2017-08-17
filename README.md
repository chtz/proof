# proof

```
$ ./scripts/create-signature.sh temp/bar.txt 
Signature date of temp/bar.txt is 2017-08-17T21:13:11Z
```

```
$ cat temp/bar.txt.sig.json 
{
  "signature": "SevJJv5xJza5p8YzNgP7i2fRdHBXHhOMP6qvWVVdUjTAu6ehPgsW9LMrtYE+OOmHYK918FldAQ5aAaWg2bpBWkDgoW9zf/JNVq9VLZ4c39VLOPP4ZA6dYljPqxzDs3WiaJBuS2++r3yMNG6odrHXi6pG9HtN/3rKqGoFHoU9B/651uO73PmDzmtvitcNxKvQ/FrKNhowFCEi1nq+rnjsVypftvINJgQbcJ3a/L5+SmUUP5HxrXOTuYttvfX0f1pnxQqcrk9DGUKAH9RRcPAkO1ilcNWpA4Qod1kUV5aHooX5Ek+9DwAIDYnJ3XNrtw7o9q0N9ZEfh+3usnjk3ASI43H8RU9IffY7edd4qwhVp0p+2hy1RFGeqLNGoc0gZLnuQS9kueTJ96Cz+SZVjla3MohCwCqpjFZDwNV+fnx99MV9YnCWo+8GsAnZc5lJj2uhIXl8NM2GtbH19bb2yEhtJfYmEqOOzF7RjqyWk6Bx3p7HOtLocjBeFHk1IhIg9zvyfgd4Rs5nPVoa/8FbbBRuNQs2jxwKq4KuQdFfFzWjN/DyrLEmd4XpaLxU7RQp8EfmmwDY1piCXOVx4xSJF5GzyEy2FVxXHf62TbAnCF/mydw5UVKz6Ll1/wcNgnD1/N6/cFx7TxENCmxQbhWshGXwGGx1EI9qKipcI10xEsVigRU=",
  "signatureTimestamp": "2017-08-17T21:13:11Z"
}
```

```
$ ./scripts/verify-signature.sh temp/bar.txt
Signature date of temp/bar.txt is valid: true
```

```
$ rm temp/bar.txt.sig.json
```

```
$ ./scripts/lookup-signature.sh temp/bar.txt
[
   {
      "signatureTimestamp" : "2017-08-17T21:13:11Z",
      "signatureBase" : "2017-08-17T21:13:11Z//cc06808cbbee0510331aa97974132e8dc296aeb795be229d064bae784b0a87a5cf4281d82e8c99271b75db2148f08a026c1a60ed9cabdb8cac6d24242dac4063  temp/bar.txt",
      "signaturePublicKey" : "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAlGFS/T2EPpHsEngyr2HmLXi/I1zNJhPREX2jykkjv2EH54G5QW9t2XGpJ/oL1DwVc+oKX0runbIZPnhozxC/1rcz3w18uq4S1/79rhI79UMuM2FdlhWSuq85WOtk7pGLmrQqdNA2/MYTNzbhLyp0oae0HNW3eqVCuvDsMA+3jT1Grb1Oc5YcG/flZNRTA+0vnuraF6WE7SC3UfpRJ6tWhtJ7xPxm46VmuEbPfT2ri0EUvxbd0+XDFMOnaDTlgojS8bsFrVGgZC6VO7zigw5JcO7Mqrolv5yl0Wn/G7AH4l3FfQld3HjypKSTJ9kVUrW4ygQnK3z0FWodaEZl5oiTeAMREPf+vzV/hAHCrnjDHAZRxbMu7kzHBFMdft3Z6IZb950Elf84KWnrfITjpy8jHeyAKbF4lPyTxTs+a1jLWNBT/hE0D2C9p/kdDtsfB64LnyErCgZU1M7ZuKJBOZ9UkNjvwsSfSzBMEYkIMPXRFJtpDrseBpIenXY84HvDQSrW8Gc3QKx2Xy05T/b8rlBztwLyUtUGdNTgDBcqUdnLuPiQb554qTq2zT1jXgEjcTvwhtwvYrAEr6QTaIM2nDJ6TVI0/h9ZAZEX1FWvQncnHpYAAlelmbqf9RtNr/2RpHzJQ+NRgSm2EoI12KkIj0/g/d+vg1+HncHhI84V4DtFipkCAwEAAQ==",
      "signatureBasePattern" : "signatureTimestamp + '//' + signatureHash",
      "signature" : "SevJJv5xJza5p8YzNgP7i2fRdHBXHhOMP6qvWVVdUjTAu6ehPgsW9LMrtYE+OOmHYK918FldAQ5aAaWg2bpBWkDgoW9zf/JNVq9VLZ4c39VLOPP4ZA6dYljPqxzDs3WiaJBuS2++r3yMNG6odrHXi6pG9HtN/3rKqGoFHoU9B/651uO73PmDzmtvitcNxKvQ/FrKNhowFCEi1nq+rnjsVypftvINJgQbcJ3a/L5+SmUUP5HxrXOTuYttvfX0f1pnxQqcrk9DGUKAH9RRcPAkO1ilcNWpA4Qod1kUV5aHooX5Ek+9DwAIDYnJ3XNrtw7o9q0N9ZEfh+3usnjk3ASI43H8RU9IffY7edd4qwhVp0p+2hy1RFGeqLNGoc0gZLnuQS9kueTJ96Cz+SZVjla3MohCwCqpjFZDwNV+fnx99MV9YnCWo+8GsAnZc5lJj2uhIXl8NM2GtbH19bb2yEhtJfYmEqOOzF7RjqyWk6Bx3p7HOtLocjBeFHk1IhIg9zvyfgd4Rs5nPVoa/8FbbBRuNQs2jxwKq4KuQdFfFzWjN/DyrLEmd4XpaLxU7RQp8EfmmwDY1piCXOVx4xSJF5GzyEy2FVxXHf62TbAnCF/mydw5UVKz6Ll1/wcNgnD1/N6/cFx7TxENCmxQbhWshGXwGGx1EI9qKipcI10xEsVigRU=",
      "signatureHash" : "cc06808cbbee0510331aa97974132e8dc296aeb795be229d064bae784b0a87a5cf4281d82e8c99271b75db2148f08a026c1a60ed9cabdb8cac6d24242dac4063  temp/bar.txt"
   }
]
```
