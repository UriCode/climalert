    # Imagen oficial de Java 21 de Microsoft
    FROM mcr.microsoft.com/devcontainers/java:21
  
    # Actualizacion del gestor de paquetes e instalacion de Graphviz y Maven
    RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
        && apt-get -y install --no-install-recommends graphviz maven
