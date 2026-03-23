# Spring Boot Java8 API + CLI + Static Web Template (No Batch System)

## 📌 Overview

This project is a **Spring Boot 2.7 (Java 8 compatible)** backend template that supports:

* REST API
* CLI execution (shared business logic)
* Static file hosting (HTML / JS / CSS)
* MyBatis + Oracle DB access
* Optional multiple DataSources
* Profile-based configuration
* Secure credential management via `.env`
* Log rotation (daily + retention)

❗ This template **does NOT include a batch framework**
CLI is used for manual or scheduled execution (cron, etc.)

---

## 🎯 Requirements

### Runtime

* Java 1.8
* Maven 3.x
* Oracle DB (19c / 21c supported)

### Framework

* Spring Boot 2.7.x
* MyBatis
* Lombok
* HikariCP

---

## 🧱 Architecture

```
[ Browser ]
    ↓
[ Static HTML / JS ]
    ↓
[ REST API (/api) ]
    ↓
[ Service Layer ] ← shared
    ↑
[ CLI Command ]
    ↓
[ MyBatis ]
    ↓
[ Oracle DB ]
```

---

## 📁 Project Structure

```
src/main/java/com/example/app/
  api/
    ApiApplication.java
    controller/
    config/
  cli/
    CliApplication.java
    command/
  common/
    config/
    domain/
    service/
  primarydb/
    mapper/
  secondarydb/ (optional)

src/main/resources/
  static/
  templates/
  mybatis/
    primary/
    secondary/
  application.yaml
  application-dev.yaml
  application-prod.yaml
  logback-spring.xml
```

---

## ⚙️ Features

### 1. REST API

Base path:

```
/api/**
```

Example:

```
GET /api/users/{id}
```

---

### 2. CLI Execution

Run commands using:

```
java -cp app.jar com.example.app.cli.CliApplication <command> [args]
```

Example:

```
user A001
```

CLI shares the same Service layer as API.

---

### 3. Static File Hosting

Location:

```
src/main/resources/static/
```

Access:

```
http://localhost:8080/
```

---

### 4. MyBatis + Oracle

* XML mapper support
* Oracle 19c / 21c compatible
* Uses ojdbc8 (no Instant Client required)

---

### 5. Multiple DataSources (Optional)

* primary → main business DB
* secondary → optional

Each requires:

* DataSource
* SqlSessionFactory
* TransactionManager

⚠ No cross-database transaction (unless XA is introduced)

---

### 6. Profiles

```
application.yaml
application-dev.yaml
application-prod.yaml
```

Run:

```
--spring.profiles.active=dev
```

---

### 7. Environment Variables (.env)

Secrets must NOT be stored in YAML.

Use:

```
DB_USERNAME
DB_PASSWORD
```

Load:

```
set -a
source .env
set +a
```

---

### 8. Logging

* Daily rotation
* Retention control

Configured via:

```
logback-spring.xml
```

---

## 🚀 Build

```
mvn clean package
```

---

## ▶️ Run (API)

```
set -a
source .env
set +a

java -jar target/app.jar --spring.profiles.active=dev
```

---

## ▶️ Run (CLI)

```
set -a
source .env
set +a

java -cp target/app.jar com.example.app.cli.CliApplication user A001 --spring.profiles.active=dev
```

---

## 🧠 Design Rules

### API

* Prefix `/api`
* Stateless
* JSON only

---

### CLI

* No batch framework
* Simple command dispatcher
* Reuse Service layer

---

### DB

* Keep schema clean
* Avoid mixing responsibilities

---

### Transaction

* One DB per transaction
* No distributed transaction by default

---

### Static Frontend

* Simple HTML + JS
* API-driven

---

## 🔐 Security

* `.env` for credentials
* `.gitignore` required

---

## 📦 .env Example

```
DB_URL=jdbc:oracle:thin:@//localhost:1521/ORCLPDB1
DB_USERNAME=app
DB_PASSWORD=secret
```

---

## 🧩 Extensibility

Future upgrades:

* Spring Boot 3 (Java 17)
* ojdbc11
* Docker
* Frontend framework (React / Vue)
* Spring Batch (if needed later)

---

## ⚠️ Notes

* Java 8 → Spring Boot 2.7.x required
* ojdbc8 is sufficient (Instant Client NOT required)
* CLI is NOT a batch system

---

## 🎯 Goal

Provide a **lightweight, production-ready template** for:

* API + CLI hybrid systems
* Internal tools
* Admin panels
* Scheduled tasks via cron

---

## 📌 Usage with Codex / AI

Prompt example:

"Generate full Spring Boot project based on this README"

Expected outputs:

* pom.xml
* Java source
* YAML config
* MyBatis mapper XML
* Static HTML/JS/CSS

---

## ✅ End

