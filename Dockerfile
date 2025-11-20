# Usa uma imagem leve do Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define a pasta de trabalho
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . .

# Dá permissão de execução para o facilitador do Maven
RUN chmod +x mvnw

# Constrói o projeto (gera o arquivo .jar)
RUN ./mvnw clean package -DskipTests

# Expõe a porta padrão (o Render vai usar a variável PORT automaticamente)
EXPOSE 8080

# Comando para iniciar o sistema
ENTRYPOINT ["java", "-jar", "target/portal-0.0.1-SNAPSHOT.jar"]