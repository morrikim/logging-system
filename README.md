# logging-system
docker ,springboot, logging, prometheus,




```dockerfile
FROM openjdk:11.0.11
LABEL maintainer="morriskim@kakao.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/logging-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} morris-springboot.jar
ENTRYPOINT ["java","-jar","morris-springboot.jar"]
```

docker image 생성
<br>
` docker build -t morris-springboot .`

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
