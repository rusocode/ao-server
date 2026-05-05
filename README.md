# AO-Server

![Maven Build](https://github.com/rusocode/ao-server/actions/workflows/maven.yml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/rusocode/ao-server/badge.svg?branch=main)](https://coveralls.io/github/rusocode/ao-server?branch=main)

<img align="right" width="100" src="logo.png"/>

Servidor de [Argentum Online](https://es.wikipedia.org/wiki/Argentum_Online) escrito en Java compatible con el [cliente](https://github.com/gasti-jm/argentum-online-lwjgl3/tree/network-java) en Java.
> IMPORTANTE: Verifica que la rama seleccionada en el cliente sea <b><i>network-java</i></b> ya que esta es la que mantiene compatibilidad con este servidor, la rama <b><i>main</i></b> del cliente mantiene compatibilidad con el servidor de VB6.

El objetivo del servidor es aprovechar el multithreading y las características que ofrece Java (APIs, librerías y frameworks) para mejorar el rendimiento del juego.

<b>AO-Server está basado en [AOXP-Server](https://github.com/aoxp/AOXP-Server) dado que la implementación original dejó de mantenerse.</b>

## ✨ Características

- 🔌 **Networking asíncrono** con [Netty](https://netty.io/) (NIO, patrón Reactor)
- 💉 **Inyección de dependencias** con [Google Guice](https://github.com/google/guice)
- 🗺️ **290 mapas** cargados desde formato binario legacy (100×100 tiles)
- 🎭 **14 arquetipos** de personaje (Guerrero, Mago, Paladín, Clérigo, etc.)
- 🧬 **5 razas** (Humano, Elfo, Elfo Oscuro, Enano, Gnomo)
- 🗡️ **~30 tipos de objetos** (armas, armaduras, pociones, botes, instrumentos...)
- ✨ **8 efectos de hechizo** (curación, veneno, parálisis, invisibilidad...)
- 🧪 **64 tests unitarios** con JUnit 5 + AssertJ + Mockito
- 📊 **CI/CD** con GitHub Actions y reportes de cobertura.

> [!TIP]
> Para un análisis técnico profundo de la arquitectura de red (Netty), inyección de dependencias (Guice) y modelo de dominio, consulta la documentación de la [arquitectura del servidor](https://github.com/rusocode/ao-server/blob/main/server-architecture.md).

---

## 🏗️ Arquitectura

```
aoserver (POM padre)
├── server/            → Módulo principal: red, modelo, servicios, datos
└── server-security/   → Módulo de seguridad: cifrado/descifrado de tráfico
```

### Stack Tecnológico

| Componente | Tecnología |
|-----------|-----------|
| Lenguaje | Java 17 |
| Build | Maven (multi-módulo) |
| Networking | Netty 4.1.119.Final |
| IoC/DI | Google Guice 7.0.0 |
| Logging | SLF4J 2.0.17 + Logback 1.5.18 |
| Configuración | Apache Commons Configuration2 |
| Validación | Hibernate Validator 9.0.0 |
| Testing | JUnit 5.13.4 · AssertJ 3.27.3 · Mockito 5.18.0 |
| Cobertura | JaCoCo 0.8.13 |

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
├── action/            ← Executor asíncrono de acciones
├── config/            ← Configuración (INI)
├── context/           ← Contexto de aplicación (Guice)
├── data/dao/          ← DAOs para archivos legacy
├── ioc/module/        ← 5 módulos Guice
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

## 📦 Compilación

```bash
# Compilar y empaquetar
mvn -B package

# Ejecutar tests con reporte de cobertura
mvn clean test jacoco:report

# Generar JAR con dependencias
mvn -B package -pl server -am
```

## ▶️ Ejecución

```bash
java -jar server/target/server-1.0-SNAPSHOT-jar-with-dependencies.jar
```

El servidor se inicia en `127.0.0.1:7666` por defecto.

---

## ⚙️ Configuración

### `server.ini` — Configuración principal del servidor

```ini
[INIT]
ServerIp = 127.0.0.1
StartPort = 7666
Version = 0.13.0
MaxUsers = 550
PuedeCrearPersonajes = 1
ServerSoloGMs = 0
```

### `project.properties` — Rutas y configuración interna

Define rutas a archivos de datos (`objects.dat`, `npcs.dat`, `cities.dat`, mapas), configuración de razas (heads y bodies), y otros parámetros internos.

---

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Ver reporte de cobertura (después de ejecutar tests)
# Abrir: server/target/site/jacoco/index.html
```

| Capa | Tests | Estado |
|------|-------|--------|
| Objetos (~30 tipos) | 35 | ✅ Excelente |
| Efectos de hechizo | 7 | ✅ Buena |
| DAOs | 5 | ✅ Buena |
| Servicios | 4 | ⚠️ Parcial |
| Personajes | 4 | ⚠️ Parcial |
| Red (paquetes) | 2 | ⚠️ Básica |
| Mapas | 2 | ✅ Buena |
| Usuarios | 1 | ⚠️ Básica |

---

## 📋 Estado del Proyecto

### ✅ Implementado
- Modelo de dominio completo (personajes, objetos, hechizos, mapas)
- Lectura de datos legacy (archivos INI y mapas binarios)
- Infraestructura de red con Netty
- Inyección de dependencias con Guice
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
- Cifrado real de tráfico
- Más paquetes del protocolo AO (~43 pendientes)
- Spawning de NPCs
- Administración en runtime (comandos GM)

---

## 📁 Datos del Juego

| Recurso | Ubicación | Formato |
|---------|-----------|---------|
| Mapas (290) | `resources/maps/` | Binario (`.map`, `.inf`, `.dat`) |
| Objetos | `resources/data/objects.dat` | INI |
| NPCs | `resources/data/npcs.dat` | INI |
| Ciudades | `resources/data/cities.dat` | INI |
| Arquetipos | `resources/data/balances.dat` | INI |
| Personajes | `charfiles/` | INI |
| Servidor | `resources/server.ini` | INI |

---

## 🤝 Contribuir

1. Fork del repositorio
2. Crear branch de feature (`git checkout -b feature/mi-feature`)
3. Commit de cambios (`git commit -m 'Agregar mi feature'`)
4. Push al branch (`git push origin feature/mi-feature`)
5. Abrir un Pull Request

---

## 📄 Licencia

Este proyecto es un remake educativo/comunitario de Argentum Online.
