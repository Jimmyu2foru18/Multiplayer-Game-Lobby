import java.util.List;

public interface LobbyStrategy {
    boolean addPlayer(Player player);
    boolean removePlayer(String playerId);
    Player getPlayer(String playerId);
    List<Player> getAllPlayers();
    Room createRoom(String name, int capacity);
    boolean createRoom(Room room);
    Room getRoom(String roomId);
    List<Room> getAllRooms();
    boolean addPlayerToRoom(String playerId, String roomId);
    boolean removePlayerFromRoom(String playerId);
    int matchPlayers();
    int getPlayerCount();
    int getRoomCount();
    void clear();
}