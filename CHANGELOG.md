# Changelog

Alle nennenswerten Änderungen an diesem Projekt werden in dieser Datei dokumentiert.

Das Format basiert auf [Keep a Changelog](https://keepachangelog.com/de/1.0.0/),
dieses Projekt orientiert sich an [Semantic Versioning](https://semver.org/lang/de/).

---

## [Unreleased]

---

## [1.4.0] – 2026-03-25

### Hinzugefügt
- `build.sh` – Shell-Skript zum schnellen Bauen der `.jar` via Maven
  - Unterstützt `--full` für Fat-JAR (inkl. aller Abhängigkeiten)
  - Unterstützt `--skip-tests` zum Überspringen der Tests
  - Prüft automatisch Voraussetzungen (Maven, JDK) und gibt eine klare Fehlermeldung aus
- README: Abschnitt „Build from Source" mit `build.sh`-Nutzungsanleitung ergänzt
- `QueryLoginCommands` – Unterstützung für `queryloginadd`, `querylogindel` und `queryloginlist`
- `QueryLogin` / `CreatedQueryLogin` – neue Wrapper-Klassen zur Verwaltung von Query-Logins inkl. Passwort-Abruf
- `CustomPropertyCommands` – Unterstützung für `customset`, `customdelete`, `custominfo` und `customsearch`
- `CustomPropertyAssignment` – neuer Wrapper für benutzerdefinierte Client-Eigenschaften
- `Automatic-Module-Name: com.github.theholywaffle.teamspeak3` im JAR-Manifest für JPMS-Kompatibilität
- Reproduzierbare Builds via `project.build.outputTimestamp` in `pom.xml`
- `Wrapper.getOptional(String)` – `Optional<String>`-Getter als Alternative zu `get()` (gibt leer zurück statt `""`)
- `Wrapper.getOptionalInt(String)` – `OptionalInt`-Getter als Alternative zu `getInt()` (gibt leer zurück statt `-1`)
- `Wrapper.getOptionalLong(String)` – `OptionalLong`-Getter als Alternative zu `getLong()` (gibt leer zurück statt `-1`)
- `TS3Listener` – alle Methoden haben nun `default`-Implementierungen (kein Rumpf); `TS3Listener` kann direkt implementiert werden ohne alle Methoden zu überschreiben

### Geändert
- **Java-Version** auf 21 angehoben (Compiler-Source & Target)
- **Virtual Threads (JEP 444):** `TS3Query` verwendet jetzt `Executors.newThreadPerTaskExecutor` mit `Thread.ofVirtual()` statt `newCachedThreadPool` – bessere Skalierbarkeit bei I/O-lastigen Aufgaben
- **Sealed Classes (JEP 409):** `TS3Event` ist nun ein `sealed interface`, `BaseEvent` eine `sealed abstract class`; alle 12 konkreten Event-Typen sind `final` – die Event-Hierarchie ist zur Compilezeit vollständig bekannt und erlaubt exhaustives Pattern Matching mit `switch`
- **Pattern Matching für `instanceof` (JEP 394):** `TS3ApiAsync.isQueryError` verwendet jetzt `instanceof TS3CommandFailedException cfe` ohne separaten Cast
- **Switch-Expressions (JEP 361):** `TextMessageEvent.getTargetMode()` und `TS3Config.getQueryPort()` nutzen jetzt Switch-Expressions statt for-Schleifen bzw. if-else
- **`Thread.sleep(Duration)`:** `ReconnectingConnectionHandler` verwendet die typsicherere `Thread.sleep(Duration.ofMillis(...))` Overload
- **`String.formatted()`:** `TS3CommandFailedException.buildMessage` verwendet `String.formatted()` für den fixen Nachrichtenteil
- **`Map.ofEntries()`:** `EventManager` initialisiert die Event-Map jetzt mit unveränderlichem `Map.ofEntries()` statt `HashMap` + `static`-Initializer
- **`Map.getOrDefault()`:** `Wrapper.get(String)` nutzt `map.getOrDefault()` statt manuellem `null`-Vergleich
- **`@Serial`-Annotation:** Alle `serialVersionUID`-Felder in den Exception-Klassen mit `@Serial` (JEP 260) annotiert
- Array-Deklarationsstil in `StreamReader` von `String arr[]` auf `String[] arr` korrigiert
- Abhängigkeiten aktualisiert:
  - `sshj` → `0.38.0`
  - `slf4j` → `2.0.16`
- Maven-Plugins aktualisiert:
  - `maven-compiler-plugin` → `3.13.0`
  - `maven-jar-plugin` → `3.4.2`
  - `maven-surefire-plugin` → `3.5.2`
  - `maven-shade-plugin` → `3.6.0`
  - `maven-source-plugin` → `3.3.1`
  - `maven-javadoc-plugin` → `3.11.2`

### Entfernt
- `TS3EventAdapter` – Klasse vollständig entfernt; `TS3Listener` direkt implementieren (alle Methoden haben `default`-Implementierungen)
- `TS3Api.addClientPermission(int, String, int, boolean)` – veraltete String-basierte Überladung entfernt; `addClientPermission(int, IPermissionType, int, boolean)` verwenden
- `TS3Api.deleteClientPermission(int, String)` – veraltete String-basierte Überladung entfernt; `deleteClientPermission(int, IPermissionType)` verwenden
- `TS3ApiAsync.addClientPermission(int, String, int, boolean)` – veraltete String-basierte Überladung entfernt
- `TS3ApiAsync.deleteClientPermission(int, String)` – veraltete String-basierte Überladung entfernt

---

## [1.3.0] – 2023-01-01

### Hinzugefügt
- SSH-Verbindungsunterstützung via `SSHChannel` / `TS3Query.Protocol.SSH`
- Asynchrone API `TS3ApiAsync` mit `CommandFuture`-basiertem Rückgabemodell
- `ReconnectExample` – Beispiel für automatischen Wiederverbindungsversuch
- SLF4J-Logging-Abstraktion für flexible Logger-Integration

### Geändert
- `TS3Config` um optionale SSH-Parameter erweitert
- `FloodRate`-Handling verbessert

### Behoben
- Diverse Race Conditions im `EventManager`
- Korrekte Behandlung von Sonderzeichen in Befehlen

---

## [1.2.0] – 2021-06-15

### Hinzugefügt
- `FileTransferHelper` für Up- und Download von Dateien
- `FileTransferExample` als Anwendungsbeispiel

### Geändert
- Interne Refaktorierung von `CommandQueue` und `StreamReader`

### Behoben
- Timeout-Handling bei langsamen Serverantworten

---

## [1.1.0] – 2020-03-10

### Hinzugefügt
- Keep-Alive-Mechanismus (`KeepAlive`)
- Weitere Server-Query-Befehle abgedeckt (Channel, Client, Permission APIs)

### Geändert
- Verbesserte Fehlerbehandlung in `Connection`

---

## [1.0.0] – 2019-01-01

### Hinzugefügt
- Erstveröffentlichung
- Grundlegende TeamSpeak 3 Server Query API-Anbindung
- Synchrone API (`TS3Api`)
- Eventbasiertes System (`EventManager`, `EventListenerExample`)
- Maven-Build-Setup (`pom.xml`)

---

[Unreleased]: https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API/compare/v1.4.0...HEAD
[1.4.0]: https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API/compare/v1.3.0...v1.4.0
[1.3.0]: https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API/releases/tag/v1.0.0

