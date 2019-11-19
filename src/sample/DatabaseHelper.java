package sample;

import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TimeZone;

public class DatabaseHelper {
    private static DatabaseHelper handler = null;
    private static String dbUrl = null;
    private static String dbUsername = null;
    private static String dbPassword = null;
    private static Connection conn;
    private static Statement stmt;

    static {
        createConnection();
    }

    public static DatabaseHelper getInstance() {
        if (handler == null) {
            handler = new DatabaseHelper();
        }
        return handler;
    }

    private static void createConnection() {
        try {
            FileInputStream fis = new FileInputStream("resources/db.properties");
            Properties p = new Properties();
            p.load(fis);
            dbUrl = (String) p.get("db.url");
            dbUsername = (String) p.get("db.user");
            dbPassword = (String) p.get("db.password");
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("connection made");

        } catch (Exception e) {

            alertMaker.showErrorMessage("Error connecting to database server", "");
        }
    }

    public static void closeConnection() {
        try {
            getInstance().getConnection().close();
        } catch (SQLException e) {
            alertMaker.showErrorMessage("Not able to close Db connection", "");
        }

    }

    public Connection getConnection() {
        return conn;
    }

    public ArrayList<String> getAllRooms() {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        try {
            String query = "SELECT * from bookingsystem.rooms order by roomname";
            rs = execQuery(query);
            while (rs.next())
                list.add(rs.getString("roomname"));
        } catch (Exception e) {
            System.out.println("eerrr");// TODO: handle exception
        }
        return list;

    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery" + ex.getLocalizedMessage());
            return null;
        }

        return result;
    }

    public void deleteRoom(String name) {
        try {
            String query = "delete from  bookingsystem.rooms where roomname=?";
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            int p = preparedStatement.executeUpdate();
            System.out.println("val p " + p);

        } catch (Exception e) {

            alertMaker.showErrorMessage("", "Rooms table deleting room error");
        }
        deletefromMainTable(name);

    }

    private void deletefromMainTable(String name) {
        try {
            String query = "delete from  bookingsystem.main where roomname=?";
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            int p = preparedStatement.executeUpdate();
            System.out.println("val p " + p);
            addToReports(" Room "+ name+" is deleted");

        } catch (Exception e) {

            alertMaker.showErrorMessage("", "Deleting room error");
        }
    }

    public void addRoom(String name) {
        try {
            String query = "Insert into bookingsystem.rooms values (?)";
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            int p = preparedStatement.executeUpdate();
            System.out.println("val p " + p);
            addRoomToMainTable(name);
            addToReports(" Room "+ name+" is added");
            alertMaker.showInfoMessage("Added succesfully", "Room added succesfully", "");

        } catch (Exception e) {

            alertMaker.showErrorMessage("", "Adding room error");
        }

    }

    private void addRoomToMainTable(String name) {
        int[] timeslots = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        try {
            for (String k : days) {
                for (int j : timeslots) {
                    String query = "Insert into bookingsystem.main values (?,?,?,?)";
                    PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, j);
                    preparedStatement.setInt(3, 0);
                    preparedStatement.setString(4, k);
                    int p = preparedStatement.executeUpdate();
                    System.out.println("val p " + p);

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void addUser(String a, String b, String c) {

        try {
            String query = "Insert into bookingsystem.employees values (?,?,?,?)";
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, a);
            preparedStatement.setString(2, b);
            preparedStatement.setString(3, "test123");
            preparedStatement.setString(4, c);
            int p = preparedStatement.executeUpdate();
            System.out.println("val p " + p);
            addToReports(" user "+ a+" with  username "+b+" is added");
            alertMaker.showInfoMessage("Added succesfully", "User added succesfully", "");

        } catch (Exception e) {

            alertMaker.showErrorMessage("", "Adding User error");
        }

    }

    public ArrayList<String> getAllDays() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Monday");
        list.add("Tuesday");
        list.add("Wednesday");
        list.add("Thrusday");
        list.add("Friday");
        list.add("Saturday");
        list.add("Sunday");

        return list;

    }

    public ArrayList<String> getAllTimeSlots() {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        try {
            String query = "SELECT distinct timeslot from bookingsystem.main";
            rs = execQuery(query);
            while (rs.next()) {
                String str = "";
                int s = rs.getInt("timeslot") % 12;
                if (s < 8 && s > 0)
                    str = s + "PM" + " - " + (s + 1) + "PM";
                else {
                    if (s == 0)
                        str = " 12PM - 1PM";
                    else

                        str = s + "AM" + " - " + (s + 1) + "AM";
                }
                list.add(str);
            }
        } catch (Exception e) {
            System.out.println("eerrr");// TODO: handle exception
        }
        return list;

    }

    public void addTimeslot(String a, int i, String c) {
        System.out.println("in add time slot" + a + (i) + c);
        try {
            String query = "update  bookingsystem.main set isAvailable =1 Where roomname=? and timeslot=? and day=?";
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, a);
            preparedStatement.setInt(2, i);
            preparedStatement.setString(3, c);
            int p = preparedStatement.executeUpdate();
            System.out.println("val p " + p);
            addToReports(" Time slot no "+ i+" of day "+c+" of room "+a+ " is added");
        } catch (Exception e) {

            alertMaker.showErrorMessage("Failed in deleting questions of this Category", "");
        }

    }

    public ArrayList<usersModel> getAllUsers() {
        ArrayList<usersModel> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String query = "select name,username,password,email from bookingsystem.employees";
            rs = execQuery(query);
            while (rs.next()) {
                usersModel newObj = new usersModel(rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getString("email"));

                list.add(newObj);
            }

        } catch (Exception e) {
            alertMaker.showErrorMessage("Error loading users data", "");
        }
        return list;

    }

    public int deleteUser(usersModel rowToDelete) {
        int p = 0;
        try {
            String query = "delete from  bookingsystem.employees where username=?";
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, rowToDelete.getUsername());
            p = preparedStatement.executeUpdate();
            System.out.println("val p " + p);
            addToReports(" user "+ rowToDelete.getName()+" with  username "+rowToDelete.getUsername()+" is deleted");
        } catch (Exception e) {

            alertMaker.showErrorMessage("", "Deleting room error");
        }
        return p;}

    public void deleteTimeslot(String a, int i, String c) {
        System.out.println("in add time slot" + a + (i) + c);
        try {
            String query = "update  bookingsystem.main set isAvailable =0 Where roomname=? and timeslot=? and day=?";
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, a);
            preparedStatement.setInt(2, i);
            preparedStatement.setString(3, c);
            int p = preparedStatement.executeUpdate();
            System.out.println("val p " + p);
            addToReports(" timeslot no. "+ i+" of room "+a+" is deleted and day is"+c);
        } catch (Exception e) {

            alertMaker.showErrorMessage("Failed in deleting questions of this Category", "");
        }}


    public ArrayList<ReportsModel> getAllReports()  {
        ArrayList<ReportsModel> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String query = "SELECT * from bookingsystem.reports order by creationtime desc";
            rs = execQuery(query);
            while (rs.next())
                list.add(new ReportsModel(rs.getString("message")));
        } catch (Exception e) {
            System.out.println("eerrr");// TODO: handle exception
        }
        return list;

    }
    public void addToReports(String message)
    {

        java.util.Date date= new java.util.Date();
        Timestamp t= new Timestamp(date.getTime());
        try
        {
            String query = "Insert into bookingsystem.reports values (?,?)";
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(query);
            preparedStatement.setTimestamp(1, t);
            preparedStatement.setString(2,message);
            int p = preparedStatement.executeUpdate();
            System.out.println("val p " + p);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

