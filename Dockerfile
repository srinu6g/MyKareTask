FROM public.ecr.aws/docker/library/openjdk:17
COPY .mvn/ .mvn
COPY mvnw  ./
COPY pom.xml ./
RUN chmod +x mvnw
RUN sed -i 's/\r$//' mvnw
RUN ./mvnw dependency:resolve
COPY src ./src
EXPOSE 8989
CMD ["./mvnw", "spring-boot:run"]