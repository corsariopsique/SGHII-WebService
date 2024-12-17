# SGHII-WebService

El **Sistema de Gestión de Herramienta Ingeniar Inoxidables (SGHII)** es una aplicación diseñada para facilitar la gestión de inventarios de herramientas en entornos industriales. Este repositorio contiene el código del servicio web (Backend) desarrollado con **Java(Spring)**, para la gestion de inventarios de la empresa.

## Descripción general

El backend del SGHII se encarga de:
- Gestión de inventario de herramientas.
- Registro y seguimiento de préstamos y devoluciones.
- Generación de reportes de uso.
- Autenticación y control de accesos.

## Características principales

- **Arquitectura**: Basada en Spring Boot, con enfoque en modularidad.
- **Seguridad**:
  - Implementación de autenticación JWT.
  - Gestión de roles y permisos con Spring Security.
- **Persistencia**: Hibernate y MySQL para la gestión de datos.

## Tecnologías utilizadas

- **Lenguaje**: Java 17.
- **Frameworks**:
  - Spring Boot (2.7.x)
  - Spring Security
  - Spring Data JPA
  - Spring Web
- **Base de Datos**: MySQL 8.x.
- **Otros**:
    - Maven para la gestión de dependencias.
    - JWT para autenticación.

## Requisitos previos

Entorno Local:
- **JDK** (17 o superior)
- **Maven** (3.6.x o superior)
- **MySQL Server** (8.x)


## Estructura del proyecto
```bash

src/
├── main/
│   ├── java/com/ingeniarinoxidables/sghiiwebservice/
│   │   ├── auxiliares/         # Comparadores de instancias especificas
│   │   ├── config/             # Configuraciones de seguridad y aplicación
│   │   ├── controlador/        # Controladores REST
│   │   ├── DataSets/           # Recopiladores de datos para generacion de graficas
│   │   ├── DTOs/               # Clases DTO para transferencia de datos
│   │   ├── modelo/             # Clases de entidad (mapeo JPA)
│   │   ├── repositorio/        # Interfaces JPA para acceso a datos
│   │   ├── servicio/           # Lógica de negocio
│   │   └── sessionmanagement/  # Gestion de tokens JWT (autenticaciones) 
│   └── resources/ 
│       └── application.properties  # Configuraciones del entorno 
└── README.md                       # Documentación

```
## Instalación y configuración

1. **Clona el repositorio**:
   ```bash
    git clone https://github.com/corsariopsique/SGHII-WebService.git
    cd SGHII-WebService
2. **Crea variables de entorno**:
   ```bash
   ENCRYPTION_KEY={llave de encriptación de 256 bits generada en el servicio de gestion de usuarios} (Gestión tokens JWT)   
3. **Configuración base de datos**:
   - Cargar el archivo SGHII-fixed.sql alojado en el repositorio SGHII-App/data en MySQL
   ```bash
   SOURCE /ruta/al/archivo/SGHII-fixed.sql;
   ```
   - Actualizar el archivo application.properties con las credenciales de tu base de datos:
   ```bash
   spring.datasource.url=jdbc:mysql://ubicacion-MySQL:3306/sghii
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
4. **Compilar y construir el proyecto**:
   ```bash
   mvn clean install
5. **Ejecutar la aplicación**:
   ```bash
   mvn spring-boot:run
## Licencia

Este proyecto está licenciado bajo la **MIT License**. Consulta el archivo `LICENSE` para más detalles.

