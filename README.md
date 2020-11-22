# sequence-bank

### Run from IDE

```bash
mvn clean package
```

Run database in docker (change `<PORT>`):
```bash
docker run -d -p <PORT>:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=sequence-bank --name sequence-bank-db postgres:12
```

Now run service from your IDE.

### Run locally on Docker

```bash
mvn clean package
```

Build docker image:
```bash
docker build -t sequence-bank .
```

Create docker network:
```bash
docker network create sequence-bank-network
```

Run database in docker:
```bash
docker run -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=sequence-bank --network sequence-bank-network --name sequence-bank-db postgres:12
```

Run docker image (change `<PORT>`):
```bash
docker run -d -p <PORT>:8080 -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://sequence-bank-db:60000/sequence-bank -e KUMULUZEE_DATASOURCES0_USERNAME=postgres -e KUMULUZEE_DATASOURCES0_PASSWORD=postgres --network sequence-bank-network --name sequence-bank-service sequence-bank
```