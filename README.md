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
2. Mover /WS/nuevo a /WS/tiw-p3/nuevo (El STS/Eclipse detectara que no existe y pedira que lo borres, hazlo)
3. Hacer el git add y commit en /WS/tiw-p3
4. Importar el proyecto "nuevo" con [2]

# Enlaces de interes
- Best practices: https://aulaglobal.uc3m.es/mod/page/view.php?id=2273099
- Ejemplo Spring Data: https://aulaglobal.uc3m.es/mod/page/view.php?id=2273182
- Video ejemplo REST con JPA: https://www.youtube.com/watch?v=DvzGf0cAlg4
- Postman: https://www.youtube.com/watch?v=DEt4bfSCuIs

# APIs

## MicroserviceCatalogue (http://localhost:11133/categories)
### Categories
- categories, GET: devuelve todas las categorias y OK (o NO_CONTENT)
- categories/{id}, GET: devuelve la categoria {id} y OK (o NOT_FOUND)
- categories?name, GET: devuelve la categoria con string name y OK (o NOT_FOUND)
- categories, POST: añade la categoria en RequestBody y CREATED
- categories/{id}, PUT: modifica la categoria con {id} con lo del RequestBody y OK (o NOT_FOUND)
- categories/{id}, DELETE: elimina la categoria con {id} y OK (o NOT_FOUND)

### Products
(Los que tienen *ADMIN: Con el parametro opcional ?admin=true, busca los que tienen user==NULL (borrados))
- products, GET: devuelve todos los productos y OK (o NO_CONTENT). *ADMIN
- products/{id}, GET: devuelve el producto {id} y OK (o NOT_FOUND)
- products/category/{id}, GET: devuelve los productos con categoria {id} y OK (o NO_CONTENT). *ADMIN
- products/category?category, GET: devuelve los productos con categoria ?category y OK (o NO_CONTENT). *ADMIN
- products/category, POST: devuelve los productos con categorias en RequestBody (array de enteros) y OK (o NO_CONTENT). *ADMIN
- products/seller/{seller}, GET: devuelve los productos con {seller} y OK (o NO_CONTENT)
- products/last?quantity, GET: devuelve los ultimos ?quantity [opcional, por defecto=4] productos y OK (o NO_CONTENT)
- products/{id}, DELETE: elimina (pone user=null) el producto {id} y OK (o NOT_FOUND)
- products/{id}, PUT: modifica el producto {id} con RequestBody y OK (o NOT_FOUND)
- products/stock/{id}?stock, PUT: pone al producto {id} ?stock de stock y OK (o NOT_FOUND)
- products, POST: añade el producto con RequestBody y CREATED
- products/search?name, GET: devuelve los productos con ?name y OK (o NO_CONTENT). *ADMIN
- products/search/between_prices?min&max, GET: devuelve los productos con el price entre ?min&max y OK (o NO_CONTENT)
- products/search/between_sale_prices?min&max, GET: devuelve los productos con el sale_price entre ?min&max y OK (o NO_CONTENT)
- products/search/ship_price?price, GET: devuelve los productos con el ship_price menor a ?price [opcional], si no esta el parametro, ship_price<=0 y OK (o NO_CONTENT)
- products/search/stock?stock, GET: devuelve los productos con el stock > ?stock y OK (o NO_CONTENT)