package main;

public class GetInfo {
	private static User currentUser;
	private static Road currentRoad = null;
	private DBProcessor dbProcessor = new DBProcessor();
	
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
