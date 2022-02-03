package entity;

public class Category {
	private int id;
	private String name;
	private boolean show;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public Category(int id, String name, boolean show) {
		super();
		this.id = id;
		this.name = name;
		this.show = show;
	}
	public Category() {
		super();
	}
	
	
}
