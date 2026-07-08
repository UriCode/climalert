# Desiciones de diseño

## Arquitectura y modularizacion
Para esta primera iteracion decido hacer una separacion en 2 modulos principales
* commons-lib: donde voy a plasmar el dominio
* alerta-service: donde va a estar la infraestructura
La idea es aplciar el principio de inversion de dependencias, para que  la logica de negocio no dependa de la infraestructura

## Reglas de la alerta
La regla de evaluacion de clima del servicio va a ser una Interfaz ReglaAlerta y aplicare el patron strategy ReglaAlertaCritica
De esta manera separo la regla de evaluacion del clima del servicio que va a coordinar las notificaciones. Ademas me mantengo abierto a nuevas reglas de alerta (Por ejemplo definir una alerta amarilla de lluvia)

## Integracion con WeatherAPI
Patron Adapter: Usando una interfaz ProveedorClima y el adaptador WeatherApiAdapter que la consuma
Con esto me separo de tener que conocer la estructura de la API externa, el adaptador va a traducir las peticiones a un objeto del dominio (MedicionClimatica)

## Persistencia
voy a persistir en memoria con un repository para esta primer iteracion

## Notificaciones
Con la finalidad de poder probar el flujo completo en terminal y sin necesidad de configurar un servidor SMTP voy a definir una interfaz NotificadorAlerta y un adaptador ConsoleMailAdapter y un JavaMailSenderAdapter

