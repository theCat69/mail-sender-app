# Generate files for TLS

## Dev self-sign

### Generate self-sign certificates :
```sh 
openssl req -x509 -newkey rsa:4096 -sha256 -days 3650 -nodes \ 
            -keyout localhost.key -out localhost.crt -subj "/CN=localhost" \
            -addext "subjectAltName=DNS:localhost,DNS:*.localhost,IP:127.0.0.1"
```

### Convert those file to pkcs12 format
```sh
openssl pkcs12 -export -in localhost.crt -inkey localhost.key \ 
               -out localhost.p12 -name localhost \
               -CAfile $JAVA_HOME\lib\security\cacerts \
               -caname root
```
When prompt for password choose "changeit"

### Convert pkcs12 to JKS
```sh
keytool -importkeystore \ 
        -deststorepass changeit -destkeypass changeit -destkeystore localhost.jks \ 
        -srckeystore localhost.p12 -srcstoretype PKCS12 -srcstorepass changeit \
        -alias localhost
```

## Prod

### Generate certificates :
- this is out of this scope
- use letsencrypt if you don't know what to use


### Convert those file to pkcs12 format
```sh
openssl pkcs12 -export -in [cert_file] -inkey [key_file] \
               -out [p12store_name].p12 -name [entry_name_in_store] \
               -CAfile ca.crt -caname root
```

### Convert pkcs12 to JKS
```sh
keytool -importkeystore \
        -deststorepass [changeit] -destkeypass [changeit] -destkeystore [keystore_name].jks \
        -srckeystore [p12store_name].p12 -srcstoretype PKCS12 -srcstorepass [some-password] \
        -alias [some-alias]
```
