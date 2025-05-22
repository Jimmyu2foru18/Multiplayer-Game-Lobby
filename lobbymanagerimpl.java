import java.util.List;

public class LobbyManagerImpl implements LobbyManager {
    
    private LobbyStrategy strategy;
    
    public LobbyManagerImpl(LobbyStrategy strategy) {
        this.strategy = strategy;
    }
    
    @Override
    public boolean joinLobby(Player player) {
        return strategy.addPlayer(player);
    }
    
    @Override
    public boolean leaveLobby(String playerId) {
        return strategy.removePlayer(playerId);
    }
    
    @Override
    public Player getPlayer(String playerId) {
        return strategy.getPlayer(playerId);
    }
    
    @Override
    public List<Player> getAllPlayers() {
        return strategy.getAllPlayers();
    }
    
    @Override
    public Room createRoom(String name, int capacity) {
        return strategy.createRoom(name, capacity);
    }
    
    @Override
    public Room getRoom(String roomId) {
        return strategy.getRoom(roomId);
    }
    
    @Override
    public List<Room> getAllRooms() {
        return strategy.getAllRooms();
    }
    
    @Override
    public boolean joinRoom(String playerId, String roomId) {
        return strategy.addPlayerToRoom(playerId, roomId);
    }
    
    @Override
    public boolean leaveRoom(String playerId) {
        return strategy.removePlayerFromRoom(playerId);
    }
    
    @Override
    public int matchPlayers() {
        return strategy.matchPlayers();
    }
    
    @Override
    public int getPlayerCount() {
        return strategy.getPlayerCount();
    }
    
    @Override
    public int getRoomCount() {
        return strategy.getRoomCount();
    }
    
    @Override
    public void setStrategy(LobbyStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.strategy = strategy;
    }
    
    @Override
    public LobbyStrategy getStrategy() {
        return strategy;
    }
    
    @Override
    public void clear() {
        strategy.clear();
    }
}