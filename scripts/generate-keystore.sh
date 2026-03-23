#!/bin/bash
# Generate a self-signed PKCS12 keystore for local development.
# Usage: ./scripts/generate-keystore.sh [port]
#
# Output: ./keystore/keystore.p12

set -e

PORT="${1:-8443}"
KEYSTORE_DIR="$(dirname "$0")/../keystore"
KEYSTORE_PATH="$KEYSTORE_DIR/keystore.p12"
PASSWORD="${SSL_KEY_STORE_PASSWORD:-changeit}"
ALIAS="${SSL_KEY_ALIAS:-app}"

mkdir -p "$KEYSTORE_DIR"

echo "Generating self-signed certificate (valid 365 days)..."
keytool -genkeypair \
  -alias "$ALIAS" \
  -keyalg RSA \
  -keysize 2048 \
  -validity 365 \
  -storetype PKCS12 \
  -keystore "$KEYSTORE_PATH" \
  -storepass "$PASSWORD" \
  -dname "CN=localhost, OU=Dev, O=Example, L=Tokyo, ST=Tokyo, C=JP" \
  -ext "SAN=DNS:localhost,IP:127.0.0.1" \
  -noprompt

echo ""
echo "Keystore created: $KEYSTORE_PATH"
echo ""
echo "Start the app with:"
echo "  set -a && source .env && set +a"
echo "  java -jar target/app.jar --spring.profiles.active=dev"
echo ""
echo "Access: https://localhost:${PORT}/api/health"
