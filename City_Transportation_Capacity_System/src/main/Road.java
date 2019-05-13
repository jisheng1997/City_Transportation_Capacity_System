package main;

public class Road {

	private int road_id;
	private String road_name;
	private int road_laneNumber;	
	private String road_type;
	private String road_speed;
	private boolean road_left;
	private int road_capacity;

	public Road(int road_id,String road_name,int road_laneNumber,String road_type,String road_speed,boolean road_left,int road_capacity) {
		this.road_id = road_id;
		this.road_name = road_name;
		this.road_laneNumber = road_laneNumber;
		this.road_type = road_type;
		this.road_speed = road_speed;
		this.road_left  = road_left;
		this.road_capacity = road_capacity;
	}

	public int getRoad_id() {
		return road_id;
	}

	public String getRoad_type() {
		return road_type;
	}

	public void setRoad_type(String road_type) {
		this.road_type = road_type;
	}

	public void setRoad_id(int road_id) {
		this.road_id = road_id;
	}

	public String getRoad_name() {
		return road_name;
	}

	public void setRoad_name(String road_name) {
		this.road_name = road_name;
	}

	public int getRoad_laneNumber() {
		return road_laneNumber;
	}

	public void setRoad_laneNumber(int road_laneNumber) {
		this.road_laneNumber = road_laneNumber;
	}

	public String getRoad_speed() {
		return road_speed;
	}

	public void setRoad_speed(String road_speed) {
		this.road_speed = road_speed;
	}

	public boolean isRoad_left() {
		return road_left;
	}

	public void setRoad_left(boolean road_left) {
		this.road_left = road_left;
	}

	public int getRoad_capacity() {
		return road_capacity;
	}

	public void setRoad_capacity(int road_capacity) {
		this.road_capacity = road_capacity;
	}
	
	
}
