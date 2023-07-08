import java.util.*;
import java.io.*;
import java.time.LocalTime;

public class PrabhuBank {
    private String cardNumber;
    private String customerName;
    private int PIN;
    private double balance;

	//Writing Account Data In a File
    void accountDataWrite() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Account.txt"))) {
            String cardNumber = "111222333444";
            String customerName = "Ram Basnet";
            String pin = "1234";
            double balance = 10000.15;

            writer.write("Card Number: " + cardNumber);
            writer.newLine();
            writer.write("Customer Name: " + customerName);
            writer.newLine();
            writer.write("PIN: " + pin);
            writer.newLine();
            writer.write("Balance: " + balance);
            writer.newLine();

            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	//Reading and asigning data in above declared variable
    void accountDataRead() {
        try (FileReader fileReader = new FileReader("Account.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Process each line to extract information
                if (line.startsWith("Card Number:")) {
                    cardNumber = line.substring(line.indexOf(":") + 2);
                    System.out.println("Card Number: " + cardNumber);
                } else if (line.startsWith("Customer Name:")) {
                    customerName = line.substring(line.indexOf(":") + 2);
                    System.out.println("Customer Name: " + customerName);
                } else if (line.startsWith("PIN:")) {
                    PIN = Integer.parseInt(line.substring(line.indexOf(":") + 2));
                    System.out.println("PIN: " + PIN);
                } else if (line.startsWith("Balance:")) {
                    balance = Double.parseDouble(line.substring(line.indexOf(":") + 2));
                    System.out.println("Balance: " + balance);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrabhuBank pb = new PrabhuBank();
		Scanner sc = new Scanner(System.in);
		
        pb.accountDataWrite();
        pb.accountDataRead();

		System.out.println("\n********************Welcome To PrabhuBank ATM******************\n");
        pb.cardNumberValidation(sc);
		
		System.out.println("\n----------------------- Welcome "+pb.customerName+"---------------------");
		pb.choiceMenu(sc);
    }
	
	//Method for cardNumber validation
	void cardNumberValidation(Scanner sc){
		
		System.out.print("Enter Your Card Number : ");
		String cn = sc.nextLine();
		
		if(cardNumber.equals(cn)){
			int count = 3;
			while(count>=1){
				
				try{
					System.out.print("Enter Your PIN Number : ");
					int pin = Integer.parseInt(sc.nextLine());
					if(pin==PIN){
					displayMenu();
					break;
					}else{
						if(count==1){System.out.print("Your Account has been blocked, Please contact our Bank!");--count;}
						else{System.out.println("Wrong PIN! You have "+--count+" attempt left!");}
					}
					}catch(Exception e){
						System.out.println("Enter Digits only!");
					}
				
			}
			
			
		}else{
			System.out.println("Invalid Card Number!");
		}
	}
	
	//Method for displaying menu
	void displayMenu(){
		System.out.println("\n************************Displaying Menu*************************\n");
		System.out.println("1.Balance Inquiry");
		System.out.println("2.Mini Statement");
		System.out.println("3.Change PIN");
		System.out.println("4.Withdraw Balance");
		System.out.println("5.Exit");
	}
	
	//choice
	void choiceMenu(Scanner sc){
		System.out.print("Please ! Select an Option : ");
		int choice = sc.nextInt();
		
		switch(choice){
			case 1:
				balanceInquiry();
				subMenu(sc);
				choiceMenu(sc);
				break;
			case 2:
				miniStatement();
				subMenu(sc);
				choiceMenu(sc);
				break;
			case 3:
				changePin(sc);
				subMenu(sc);
				choiceMenu(sc);
				break;
			case 4:
				withdrawBalance(sc);
				subMenu(sc);
				choiceMenu(sc);
				break;
			case 5:
				System.out.println("Thank You For Your Service!");
				System.exit(0);
				choiceMenu(sc);
				break;
			default:
				System.out.println("Invalid Choice!");
				displayMenu();
				choiceMenu(sc);
				break;
		}
	}
	
	//Balance Inquiry
	void balanceInquiry(){
		System.out.println("************Balance Inquiry************");
		System.out.println("************"+customerName+"****************");
		System.out.printf("\nTotal Balance : %.2f \n",balance);
	}
	
	//Mini Statement
	void miniStatement(){
		System.out.println("************Mini Statement************");
		System.out.println("************Withdraw Details****************");
		try (BufferedReader br = new BufferedReader(new FileReader("miniStatement.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//Change PIN
	void changePin(Scanner sc){
		System.out.println("************Change PIN************");
		System.out.print("Enter Your Old PIN : ");
		int op = sc.nextInt();
		if(op==PIN){
			System.out.print("Enter Your New PIN : ");
			int np = sc.nextInt();
			System.out.print("Confirm Your New PIN : ");
			int npc = sc.nextInt();
			if(np==npc){
				PIN = np;
				System.out.println("PIN changed Successfully! Your New PIN is : "+PIN);
			}else{
				System.out.println("Wrong Confirmation!");
				changePin(sc);
			}
		}else{
			System.out.println("Wrong Old PIN!");
			changePin(sc);
		}
		
	}
	
	//Withdraw Balance
	void withdrawBalance(Scanner sc){
		System.out.println("************Withdraw Balance************");
		System.out.print("Enter Withdraw Amount : ");
		double withdrawAmount = sc.nextDouble();
		
		if(withdrawAmount%500==0){
			if(withdrawAmount<500){
				System.out.print("Required Minimum Balance Rs 500.");
				}else if(withdrawAmount>balance){
				System.out.println("Not Sufficient Balance");
				}else{
				int count=1;
				balance = balance - withdrawAmount;
				try(BufferedWriter wr = new BufferedWriter(new FileWriter("miniStatement.txt"))){
				
				//for (int i = 1; i <= 5; i++) {
					LocalTime currentTime = LocalTime.now();
					System.out.println("Current Time: " + currentTime);
					//wr.write("Transaction: " + i);
					wr.write("Transaction: 1");
					wr.newLine();
					wr.write("Time: " + currentTime);
					wr.newLine();
					wr.write("Name: " + customerName);
					wr.newLine();
					wr.write("Card Number: " + cardNumber);
					wr.newLine();
					wr.write("Withdraw Amount: " + withdrawAmount);
					wr.newLine();
					wr.write("Remaining Amount: " + balance);
					wr.newLine();
					wr.newLine(); // Add an extra newline for separation between sets of data
				//}
				}catch(Exception e){
					
				}
				System.out.println("You have Successfully Withdraw Amount : "+withdrawAmount);
				//count++;
			}
		}else{
			System.out.println("Only Rs.500 and Rs.1000 Notes are available!");
			withdrawBalance(sc);
		}
		
	}
	
	//sub menu 
	void subMenu(Scanner sc){
		System.out.println("1. Main Menu");
		System.out.println("2. Exit");
		
		System.out.print("Please ! Select an Option : ");
		int subChoice = sc.nextInt();
		
		switch(subChoice){
			case 1:
				displayMenu();
				break;
			case 2:
				System.out.println("Thank You For Your Service!");
				System.exit(0);
				break;
			default:
				displayMenu();
				break;
		}
	}

}
		

