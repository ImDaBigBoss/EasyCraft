package com.github.imdabigboss.easycraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoom {	
	public Map<String, ArrayList<String>> chatRooms = new HashMap<String, ArrayList<String>>();
	public Map<String, String> playerRooms = new HashMap<String, String>();
	
	public int createRoom(String roomName, String player) {
		if (chatRooms.containsKey(roomName))
			return 1;
		
		ArrayList<String> players = new ArrayList<String>();
		players.add(player);
		chatRooms.put(roomName, players);
		setPlayerRoom(roomName, player);
		return 0;
	}
	
	public int joinRoom(String roomName, String player) {
		if (!chatRooms.containsKey(roomName))
			return 1;
		
		ArrayList<String> players = chatRooms.get(roomName);
		players.add(player);
		
		chatRooms.replace(roomName, players);
		setPlayerRoom(roomName, player);
		return 0;
	}
	
	public int leaveRoom(String player) {
		if (!playerRooms.containsKey(player))
			return 1;
		
		String currentRoom = playerRooms.get(player);
		ArrayList<String> roomplayers = chatRooms.get(currentRoom);
		roomplayers.remove(player);
		
		if (roomplayers.isEmpty()) {
			chatRooms.remove(currentRoom);
			playerRooms.remove(player);
			return 2;
		} else {
			chatRooms.replace(currentRoom, roomplayers);
			playerRooms.remove(player);
		}
		return 0;
	}
	
	public boolean isInRoom(String player) {
		return playerRooms.containsKey(player);
	}
	
	public List<String> getRoomPlayers(String player) {
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
