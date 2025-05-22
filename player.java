import java.util.Objects;

public class Player {
    private String id;
    private String name;
    private boolean inRoom;
    private String roomId;
    
    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.inRoom = false;
        this.roomId = null;
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
    
    public boolean isInRoom() {
        return inRoom;
    }
    
    public void setInRoom(boolean inRoom) {
        this.inRoom = inRoom;
    }
    
    public String getRoomId() {
        return roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
        this.inRoom = (roomId != null);
    }
    
    @Override
    public String toString() {
        return "Player{id='" + id + "', name='" + name + "', inRoom=" + inRoom + 
               (roomId != null ? ", roomId='" + roomId + "'" : "") + "}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}