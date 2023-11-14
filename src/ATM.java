import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {

    public static class user{
        private String username;
        private int pin;
        private double balance;
        private List<String> transactionHistory;

        public  user(String username, int pin){
            this.username = username;
            this.pin = pin;
            this.balance = 1000.00;
            this.transactionHistory = new ArrayList<>();
        }

        public  String getUsername(){
            return username;
        }

        public int getPin(){
            return  pin;
        }



        public List<String> getTransactionHistory(){
            return transactionHistory;
        }

        public void deposit(double amount){
            if(amount >=0){
                balance += amount;
                transactionHistory.add("Deposit: Rs" + amount);
            }
        }

        public boolean withdraw(double amount){
             if(amount > 0 && amount <= balance){
                 balance -= amount;
                 transactionHistory.add("Withdraw: Rs" + amount);
                 return true;
             }
                  return  false;

        }

        public  void transfer(user recipient, double amount){
            if(amount > 0 && amount <= balance){
                balance -= amount;
                recipient.deposit(amount);
                transactionHistory.add("Transfer to" + recipient.getUsername() + ":$" + amount
                );
            }
        }
    }

    private static List<user> users = new ArrayList<>();

    public static void main(String[] args) {
          user user1 = new user("Aayan", 1999);
          user user2 = new user("Praful", 5845);
          user user3 = new user("Joe", 2234);
          user user4 = new user("Alex", 2020);
          users.add(user1);
          users.add(user2);
          users.add(user3);
          users.add(user4);

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("Enter username:");
            String username = scanner.nextLine();
            System.out.print("Enter PIN: ");
            int pin = Integer.parseInt(scanner.nextLine());
            scanner.nextLine();

            user currentUser = authenticateUser(username, pin);

            if(currentUser != null){
                performTransaction(scanner, currentUser);
            }
            else{
                System.out.println("User not valid. Try again later");
            }
        }

    }

    private static user authenticateUser(String username, int pin) {
        for(user User : users){
            if (User.getUsername().equals(username ) && User.getPin() == pin){
                return User;
            }
        }
        return  null;
    }

    private  static  void performTransaction(Scanner scanner, user User){
        while (true){
            System.out.println("\nWelcome, " + User.getUsername() +"!");
            System.out.println("ATM Menu:");
            System.out.println("1.Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit" );
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            scanner.nextLine();
            System.out.println();

             switch (choice){
                 case 1:
                     System.out.println("Transaction History:");
                     for (String transaction: User.getTransactionHistory()){
                         System.out.println(transaction);
                     }
                     break;

                 case 2:
                     System.out.println("Enter the amount to withdraw: Rs");
                     double withdrawAmount = scanner.nextDouble();
                     scanner.nextLine();
                     boolean withdrawSuccess = User.withdraw(withdrawAmount);
                     if(withdrawSuccess){
                         System.out.println("Withdrawal successful");
                     }
                     else{
                         System.out.println("Invalid withdrawal amount or insufficient balance");
                     }
                     break;

                 case 3:
                     System.out.println("Enter the amount to deposit: Rs");
                     double depositAmount = scanner.nextDouble();
                     scanner.nextLine();
                     User.deposit(depositAmount);
                     System.out.println("Deposit successful");
                     break;

                 case 4:
                     System.out.println("Enter the recipient's username: ");
                     String recipientUsername = scanner.nextLine();
                     System.out.print("Enter the amount to transfer: Rs");
                     double transferAmount = Double.parseDouble(scanner.nextLine());
                     scanner.nextLine();
                     user recipient = findUserByUsername(recipientUsername);
                     if(recipient != null){
                         User.transfer(recipient, transferAmount);
                         System.out.println("Transfer successful");
                     }
                     else{
                         System.out.println("Transfer not found");
                     }
                     break;

                 case 5:
                     System.out.println("Exit");
                     return;
                 default:
                     System.out.println("Invalid choice. Please try again");
             }


        }
    }

    private static user findUserByUsername(String username){
        for (user User: users){
            if(User.getUsername().equals(username)){
                return User;
            }
        }
        return null;
    }
}