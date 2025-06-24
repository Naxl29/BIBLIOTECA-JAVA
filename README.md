# CRUD-JAVA - Biblioteca

Este es un proyecto de aplicación **CRUD (Crear, Leer, Actualizar, Eliminar)** en **Java**, que permite gestionar una **biblioteca** con funcionalidades para manejar **libros**, **personas** y **préstamos**. La aplicación está construida utilizando **Java 1.8**, con **MySQL** como sistema de base de datos y una interfaz gráfica de usuario construida con **AWT** y **Swing**.

## CARACTERÍSTICAS

- **Gestión de Libros**: Permite agregar, ver, actualizar y eliminar libros en la biblioteca.
- **Gestión de Personas**: Permite agregar, ver y eliminar personas en el sistema.
- **Gestión de Préstamos**: Permite gestionar los préstamos entre personas y libros.
- **Interfaz Gráfica de Usuario (GUI)**: Construida utilizando **AWT** y **Swing** para facilitar la interacción con el sistema.
- **Base de Datos MySQL**: Utiliza **JDBC** para interactuar con la base de datos MySQL, donde se almacenan los libros, personas y préstamos.
- **Operaciones CRUD**: Implementa las operaciones básicas para gestionar la información de libros, personas y préstamos.

## TECNOLOGÍAS USADAS

- **Java 1.8**: Lenguaje de programación utilizado para el desarrollo del proyecto.
- **AWT y Swing**: Bibliotecas para la creación de interfaces gráficas de usuario (GUI).
- **MySQL**: Sistema de gestión de bases de datos utilizado para almacenar la información.
- **JDBC (Java Database Connectivity)**: Conector para interactuar con la base de datos MySQL desde Java.
- **Eclipse IDE**: Entorno de desarrollo utilizado para construir y ejecutar el proyecto.

## REQUISITOS PREVIOS

Antes de ejecutar este proyecto, asegúrate de tener lo siguiente instalado en tu máquina:

### 1. **Java Development Kit (JDK)**

Este proyecto está desarrollado con **Java 1.8**. Necesitarás tener **JDK 1.8** o superior instalado en tu máquina.

- **Descargar JDK 1.8**: [Descargar JDK](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)

Para verificar si Java está instalado, ejecuta el siguiente comando en la terminal:

java -version

##INSTALACIÓN Y CONFIGURACIÓN
Primero, clona el repositorio en tu máquina local con el siguiente comando de Git:

bash
git clone https://github.com/Naxl29/CRUD-JAVA.git

Segundo, Importar el Proyecto en Eclipse

PASO A PASO

Abre Eclipse.

En Eclipse, selecciona File > Import.

En el asistente de importación, selecciona Existing Projects into Workspace y haz clic en Next.

En Select root directory, busca la carpeta donde clonaste el repositorio, selecciona la carpeta del proyecto y haz clic en Finish.

Tercero, Configurar la Base de Datos MySQL

crear la base de datos , esto se encuentra en el archivo biblioteca_java.sql
