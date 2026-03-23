# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Spring Boot 2.7 (Java 8) template for hybrid REST API + CLI applications with Oracle DB. No batch framework — CLI handles manual/scheduled execution via cron.

## Build & Run

**Build:**
```bash
mvn clean package
```

**Load environment variables (required before running):**
```bash
set -a && source .env && set +a
```

**Run API server:**
```bash
java -jar target/app.jar --spring.profiles.active=dev
```

**Run CLI command:**
```bash
java -cp target/app.jar com.example.app.cli.CliApplication <command> [args] --spring.profiles.active=dev
# Example: java -cp target/app.jar com.example.app.cli.CliApplication user A001 --spring.profiles.active=dev
```

**Run tests:**
```bash
mvn test                                        # 全テスト
mvn test -Dtest=UserServiceTest                 # クラス指定
mvn test -Dtest=UserServiceTest#findById*       # メソッド指定
```

テストは DB 接続・SSL 証明書不要（H2 in-memory DB を使用）。

## Architecture

```
Browser → Static HTML/JS → REST API (/api/**)
                                  ↓
CLI Command ──────────────→ Service Layer (shared)
                                  ↓
                            MyBatis Mappers
                                  ↓
                            Oracle DB (19c/21c)
```

**Package layout:**
- `api/` — Controllers, API-specific config, `ApiApplication.java`
- `cli/` — Command dispatcher, `CliApplication.java`
- `common/` — Shared domain, services, config (used by both API and CLI)
- `primarydb/mapper/` — MyBatis mappers for main DB
- `secondarydb/` — Optional secondary DB mappers

**MyBatis XML mappers** live in `src/main/resources/mybatis/primary/` (and `secondary/`).

**Static frontend** (HTML/JS/CSS) served from `src/main/resources/static/` at `http://localhost:8080/`.

## Key Design Rules

- **API:** All endpoints under `/api` prefix, stateless, JSON-only
- **CLI:** Simple command dispatcher — reuses Service layer, not a batch framework
- **Transactions:** One DB per transaction; no distributed transactions (XA not configured)
- **Multiple DataSources:** Each DB needs its own `DataSource`, `SqlSessionFactory`, and `TransactionManager`

## Configuration

Profiles: `application.yaml` (base), `application-dev.yaml`, `application-prod.yaml`

**Secrets** go in `.env` (never in YAML); add `.env` to `.gitignore`:
```
DB_URL=jdbc:oracle:thin:@//localhost:1521/ORCLPDB1
DB_USERNAME=app
DB_PASSWORD=secret
```

Logging configured in `logback-spring.xml` (daily rotation with retention).

## Docker & HTTPS

**Quick start (Docker):**
```bash
cp .env.example .env   # fill in DB credentials
docker compose up --build
# Access: https://localhost:8443/api/health
```

**Custom port:**
```bash
SERVER_PORT=9443 docker compose up --build
```

**Certificate behavior:**
- `./keystore/keystore.p12` が存在しない場合、コンテナ起動時に `docker-entrypoint.sh` が自動的に自己署名証明書を生成する
- 本番用の証明書を使う場合は `./keystore/keystore.p12` に配置してから起動する

**ローカル開発で HTTPS:**
```bash
./scripts/generate-keystore.sh        # keystore/keystore.p12 を生成
set -a && source .env && set +a
java -jar target/app.jar --spring.profiles.active=dev
```

**SSL 設定の環境変数:**
| 変数 | デフォルト | 説明 |
|------|-----------|------|
| `SERVER_PORT` | `8443` | HTTPS ポート |
| `SSL_KEY_STORE_PASSWORD` | `changeit` | キーストアのパスワード |
| `SSL_KEY_ALIAS` | `app` | 証明書のエイリアス |

## Constraints

- Java 1.8 + Spring Boot 2.7.x — do not upgrade to Spring Boot 3 / Java 17
- ojdbc8 driver (Oracle Instant Client not required)
