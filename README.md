# Demo de um microserviço com spring boot 

O objetivo é um microserviço que conforme informado a cidade, dependendo da temperatura atual nessa cidade, seja sugerido algumas músicas por estilo musical exemplo (POP, ROCK).

 * Utilização do Feing para requisição Http
 * Utilização do Resilience4j para garantir a resiliência, tolerância a falhas e responsivo;
 * Utilização do Redis para cache
 * Utilização do micrometer com prometheus para coletar métricas do microserviço
 * Utilização de docker e docker compose para iniciar container com redis, prometheus, grafana e jaeger

 
## Pré-requisitos para rodar o microserviço
 * docker (Para uso do Redis, Prometheus, Grafana e Jaeger)
 * java 11 (Nessário para rodar a aplicação em sua maquina local, para rodar em um docker não é necessário)
 * maven 3.6.3 (Nessário para rodar a aplicação em sua maquina local, para rodar em um docker não é necessário)
 
## Opção de execução 1 
Acessar a raiz do projeto usando o prompt e executar  (tudo ocorrendo você deve conseguir acessar )

 ```
 mvn spring-boot:run
 ```
Ainda na raiz do projeto executar o docker compose para subir os serviços de redis, prometheus e grafana
 
 ```
 docker-compose up
 ```
 
Tudo ocorrendo bem, você deve conseguir acessar o link http://localhost:8080/swagger-ui-custom.html 


## Opção de execução 2
Para rodar tudo dentro do docker execute o comando a baixo na raiz do projeto:

```
docker-compose -f docker-compose.yml -f docker-compose-app.yml up
```
 
## Usando o microserviço

Se tudo estiver ocorrido bem basta abrir no browser a url a baixo, será possivel visualizar as endpoints disponíveis.

```
http://localhost:8080/swagger-ui-custom.html
```

Chamada com postman que irá retornar lista de albuns interessantes de acordo com a temperatura da cidade informada

```
curl --location --request GET 'http://localhost:8080/musicbycurrentweather?q=sao%20paulo'
```

Chamada com postman obter apenas a temperatura

```
curl --location --request GET 'http://localhost:8080/currentweather?q=sao%20paulo'
```

Chamada com para simular um fallback(Essa URL de fallback irá simular de maneira aleatoria uma chamada que irá demorar a responder e/ou chamada com falha)

```
curl --location --request GET 'http://localhost:8080/currentweatherfallback?q=sao%20paulo'
```

Ao chamar a url de fallback algumas vezes um circuit breaker será aberto, o que poderá ser acompanhado pelo grafana no dashborad de circuit breaker, no endereço http://localhost:3000


Na pasta docker, no arquivo app.env e em resource no arquivo application-dev.yml , existem as seguintes variáveis:
* APP_SPOTIFY_KEY=e359cda92a394e08a39237346311471b #Key do spotify
* APP_SPOTIFY_SECRET=c3f4976f68154e0a864160733cf7cdcc #Secret do spotfy
* APP_OPENWEATHER_APPID=d1b7f11761729386d59886df8fb09e8c #AppId do openweather

* APP_REDIS_HOST=redis #endereço do host do redis
* APP_REDIS_PORT=6379 #porta do host do redis

Essas chaves foram geradas com email temporários e login falsos, e podem parar de funcionar, caso você tenha algum problema, pode ser que essas chaves não funcione mais, você pode obter novas chaves para usar em [Spotify](https://developer.spotify.com/documentation/web-api/quick-start/) e [Openweather](https://home.openweathermap.org/users/sign_up)


## Documentações de referência para bibliotecas utilizadas

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#production-ready)

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Circuit Breaker](https://spring.io/guides/gs/circuit-breaker/)
* [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)
* [resilience4j](https://github.com/resilience4j/resilience4j)
* [Caching](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#boot-features-caching)
* [Spotify Web Api Java](https://github.com/thelinmichael/spotify-web-api-java)
* [Spring Doc](https://springdoc.org/)
* [Micrometer](http://micrometer.io/docs/registry/prometheus#_installing)
* [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
