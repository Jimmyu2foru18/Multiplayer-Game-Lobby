import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

public class Room {
    private String id;
    private String name;
    private int capacity;
    private List<Player> players;
    private boolean active;
    
    public Room(String name, int capacity) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.capacity = capacity;
        this.players = new ArrayList<>();
        this.active = true;
    }
    
    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.players = new ArrayList<>();
        this.active = true;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isFull() {
        return players.size() >= capacity;
    }
    
    public int getPlayerCount() {
        return players.size();
    }
    
    public boolean addPlayer(Player player) {
        if (isFull() || !active) {
            return false;
        }
        
        if (players.contains(player)) {
            return false;
        }
        
        players.add(player);
        player.setRoomId(id);
        return true;
    }
    
    public boolean removePlayer(Player player) {
        boolean removed = players.remove(player);
        if (removed) {
            player.setRoomId(null);
        }
        return removed;
    }
    
    public boolean removePlayer(String playerId) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.getId().equals(playerId)) {
                players.remove(i);
                player.setRoomId(null);
                return true;
            }
        }
        return false;
    }
    
    public boolean hasPlayer(String playerId) {
        for (Player player : players) {
            if (player.getId().equals(playerId)) {
                return true;
            }
        }
        return false;
    }
    
    public Player getPlayer(String playerId) {
        for (Player player : players) {
            if (player.getId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "Room{id='" + id + "', name='" + name + "', capacity=" + capacity + 
               ", playerCount=" + players.size() + ", active=" + active + "}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}