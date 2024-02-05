# App-Candidates

Bienvenido a App-Candidates, un proyecto desarrollado en Spring Boot con Maven y ejecutable mediante Docker Compose. Esta aplicación gestiona candidatos y utiliza una base de datos MySQL. Sigue estos pasos para ejecutar la aplicación de manera local.

## Requisitos Previos

Asegúrate de tener instalados los siguientes componentes antes de comenzar:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Maven](https://maven.apache.org/download.cgi)
- [Postman](https://www.postman.com/downloads/)
- Git (opcional, pero recomendado para clonar el repositorio)

## Clonar el Repositorio

Si no has clonado el repositorio de App-Candidates, puedes hacerlo ejecutando el siguiente comando:

```bash
git clone https://github.com/ArturoDiaz02/app-candidate.git
cd app-candidate
```
## Colección de Postman

Para facilitar la interacción con la API de App-Candidates, hemos creado una colección de Postman que incluye diversos escenarios y casos de uso. Puedes importar esta colección en tu cliente de Postman siguiendo estos pasos:

1. Descarga el archivo JSON de la colección de Postman desde el siguiente enlace:

   [Colección de Postman JSON](https://drive.google.com/file/d/1Mv_x8ZRjgtw5DTZt8hRXxqjC2aCPnV4Y/view?usp=sharing) --
   [WorkSpace de Postman](https://www.postman.com/nortquery/workspace/seek/overview)
2. Abre Postman y haz clic en la pestaña "Import" en la parte superior izquierda.

3. Selecciona "Paste Raw Text" y pega el contenido copiado.

4. Haz clic en "Import" para agregar la colección a tu entorno de Postman.

### Uso de la Colección

Una vez que hayas importado la colección, podrás acceder a diferentes escenarios y casos de uso relacionados con la gestión de candidatos en App-Candidates. Asegúrate de haber ejecutado la aplicación localmente o mediante Docker Compose antes de probar los endpoints.

Recuerda configurar el token de autenticación en la sección de variables para realizar operaciones que requieran autorización; ademas, recuerda configurar la url.

¡Explora y prueba la colección para conocer más sobre la API de App-Candidates!

## Configuración

Antes de ejecutar la aplicación, verifica la configuración en el archivo `docker-compose.yml`. Asegúrate de que las variables de entorno y los puertos sean los adecuados para tu entorno.

```yaml
# docker-compose.yml

...

environment:
   SPRING_PROFILES_ACTIVE: prod
   SPRING_R2DBC_URL: r2dbc:mysql://db:3306/app-db
   SPRING_FLYWAY_URL: jdbc:mysql://db:3306/app-db
ports:
    - "3306:3306"  # Puerto para la base de datos MySQL
    - "8080:8080"  # Puerto para la aplicación Spring Boot

...
```

## Produccion

La aplicacion esta desplegada en Azure, por lo que puedes acceder a la aplicacion mediante las siguientes url:

```bash
ssh seek@52.186.181.94
```
La contraseña es: `Seek@1234567`

El codido fuente esta en la carpeta `/app/app-candidate/`

### Consumir API de Produccion

Puedes consumir la API de produccion mediante la siguiente url sin necesidad de ejecutar la aplicacion localmente:

```
http://52.186.181.94:8080/
```


## Ejecución con Docker Compose

Para ejecutar la aplicación con Docker Compose, utiliza el siguiente comando en el directorio principal del proyecto:

```bash
docker-compose up --build
```

Este comando descargará las imágenes necesarias, construirá la aplicación y levantará los contenedores de MySQL y Spring Boot.

La aplicación Spring Boot se ejecutará en el perfil de producción (`prod`), utilizando una base de datos MySQL en un contenedor y se expondrá en el puerto 8080.

## Ejecución sin Docker (Perfil Dev)

Si prefieres ejecutar la aplicación sin Docker, asegúrate de tener una instancia de MySQL ejecutándose localmente con las siguientes credenciales:

- Usuario: root
- Contraseña: root
- Nombre de la base de datos: app-db

Además, para ejecutar la aplicación Spring Boot sin Docker, utiliza el siguiente comando en el directorio principal del proyecto:

```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

Esto iniciará la aplicación en el perfil de desarrollo (`dev`), utilizando la base de datos local de MySQL y se expondrá en el puerto 8080.

## Swagger UI

Una vez que la aplicación esté en ejecución, puedes explorar la API a través de Swagger UI ingresando a la siguiente URL en tu navegador:

```
http://localhost:8080/swagger-doc/v3/swagger-ui.html
```
Además, la documentación de la API está disponible en formato JSON mediante el siguiente enlace:

```
http://localhost:8080/swagger-doc/v3/api-docs
```

O puedes utilizar las url de produccion para ver la documentacion sin necesidad de ejecutar la aplicacion localmente, las url son las siguientes:

```
http://52.186.181.94:8080/swagger-doc/v3/swagger-ui.html
http://52.186.181.94:8080/swagger-doc/v3/api-docs
```

## Detener la Aplicación

Para detener la aplicación y los contenedores, utiliza el siguiente comando:

```bash
docker-compose down
```

Este comando apagará y eliminará los contenedores, pero mantendrá los datos de la base de datos.

## Conclusión

¡Felicidades! Has configurado y ejecutado con éxito la aplicación App-Candidates en tu entorno local. Si tienes alguna pregunta o encuentras problemas, revisa la documentación del proyecto o comunícate con el equipo de desarrollo. ¡Disfruta explorando App-Candidates!