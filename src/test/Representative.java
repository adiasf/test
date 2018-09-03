package test;

public class Representative {
	
	private String cpf;
	private String name;
	private Double salary;
	
	public Representative(String cpf, String name, Double salary) {
		this.cpf = cpf;
		this.name = name;
		this.salary = salary;
	}	
	
	public String getCPF() {
		return this.cpf;
	}
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSalary() {
		return this.salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
}
