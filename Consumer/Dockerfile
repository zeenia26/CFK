FROM openjdk:11

WORKDIR /mnt
COPY target/CFK-1.0-SNAPSHOT.jar /mnt/CFK-1.0-SNAPSHOT.jar
#COPY client.properties /mnt/client.properties

# Command to run the Java code
CMD ["java", "-jar", "CFK-1.0-SNAPSHOT.jar", "org.example.ConsumerDemo"]
