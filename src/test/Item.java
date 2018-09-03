package test;

public class Item {
	
	private Long id;
	private Integer quantity;
	private Double unitPrice;
	
	public Item(Long id, Integer quantity, Double unitPrice) {
		this.id = id;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}	
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return this.quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return this.unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
