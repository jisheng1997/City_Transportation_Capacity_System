package main;

public class Region {
	private int region_id;
	private String region_name;
	private String region_road;
	
	public Region(int region_id,String region_name,String region_road) {
		this.region_id = region_id;
		this.region_name = region_name;
		this.region_road = region_road;
	}
	public int getRegion_id() {
		return region_id;
	}
	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getRegion_road() {
		return region_road;
	}
	public void setRegion_road(String region_road) {
		this.region_road = region_road;
	}
	
}
