## FERREMAS API
Proyecto para integracion de plataformas para la empresa ferremas el cual tiene como objetivo integrar apis externas e internas, para poder visualizar productos y sucursales, ademas de mostrar sus precios. Se aplico una arquitectura API REST utilizando Java Spring Boot
---
## Tecnologia utilizadas
- **Lenguaje:** Java  
- **Framework:** Spring Boot  
- **Base de datos:** MySQL  
- **Herramientas adicionales:**Postman
## Controladores REST
**MindicadorController**
Controlador que consume la api externa de [mindicador.cl] (https://mindicador.cl) para obtener indicadores económicos y realizar conversiones de moneda.

**Endpoints.**
  - `GET /indicadores/dolar` — Obtiene el valor actual del dólar.
  - `GET /indicadores/euro` — Obtiene el valor actual del euro.
  - `GET /indicadores/uf` — Obtiene el valor actual de la UF.
  - `GET /convertir?de=CLP&a=USD&cantidad=10000` — Convierte entre monedas según el valor actual.

**ProductosController**
Controlador para la gestión CRUD de productos.

**Endpoints:**

- `POST /productos` — Crear un nuevo producto.  
- `GET /productos/{id}` — Obtener un producto por su ID.  
- `GET /productos` — Listar todos los productos.  
- `PUT /productos/{id}` — Actualizar un producto existente.  
- `DELETE /productos/{id}` — Eliminar un producto por ID.  
- `GET /productos/sucursal/{sucursalId}` — Listar productos de una sucursal específica.

**SucursalController**
Controlador para la administración de sucursales.

**Endpoints:**

- `POST /sucursales` — Crear una nueva sucursal.  
- `GET /sucursales/{id}` — Obtener detalles de una sucursal.  
- `GET /sucursales` — Listar todas las sucursales.  
- `PUT /sucursales/{id}` — Actualizar una sucursal existente.  
- `DELETE /sucursales/{id}` — Eliminar una sucursal.  
- `GET /sucursales/{id}/productos` — Listar productos asociados a una sucursal.

**PrecioController**

Controlador que consume la API de [mindicador.cl](https://mindicador.cl) para obtener precios referenciales y realizar conversiones de moneda.

**Endpoints:**

- `GET /precios/dolar` — Obtener el valor actual del dólar (CLP → USD).  
- `GET /precios/euro` — Obtener el valor actual del euro (CLP → EUR).  
- `GET /precios/uf` — Obtener el valor actual de la UF.  
- `GET /precios/convertir` — Convertir un valor desde CLP a otra moneda o viceversa.

# Clonar el repositorio
git clone https://github.com/BenQuinta/IntegracionPlataformas.git

# Ingresar al proyecto
cd IntegracionPlataformas

# Instalar dependencias y compilar
mvn clean install

# Ejecutar el proyecto
mvn spring-boot:run


