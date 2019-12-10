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

## MicroserviceCatalogue (http://localhost:11133)
### Categories
- /categories, GET: devuelve todas las categorias y OK (o NO_CONTENT)
- /categories/{id}, GET: devuelve la categoria {id} y OK (o NOT_FOUND)
- /categories?name, GET: devuelve la categoria con string name y OK (o NOT_FOUND)
- /categories, POST: añade la categoria en RequestBody y CREATED
- /categories/{id}, PUT: modifica la categoria con {id} con lo del RequestBody y OK (o NOT_FOUND)
- /categories/{id}, DELETE: elimina la categoria con {id} y OK (o NOT_FOUND)

### Products
(Los que tienen *ADMIN: Con el parametro opcional ?admin=true, busca los que tienen user==NULL (borrados))
- /products, GET: devuelve todos los productos y OK (o NO_CONTENT). *ADMIN
- /products/{id}, GET: devuelve el producto {id} y OK (o NOT_FOUND)
- /products/category/{id}, GET: devuelve los productos con categoria {id} y OK (o NO_CONTENT). *ADMIN
- /products/category?category, GET: devuelve los productos con categoria ?category y OK (o NO_CONTENT). *ADMIN
- /products/category, POST: devuelve los productos con categorias en RequestBody (array de enteros) y OK (o NO_CONTENT). *ADMIN
- /products/seller/{seller}, GET: devuelve los productos con {seller} y OK (o NO_CONTENT)
- /products/last?quantity, GET: devuelve los ultimos ?quantity [opcional, por defecto=4] productos y OK (o NO_CONTENT)
- /products/{id}, DELETE: elimina (pone user=null) el producto {id} y OK (o NOT_FOUND)
- /products/{id}, PUT: modifica el producto {id} con RequestBody y OK (o NOT_FOUND)
- /products/stock/{id}?stock, PUT: pone al producto {id} ?stock de stock y OK (o NOT_FOUND)
- /products, POST: añade el producto con RequestBody y CREATED
- /products/search?name, GET: devuelve los productos con ?name y OK (o NO_CONTENT). *ADMIN
- /products/search/between_prices?min&max, GET: devuelve los productos con el price entre ?min&max y OK (o NO_CONTENT)
- /products/search/between_sale_prices?min&max, GET: devuelve los productos con el sale_price entre ?min&max y OK (o NO_CONTENT)
- /products/search/ship_price?price, GET: devuelve los productos con el ship_price menor a ?price [opcional], si no esta el parametro, ship_price<=0 y OK (o NO_CONTENT)
- /products/search/stock?stock, GET: devuelve los productos con el stock > ?stock y OK (o NO_CONTENT)

## MicroserviceUser (http://localhost:11144)
### Categories
- /users, GET: devuelve todas los usuarios y OK (o NO_CONTENT)
- /users/{email}, GET: devuelve el usuario con el {email} y OK (o NOT_FOUND)
- /users?type, GET: devuelve los usuarios de un tipo(0 = compradores, 1 = vendedores, 2 = administradores) y OK (o NOT_FOUND)
- /users, POST: añade el usuario en RequestBody y CREATED
- /users/{id}, PUT: modifica el usuario con {email} con lo del RequestBody(el email de requestBody tiene que ser igual a {email}) y OK (o NOT_FOUND)
- /users/{id}, DELETE: elimina el usuario con {email} y OK (o NOT_FOUND)

## MicroserviceChat (http://localhost:11188)
### Categories
- /messages, GET: devuelve todas los mensajes guardados en MongoDB y OK (o NO_CONTENT)
- /messages?email, GET: devuelve todos los mensajes destinados al usuario que pertenece el email y OK (o NO_CONTENT)
- /messages{message}, POST: crea un mensaje con campos de {sender, receiver y message} y CREATED
- /messages/{id}, DELETE: borra el mensaje con {id} y OK (o NOT_FOUND)

## MicroserviceBank (http://localhost:11199)
### Categories
- /transactions, GET: devuelve todas las transacciones guardadas en MongoDB y OK (o NO_CONTENT)
