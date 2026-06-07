# Automobilių serviso valdymo sistema (Car Service Management System)

## Projekto aprašymas

Tai RESTful Web Service projektas, sukurtas naudojant Spring Boot. Sistema skirta klientų, automobilių ir jų aptarnavimo istorijos valdymui.

Projektas įgyvendina REST architektūros principus, HATEOAS navigaciją, OpenAPI/Swagger dokumentaciją, išorinių servisų integraciją bei automatizuotą testavimą.

## Naudotos technologijos

* Java 21
* Spring Boot 4.0.6
* Maven
* MySQL
* H2 Database (testams)
* Spring Data JPA
* Spring HATEOAS
* Swagger / OpenAPI
* RestClient
* Cucumber
* JUnit 5
* Mockito

## BDD testavimas naudojant Cucumber

Projektui naudojamas BDD (Behavior Driven Development) metodas su Cucumber sistema.

Testavimo scenarijai aprašomi natūralia lietuvių kalba naudojant Gherkin sintaksę.

Feature failai saugomi kataloge:

```text
src/test/resources/features
```

Step Definitions klasės saugomos:

```text
src/test/java/.../cucumber
```

### Įgyvendinti scenarijai

#### Kliento sukūrimas

Tikrinama, ar sistema gali sukurti naują klientą.

#### Automobilio sukūrimas

Tikrinama, ar klientui galima priskirti naują automobilį.

#### Serviso įrašo sukūrimas

Tikrinama, ar automobiliui galima sukurti naują aptarnavimo įrašą.

### Testų konfigūracija

Cucumber testai vykdomi naudojant atskirą Spring profilį:

```java
@ActiveProfiles("test")
```

Testavimo metu naudojama H2 atmintinė duomenų bazė:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
```

Todėl testai neturi įtakos pagrindinei MySQL duomenų bazei.

### Testų paleidimas

Visi testai paleidžiami komanda:

```bash
mvn test
```

Arba naudojant IntelliJ IDEA:

```text
Run → RunCucumberTest
```

### Testavimo rezultatai

Šiuo metu sėkmingai vykdomi šie Cucumber scenarijai:

* Customer Creation
* Car Creation
* Service Record Creation

Visi scenarijai vykdomi naudojant Spring Boot kontekstą ir H2 testinę duomenų bazę.

## Duomenų modelis

### Customer

Klientas sistemoje.

Laukai:

- id
- firstName
- lastName
- email
- phoneNumber

Ryšiai:

- Vienas klientas gali turėti daug automobilių.

### Car

Automobilis priklausantis klientui.

Laukai:

- id
- brand
- model
- productionYear
- vin

Ryšiai:

- Priklauso vienam klientui.
- Gali turėti daug serviso įrašų.

### ServiceRecord

Automobilio aptarnavimo įrašas.

Laukai:

- id
- serviceType
- description
- serviceDate
- cost

Ryšiai:

- Priklauso vienam automobiliui.

## Klaidų apdorojimas

Projektas naudoja centralizuotą klaidų apdorojimą naudojant:

- GlobalExceptionHandler
- ProblemDetail

Pavyzdys:

```json
{
  "type": "about:blank",
  "title": "Customer not found",
  "status": 404,
  "detail": "Customer with id 5 not found"
}
```

## HATEOAS

Projektas naudoja Spring HATEOAS.

Atsakymuose pateikiamos navigacinės nuorodos.

Pavyzdys:

```json
{
  "id": 1,
  "firstName": "Jonas",
  "_links": {
    "self": {
      "href": "/api/customers/1"
    }
  }
}
```

## Meteo.lt integracija

Projektas naudoja RestClient ir Meteo.lt API.

Galimybės:

- Dabartinių orų gavimas.
- DTO transformavimas.
- Aktualios prognozės pateikimas klientui.

## Projekto struktūra

```text
src
├── main
│   ├── java
│   │   └── lt.viko.eif.kskrebe.carservice
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       ├── model
│   │       ├── repository
│   │       └── service
│   └── resources
│       └── application.properties
│
└── test
    ├── java
    │   └── lt.viko.eif.kskrebe.carservice
    │       ├── controller
    │       ├── cucumber
    │       └── service
    │
    └── resources
        ├── application-test.properties
        └── features
