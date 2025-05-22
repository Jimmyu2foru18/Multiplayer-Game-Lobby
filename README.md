# Multiplayer Game Lobby System

## Overview
This Java application is a multiplayer game lobby system that uses two different approaches 
to managing player sessions and matchmaking:
- **Stateful Strategy**: Maintains active users and rooms in memory
- **Stateless Strategy**: Makes matchmaking decisions fresh with each request

The system uses these strategies to allow switching between these 
implementations durring runtime, showing flexibility in how the lobbies manages players and rooms.

## Features
- Player management - to add/remove players
- Room creation and management
- Player-room interactions - to join/leave
- Automatic matchmaking
- Runtime strategy switching
- Both Console and GUI interfaces

## Requirements 
- JRE 
- JDK

## Project Structure
```
├── player.java           
├── room.java           
├── lobbymanager.java     
├── lobbymanagerimpl.java 
├── lobbystrategy.java  
├── statefullobbystrategy.java 
├── statelesslobbystrategy.java 
├── lobbydemo.java   
├── lobbygui.java         
└── lobbylauncher.java   
```

## Running the Application

### Console Interface
To run the console-based demo:
1. First compile all Java files:
```bash
javac *.java
```
2. Then run:
```bash
java -cp bin LobbyDemo
```

### Graphical Interface
To launch the GUI version:
1. First compile all Java files:
```bash
javac *.java
```
2. Then run:
```bash
java -cp bin LobbyGUI
```

## GUI Features

### Players Tab
- Add new players with custom names
- View all players in the system
- Remove selected players

### Rooms Tab
- Create rooms with specified names and capacities
- View all available rooms and their status

### Interactions Tab
- Join players to specific rooms
- Remove players from rooms
- Trigger automatic matchmaking

### Strategy Tab
- Switch between Stateful and Stateless strategies
- View current strategy status

## Console Interface Features
- Interactive menu-driven interface
- Player and room management
- Manual and automatic matchmaking
- Strategy switching
- Status display

## Implementation Details

### Player Management
```java
Player player = new Player("1", "Player1");
lobbyManager.joinLobby(player);
```

### Room Operations
```java
Room room = new Room("1", "Game Room", 4);
lobbyManager.joinRoom(player, room);
```

### Strategy Switching
```java
lobbyManager.setStrategy(new StatefulLobbyStrategy());
lobbyManager.setStrategy(new StatelessLobbyStrategy());
```

## Design Patterns

### Strategy Pattern
The encapsulates different lobby management strategies:
- `LobbyStrategy` interface defines the contract
- `StatefulLobbyStrategy` maintains state
- `StatelessLobbyStrategy` processes each request independently

### MVC Pattern (GUI)
The GUI follows the Model-View-Controller pattern:
- Model: LobbyManager and related entities
- View: Swing-based GUI components
- Controller: Event listeners in LobbyGUI