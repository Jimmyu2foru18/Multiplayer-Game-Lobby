import java.util.List;

public interface LobbyManager {
    boolean joinLobby(Player player);
    boolean leaveLobby(String playerId);
    Player getPlayer(String playerId);
    List<Player> getAllPlayers();
    Room createRoom(String name, int capacity);
    Room getRoom(String roomId);
    List<Room> getAllRooms();
    boolean joinRoom(String playerId, String roomId);
    boolean leaveRoom(String playerId);
    int matchPlayers();
    int getPlayerCount();
    int getRoomCount();
    void setStrategy(LobbyStrategy strategy);
    LobbyStrategy getStrategy();
    void clear();
}