# sequence-bank

[![Build Status](https://jenkins.din-cloud.com/buildStatus/icon?job=mydnacodes%2Fsequence-bank%2Fmaster&subject=CI/CD)](https://jenkins.din-cloud.com/job/mydnacodes/job/sequence-bank/job/master/)

### Library
```xml
<dependency>
    <groupId>codes.mydna</groupId>
    <artifactId>sequence-bank-lib-v1</artifactId>
    <version>${sequence-bank.version}</version>
</dependency>
```

### Docker

*Note: This service requires DB.*

Pull docker image:
```bash
docker pull mydnacodes/sequence-bank
```

Run docker image:
```bash
docker run -d -p <PORT>:8080 
    -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://<DB_HOST>:<DB_PORT>/sequence-bank 
    -e KUMULUZEE_DATASOURCES0_USERNAME=<DB_USERNAME> 
    -e KUMULUZEE_DATASOURCES0_PASSWORD=<DB_PASSWORD> 
    -e KEYCLOAK_REALM=<KEYCLOAK_REALM_NAME>
    -e KEYCLOAK_CLIENTID=<KEYCLOAK_CLIENT_ID>
    -e KEYCLOAK_AUTHSERVERURL=<KEYCLOAK_SERVER_URL>
    -e KEYCLOAK_AUTH_CLIENTSECRET=<KEYCLOAK_CLIENT_SECRET>
    --name sequence-bank-service 
    mydnacodes/sequence-bank
```

