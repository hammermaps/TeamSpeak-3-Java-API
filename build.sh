#!/bin/bash

# ============================================================
#  build.sh – Baut das TeamSpeak-3-Java-API Projekt als .jar
# ============================================================

set -e

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
TARGET_DIR="$PROJECT_DIR/target"

# Farben
GREEN="\033[0;32m"
CYAN="\033[0;36m"
RED="\033[0;31m"
NC="\033[0m"

echo -e "${CYAN}============================================${NC}"
echo -e "${CYAN}  TeamSpeak-3-Java-API – Build Script       ${NC}"
echo -e "${CYAN}============================================${NC}"

# Voraussetzungen prüfen
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}[FEHLER] Maven (mvn) wurde nicht gefunden. Bitte installieren.${NC}"
    exit 1
fi

if ! command -v java &> /dev/null; then
    echo -e "${RED}[FEHLER] Java wurde nicht gefunden. Bitte JDK 21+ installieren.${NC}"
    exit 1
fi

echo -e "${CYAN}[INFO]${NC} Maven:  $(mvn -v 2>&1 | head -1)"
echo -e "${CYAN}[INFO]${NC} Java:   $(java -version 2>&1 | head -1)"
echo ""

# Build-Modus ermitteln (Standard oder Fat-JAR)
FULL=false
SKIP_TESTS=false

for arg in "$@"; do
    case "$arg" in
        --full|-f)    FULL=true ;;
        --skip-tests) SKIP_TESTS=true ;;
        --help|-h)
            echo "Verwendung: ./build.sh [Optionen]"
            echo ""
            echo "  (kein Argument)   Standard-JAR (ohne Abhängigkeiten)"
            echo "  --full, -f        Fat-JAR inkl. aller Abhängigkeiten"
            echo "  --skip-tests      Tests überspringen"
            echo "  --help, -h        Diese Hilfe anzeigen"
            exit 0
            ;;
    esac
done

# Maven-Befehl zusammenbauen
MVN_CMD="mvn clean package"
if $FULL; then
    MVN_CMD="$MVN_CMD -Pfull"
    echo -e "${CYAN}[INFO]${NC} Modus: Fat-JAR (mit allen Abhängigkeiten)"
else
    echo -e "${CYAN}[INFO]${NC} Modus: Standard-JAR"
fi

if $SKIP_TESTS; then
    MVN_CMD="$MVN_CMD -DskipTests"
    echo -e "${CYAN}[INFO]${NC} Tests werden übersprungen"
fi

echo ""
echo -e "${CYAN}[INFO]${NC} Starte Build: ${MVN_CMD}"
echo ""

cd "$PROJECT_DIR"
$MVN_CMD

# Ergebnis anzeigen
echo ""
echo -e "${GREEN}============================================${NC}"
echo -e "${GREEN}  Build erfolgreich!                        ${NC}"
echo -e "${GREEN}============================================${NC}"
echo -e "${GREEN}Erzeugte JAR-Datei(en):${NC}"
find "$TARGET_DIR" -maxdepth 1 -name "*.jar" | while read -r jar; do
    SIZE=$(du -h "$jar" | cut -f1)
    echo -e "  ${GREEN}✔${NC}  $jar  (${SIZE})"
done
echo ""

