# iapps demo task

Spring boot application with following  Rest APIs:
- Read XML file validate against XSD and extract information to store in DB using JAXB
- List all the extracted POJO class - Epaper

## Requirements

For building and running the application you need:

- [JDK 11]
- [Maven 3]
- [POSTGRES 13]


## Running the application locally without Docker

Before starting application you must need to build the project using following command.

Make sure you up and run Postgres first locally

```shell
mvn clean install
```

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.tatva.iapps.IappsApplication` class from your IDE.


Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Running Unit test cases

```shell
mvn test
```

## Test the APIs using curl

Fetch Epaper list based on following filters :

- PageNumber
- PageSize
- SearchText
- StartDate
- EndDate
- Sort by UploadDateTime (0 = ASC, 1 = DESC)

```shell
curl --location --request GET 'http://localhost:9090/api/epapers/?pageNumber=0&pageSize=5&sortOrderBy=1&searchText&startDate=2023-08-01 11:42:02'

```

Upload Epaper API based on valid XML file :

```shell
curl --location --request POST 'http://localhost:9090/api/epapers/upload' \
--form 'xmlFile=@"/path/to/file"'

```

## Running the application locally using Docker

Before starting application you must need to build the project using following command.

```shell
mvn clean install -DskipTests
```

```shell
docker build -t iapps .
```

```shell
docker-compose up
```