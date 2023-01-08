# prueba_java_nrs_group
Prueba de java avanzado del grupo nrs

Repositorio de github de la prueba de java avanzado del grupo nrs.

El proyecto gestormensajes trata sobre la gestion de mensajes por parte de un consumidor y un productor en donde ambos utilizan kafka como broker de mensajeria. El productor envia un mensaje json al con consumidor con propiedades como fecha actual, email y mensaje. El consumidor se encarga de consumir el mensaje json enviado por el productor e imprime ese mensaje json con la propiedad fecha actual actualizada con la fecha actual del sistema. Los mensajes enviados por el productor empiezan a enviarse al consumidor despues de una pausa de treinta segundos justo cuando se inicia el microservicio gestormensajes, despues los mensajes son enviados por el productor al consumidor con un intervalo de cinco segundos entre cada mensaje.

Requerimientos para ejecutar el proyecto de gestion de mensajes

1. Tener instalado docker en el computador local
2. Tener instalado docker-compose en el computador local
3. Poder acceder a docker en el computador local por medio de la linea de comandos
4. Poder acceder a docker-compose en el computador local por medio de la linea de comandos

Pasos para ejecutar el proyecto de gestion de mensajes

1. Descargar el proyecto al computador local
2. Abrir una consola de comandos
3. Ingresar a la carpeta gestormensajes del proyecto prueba_java_nrs_group en la consola de comandos
4. Ejecutar el comando "sudo chmod 777 mvnw" el cual se encarga de asignar permisos al ejecutable de maven mvnw
4. Ejecutar el archivo Dockerfile por medio del comando "docker-compose up" en la consola de comandos

Pasos para ver los mensajes de log de la ejecucion del microservicio gestormensajes

1. Abrir una consola de comandos
2. Ingresar a la carpeta gestormensajes del proyecto prueba_java_nrs_group en la consola de comandos
3. Ejecutar el comando "docker ps" para ver los diferentes contenedores de docker que se estan ejecutando
4. Ejecutar el comando "docker logs gestormensajes" para ver los mensajes de log que ha generado el microservicio gestormensajes hasta el momento
