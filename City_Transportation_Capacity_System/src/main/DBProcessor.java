package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import main.User;
import main.DBUtil;

public class DBProcessor {

	/**
	 * Acquire all the user infomation in the DB
	 * 
	 * @return ArrayList contains all the user in the DB
	 */
	public ArrayList<User> fetchUserInfo() {

		Connection connection = DBUtil.getConnection();
		String sql = "select*from user;";
		ArrayList<User> userlist = new ArrayList<>(); // To store all the users in the DB

		try {
			Statement statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				User userInDB = new User(rSet.getString(2), rSet.getString(3),rSet.getBoolean(4));
				userInDB.setUser_id(rSet.getInt(1));
				userlist.add(userInDB);
			}
			rSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userlist;
	}

	public ArrayList<Integer> matchRoadName(String name) {

		ArrayList<Integer> result = new ArrayList<Integer>();
		Connection conn = DBUtil.getConnection();
		String sql = "select road_id from road_details where road_name like ?;";
		
		try {
			PreparedStatement queryPStmt = conn.prepareStatement(sql);
			queryPStmt.setString(1, "%" + name + "%");
			ResultSet rSet = queryPStmt.executeQuery();
			while (rSet.next()) {
				result.add(rSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Integer> matchRoad(String road_laneNumber,String road_type,String isleft,String speed,String capacity) {

		ArrayList<Integer> result = new ArrayList<Integer>();
		Connection conn = DBUtil.getConnection();
		String sql = "select road_id from road_details where ";
		boolean isfirst = false;
		if (!(road_laneNumber == "任意")) {
			sql = sql + "road_laneNumber = '" + road_laneNumber + "'";
			isfirst = true;
		}
		
		if (!(road_type == "任意")) {
			if (!isfirst) {
				sql = sql + "road_type = '" + road_type + "'";
				isfirst = true;
			}else {
				sql = sql + "and road_type = '" + road_type + "'";
			}
		}
		
		if (!(isleft == "任意")) {
			if (!isfirst) {
				sql = sql + "road_if_have_Left_turning_lane = '" + isleft + "'";
				isfirst = true;
			}else {
				sql = sql + "and road_if_have_Left_turning_lane = '" + isleft + "'";
			}
		}
		
		if (!(speed.isEmpty())) {
			if (!isfirst) {
				sql = sql + "road_speed = '" + speed + "'";
				isfirst = true;
			}else {
				sql = sql + "and road_speed = '" + speed + "'";
			}
		}
		
		if (!(capacity.isEmpty())) {
			if (!isfirst) {
				sql = sql + "road_basic_transportation_capacity = '" + capacity + "'";
				isfirst = true;
			}else {
				sql = sql + "and road_basic_transportation_capacity = '" + capacity + "'";
			}
		}
		try {
			PreparedStatement queryPStmt = conn.prepareStatement(sql);
			ResultSet rSet = queryPStmt.executeQuery();
			while (rSet.next()) {
				result.add(rSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public void insertUser(User user) {
		Connection conn = DBUtil.getConnection();

		// At first, find the maximum id of current db
		String idSql = "select max(user_id) from user;";
		Statement statement = null;
		int maxID = 0;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(idSql);
			if (resultSet.next())
				maxID = resultSet.getInt(1);
			else
				maxID = 0;

			resultSet.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String sql = "insert into user(user_id,username,password,root) values(?,?,?,?);";
		try {
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, maxID + 1);
			pStatement.setString(2, user.getUsername());
			pStatement.setString(3, user.getPassword());
			pStatement.setBoolean(4, false);

			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer, Road> fetchRoad() {

		Connection connection = DBUtil.getConnection();
		String sql1 = "select*from road_details;";
		HashMap<Integer, Road> roadList = new HashMap<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery(sql1);
			while (rSet.next()) {
				Road road = new Road(rSet.getInt(1),rSet.getString(2),rSet.getInt(3), rSet.getString(4),rSet.getString(5),rSet.getBoolean(6),rSet.getInt(7));
				roadList.put(road.getRoad_id(), road);
			}
			rSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roadList;
	}
	
	public void deleteRoad(int roadId) {
		Connection connection = DBUtil.getConnection();
		String sql = "delete from road_details where road_id=?;";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, roadId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addRoad(String roadname,String roadlaneNumber,String roadspeed,String isleft,String roadcapacity) {
		Connection connection = DBUtil.getConnection();
		
		// At first, find the maximum id of current db
		String idSql = "select max(road_id) from road_details;";
		Statement statement = null;
		int maxID = 0;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(idSql);
			if (resultSet.next())
				maxID = resultSet.getInt(1);
			else
				maxID = 0;

			resultSet.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		String sql = "insert into road_details(road_id,road_name,road_laneNumber,road_type,road_speed,road_if_have_Left_turning_lane,road_basic_transportation_capacity) values(?,?,?,?,?,?,?);";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, maxID + 1);
			pStatement.setString(2,roadname);
			pStatement.setString(3, roadlaneNumber);
			pStatement.setString(4, roadspeed);
			if (isleft == "是") {
				pStatement.setBoolean(6, true);
			}else {
				pStatement.setBoolean(6, false);
			}
			pStatement.setString(7, roadcapacity);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
