# Project Proposal: Multiplayer Game Lobby System

## Overview
This project implements a multiplayer game lobby system in Java that uses stateful and stateless 
approaches to managing player sessions and matchmaking. 
The system will uses design pattern to allow switching between different implementations durring runtime.

## Architecture

### Core Components

1. **Player**: Represents a user in the system with a unique ID and other attributes.

2. **Room**: Represents a game room that can host multiple players.

3. **LobbyManager Interface**: Defines the core operations for the lobby system:
   - Join/leave lobby
   - Match players into rooms
   - Get lobby statistics

4. **LobbyStrategy Interface**: Defines the strategy for managing players and rooms.

5. **Implementations**:
   - **StatefulLobbyStrategy**: Maintains state of all players and rooms in memory.
   - **StatelessLobbyStrategy**: Makes matchmaking decisions fresh with each request without maintaining state.

### Design Patterns

1. **Strategy Pattern**: Allows switching between stateful and stateless.
2. **Observer Pattern**: Optional extension for notifying players of lobby events.
3. **Factory Pattern**: For creating rooms and managing player instances.

## Features

### Basic Features
1. Player registration and management
2. Room creation and management
3. Matchmaking players into rooms
4. Joining/leaving the lobby

### Advanced Features
1. Room capacity constraints
2. Skill-based matchmaking
3. Team formation
4. Game type preferences

## Implementation Plan

1. Define core interfaces and classes
2. Implement the stateful lobby strategy
3. Implement the stateless lobby strategy
4. Create a simple console-based demo application
5. Develop test cases to verify functionality

## Expected Outcomes

This project will demonstrate:
1. How different multiplayer game architectures handle session management
2. The trade-offs between stateful and stateless approaches
3. Practical application of the Strategy design pattern
4. Object-oriented design principles in a real-world scenario

## Technologies

- Java
- JUnit
- Maven for build management