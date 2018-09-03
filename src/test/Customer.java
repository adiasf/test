package test;

public class Customer {

	private String cnpj;
	private String name;
	private String businessArea;
	
	public Customer(String cnpj, String name, String businessArea) {
		this.cnpj = cnpj;
		this.name = name;
		this.businessArea = businessArea;
	}

	public String getCNPJ() {
		return this.cnpj;
	}

	public void setCNPJ(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessArea() {
		return this.businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	
	
}
