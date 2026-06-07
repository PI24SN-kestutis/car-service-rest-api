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
