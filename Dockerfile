FROM java:alpine

EXPOSE 8081

COPY ./build/libs/*.jar .

CMD java -jar  *.jar
