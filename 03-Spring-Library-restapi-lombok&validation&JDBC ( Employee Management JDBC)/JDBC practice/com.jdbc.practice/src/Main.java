import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");

        DBConnection.getConnection();
        // hall table created inside demoDB
        HallDAO.createHallTable();

        System.out.println("Enter your choice");
        System.out.println("1. See the Hall table content");
        System.out.println("2. Enter the details into the Hall Table");
        System.out.println("3. Update contact detail of a particular owner");
        System.out.println("4. Delete a particular owner");

        HallDAO hdao = new HallDAO();

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice)
        {
            case 1 :
                    List<Hall> halls = hdao.getHallList();
                    for( Hall h : halls)
                    {
                        System.out.println(h.getId()+",'"+h.getName()+"','"+h.getContactDetail()+"',"+h.getCostPerDay()+",'"+h.getOwner()+"'");
                    }
                break;
            case 2 :
                System.out.println("Enter the no of hall to be inserted");

                int n = sc.nextInt();
                sc.nextLine();

                System.out.println("Enter the hall details in (hallName , contactNo  , cost , OwnerName) format");
                // Ram Setu,9620922432,15000,Ram
                // Ram Bhavan,9620772432,12000,Sam
                // Bhavan,8920922432,1000,Arindam

                List<Hall> hallList = new ArrayList<>();
                for( int i =0;i<n;i++ )
                {
                    System.out.println("Enter the hall "+(i+1)+" details :");
                    String str = sc.nextLine();

                    String stra[] = str.split("[,]");
                    Hall newHall = new Hall(stra[0] , stra[1] , Double.parseDouble(stra[2]) ,stra[3]);
                    hallList.add(newHall);

                }
                hdao.bulkInsert(hallList);
                break;
            case 3 :
                System.out.println("Enter the owner name for which the contact to be updated");
                String ownerName = sc.nextLine();
                System.out.println("Enter the new contact detail of "+ownerName);
                String newContact = sc.nextLine();
                hdao.updateHall(ownerName, newContact);
                break;
            case 4 :
                System.out.println("Enter the owner name for which the hall to be deleted");
                String ownerName2 = sc.nextLine();
                hdao.deleteHall(ownerName2);
                break;
            default:
                System.out.println("Invalid choice");
        }




    }
}