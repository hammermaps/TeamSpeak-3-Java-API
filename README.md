TeamSpeak 3 Java API
====================

A Java wrapper of the [TeamSpeak 3](http://media.teamspeak.com/ts3_literature/TeamSpeak%203%20Server%20Query%20Manual.pdf) Server Query API

> **Requires Java 21+**

## Features

- Contains almost all server query functionality! (see [TeamSpeak 3 Server Query Manual](https://www.teamspeak-info.de/downloads/ts3_serverquery_manual.pdf))
- Built-in keep alive method
- Threaded event-based system powered by **Virtual Threads** (Java 21) for optimal scalability
- Both [synchronous](src/main/java/com/github/theholywaffle/teamspeak3/TS3Api.java) and [asynchronous](src/main/java/com/github/theholywaffle/teamspeak3/TS3ApiAsync.java) implementations available
- Can be set up to reconnect and automatically resume execution after a connection problem
- **Sealed event hierarchy** (Java 21) – enables exhaustive `switch` pattern matching over all event types
- **`TS3Listener`** with `default` method implementations – implement only the events you care about
- Utilizes [SLF4J](https://www.slf4j.org/) for logging abstraction and integrates with your logging configuration

## Getting Started

### Download

- **Option 1 (Standalone Jar)**:

    Download the [latest release](https://github.com/hammermaps/TeamSpeak-3-Java-API/releases/download/1.4.0/teamspeak3-api-1.4.0-SNAPSHOT.jar) and add this jar to the buildpath of your project.

- **Option 2 (Build from Source)**:

    Requires **JDK 21+** and **Maven**. Clone the repository and use the included build script:

    ```bash
    # Projekt klonen
    git clone https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API.git
    cd TeamSpeak-3-Java-API
    chmod +x build.sh

    # Standard-JAR bauen
    ./build.sh

    # Fat-JAR inkl. aller Abhängigkeiten bauen
    ./build.sh --full

    # Build ohne Tests
    ./build.sh --skip-tests

    # Hilfe anzeigen
    ./build.sh --help
    ```

    Die fertige JAR liegt anschließend unter `target/`:
    - `target/teamspeak3-api-<version>.jar` – Standard-JAR
    - `target/teamspeak3-api-<version>-with-dependencies.jar` – Fat-JAR (bei `--full`)

- **Option 3 (Maven)**:

    Add the following to your pom.xml:

    ```xml
    <dependency>
        <groupId>com.github.theholywaffle</groupId>
        <artifactId>teamspeak3-api</artifactId>
        <version>...</version>
    </dependency>
    ```

    This API utilizes [SLF4J](https://www.slf4j.org/) for logging purposes and doesn't come shipped with a default logging implementation.
    You will manually have to add one via Maven to get any logging going, if you don't have one already.

    The easiest way to do so is to add SimpleLogger to your project:

    ```xml
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>2.0.16</version>
    </dependency>
    ```

    See this [configuration example](https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API/blob/master/src/main/resources/simplelogger.properties) for SimpleLogger.
    You can also choose whichever logging framework suits your needs best – just add your framework and the
    corresponding [SLF4J binding](https://mvnrepository.com/artifact/org.slf4j) to your pom.xml.

### Usage

All functionality is contained in the [TS3Api](src/main/java/com/github/theholywaffle/teamspeak3/TS3Api.java) object.

1. Create a [TS3Config](src/main/java/com/github/theholywaffle/teamspeak3/TS3Config.java) object and customize it.
2. Create a [TS3Query](src/main/java/com/github/theholywaffle/teamspeak3/TS3Query.java) object with your TS3Config as argument.
3. Call `TS3Query#connect()` to connect to the server.
4. Call `TS3Query#getApi()` to get a [TS3Api](src/main/java/com/github/theholywaffle/teamspeak3/TS3Api.java) object.
5. Do whatever you want with this api :)

### Example

```java
final TS3Config config = new TS3Config();
config.setHost("77.77.77.77");

final TS3Query query = new TS3Query(config);
query.connect();

final TS3Api api = query.getApi();
api.login("serveradmin", "serveradminpassword");
api.selectVirtualServerById(1);
api.setNickname("PutPutBot");
api.sendChannelMessage("PutPutBot is online!");
```

### Event Listening

`TS3Listener` has `default` no-op implementations for all methods.
Simply implement only the events you need – no adapter class required:

```java
api.registerEvent(TS3EventType.TEXT_CHANNEL, 0);
api.addTS3Listeners(new TS3Listener() {
    @Override
    public void onTextMessage(TextMessageEvent e) {
        if (e.getMessage().equals("!ping")) {
            api.sendChannelMessage("pong");
        }
    }
});
```

Java 21 sealed classes also allow exhaustive `switch` pattern matching over all event types:

```java
String info = switch (event) {
    case TextMessageEvent   e -> "Message: "  + e.getMessage();
    case ClientJoinEvent    e -> "Joined: "   + e.getClientNickname();
    case ClientLeaveEvent   e -> "Left: "     + e.getClientId();
    case ChannelCreateEvent e -> "New channel: " + e.getChannelId();
    // ... alle 12 Typen werden vom Compiler geprüft
    default -> "Other event";
};
```

### More examples

[here](example)

## Extra notes

### FloodRate

Only use `FloodRate.UNLIMITED` if you are sure that your query account is whitelisted (`query_ip_whitelist.txt` in the TeamSpeak server folder). If not, use `FloodRate.DEFAULT`. The server will temporarily ban your account if you send too many commands in a short period of time. For more info, check the [TeamSpeak 3 Server Query Manual, page 6](http://media.teamspeak.com/ts3_literature/TeamSpeak%203%20Server%20Query%20Manual.pdf#page=6).

### TS3Config Settings

| Option | Description | Method | Default | Required |
| --- | --- | --- | :---: | :---: |
| Host/IP | IP/Hostname of the TeamSpeak 3 server | `setHost(String)` | | **yes** |
| QueryPort | Query port of the TeamSpeak 3 server | `setQueryPort(int)` | `10011` | no |
| FloodRate | Prevents possible spam to the server | `setFloodRate(FloodRate)` | `FloodRate.DEFAULT` | no |
| Communications logging | Log raw client–server communication | `setEnableCommunicationsLogging(boolean)` | `false` | no |
| Command timeout | Time until a pending command fails (ms) | `setCommandTimeout(int)` | `4000` | no |
| Reconnect strategy | Behaviour after losing the connection | `setReconnectStrategy(ReconnectStrategy)` | `disconnect()` | no |
| Connection handler | Callback on connect / disconnect | `setConnectionHandler(ConnectionHandler)` | | no |

### Migration von 1.3.x → 1.4.0

| Entfernt | Ersatz |
| --- | --- |
| `TS3EventAdapter` | `TS3Listener` direkt implementieren (alle Methoden haben `default`-Implementierungen) |
| `addClientPermission(int, String, int, boolean)` | `addClientPermission(int, IPermissionType, int, boolean)` |
| `deleteClientPermission(int, String)` | `deleteClientPermission(int, IPermissionType)` |

## Questions or bugs?

Please let us know [here](../../issues). We'll try to help you as soon as we can.
