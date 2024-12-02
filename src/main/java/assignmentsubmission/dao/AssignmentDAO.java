package assignmentsubmission.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import assignmentsubmission.model.Assignment;


public class AssignmentDAO {
	private static final String jdbcUrl = "jdbc:mysql://localhost:3306/student";
    private static final String jdbcUser = "root";
    private static final String jdbcPassword = "root";
    private Connection connection;
    
    public AssignmentDAO() {
        connection = getConnection();
    }

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

         //Create new Assignment
	  	
    public void insertAssignment(String stdname, String aName) throws SQLException {
        try {
            String query = "INSERT INTO assignment (stdname, aName, date) VALUES (?, ?, NOW())";
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, stdname);
            pstmt.setString(2, aName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle the SQLException here
            throw e; // Re-throw the exception to be caught in the calling method
        }
    }


	    // Select all Assignments
	    
	    public List<Assignment> selectAllAssignment() {

	        List<Assignment> assignment = new ArrayList<>();

	        try {

	            String sql = "SELECT * FROM assignment";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            System.out.println(pstmt);

	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                int aid = rs.getInt("aid");
	                String stdname = rs.getString("stdname");
	                String aName = rs.getString("aName");
	                String date = rs.getString("date");

	                assignment.add(new Assignment(aid, stdname, aName, date));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return assignment;
	    }
	    
	    
	    //Select Assignment by aid
	    
	    public static Assignment selectAssignment(int aid) {
	    	Assignment assignment = null;
	        
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM assignment WHERE aid = ?")) {
	            
	            preparedStatement.setInt(1, aid);
	            
	            System.out.println(preparedStatement);
	            
	            ResultSet rs = preparedStatement.executeQuery();
	            
	            while (rs.next()) {
	                String stdname = rs.getString("stdname");
	                String aName = rs.getString("aName");
	                String date = rs.getString("date");
	                
	                assignment = new Assignment(aid, stdname, aName, date);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return assignment;
	    }

	    
	    // Update an Assignment
	    public static void updateAssignment(Assignment std) throws SQLException {
	        try (Connection connection = getConnection();
	             PreparedStatement pstmt = connection.prepareStatement(
	                 "UPDATE assignment SET stdname=?, aName=?, date=? WHERE aid=?")) {

	            pstmt.setString(1, std.getStdname());
	            pstmt.setString(2, std.getaName());
	            pstmt.setString(3, std.getDate());           
	            pstmt.setInt(4, std.getAid());  

	            pstmt.executeUpdate();
	        }
	    }


	    // Delete Assignment
	    public static boolean deleteAssignment(int aid) throws SQLException {
	        boolean rowDeleted;
	        
	        try (Connection connection = getConnection();
	        		PreparedStatement pstmt = connection.prepareStatement("DELETE FROM assignment WHERE aid = ?");) {
	        	pstmt.setInt(1, aid);
	            rowDeleted = pstmt.executeUpdate() > 0;
	        }
	        return rowDeleted;
	    }
	    
	    //Calculate Total Assignment 
	    public int totalAssignment() {
	    	int totalRows = 0;
	    	
	    	try {
	    		 String sql = "SELECT COUNT(*) AS total_assignment FROM assignment";
	    		 PreparedStatement pstmt = connection.prepareStatement(sql);
	    		 ResultSet resultSet = pstmt.executeQuery();
	    		 if(resultSet.next()) {
	    			 totalRows = resultSet.getInt("total_assignment");
	    		 }
	    		 resultSet.close();
	    		 pstmt.close();
	             connection.close();
	    		
	    	} catch (Exception e) {
	            e.printStackTrace();
	        }
	    	
	    	return totalRows;
	    }


}