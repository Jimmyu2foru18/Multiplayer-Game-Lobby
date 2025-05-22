import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StatelessLobbyStrategy implements LobbyStrategy {
    private Map<String, Player> players;
    private Map<String, Room> rooms;
    private Random random;
    
    public StatelessLobbyStrategy() {
        players = new HashMap<>();
        rooms = new HashMap<>();
        random = new Random();
    }
    
    @Override
    public boolean addPlayer(Player player) {
        if (player == null || players.containsKey(player.getId())) {
            return false;
        }
        players.put(player.getId(), player);
        return true;
    }
    
    @Override
    public boolean removePlayer(String playerId) {
        Player player = players.get(playerId);
        if (player == null) {
            return false;
        }
        
        if (player.isInRoom()) {
            removePlayerFromRoom(playerId);
        }
        
        players.remove(playerId);
        return true;
    }
    
    @Override
    public Player getPlayer(String playerId) {
        return players.get(playerId);
    }
    
    @Override
    public List<Player> getAllPlayers() {
        return new ArrayList<>(players.values());
    }
    
    @Override
    public Room createRoom(String name, int capacity) {
        Room room = new Room(name, capacity);
        rooms.put(room.getId(), room);
        return room;
    }
    
    @Override
    public boolean createRoom(Room room) {
        if (room == null || rooms.containsKey(room.getId())) {
            return false;
        }
        rooms.put(room.getId(), room);
        return true;
    }
    
    @Override
    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }
    
    @Override
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }
    
    @Override
    public boolean addPlayerToRoom(String playerId, String roomId) {
        Player player = players.get(playerId);
        Room room = rooms.get(roomId);
        
        if (player == null || room == null) {
            return false;
        }
        
        if (player.isInRoom()) {
            removePlayerFromRoom(playerId);
        }
        
        return room.addPlayer(player);
    }
    
    @Override
    public boolean removePlayerFromRoom(String playerId) {
        Player player = players.get(playerId);
        if (player == null || !player.isInRoom()) {
            return false;
        }
        
        Room room = rooms.get(player.getRoomId());
        if (room == null) {
            player.setRoomId(null);
            return true;
        }
        
        return room.removePlayer(playerId);
    }
    
    @Override
    public int matchPlayers() {
        List<Player> unassignedPlayers = new ArrayList<>();
        for (Player player : players.values()) {
            if (!player.isInRoom()) {
                unassignedPlayers.add(player);
            }
        }
        
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (room.isActive() && !room.isFull()) {
                availableRooms.add(room);
            }
        }
        
        if (unassignedPlayers.isEmpty() || availableRooms.isEmpty()) {
            return 0;
        }
        
        int matchedCount = 0;
        
        for (Player player : unassignedPlayers) {
            if (availableRooms.isEmpty()) {
                break;
            }
            
            int randomIndex = random.nextInt(availableRooms.size());
            Room selectedRoom = availableRooms.get(randomIndex);
            
            if (selectedRoom.addPlayer(player)) {
                matchedCount++;
                
                if (selectedRoom.isFull()) {
                    availableRooms.remove(randomIndex);
                }
            }
        }
        
        return matchedCount;
    }
    
    @Override
    public int getPlayerCount() {
        return players.size();
    }
    
    @Override
    public int getRoomCount() {
        return rooms.size();
    }
    
    @Override
    public void clear() {
        players.clear();
        rooms.clear();
    }
}