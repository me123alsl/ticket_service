# Docker 실행 환경 설정

<!-- TOC -->
* [Docker 실행 환경 설정](#docker---)
  * [Mysql](#mysql)
  * [Redis](#redis)
  * [Kafka](#kafka)
    * [Kafka Basic Command and Test](#kafka-basic-command-and-test)
<!-- TOC -->

## Mysql

> Mysql 실행
``` sh
 docker pull mysql
 docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name mysql mysql
 docker exec -it mysql bash
 mysql -u root -p
 # 1234
 create database ticket_example;
```

## Redis

### Redis 실행
``` sh
docker pull redis
docker run -d -p 6379:6379 --name redis redis
```

### Redis 초기화
``` sh
docker exec -it redis redis-cli flushall
```
## Kafka

```bash
# docker 실행
docker-compose up -d
```

```yaml
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka:2.12-2.5.0
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```

###  토픽 생성
``` bash
docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic ticketing-create
```
### 프로듀서 실행
``` bash
docker exec -it kafka kafka-console-consumer.sh --topic ticketing-create --bootstrap-server localhost:9092 --key-deserializer "org.apache.kafka.common.serialization.StringDeserializer" --value-deserializer "org.apache.kafka.common.serialization.LongDeserializer"
```

---

### Kafka Basic Command and Test

#### 토픽 생성
``` bash
docker exec -it kafka kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic ticketing
```

#### 프로듀서 실행
``` bash
docker exec -it kafka kafka-console-producer.sh --broker-list localhost:9092 --topic ticketing
```


#### 컨슈머 실행
``` bash
docker exec -it kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ticketing --from-beginning
```

#### 토픽 삭제
```bash
docker exec -it kafka kafka-topics.sh --delete --zookeeper zookeeper:2181 --topic coupon_create
```


