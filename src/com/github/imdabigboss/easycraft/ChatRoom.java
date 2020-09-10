package com.github.imdabigboss.easycraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public class ChatRoom {	
	public Map<String, ArrayList<Player>> chatRooms = new HashMap<String, ArrayList<Player>>();
	public Map<String, String> playerRooms = new HashMap<String, String>();
	
	public int createRoom(String roomName, Player player) {
		if (chatRooms.containsKey(roomName))
			return 1;
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player);
		chatRooms.put(roomName, players);
		setPlayerRoom(roomName, player.getName());
		return 0;
	}
	
	public int joinRoom(String roomName, Player player) {
		if (!chatRooms.containsKey(roomName))
			return 1;
		
		ArrayList<Player> players = chatRooms.get(roomName);
		players.add(player);
		
		chatRooms.replace(roomName, players);
		setPlayerRoom(roomName, player.getName());
		
		for(Player p : players) {
			p.sendMessage(player.getDisplayName() + " joined this chatroom!");
		}
		return 0;
	}
	
	public int leaveRoom(Player player) {
		if (!playerRooms.containsKey(player.getName()))
			return 1;
		
		String currentRoom = playerRooms.get(player.getName());
		ArrayList<Player> roomplayers = chatRooms.get(currentRoom);
		roomplayers.remove(player);
		
		if (roomplayers.isEmpty())
			chatRooms.remove(currentRoom);
		else {
			for(Player p : roomplayers) {
				p.sendMessage(player.getDisplayName() + " left this chatroom!");
			}
			chatRooms.replace(currentRoom, roomplayers);
		}
		
		playerRooms.remove(player.getName());
		return 0;
	}
	
	public boolean isInRoom(String player) {
		return playerRooms.containsKey(player);
	}
	
	public List<Player> getRoomPlayers(String player) {
		return chatRooms.get(playerRooms.get(player));
	}
	
	private void setPlayerRoom(String roomName, String player) {
		if (playerRooms.containsKey(player)) {
			playerRooms.replace(player, roomName);
		} else {
			playerRooms.put(player, roomName);
		}
	}
}
