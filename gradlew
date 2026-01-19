#!/bin/sh
# Это упрощенный скрипт. 
# В идеале здесь должен быть механизм скачивания gradle-wrapper.jar, если его нет.
# Для GitHub Actions это не критично, так как там можно установить Gradle через экшен,
# но мы сделаем так, чтобы он скачивал Wrapper Jar сам.

APP_HOME=$(dirname "$0")
APP_HOME=$(cd "$APP_HOME"; pwd)

WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
WRAPPER_PROPS="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"

if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Downloading Gradle Wrapper..."
    mkdir -p "$APP_HOME/gradle/wrapper"
    # Скачиваем wrapper jar с официального репо (версия 8.5)
    curl -L -o "$WRAPPER_JAR" https://raw.githubusercontent.com/gradle/gradle/v8.5.0/gradle/wrapper/gradle-wrapper.jar
fi

java -jar "$WRAPPER_JAR" "$@"
