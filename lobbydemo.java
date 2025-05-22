import java.util.List;
import java.util.Scanner;

public class LobbyDemo {

    private static LobbyManager lobbyManager;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        lobbyManager = new LobbyManagerImpl(new StatefulLobbyStrategy());
        scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Multiplayer Game Lobby Demo!");
        System.out.println("Current strategy: Stateful Lobby Strategy");
        
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addPlayer();
                    break;
                case 2:
                    removePlayer();
                    break;
                case 3:
                    createRoom();
                    break;
                case 4:
                    joinRoom();
                    break;
                case 5:
                    leaveRoom();
                    break;
                case 6:
                    matchPlayers();
                    break;
                case 7:
                    listPlayers();
                    break;
                case 8:
                    listRooms();
                    break;
                case 9:
                    switchStrategy();
                    break;
                case 10:
                    runAutomatedDemo();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        System.out.println("Thank you for using the Multiplayer Game Lobby Demo!");
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add Player");
        System.out.println("2. Remove Player");
        System.out.println("3. Create Room");
        System.out.println("4. Join Room");
        System.out.println("5. Leave Room");
        System.out.println("6. Match Players");
        System.out.println("7. List Players");
        System.out.println("8. List Rooms");
        System.out.println("9. Switch Strategy");
        System.out.println("10. Run Automated Demo");
        System.out.println("0. Exit");
    }
    
    private static void addPlayer() {
        System.out.println("\n----- Add Player -----");
        String id = getStringInput("Enter player ID: ");
        String name = getStringInput("Enter player name: ");
        
        Player player = new Player(id, name);
        boolean success = lobbyManager.joinLobby(player);
        
        if (success) {
            System.out.println("Player added successfully!");
        } else {
            System.out.println("Failed to add player. Player ID may already exist.");
        }
    }
    
    private static void removePlayer() {
        System.out.println("\n----- Remove Player -----");
        String id = getStringInput("Enter player ID: ");
        
        boolean success = lobbyManager.leaveLobby(id);
        
        if (success) {
            System.out.println("Player removed successfully!");
        } else {
            System.out.println("Failed to remove player. Player ID may not exist.");
        }
    }
    
    private static void createRoom() {
        System.out.println("\n----- Create Room -----");
        String name = getStringInput("Enter room name: ");
        int capacity = getIntInput("Enter room capacity: ");
        
        Room room = lobbyManager.createRoom(name, capacity);
        System.out.println("Room created successfully! Room ID: " + room.getId());
    }
    
    private static void joinRoom() {
        System.out.println("\n----- Join Room -----");
        String playerId = getStringInput("Enter player ID: ");
        String roomId = getStringInput("Enter room ID: ");
        
        boolean success = lobbyManager.joinRoom(playerId, roomId);
        
        if (success) {
            System.out.println("Player joined room successfully!");
        } else {
            System.out.println("Failed to join room. Player or room may not exist, or room may be full.");
        }
    }
    
    private static void leaveRoom() {
        System.out.println("\n----- Leave Room -----");
        String playerId = getStringInput("Enter player ID: ");
        
        boolean success = lobbyManager.leaveRoom(playerId);
        
        if (success) {
            System.out.println("Player left room successfully!");
        } else {
            System.out.println("Failed to leave room. Player may not exist or may not be in a room.");
        }
    }
    
    private static void matchPlayers() {
        System.out.println("\n----- Match Players -----");
        int matched = lobbyManager.matchPlayers();
        System.out.println(matched + " players were matched into rooms.");
    }
    
    private static void listPlayers() {
        System.out.println("\n----- Players -----");
        List<Player> players = lobbyManager.getAllPlayers();
        
        if (players.isEmpty()) {
            System.out.println("No players in the lobby.");
            return;
        }
        
        for (Player player : players) {
            System.out.println(player);
        }
    }
    
    private static void listRooms() {
        System.out.println("\n----- Rooms -----");
        List<Room> rooms = lobbyManager.getAllRooms();
        
        if (rooms.isEmpty()) {
            System.out.println("No rooms in the lobby.");
            return;
        }
        
        for (Room room : rooms) {
            System.out.println(room);
            System.out.println("Players in room:");
            List<Player> players = room.getPlayers();
            if (players.isEmpty()) {
                System.out.println("  None");
            } else {
                for (Player player : players) {
                    System.out.println("  " + player);
                }
            }
        }
    }
    
    private static void switchStrategy() {
        System.out.println("\n----- Switch Strategy -----");
        System.out.println("Current strategy: " + (lobbyManager.getStrategy() instanceof StatefulLobbyStrategy ? "Stateful" : "Stateless"));
        System.out.println("1. Switch to Stateful Strategy");
        System.out.println("2. Switch to Stateless Strategy");
        System.out.println("0. Cancel");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                if (lobbyManager.getStrategy() instanceof StatefulLobbyStrategy) {
                    System.out.println("Already using Stateful Strategy.");
                } else {
                    LobbyStrategy newStrategy = new StatefulLobbyStrategy();
                    transferStrategyState(newStrategy);
                    lobbyManager.setStrategy(newStrategy);
                    System.out.println("Switched to Stateful Strategy.");
                }
                break;
            case 2:
                if (lobbyManager.getStrategy() instanceof StatelessLobbyStrategy) {
                    System.out.println("Already using Stateless Strategy.");
                } else {
                    LobbyStrategy newStrategy = new StatelessLobbyStrategy();
                    transferStrategyState(newStrategy);
                    lobbyManager.setStrategy(newStrategy);
                    System.out.println("Switched to Stateless Strategy.");
                }
                break;
            case 0:
                System.out.println("Switch cancelled.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void transferStrategyState(LobbyStrategy newStrategy) {
        List<Player> players = lobbyManager.getAllPlayers();
        List<Room> rooms = lobbyManager.getAllRooms();
        
        for (Player player : players) {
            newStrategy.addPlayer(new Player(player.getId(), player.getName()));
        }
        
        for (Room room : rooms) {
            Room newRoom = new Room(room.getId(), room.getName(), room.getCapacity());
            newStrategy.createRoom(newRoom);
        }
        
        for (Player player : players) {
            if (player.isInRoom()) {
                newStrategy.addPlayerToRoom(player.getId(), player.getRoomId());
            }
        }
    }
    
    private static void runAutomatedDemo() {
        System.out.println("\n----- Running Automated Demo -----");
        
        lobbyManager.clear();
        
        System.out.println("Creating players...");
        Player p1 = new Player("p1", "Player 1");
        Player p2 = new Player("p2", "Player 2");
        Player p3 = new Player("p3", "Player 3");
        Player p4 = new Player("p4", "Player 4");
        Player p5 = new Player("p5", "Player 5");
        Player p6 = new Player("p6", "Player 6");
        
        lobbyManager.joinLobby(p1);
        lobbyManager.joinLobby(p2);
        lobbyManager.joinLobby(p3);
        lobbyManager.joinLobby(p4);
        lobbyManager.joinLobby(p5);
        lobbyManager.joinLobby(p6);
        
        System.out.println("Creating rooms...");
        Room r1 = lobbyManager.createRoom("Room 1", 2);
        Room r2 = lobbyManager.createRoom("Room 2", 3);
        
        System.out.println("Joining rooms manually...");
        lobbyManager.joinRoom("p1", r1.getId());
        lobbyManager.joinRoom("p2", r2.getId());
        
        System.out.println("Current state:");
        listPlayers();
        listRooms();
        
        System.out.println("\nMatching remaining players...");
        int matched = lobbyManager.matchPlayers();
        System.out.println(matched + " players were matched into rooms.");
        
        System.out.println("\nFinal state:");
        listPlayers();
        listRooms();
        
        System.out.println("\nDemo completed!");
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}