# logging-system
docker ,springboot, logging, prometheus,


### springboot
```dockerfile
FROM openjdk:11.0.11
LABEL maintainer="morriskim@kakao.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/logging-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} morris-springboot.jar
ENTRYPOINT ["java","-jar","morris-springboot.jar"]
```
prometheus를 위해 만든 프로젝트의 메트릭 정보를 얻기 위해 java actuator 와 prometheus 를 추가한다
<br>
build.gradle
```yaml
    //prometheus
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'io.micrometer:micrometer-registry-prometheus'
```

application.yml
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info, prometheus
```
이렇게 생성해두면 
`http://localhost:8080/actuator` 에 접속하면 아래와 같이 데이터들인 할 수 있다.
prometheus에 연결하기 위해서는 제알 아래 링크를 사용함
```json
{
   "_links":{
      "self":{
         "href":"http://localhost:8080/actuator",
         "templated":false
      },
      "health":{
         "href":"http://localhost:8080/actuator/health",
         "templated":false
      },
      "health-path":{
         "href":"http://localhost:8080/actuator/health/{*path}",
         "templated":true
      },
      "info":{
         "href":"http://localhost:8080/actuator/info",
         "templated":false
      },
      "prometheus":{
         "href":"http://localhost:8080/actuator/prometheus",
         "templated":false
      }
   }
}
```
docker image 생성
<br>
` docker build -t morris-springboot .`

<br>
docker iamge 이름 변경

```shell
docker image tag [기존의 이미지명]:[기존의 태그명]  [새로운 이미지명]:[새로운 태그명]`
```
<br>

docker 실행
<br>
`docker run -p 8080:8080 morriskim/morris-springboot:0.1`

### prometheus

docker pull

```shell
docker pull prom/prometheus
```

Volumn에 `prometheus.yml`를 만들어서 추가

prometheus 설정 
```yaml
# 기본적인 전역 설정
global:
  scrape_interval:     15s # 15초마다 매트릭을 수집한다. 기본은 1분이다.
  evaluation_interval: 15s # 15초마다 매트릭을 수집한다. 기본은 1분이다.
  # 'scrpae_timeout' 이라는 설정은 기본적으로 10초로 세팅되어 있다.
# Alertmanager 설정
alerting:
  alertmanagers:
  - static_configs:
    - targets:
      # - alertmanager:9093
# 규칙을 처음 한번 로딩하고 'evaluation_interval'설정에 따라 정기적으로 규칙을 평가한다.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"
# 매트릭을 수집할 엔드포인트를 설정. 여기서는 Prometheus 서버 자신을 가리키는 설정을 했다.
scrape_configs:
  # 이 설정에서 수집한 타임시리즈에 'job=<job_name>'으로 잡의 이름을 설정한다.
  - job_name: 'spring-logging'
    # 'metrics_path'라는 설정의 기본 값은 '/metrics'이고
    # 'scheme'라는 설정의 기본 값은 'http'이다.
    metrics_path: '/actuator/prometheus'
    static_configs:
    - targets: ['localhost:8080']
```
참고사항
<br>
localhost로 해두었지만, docker prometheus에서 호스트를 실행하기 위해서는 아래의 설명대로 host.docker.internal로 설정 해야한다.
<br>

**docker에서 host.docker.internal은 특별한 DNS name으로 사용되며 docker를 실행하는 host를 가리킨다. 개발용으로만 사용해야 하며, Docker Desktop(Mac) 외부의 환경에서는 동작하지 않는다.**

<br>
promethues 시작 하기

```shell
docker run -p 9090:9090 -v /Users/daeyunkim/github_morriskim/p8s/config/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheu
```


docker build option 
<br>
`Usage:  docker build [OPTIONS] PATH | URL | -`
<br>
```shell
Options:
      --add-host list           Add a custom host-to-IP mapping (host:ip)
      --build-arg list          Set build-time variables
      --cache-from strings      Images to consider as cache sources
      --disable-content-trust   Skip image verification (default true)
  -f, --file string             Name of the Dockerfile (Default is 'PATH/Dockerfile')
      --iidfile string          Write the image ID to the file
      --isolation string        Container isolation technology
      --label list              Set metadata for an image
      --network string          Set the networking mode for the RUN instructions during build (default "default")
      --no-cache                Do not use cache when building the image
  -o, --output stringArray      Output destination (format: type=local,dest=path)
      --platform string         Set platform if server is multi-platform capable
      --progress string         Set type of progress output (auto, plain, tty). Use plain to show container output (default "auto")
      --pull                    Always attempt to pull a newer version of the image
  -q, --quiet                   Suppress the build output and print image ID on success
      --secret stringArray      Secret file to expose to the build (only if BuildKit enabled): id=mysecret,src=/local/secret
      --ssh stringArray         SSH agent socket or keys to expose to the build (only if BuildKit enabled) (format: default|<id>[=<socket>|<key>[,<key>]])
  -t, --tag list                Name and optionally a tag in the 'name:tag' format
      --target string           Set the target build stage to build.
``` 


참고 자료
https://www.tutorialworks.com/spring-boot-prometheus-micrometer/
