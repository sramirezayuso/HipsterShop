package ar.edu.itba.model;

public class Product {

	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return id+name;
	}
}
