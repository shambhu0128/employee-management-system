#!/bin/sh
set -e

if [ -n "$REDIS_CA_CERT" ]; then
  echo "Importing Redis CA certificate into JVM trust store..."
  echo "$REDIS_CA_CERT" > /tmp/redis-ca.pem
  keytool -importcert -noprompt \
    -alias aiven-redis-ca \
    -file /tmp/redis-ca.pem \
    -keystore "$JAVA_HOME/lib/security/cacerts" \
    -storepass changeit
  echo "Certificate imported successfully."
else
  echo "REDIS_CA_CERT not set, skipping certificate import."
fi

exec java -jar app.jar