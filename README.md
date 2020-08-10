#Meets And Beers

###TODOs
- Implementar login utilizando JWTs
- Segurizar y segmentar endpoints asignandoles los roles necesarios para ser usados (Usando anotaciones provistas por Spring)
- Agregar tests unitarios a cada entidad en cada capa (JUnit y Mockito)
- Terminar integracion con Docker
- Agregar job en el Jenkins dentro del docker que realize un build de la aplicacion cada vez que se haya un commit al repo
- Agregar un job que realize el empaquetado
- Probar el job que corra los tests (mvn clean test). Antes no andaba porque el jenkins no tenia instalado java 11
- Agregar Swagger para la documentacion de cada endpoint