package ar.edu.itba.model;

public class Attribute {
	private Integer id;
	private String name;
	private String[] values;
	
	public Attribute(Integer id, String name, String[] values) {
		super();
		this.id = id;
		this.name = name;
		this.values = values;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String[] getValues() {
		return values;
	}
	
	
	
	
}
