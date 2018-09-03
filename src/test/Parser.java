package test;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * @author Alex Ferreira
 *
 */
public class Parser {
	
	private static final String SALESMAN_INFO = "001";
	private static final String CUSTOMER_INFO = "002";
	private static final String SALE_INFO = "003";
	private static final String INPUT_PATH = "data\\in\\";
	private static final String OUTPUT_PATH = "data\\out\\";
	//private static String homePath = System.getenv("HOMEPATH") != null ? System.getenv("HOMEPATH") : "\\";
	private static String homePath = "C:\\test\\";
			
	public static void main(String[] args) {
		try {
			String filesPath = homePath + INPUT_PATH;
			parseDatFiles(filesPath);
			System.out.println("Arquivos .dat de entrada processados");
		} catch (Exception e) { //this can be improved for different errors
			System.out.println("Erro ao processar arquivos .dat");
			System.out.println(e.getMessage());
		}
	}
	
	private static List<File> getInputDatFiles(String filesPath) {
		File filesDir = new File(filesPath);
		List<File> filesList = Arrays.asList(filesDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File filesDir, String fileName) {
				return fileName.endsWith(".dat");
			}
		}));
		return filesList;
	}
	
	private static void parseDatFiles(String filesPath) throws Exception {
		List<File> filesList = getInputDatFiles(filesPath);
		List <Customer> customerInfoList = new ArrayList<Customer>();
		List <Representative> salesmanInfoList = new ArrayList<Representative>();
		List <Sale> salesInfoList = new ArrayList<Sale>();
		for (File aFile : filesList) {
			Scanner content = new Scanner(aFile);
			while (content.hasNextLine()) {
				String aLine = content.nextLine();
				Customer customerInfo = parseCustomerInfo(aLine);
				Representative salesmanInfo = parseRepInfo(aLine);
				Sale salesInfo = parseSalesInfo(aLine);
				if (customerInfo != null) customerInfoList.add(customerInfo);
				if (salesmanInfo != null) salesmanInfoList.add(salesmanInfo);
				if (salesInfo != null) salesInfoList.add(salesInfo);
			}
		}
		generateReport(customerInfoList, salesmanInfoList, salesInfoList);
	}
	
	private static Customer parseCustomerInfo(String aLine) {
		if (!aLine.startsWith(CUSTOMER_INFO)) return null; //not a customer
		String[] data = aLine.split("ç");
		if (data.length != 4) return null; //invalid line data
		return new Customer(data[1], data[2], data[3]);
	}

	private static Representative parseRepInfo(String aLine) {
		if (!aLine.startsWith(SALESMAN_INFO)) return null; //not a salesman
		String[] data = aLine.split("ç");
		if (data.length != 4) return null; //invalid line data
		return new Representative(data[1], data[2], Double.parseDouble(data[3]));
	}
	
	private static Sale parseSalesInfo(String aLine) {
		if (!aLine.startsWith(SALE_INFO)) return null; //not a sale
		String[] saleData = aLine.split("ç");
		if (saleData.length != 4) return null;  //invalid line data
		String itemsLine = saleData[2].substring(saleData[2].indexOf("[")+1, saleData[2].indexOf("]"));
		String[] itemsData = itemsLine.split(",");
		List<Item> itemsList = new ArrayList<Item>();
		for (int i = 0; i < itemsData.length; i++) {
			String[] itemData = itemsData[i].split("-");
			Item anItem = new Item(Long.parseLong(itemData[0]), Integer.parseInt(itemData[1]), Double.parseDouble(itemData[2]));
			itemsList.add(anItem);
		}
		return new Sale(Long.parseLong(saleData[1]), itemsList, saleData[3]);
	}	
	
	private static void generateReport(List<Customer> customerInfoList, List<Representative> salesmanInfoList,	List<Sale> salesInfoList) throws Exception {
		//total customer info (absolute and unique)
		Integer customerQuant = customerInfoList.size();
		HashMap<String, String> uniqueCustomers = new HashMap<String, String>();
		if (!customerInfoList.isEmpty()) {
			for (Customer aCustomer : customerInfoList) {
				uniqueCustomers.put(aCustomer.getCNPJ(), aCustomer.getName());
			}
		}
		Integer customerQuantUniq = uniqueCustomers.size();
		//total salesman info (absolute and unique)
		Integer salesmanQuant = salesmanInfoList.size();
		HashMap<String, String> uniqueSalesman = new HashMap<String, String>();
		if (!salesmanInfoList.isEmpty()) {
			for (Representative	aSalesman : salesmanInfoList) {
				uniqueSalesman.put(aSalesman.getCPF(), aSalesman.getName());
			}
		}
		Integer salesmanQuantUniq = uniqueSalesman.size();
		//process sales info
		Long mostExpensiveSale = 0L;
		String leastEffectiveSalesman = "Sem info de vendas";
		HashMap<String, Double> salesmanTotal = new HashMap<String, Double>() ;
		if (!salesInfoList.isEmpty()) {
			for (Sale aSale : salesInfoList) {
				List<Item> saleItems = aSale.getItems();
				Double saleTotal = 0D;
				for (Item anItem : saleItems) {
					saleTotal = saleTotal + (anItem.getUnitPrice() * anItem.getQuantity());
				}
				aSale.setSaleTotal(saleTotal);
				String salesman = aSale.getSalesRepName();
				if (salesmanTotal.containsKey(salesman)) {
					Double salesSum = salesmanTotal.get(salesman) + saleTotal;
					salesmanTotal.put(salesman, salesSum);
				} else {
					salesmanTotal.put(salesman, saleTotal);
				}
			}
			//sort sales list by sale total to find greatest sale value
			Collections.sort(salesInfoList);
			mostExpensiveSale = salesInfoList.get(salesInfoList.size()-1).getSaleId();
			//get salesman with least sales total
			Double minSaleValue = Collections.min(salesmanTotal.values());
			for (Entry<String, Double> entry : salesmanTotal.entrySet()) {
				if (entry.getValue() == minSaleValue)
					leastEffectiveSalesman = entry.getKey();
			}
		}
		//generate report file
		String lineSeparator = System.getProperty("line.separator");
		String filesPath = homePath + OUTPUT_PATH;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = filesPath+(sdf.format(new Date()))+".done.dat";
		FileWriter outputFile = new FileWriter(fileName);
		outputFile.write("- Arquivos .dat de entrada processados -" + lineSeparator + 
				"Quantidade de clientes (não únicos): "+Integer.toString(customerQuant) + " (únicos): " + Integer.toString(customerQuantUniq) + lineSeparator +
				"Quantidade de vendedores (não únicos): " + Integer.toString(salesmanQuant) + " (únicos): " + Integer.toString(salesmanQuantUniq) + lineSeparator +
				"ID da venda mais cara processada: "+((mostExpensiveSale != 0L) ? Long.toString(mostExpensiveSale) : "Sem info de vendas")+lineSeparator+
				"Pior vendedor (menor total dentre as vendas processadas): "+leastEffectiveSalesman+lineSeparator);
		outputFile.close();
	}

}
