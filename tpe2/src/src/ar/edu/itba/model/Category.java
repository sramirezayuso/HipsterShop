package ar.edu.itba.model;

public class Category {
	private Integer id;
	private String name;
	
	public Category(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
