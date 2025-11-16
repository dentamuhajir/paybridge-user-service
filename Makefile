APP_NAME=paybridge
JAR_FILE=target/$(APP_NAME)-0.0.1-SNAPSHOT.jar

.PHONY: run build clean test docker-up docker-down

run:
	mvn spring-boot:run

build:
	mvn clean package -DskipTests

test:
	mvn test

clean:
	mvn clean

docker-up:
	docker compose up --build -d

docker-down:
	docker compose down
