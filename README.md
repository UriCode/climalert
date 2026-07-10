# 🌦️ Climalert - Sistema de Alertas Meteorológicas
Repositorio donde se encuentra las distintas resoluciones que se hacen del TP
---

## 🏗️ Estructura del Proyecto

El proyecto está organizado de forma multi-módulo en Maven:

*   **`common-lib`**: Contiene las entidades puras del dominio (`MedicionClimatica`, `Alerta`), la lógica de negocio (`ReglaAlerta`, `ReglaAlertaCritica`) y los puertos (interfaces `ProveedorClima`, `NotificadorAlerta`, `RepositorioMediciones`).

*   **`alerta-service`**: Contiene la infraestructura del sistema (Spring Boot, Scheduler para automatización, RestTemplate para consumir WeatherAPI y JavaMailSender para el envío de mails).

---

## 🛠️ Requisitos Previos

*   **Java 21**
*   **Maven 3.9 o superior**

---

## ⚙️ Configuración del Entorno

Para evitar subir credenciales privadas al repositorio, el sistema utiliza un perfil de desarrollo (`dev`) que busca un archivo local de propiedades.

1.  Copia el archivo de ejemplo para crear tu configuración de desarrollo local:
    ```bash
    cp alerta-service/src/main/resources/application-dev.properties.example alerta-service/src/main/resources/application-dev.properties
    ```
2.  Regístrate de forma gratuita en [WeatherAPI](https://www.weatherapi.com/) para obtener una clave API.
3.  Abre el archivo `application-dev.properties` creado y coloca tu clave:
    ```properties
    weatherapi.key=TU_CLAVE_API_REAL
    ```

---

## 🚀 Compilación e Instalación

Para compilar todo el reactor de módulos y registrar las dependencias internas en el repositorio local de Maven, ejecuta en la raíz del proyecto:

```bash
mvn clean install
```

---

## 🏃 Ejecución de la Aplicación

Para iniciar el planificador de tareas y comenzar el monitoreo climático, ejecuta:

```bash
mvn -pl alerta-service spring-boot:run
```

El planificador ejecutará dos tareas automáticas:
*   **Cada 5 minutos**: Consulta el clima de la ubicación configurada y lo registra en el repositorio.
*   **Cada 1 minuto**: Evalúa la última medición. Si es crítica (y no ha sido procesada previamente), dispara el envío de alertas imprimiendo el correo en consola (o enviándolo si hay SMTP real configurado).

---

## 🧪 Pruebas Unitarias

Para correr las pruebas unitarias que validan la lógica de dominio y el coordinador de servicios, ejecuta:

```bash
mvn test
```