```

## Duomenų bazės schema

Sistemoje naudojamos trys pagrindinės lentelės:

### customers

Saugo klientų informaciją.

| Laukas       | Aprašymas             |
| ------------ | --------------------- |
| id           | Pirminis raktas       |
| first_name   | Kliento vardas        |
| last_name    | Kliento pavardė       |
| email        | El. paštas (unikalus) |
| phone_number | Telefono numeris      |

### cars

Saugo klientų automobilius.

| Laukas          | Aprašymas                |
| --------------- | ------------------------ |
| id              | Pirminis raktas          |
| brand           | Automobilio markė        |
| model           | Modelis                  |
| production_year | Pagaminimo metai         |
| vin             | VIN numeris (unikalus)   |
| customer_id     | Kliento identifikatorius |

### service_records

Saugo automobilių aptarnavimo istoriją.

| Laukas       | Aprašymas                    |
| ------------ | ---------------------------- |
| id           | Pirminis raktas              |
| service_type | Aptarnavimo tipas            |
| description  | Darbų aprašymas              |
| service_date | Aptarnavimo data             |
| cost         | Paslaugos kaina              |
| car_id       | Automobilio identifikatorius |

## Sistemos ryšiai

```text
Customer
    │
    └───< Car
                │
                └───< ServiceRecord
```

Vienas klientas gali turėti daug automobilių.

Vienas automobilis gali turėti daug serviso įrašų.

## HATEOAS realizacija

Projektas naudoja Spring HATEOAS biblioteką.

Pavyzdinis atsakymas:

```json
{
  "id": 1,
  "firstName": "Jonas",
  "lastName": "Jonaitis",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/customers/1"
    }
  }
}
```

## Klaidų apdorojimas

Visos klaidos apdorojamos centralizuotai naudojant GlobalExceptionHandler.

Naudojamas RFC 9457 Problem Details formatas.

Pavyzdys:

```json
{
  "detail": "Klientas nerastas su id: 5",
  "instance": "/api/customers/5",
  "status": 404,
  "title": "Resource not found",
  "timestamp": "2026-06-07T10:00:00Z"
}
```

## Testavimo rezultatai

Įgyvendinti testai:

### Unit testai

* CustomerServiceTest
* CarServiceTest
* ServiceRecordServiceTest

### Integraciniai testai

* CustomerControllerIntegrationTest

### BDD testai (Cucumber)

* Kliento sukūrimas
* Automobilio sukūrimas
* Serviso įrašo sukūrimas

Visi testai vykdomi naudojant H2 testinę duomenų bazę.

## Ateities plėtros galimybės

* Vartotojų autentifikacija ir autorizacija (Spring Security).
* Automobilių techninės apžiūros priminimai.
* El. pašto pranešimai klientams.
* Serviso rezervacijų sistema.
* PDF sąskaitų generavimas.
* Automobilių remonto statistika ir ataskaitos.

## Projekto paleidimas

```bash
mvn spring-boot:run

Swagger UI:
http://localhost:8080/swagger-ui/index.html
```
## Testų paleidimas
```bash
mvn clean test
```
## JavaDoc generavimas
```bash
mvn javadoc:javadoc
```
## CACHE
```bash
Meteo.lt užklausoms naudojamas Spring Cache mechanizmas.
Pirmoji užklausa kreipiasi į išorinį API, pakartotinė tokia pati užklausa grąžinama iš cache.
```
## POSTMAN
- kolekcija saugoma
```bash
postman/car-service-rest-api.postman_collection.json
```



