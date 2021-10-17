//Course: CPS2231-01
//Name: Jingyi Song
//E-mail: jingyis@kean.edu

//planning time: 2 hours
//coding time:  15 hours
//testing time:   5 hours
//bug fixing time:  10 hours

import java.io.IOException;
import java.util.Scanner;

public class jingyis_Program5 {
	public static void main(String[] args)throws IOException {
		
		String URLString = "http://yoda.kean.edu/~pawang/CPS2231/program5_customers.txt";
		try {	
			java.net.URL url = new java.net.URL(URLString);
			int number= 0;

			Scanner input = new Scanner(url.openStream());
			number=input.nextInt();
			
			Customer[]CustomerArray=new Customer[number];
			
			for(int i=0; i<CustomerArray.length; i++) {
				int Customer_ID=input.nextInt();
				String name=input.next();
				String EmailAddress=input.next();
				double Bill_balance=input.nextDouble();
				if(input.hasNextDouble()) {
					double taxpercent=input.nextDouble();
					CustomerArray[i]=new non_tax_exempt(Customer_ID,name,EmailAddress,Bill_balance,taxpercent);
				}
				else {
					String type=input.next();
					CustomerArray[i]=new tax_exempt(Customer_ID,name,EmailAddress,Bill_balance,type);
				}				
			}	
			
			for(int i=0; i<number; i++) {
				if(i==0) {
					printPageHeader(1);
				}
				else if(i==50){
					printPageHeader(2);
				}
				java.util.Arrays.sort(CustomerArray) ;
				System.out.println(CustomerArray[i]);
			}
		}
		
		catch (java.net.MalformedURLException ex) {
			System.out.println("Invalid URL");
		}
		
		catch (java.io.IOException ex) {
			System.out.println("I/O Errors: no such file");
		}
	}
	public static void printPageHeader(int pageNumber) {
		System.out.println();
		System.out.println();
		System.out.printf("%63s %25s %2d \n", "Office Supplies Inc Customer Report", "Page", pageNumber);
		System.out.printf("%63s \n\n", "===================================");
	
		System.out.printf("%-20s", "Customer");
		System.out.printf("%-6s", "ID");
		System.out.printf("%-30s", "Email Address");
		System.out.printf("%15s", "Balance");
		System.out.printf("%15s", "Tax Type");
		System.out.printf("%15s", "Tax Amount");
		System.out.println();
		System.out.printf("%-20s", "========");
		System.out.printf("%-6s", "==");
		System.out.printf("%-30s", "=============");
		System.out.printf("%15s", "=======");
		System.out.printf("%15s", "========");
		System.out.printf("%15s", "==========");
		System.out.println();
	}
}

class Customer implements Comparable<Customer>{

	private int Customer_ID;
	private String name;
	private String EmailAddress;
	private double Bill_balance;
	
	Customer(){}
	Customer(int Customer_ID,String name,String EmailAddress,double Bill_balance){
		this.Customer_ID=Customer_ID;
		this.name=name;
		this.EmailAddress=EmailAddress;
		this.Bill_balance=Bill_balance;
	}
	
	public int getCustomer_ID() {return Customer_ID;}
	public String getname() {return name;}
	public String getEmailAddress() {return EmailAddress;}
	public double getBill_balance() {return Bill_balance;}
	
	public void setCustomer_ID(int Customer_ID) {    
		this.Customer_ID = Customer_ID;
	}
	
	public void setname(String name) {    
		this.name = name;
	}
	
	public void setEmailAddress(String EmailAddress) {    
		this.EmailAddress = EmailAddress;
	}
	
	public void setBill_balance(double Bill_balance) {    
		this.Bill_balance = Bill_balance;
	}
	
	public String balance(double Bill_balance) {    
		int total=(int)(Bill_balance*100);
		String balance=total+"";
		if(balance.length()>5) {
			String first=(int)(total/100000)+"";
		    String second=balance.substring(first.length(),first.length()+3);
		    String third=balance.substring(balance.length()-2,balance.length());
		    return first+","+second+"."+third;
		}
		else {
			balance=total/100+"";
			return balance+"."+balance.substring(balance.length()-2,balance.length());
		}
	}
	
	public String toString() {				
		return String.format("%-20s%-6s%-30s%15s",name,Customer_ID,EmailAddress,balance(Bill_balance));  	        			  
	}
	
	public int compareTo(Customer o) {
		  if (Customer_ID > o.Customer_ID)
			  return 1;
		  else if (Customer_ID < o.Customer_ID)
			  return -1;
		  else
		  return 0;
	}
}
class tax_exempt extends Customer {

	private String type;
	
	public tax_exempt() {super();}
	public tax_exempt(int Customer_ID,String name,String EmailAddress,double Bill_balance,String type) {
		super(Customer_ID,name,EmailAddress,Bill_balance);
		this.type=type;
	}
	
	public String gettype() {return type;}	
	public void settype(String type) {    
		this.type = type;
	}
	
	@Override
	public String toString() {				
		return super.toString()+String.format("%15s%15s",gettype(),"  ");  	        			  
	}
	}
	
	class non_tax_exempt extends Customer {
	
	private double taxpercent;
	private double tax_Amount;
	
	public non_tax_exempt() {super();}
	public non_tax_exempt(int Customer_ID,String name,String EmailAddress,double Bill_balance,double taxpercent) {
		super(Customer_ID,name,EmailAddress,Bill_balance);
		this.taxpercent=taxpercent;
	}
	
	public double gettaxpercent() {return taxpercent;}	
	public void settaxpercent(double taxpercent) {    
		this.taxpercent = taxpercent;
	}
	
	public double tax_Amount(double taxpercent) {    
		double tax_Amount=taxpercent*getBill_balance();
		return tax_Amount;
	}
	
	public String tax(double tax_Amount) {    
		int total=(int)(tax_Amount*100);
		String tax=total+"";
		if(tax.length()>5) {
			String first=(int)(total/100000)+"";
		    String second=tax.substring(first.length(),first.length()+3);
		    String third=tax.substring(tax.length()-2,tax.length());	
		    return first+","+second+"."+third;
		}
		else{	
			String Total=Math.round(tax_Amount*100)+"";
			String first=Total.substring(Total.length()-2,Total.length());
			return Math.round(tax_Amount*100)/100+"."+first;
		}
	}
	
	@Override
	public String toString() {				
		return super.toString()+String.format("%15s%15s","  ",tax(tax_Amount(taxpercent)));  	        			  
	}
}
