FROM container-registry.oracle.com/graalvm/jdk:23
COPY target/*.jar MicroService-Livraison.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "MicroService-Livraison.jar"]
