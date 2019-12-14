# Asignación de puertos

## Para proyectos de Web dinámicos:
Se utilizará el puerto 8080

## Para los microservicios:
Se utilizarán los puertos 111XX, donde XX serán digitos arbitrarios decididos por el grupo exceptuando los valores '00' y '01'

# Instrucciones para usar git
Supongamos que el Workspace esta en /WS

## [1] Clone
1. Hacer git clone ... para que cree /WS/tiw-p3

## [2] Importar un proyecto de /WS/tiw-p3
No hay que importar /WS/tiw-p3 para que solo modifiquemos los proyectos individuales:
1. Desde STS/Eclipse: Import->Existing project... (escoger el proyecto que querais, no el tiw-p3) y quitar check "Copy project into workspace" (para simplemente enlazarlo)
2. Ahora en el package explorer esta el proyecto importado, los cambios se pueden commitear en /WS/tiw-p3 (por haberlo enlazado)

## [3] Nuevo proyecto:
1. Desde STS/Eclipse: New->Spring stater proyect, le llamaremos /WS/nuevo (Hacer cosas para que se pueda hacer el primer commit)
2. Mover /WS/nuevo a /WS/tiw-p3/nuevo (El STS/Eclipse detectará que no existe y pedirá que lo borres, hazlo)
3. Dejar solo la linea target/ en el .gitignore (para poder importarlo en otros workspaces)
4. Hacer el git add y commit en /WS/tiw-p3
5. Importar el proyecto "nuevo" con [2]

# Enlaces de interés
- Best practices: https://aulaglobal.uc3m.es/mod/page/view.php?id=2273099
- Ejemplo Spring Data: https://aulaglobal.uc3m.es/mod/page/view.php?id=2273182
- Video ejemplo REST con JPA: https://www.youtube.com/watch?v=DvzGf0cAlg4
- Postman: https://www.youtube.com/watch?v=DEt4bfSCuIs

# APIs
https://drive.google.com/open?id=1_xXgZQKtqF5qzKI3xSfw1PI-9OcwPOvBklswxmjbU18