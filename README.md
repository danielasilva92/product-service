# product-service (fakeStoreAPI)

En av två backend-tjänster i CloudStore. Den här tjänsten ansvarar för produkterna och hämtar produktdata från ett externt FakeStore-API, sparar dem i sin egen databas och tillhandahåller dem till resten av systemet.

## Vad tjänsten gör

- Hämtar produkter från ett externt FakeStore-API och sparar dem i databasen
- Tillhandahåller produkterna via ett öppet GET-anrop
- Skyddar känsliga endpoints med JWT, med samma delade hemlighet som user-order-service
- Har en egen databas, skild från user-order-service

## Teknik

- Java 17, Spring Boot
- Spring Security och JWT (jjwt)
- Spring Data JPA / Hibernate
- MySQL (RDS i produktion, MySQL i Docker lokalt, H2 i tester)
- Docker, GitHub Actions och AWS CodePipeline, driftsatt på AWS Elastic Beanstalk

## Köra lokalt

Tjänsten körs enklast tillsammans med resten av systemet via docker-compose i huvud-repot:
docker-compose up --build

Tjänsten lyssnar då på port 5000.

## Miljövariabler

Tjänsten använder Spring-profiler. Lokalt (dev) pekar den mot MySQL i Docker, i produktion (prod) mot RDS. Profilen väljs med `SPRING_PROFILES_ACTIVE`.

| Variabel | Beskrivning |
|----------|-------------|
| `SPRING_PROFILES_ACTIVE` | `dev` lokalt, `prod` i AWS |
| `DB_URL` | Databasens adress (prod) |
| `DB_USERNAME` | Databasanvändare |
| `DB_PASSWORD` | Databaslösenord |
| `JWT_SECRET` | Hemlig nyckel för JWT (måste vara samma som i user-order-service) |
| `FAKESTORE_URL` | Adressen till det externa FakeStore-API:t som produkterna hämtas från |

## API-endpoints

| Metod | Sökväg | Beskrivning | Kräver token |
|-------|--------|-------------|--------------|
| GET | `/products` | Lista alla produkter | Nej |
| POST | `/products/fetch` | Hämta produkter från det externa API:t och spara dem | Ja |

## Tester

Enhetstester med JUnit. Testerna använder en H2-databas i minnet så att de kan köras i pipelinen utan en riktig databas.

## CI/CD

Den här tjänsten har två flöden. GitHub Actions bygger, testar och pushar en Docker-image till DockerHub vid varje push. AWS CodePipeline hämtar koden från GitHub, bygger den med CodeBuild enligt `buildspec.yaml` och deployar automatiskt till Elastic Beanstalk.

## Drift

Tjänsten körs på AWS Elastic Beanstalk och använder en MySQL-databas i AWS RDS.
