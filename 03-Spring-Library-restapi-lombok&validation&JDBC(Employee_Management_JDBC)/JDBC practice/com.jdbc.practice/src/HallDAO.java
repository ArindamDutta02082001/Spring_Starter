import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD
 * Create - create a hall table inside demoDB  - createHallTable fn
 * Read - getting all halls - getHallList
 * Update - inserting the rows in bulk | update contact details of a user ,
 * Delete - deleting a hall row based on owner name
 */
public class HallDAO {

    public static void createHallTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        String squery = "CREATE TABLE IF NOT EXISTS Hall ( id INTEGER NOT NULL , name VARCHAR(255) NOT NULL , contact_detail VARCHAR(255) , cost_per_day DOUBLE NOT NULL , owner VARCHAR(255) NOT NULL , PRIMARY KEY(id))";
        String data1 = "INSERT INTO Hall VALUES ( 1 , 'Louis Hall' , '808816876' , 150000 , 'Beranrd')";
        String data2 = "INSERT INTO Hall VALUES ( 2 , 'Bugatti Hall' , '808789876' , 190000 , 'Polard')";
        boolean res = statement.execute(squery);
        if(res) {
            System.out.println("Table created");
            statement.execute(data1);
            statement.execute(data2);
        }
        connection.close();
    }

    public void bulkInsert(List<Hall> hallList) throws SQLException {
        Connection connection = DBConnection.getConnection();

        for( Hall h :  hallList)
        {

//            Statement statement = connection.createStatement();
//            List<Hall> totalHall = getHallList();
//            String squery = "INSERT INTO Hall VALUES ("+(totalHall.size()+1)+",'"+h.getName()+"','"+h.getContactDetail()+"',"+h.getCostPerDay()+",'"+h.getOwner()+"')";
//            int rows = statement.executeUpdate(squery);
//            if( rows > 0)
//            {
//                System.out.println("inserted . . .");
//            }
//            else
//            {
//                System.out.println("not inserted . . .");
//
//            }
//              statement.execute("commit");


            String squery = "INSERT INTO Hall VALUES( ? ,? ,? ,? ,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(squery);
            List<Hall> totalHall = getHallList();
            preparedStatement.setLong(1,totalHall.size()+1);
            preparedStatement.setString(2,h.getName());
            preparedStatement.setString(3,h.getContactDetail());
            preparedStatement.setDouble(4 , h.getCostPerDay());
            preparedStatement.setString(5,h.getOwner());
            int rows = preparedStatement.executeUpdate();
            if( rows > 0)
            {
                System.out.println("inserted . . .");
            }
            else
            {
                System.out.println("not inserted . . .");
            }
            //statement.execute("commit");
        }
        connection.close();
    }
    public List<Hall> getHallList() throws SQLException
    {
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        String squery = "SELECT * from Hall";
        ResultSet rs= statement.executeQuery(squery);
        List<Hall> result = new ArrayList<>();
        while( rs.next())
        {
            long id = rs.getLong(1);
            String name = rs.getString(2);
            String contact = rs.getString(3);
            double cost = rs.getDouble(4);
            String owner = rs.getString(5);

            Hall h = new Hall(id , name , contact , cost , owner);
            result.add(h);
        }
        connection.close();
        return result;
    }

    public void deleteHall(String ownerName) throws SQLException {
        Connection connection = DBConnection.getConnection();

        // checking if user exists
        String squery2 = "SELECT * FROM Hall WHERE owner = ? ";
        PreparedStatement preparedStatement1 = connection.prepareStatement(squery2);
        preparedStatement1.setString(1,ownerName);
        ResultSet rs = preparedStatement1.executeQuery();

        if(!rs.next())
        {
            System.out.println("No user exists !");
            return;
        }

//        String squery1 = "DELETE FROM Hall WHERE owner = "+ownerName;
//        Statement statement = connection.createStatement();
//        int rows = statement.executeUpdate(squery1);
//        if(rows > 0)
//        {
//            System.out.println("Row Successfully deleted");
//        }
//        else
//        {
//            System.out.println("Problem in deletion");
//        }

        String squery = "DELETE FROM Hall WHERE owner = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(squery);
        preparedStatement.setString(1,ownerName);
        int rows = preparedStatement.executeUpdate();

        if(rows > 0)
        {
            System.out.println("Row Successfully deleted");
        }
        else
        {
            System.out.println("Problem in deletion");
        }
        connection.close();
    }

    public void updateHall(String ownerName , String newContactDetail) throws SQLException {
        Connection connection = DBConnection.getConnection();

        // checking if user exists
        String squery2 = "SELECT * FROM Hall WHERE owner = ? ";
        PreparedStatement preparedStatement1 = connection.prepareStatement(squery2);
        preparedStatement1.setString(1,ownerName);
        ResultSet rs = preparedStatement1.executeQuery();

        if(!rs.next())
        {
            System.out.println("No user exists !");
            return;
        }

        String squery = "UPDATE Hall SET contact_detail = ? WHERE owner = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(squery);
        preparedStatement.setString(1,newContactDetail);
        preparedStatement.setString(2,ownerName);
        int rows = preparedStatement.executeUpdate();
        if(rows>0)
            System.out.println("Updated contact details of "+ownerName);
        else
            System.out.println("Updation failed . ..");

        connection.close();
    }
}
