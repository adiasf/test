package test;

import java.util.List;

public class Sale implements Comparable<Sale>{
	
	private Long saleId;
	private List<Item> items;
	private String salesRepName;
	private Double saleTotal;
	
	public Sale(Long saleId, List<Item> itemsList, String salesRepName) {
		this.saleId = saleId;
		this.items = itemsList;
		this.salesRepName = salesRepName;
	}

	public Long getSaleId() {
		return this.saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getSalesRepName() {
		return this.salesRepName;
	}

	public void setSalesRepName(String salesRepName) {
		this.salesRepName = salesRepName;
	}

	public Double getSaleTotal() {
		return this.saleTotal;
	}

	public void setSaleTotal(Double saleTotal) {
		this.saleTotal = saleTotal;
	}

	@Override
	public int compareTo(Sale other) {
		return this.saleTotal.compareTo(other.saleTotal);
	}
	
}
