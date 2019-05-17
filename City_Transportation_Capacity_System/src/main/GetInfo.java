package main;

import java.util.ArrayList;

public class GetInfo {
	private static User currentUser;
	private static Road currentRoad = null;
	private static ArrayList<Road> currentResults;
	private DBProcessor dbProcessor = new DBProcessor();
	
	
	public static ArrayList<Road> getResults() {
		return currentResults;
	}

	public static void setResults(ArrayList<Road> results) {
		currentResults = results;
	}

	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(User user) {
		currentUser = user;
	}

	public static Road getCurrentRoad() {
		return currentRoad;
	}

	public static void setCurrentRoad(Road road) {
		currentRoad = road;
	}
	
	

}
