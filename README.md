# AO-Server

![Maven Build](https://github.com/rusocode/ao-server/actions/workflows/maven.yml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/rusocode/ao-server/badge.svg?branch=main)](https://coveralls.io/github/rusocode/ao-server?branch=main)

<img align="right" width="100" src="logo.png"/>

Servidor de [Argentum Online](https://es.wikipedia.org/wiki/Argentum_Online) escrito en Java compatible con
el [cliente](https://github.com/gasti-jm/argentum-online-lwjgl3/tree/network-java) en Java.
> IMPORTANTE: Verificar que la rama seleccionada en el cliente sea <b><i>network-java</i></b> ya que es la que mantiene
> compatibilidad con este servidor, la rama <b><i>main</i></b> del cliente SOLO es compatible con el servidor de VB6.

El objetivo del servidor es aprovechar el multithreading y las caracteristicas que ofrece Java (APIs, librerias y
frameworks) para mejorar el rendimiento del juego.

<b>AO-Server esta basado en [AOXP-Server](https://github.com/aoxp/AOXP-Server) dado que la implementacion original dejo
de mantenerse.</b>

## ✨ Caracteristicas

- 🔌 **Networking asincrono** con [Netty](https://netty.io/) (NIO, patron Reactor)
- 💉 **Inyeccion de dependencias** con [Google Guice](https://github.com/google/guice)
- 🗺️ **290 mapas** cargados desde formato binario legacy (100×100 tiles)
- 🎭 **14 arquetipos** de personaje (Guerrero, Mago, Paladin, Clerigo, etc.)
- 🧬 **5 razas** (Humano, Elfo, Elfo Oscuro, Enano, Gnomo)
- 🗡️ **~30 tipos de objetos** (armas, armaduras, pociones, botes, instrumentos...)
- ✨ **8 efectos de hechizo** (curacion, veneno, paralisis, invisibilidad...)
- 🧪 **64 tests unitarios** con JUnit 5 + AssertJ + Mockito
- 📊 **CI/CD** con GitHub Actions y reportes de cobertura.

> [!TIP]
> Para un analisis tecnico profundo de la arquitectura de red (Netty), inyeccion de dependencias (Guice) y modelo de
> dominio, consulta la documentacion de
> la [arquitectura del servidor](https://github.com/rusocode/ao-server/blob/main/server-architecture.md).

---

## 🏗️ Arquitectura

```
aoserver (POM padre)
├── server/            → Modulo principal: red, modelo, servicios, datos
└── server-security/   → Modulo de seguridad: cifrado/descifrado de trafico
```

### Stack Tecnologico

| Componente    | Tecnologia                                     |
|---------------|------------------------------------------------|
| Lenguaje      | Java 17                                        |
| Build         | Maven (multi-modulo)                           |
| Networking    | Netty 4.1.119.Final                            |
| IoC/DI        | Google Guice 7.0.0                             |
| Logging       | SLF4J 2.0.17 + Logback 1.5.18                  |
| Configuracion | Apache Commons Configuration2                  |
| Validacion    | Hibernate Validator 9.0.0                      |
| Testing       | JUnit 5.13.4 · AssertJ 3.27.3 · Mockito 5.18.0 |
| Cobertura     | JaCoCo 0.8.13                                  |

### Pipeline de Red (Netty)

```
Inbound (Cliente → Servidor):
  Bytes crudos → Decrypter → Decoder → ClientPacketsManager

Outbound (Servidor → Cliente):
  OutgoingPacket → Encoder → Encrypter → Bytes cifrados
```

### Estructura de Paquetes

```
com.ao
├── Bootstrap          ← Punto de entrada
├── AOServer           ← Servidor Netty
├── action/            ← Executor asincrono de acciones
├── config/            ← Configuracion (INI)
├── context/           ← Contexto de aplicacion (Guice)
├── data/dao/          ← DAOs para archivos legacy
├── ioc/module/        ← 5 modulos Guice
├── model/
│   ├── character/     ← Personajes, NPCs, arquetipos, comportamientos
│   ├── inventory/     ← Sistema de inventario
│   ├── map/           ← Mapas, tiles, posiciones, ciudades
│   ├── object/        ← ~30 tipos de objetos
│   ├── spell/         ← Hechizos y efectos
│   └── user/          ← Usuarios y cuentas
├── network/           ← Paquetes entrantes (7) y salientes (22)
├── service/           ← Servicios de negocio
└── utils/             ← Utilidades (INI parser, rangos)
```

---

## 🚀 Requisitos

- **Java 17** o superior
- **Maven 3.8+**

## 📦 Compilacion

```bash
# Compilar y empaquetar
mvn -B package

# Ejecutar tests con reporte de cobertura
mvn clean test jacoco:report

# Generar JAR con dependencias
mvn -B package -pl server -am
```

## ▶️ Ejecucion

```bash
java -jar server/target/server-1.0-SNAPSHOT-jar-with-dependencies.jar
```

El servidor se inicia en `0.0.0.0:7666` por defecto (escucha en todas las interfaces de red).

---

## ⚙️ Configuracion

### `server.ini` — Configuracion principal del servidor

```ini
[INIT]
ServerIp = 127.0.0.1
StartPort = 7666
Version = 0.13.0
MaxUsers = 550
PuedeCrearPersonajes = 1
ServerSoloGMs = 0
```

### `project.properties` — Rutas y configuracion interna

Define rutas a archivos de datos (`objects.dat`, `npcs.dat`, `cities.dat`, mapas), configuracion de razas (heads y
bodies), y otros parametros internos.

---

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Ver reporte de cobertura (despues de ejecutar tests)
# Abrir: server/target/site/jacoco/index.html
```

| Capa                | Tests | Estado      |
|---------------------|-------|-------------|
| Objetos (~30 tipos) | 35    | ✅ Excelente |
| Efectos de hechizo  | 7     | ✅ Buena     |
| DAOs                | 5     | ✅ Buena     |
| Servicios           | 4     | ⚠️ Parcial  |
| Personajes          | 4     | ⚠️ Parcial  |
| Red (paquetes)      | 2     | ⚠️ Basica   |
| Mapas               | 2     | ✅ Buena     |
| Usuarios            | 1     | ⚠️ Basica   |

---

## 📋 Estado del Proyecto

### ✅ Implementado

- Modelo de dominio completo (personajes, objetos, hechizos, mapas)
- Lectura de datos legacy (archivos INI y mapas binarios)
- Infraestructura de red con Netty
- Inyeccion de dependencias con Guice
- Login (personaje existente y nuevo)
- Chat (hablar, gritar, susurrar)
- Movimiento de personajes
- CI/CD con GitHub Actions

### 🔧 En progreso / Pendiente

- Game loop (timers del juego)
- Sistema de combate PvP/PvE
- Sistema de comercio
- Crafting
- Guilds/Clanes
- Persistencia de personajes (escritura)
- Cifrado real de trafico
- Mas paquetes del protocolo AO (~43 pendientes)
- Spawning de NPCs
- Administracion en runtime (comandos GM)

---

## 📁 Datos del Juego

| Recurso     | Ubicacion                     | Formato                          |
|-------------|-------------------------------|----------------------------------|
| Mapas (290) | `resources/maps/`             | Binario (`.map`, `.inf`, `.dat`) |
| Objetos     | `resources/data/objects.dat`  | INI                              |
| NPCs        | `resources/data/npcs.dat`     | INI                              |
| Ciudades    | `resources/data/cities.dat`   | INI                              |
| Arquetipos  | `resources/data/balances.dat` | INI                              |
| Personajes  | `charfiles/`                  | INI                              |
| Servidor    | `resources/server.ini`        | INI                              |

---

## 🤝 Contribuir

1. Fork del repositorio
2. Crear branch de feature (`git checkout -b feature/mi-feature`)
3. Aplicar los cambios necesarios
4. Commit de cambios (`git commit -m 'Agregar mi feature'`)
5. Push al branch (`git push origin feature/mi-feature`)
6. Abrir un Pull Request

---

## 📄 Licencia

Este proyecto es un remake educativo/comunitario de Argentum Online.