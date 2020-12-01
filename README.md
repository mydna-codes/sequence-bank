# sequence-bank

### Library
```xml
<dependency>
    <groupId>codes.mydna</groupId>
    <artifactId>sequence-bank-lib</artifactId>
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
    --name sequence-bank-service 
    mydnacodes/sequence-bank
```

