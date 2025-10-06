# Despliegue en WildFly

## Configuración Realizada

Este proyecto ha sido configurado para desplegarse en WildFly. Los cambios realizados incluyen:

1. **Packaging cambiado a WAR**: El proyecto ahora genera un archivo `.war` en lugar de `.jar`
2. **Dependencias ajustadas**: Las dependencias de Tomcat, JPA y Hibernate están marcadas como `provided` ya que WildFly las incluye
3. **Configuración JPA**: Se creó `persistence.xml` en `META-INF/` para la configuración de JPA en WildFly
4. **Configuración específica de WildFly**: Se crearon archivos de configuración en `WEB-INF/`

## Archivos Creados/Modificados

- `pom.xml`: Packaging WAR y dependencias provided
- `src/main/resources/META-INF/persistence.xml`: Configuración JPA para WildFly
- `src/main/resources/application-wildfly.properties`: Configuración específica para WildFly
- `src/main/webapp/WEB-INF/jboss-web.xml`: Contexto de la aplicación
- `src/main/webapp/WEB-INF/jboss-deployment-structure.xml`: Configuración de módulos

## Requisitos Previos en WildFly

1. **Datasource**: Configurar un datasource llamado `ExampleDS` o cambiar el nombre en `persistence.xml`
2. **Base de Datos**: El `persistence.xml` está configurado para H2 (datasource por defecto de WildFly)

### Configurar MySQL en WildFly (opcional)

Si quieres usar MySQL en lugar de H2:

1. Instalar el driver JDBC de MySQL como módulo en WildFly
2. Crear un datasource de MySQL
3. Cambiar en `persistence.xml`:
   ```xml
   <jta-data-source>java:jboss/datasources/MySQLDS</jta-data-source>
   <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
   ```

## Pasos para el Despliegue

1. **Compilar el proyecto**:
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Copiar el WAR a WildFly**:
   ```bash
   cp target/CustomerService-0.0.1-SNAPSHOT.war $WILDFLY_HOME/standalone/deployments/
   ```

3. **Iniciar WildFly**:
   ```bash
   cd $WILDFLY_HOME/bin
   ./standalone.sh
   ```

4. **Acceder a la aplicación**:
   - URL: `http://localhost:8080/customerservice/`
   - Swagger UI: `http://localhost:8080/customerservice/swagger-ui.html`
   - API Docs: `http://localhost:8080/customerservice/api-docs`

## Perfiles de Configuración

- Para desarrollo local (Spring Boot standalone): usar el perfil por defecto
- Para WildFly: usar el perfil `wildfly`:
  ```
  --spring.profiles.active=wildfly
  ```

## Notas Importantes

1. **ActiveMQ**: La configuración actual asume que ActiveMQ está disponible externamente. Para usar el sistema de mensajería de WildFly, será necesario modificar la configuración.

2. **Logging**: La configuración de logging se ajustó para evitar conflictos con WildFly.

3. **Datasource**: Por defecto usa `ExampleDS` que viene preconfigurado en WildFly con H2.

## Troubleshooting

Si encuentras problemas:

1. Verifica que el datasource esté configurado en WildFly
2. Revisa los logs de WildFly en `standalone/log/server.log`
3. Asegúrate de que no hay conflictos de puerto (WildFly usa 8080 por defecto)