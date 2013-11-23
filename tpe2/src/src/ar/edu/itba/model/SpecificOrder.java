package ar.edu.itba.model;

public class SpecificOrder {
	private Integer id;
	private OrderProduct product;
	private Integer quantity;
	private Integer price;

	public Integer getId() {
		return id;
	}

	public OrderProduct getProduct() {
		return product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getPrice() {
		return price;
	}

}
