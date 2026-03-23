#!/bin/sh
set -e

KEYSTORE_DIR="/app/keystore"
KEYSTORE_PATH="${KEYSTORE_DIR}/keystore.p12"
KEYSTORE_PASSWORD="${SSL_KEY_STORE_PASSWORD:-changeit}"
KEY_ALIAS="${SSL_KEY_ALIAS:-app}"

# Auto-generate a self-signed certificate if no keystore is present
if [ ! -f "$KEYSTORE_PATH" ]; then
  echo "[entrypoint] No keystore found at $KEYSTORE_PATH — generating self-signed certificate..."
  mkdir -p "$KEYSTORE_DIR"
  keytool -genkeypair \
    -alias "$KEY_ALIAS" \
    -keyalg RSA \
    -keysize 2048 \
    -validity 365 \
    -storetype PKCS12 \
    -keystore "$KEYSTORE_PATH" \
    -storepass "$KEYSTORE_PASSWORD" \
    -dname "CN=localhost, OU=Dev, O=Example, L=Tokyo, ST=Tokyo, C=JP" \
    -noprompt
  echo "[entrypoint] Self-signed certificate generated."
fi

export SSL_KEY_STORE="file:${KEYSTORE_PATH}"

exec "$@" \
  --spring.profiles.active="${SPRING_PROFILES_ACTIVE:-prod}"
