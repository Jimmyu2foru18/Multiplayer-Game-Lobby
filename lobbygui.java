import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

public class LobbyGUI extends JFrame {

    private LobbyManager lobbyManager;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    
    private JPanel playerPanel;
    private JTextField playerNameField;
    private JButton addPlayerButton;
    private JTable playerTable;
    private DefaultTableModel playerTableModel;
    private JButton removePlayerButton;
    
    private JPanel roomPanel;
    private JTextField roomNameField;
    private JSpinner roomCapacitySpinner;
    private JButton createRoomButton;
    private JTable roomTable;
    private DefaultTableModel roomTableModel;
    
    private JPanel interactionPanel;
    private JComboBox<String> playerComboBox;
    private JComboBox<String> roomComboBox;
    private JButton joinRoomButton;
    private JButton leaveRoomButton;
    private JButton matchPlayersButton;
    
    private JPanel strategyPanel;
    private JRadioButton statefulStrategyRadio;
    private JRadioButton statelessStrategyRadio;
    private ButtonGroup strategyButtonGroup;
    
    private JPanel statusPanel;
    private JLabel playerCountLabel;
    private JLabel roomCountLabel;
    private JLabel strategyLabel;
    
    public LobbyGUI() {
        lobbyManager = new LobbyManagerImpl(new StatefulLobbyStrategy());
        
        setTitle("Multiplayer Game Lobby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        tabbedPane = new JTabbedPane();
        
        initPlayerPanel();
        initRoomPanel();
        initInteractionPanel();
        initStrategyPanel();
        initStatusPanel();
        
        tabbedPane.addTab("Players", playerPanel);
        tabbedPane.addTab("Rooms", roomPanel);
        tabbedPane.addTab("Interactions", interactionPanel);
        tabbedPane.addTab("Strategy", strategyPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        updateStatus();
    }
    
    private void initPlayerPanel() {
        playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel playerInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel playerNameLabel = new JLabel("Player Name:");
        playerNameField = new JTextField(20);
        addPlayerButton = new JButton("Add Player");
        
        playerInputPanel.add(playerNameLabel);
        playerInputPanel.add(playerNameField);
        playerInputPanel.add(addPlayerButton);
        
        playerTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "In Room", "Room ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        playerTable = new JTable(playerTableModel);
        JScrollPane playerScrollPane = new JScrollPane(playerTable);
        
        JPanel playerActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        removePlayerButton = new JButton("Remove Selected Player");
        playerActionPanel.add(removePlayerButton);
        
        playerPanel.add(playerInputPanel, BorderLayout.NORTH);
        playerPanel.add(playerScrollPane, BorderLayout.CENTER);
        playerPanel.add(playerActionPanel, BorderLayout.SOUTH);
        
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });
        
        removePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePlayer();
            }
        });
    }
    
    private void initRoomPanel() {
        roomPanel = new JPanel(new BorderLayout());
        roomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel roomInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel roomNameLabel = new JLabel("Room Name:");
        roomNameField = new JTextField(20);
        JLabel roomCapacityLabel = new JLabel("Capacity:");
        roomCapacitySpinner = new JSpinner(new SpinnerNumberModel(4, 2, 10, 1));
        createRoomButton = new JButton("Create Room");
        
        roomInputPanel.add(roomNameLabel);
        roomInputPanel.add(roomNameField);
        roomInputPanel.add(roomCapacityLabel);
        roomInputPanel.add(roomCapacitySpinner);
        roomInputPanel.add(createRoomButton);
        
        roomTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Capacity", "Players", "Active"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable = new JTable(roomTableModel);
        JScrollPane roomScrollPane = new JScrollPane(roomTable);
        
        roomPanel.add(roomInputPanel, BorderLayout.NORTH);
        roomPanel.add(roomScrollPane, BorderLayout.CENTER);
        
        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRoom();
            }
        });
    }
    
    private void initInteractionPanel() {
        interactionPanel = new JPanel(new BorderLayout());
        interactionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel interactionInputPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        
        JPanel playerSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel playerLabel = new JLabel("Select Player:");
        playerComboBox = new JComboBox<>();
        playerSelectionPanel.add(playerLabel);
        playerSelectionPanel.add(playerComboBox);
        
        JPanel roomSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel roomLabel = new JLabel("Select Room:");
        roomComboBox = new JComboBox<>();
        roomSelectionPanel.add(roomLabel);
        roomSelectionPanel.add(roomComboBox);
        
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        joinRoomButton = new JButton("Join Room");
        leaveRoomButton = new JButton("Leave Room");
        matchPlayersButton = new JButton("Match Players");
        actionButtonPanel.add(joinRoomButton);
        actionButtonPanel.add(leaveRoomButton);
        actionButtonPanel.add(matchPlayersButton);
        
        interactionInputPanel.add(playerSelectionPanel);
        interactionInputPanel.add(roomSelectionPanel);
        interactionInputPanel.add(actionButtonPanel);
        
        interactionPanel.add(interactionInputPanel, BorderLayout.NORTH);
        
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setText("This panel allows you to manage player-room interactions.\n\n"
                + "- Join Room: Add a selected player to a selected room\n"
                + "- Leave Room: Remove a selected player from their current room\n"
                + "- Match Players: Automatically match players to rooms based on the current strategy");
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(getBackground());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        
        interactionPanel.add(descriptionPanel, BorderLayout.CENTER);
        
        joinRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinRoom();
            }
        });
        
        leaveRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaveRoom();
            }
        });
        
        matchPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                matchPlayers();
            }
        });
    }
    
    private void initStrategyPanel() {
        strategyPanel = new JPanel(new BorderLayout());
        strategyPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel strategySelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel strategySelectionLabel = new JLabel("Select Lobby Strategy:");
        statefulStrategyRadio = new JRadioButton("Stateful Strategy");
        statelessStrategyRadio = new JRadioButton("Stateless Strategy");
        
        strategyButtonGroup = new ButtonGroup();
        strategyButtonGroup.add(statefulStrategyRadio);
        strategyButtonGroup.add(statelessStrategyRadio);
        
        statefulStrategyRadio.setSelected(true);
        
        strategySelectionPanel.add(strategySelectionLabel);
        strategySelectionPanel.add(statefulStrategyRadio);
        strategySelectionPanel.add(statelessStrategyRadio);
        
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setText("This panel allows you to switch between different lobby management strategies.\n\n"
                + "Stateful Strategy:\n"
                + "- Maintains state of all players and rooms in memory\n"
                + "- Provides consistent and predictable behavior\n"
                + "- Suitable for smaller-scale applications\n\n"
                + "Stateless Strategy:\n"
                + "- Makes matchmaking decisions fresh with each request\n"
                + "- Less memory-intensive\n"
                + "- Suitable for larger-scale applications\n\n"
                + "Note: Switching strategies will preserve existing players and rooms.");
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(getBackground());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        
        strategyPanel.add(strategySelectionPanel, BorderLayout.NORTH);
        strategyPanel.add(descriptionPanel, BorderLayout.CENTER);
        
        statefulStrategyRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToStatefulStrategy();
            }
        });
        
        statelessStrategyRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToStatelessStrategy();
            }
        });
    }
    
    private void initStatusPanel() {
        statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
        
        playerCountLabel = new JLabel("Players: 0");
        roomCountLabel = new JLabel("Rooms: 0");
        strategyLabel = new JLabel("Strategy: Stateful");
        
        statusPanel.add(playerCountLabel);
        statusPanel.add(new JLabel(" | "));
        statusPanel.add(roomCountLabel);
        statusPanel.add(new JLabel(" | "));
        statusPanel.add(strategyLabel);
    }
    
    private void addPlayer() {
        String name = playerNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a player name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String id = UUID.randomUUID().toString();
        Player player = new Player(id, name);
        
        boolean success = lobbyManager.joinLobby(player);
        if (success) {
            playerNameField.setText("");
            
            updatePlayerTable();
            updatePlayerComboBox();
            updateStatus();
            
            JOptionPane.showMessageDialog(this, "Player added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add player", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removePlayer() {
        int selectedRow = playerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a player to remove", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String playerId = (String) playerTableModel.getValueAt(selectedRow, 0);
        boolean success = lobbyManager.leaveLobby(playerId);
        
        if (success) {
            updatePlayerTable();
            updateRoomTable();
            updatePlayerComboBox();
            updateRoomComboBox();
            updateStatus();
            
            JOptionPane.showMessageDialog(this, "Player removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove player", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createRoom() {
        String name = roomNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a room name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int capacity = (int) roomCapacitySpinner.getValue();
        Room room = lobbyManager.createRoom(name, capacity);
        
        if (room != null) {
            roomNameField.setText("");
            
            updateRoomTable();
            updateRoomComboBox();
            updateStatus();
            
            JOptionPane.showMessageDialog(this, "Room created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create room", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void joinRoom() {
        if (playerComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a player", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (roomComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a room", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String playerInfo = (String) playerComboBox.getSelectedItem();
        String playerId = playerInfo.split(" - ")[0];
        
        String roomInfo = (String) roomComboBox.getSelectedItem();
        String roomId = roomInfo.split(" - ")[0];
        
        boolean success = lobbyManager.joinRoom(playerId, roomId);
        
        if (success) {
            updatePlayerTable();
            updateRoomTable();
            updatePlayerComboBox();
            updateRoomComboBox();
            
            JOptionPane.showMessageDialog(this, "Player joined room successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to join room", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void leaveRoom() {
        if (playerComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a player", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String playerInfo = (String) playerComboBox.getSelectedItem();
        String playerId = playerInfo.split(" - ")[0];
        
        boolean success = lobbyManager.leaveRoom(playerId);
        
        if (success) {
            updatePlayerTable();
            updateRoomTable();
            updatePlayerComboBox();
            updateRoomComboBox();
            
            JOptionPane.showMessageDialog(this, "Player left room successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to leave room", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void matchPlayers() {
        int matchCount = lobbyManager.matchPlayers();
        
        updatePlayerTable();
        updateRoomTable();
        updatePlayerComboBox();
        updateRoomComboBox();
        
        JOptionPane.showMessageDialog(this, matchCount + " players matched to rooms", "Match Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void switchToStatefulStrategy() {
        if (lobbyManager.getStrategy() instanceof StatefulLobbyStrategy) {
            return;
        }
        
        LobbyStrategy statefulStrategy = new StatefulLobbyStrategy();
        
        transferStrategyState(statefulStrategy);
        lobbyManager.setStrategy(statefulStrategy);
        
        updateStatus();
        JOptionPane.showMessageDialog(this, "Switched to Stateful Strategy", "Strategy Change", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void switchToStatelessStrategy() {
        if (lobbyManager.getStrategy() instanceof StatelessLobbyStrategy) {
            return;
        }
        
        LobbyStrategy statelessStrategy = new StatelessLobbyStrategy();
        
        transferStrategyState(statelessStrategy);
        lobbyManager.setStrategy(statelessStrategy);
        
        updateStatus();
        JOptionPane.showMessageDialog(this, "Switched to Stateless Strategy", "Strategy Change", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void transferStrategyState(LobbyStrategy newStrategy) {
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
    
    private void updatePlayerTable() {
        playerTableModel.setRowCount(0);
        
        List<Player> players = lobbyManager.getAllPlayers();
        for (Player player : players) {
            playerTableModel.addRow(new Object[]{
                player.getId(),
                player.getName(),
                player.isInRoom(),
                player.getRoomId()
            });
        }
    }
    
    private void updateRoomTable() {
        roomTableModel.setRowCount(0);
        
        List<Room> rooms = lobbyManager.getAllRooms();
        for (Room room : rooms) {
            roomTableModel.addRow(new Object[]{
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getPlayerCount() + "/" + room.getCapacity(),
                room.isActive()
            });
        }
    }
    
    private void updatePlayerComboBox() {
        playerComboBox.removeAllItems();
        
        List<Player> players = lobbyManager.getAllPlayers();
        for (Player player : players) {
            playerComboBox.addItem(player.getId() + " - " + player.getName());
        }
    }
    
    private void updateRoomComboBox() {
        roomComboBox.removeAllItems();
        
        List<Room> rooms = lobbyManager.getAllRooms();
        for (Room room : rooms) {
            if (room.isActive() && !room.isFull()) {
                roomComboBox.addItem(room.getId() + " - " + room.getName() + " (" + room.getPlayerCount() + "/" + room.getCapacity() + ")");
            }
        }
    }
    
    private void updateStatus() {
        playerCountLabel.setText("Players: " + lobbyManager.getPlayerCount());
        roomCountLabel.setText("Rooms: " + lobbyManager.getRoomCount());
        
        if (lobbyManager.getStrategy() instanceof StatefulLobbyStrategy) {
            strategyLabel.setText("Strategy: Stateful");
        } else {
            strategyLabel.setText("Strategy: Stateless");
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set look and feel: " + e.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> {
            LobbyGUI gui = new LobbyGUI();
            gui.setVisible(true);
        });
    }
}