package assignmentsubmission.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import assignmentsubmission.dao.AssignmentDAO;
import assignmentsubmission.model.Assignment;

public class AssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AssignmentServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();

        try {
            switch (action) {
                case "/addAssignment":
                	addAssignment(request, response);
                    break;

                case "/listAssignment":
                	listAssignment(request, response);
                    break;
                case "/showAssignmentEditForm":
                	showAssignmentEditForm(request, response);
                    break;
                case "/editAssignment":
                	editAssignment(request, response);
                    break;
                case "/delteAssignment":
                	delteAssignment(request, response);
                    break;
                default:
                	listAssignment(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related errors
            response.sendRedirect(request.getContextPath() + "/Error.jsp");
        }
	}
	
	private void addAssignment(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {
	    String stdname = request.getParameter("stdname");
	    String aName = request.getParameter("aName");

	    AssignmentDAO assignmentDAO = new AssignmentDAO();

	    try {
	        // Check if the provided credentials are valid
	        assignmentDAO.insertAssignment(stdname, aName);
	        response.sendRedirect(request.getContextPath() + "/listAssignment");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle database-related errors
	        response.sendRedirect(request.getContextPath() + "/Error.jsp");
	    }
	}
	
		private void listAssignment(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	
		    AssignmentDAO assignmentDAO = new AssignmentDAO(); 
		    List<Assignment> listAssignment = assignmentDAO.selectAllAssignment();
		    request.setAttribute("listAssignment", listAssignment);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/list-assignment.jsp");
		    dispatcher.forward(request, response);
		}
		
		
		private void showAssignmentEditForm(HttpServletRequest request, HttpServletResponse response)
	    	    throws SQLException, ServletException, IOException {
			
	    	    int aid = Integer.parseInt(request.getParameter("id"));
	    	    
	    	    Assignment asg = AssignmentDAO.selectAssignment(aid); 
	    	    RequestDispatcher dispatcher = request.getRequestDispatcher("edit-assignment.jsp");
	    	    request.setAttribute("asg", asg);
	    	    dispatcher.forward(request, response);
	    }
		
		private void editAssignment(HttpServletRequest request, HttpServletResponse response) 
	            throws SQLException, IOException {
	        try {
	        	int aid = Integer.parseInt(request.getParameter("aid"));
	            String stdname = request.getParameter("stdname");
	            String aName = request.getParameter("aName");
	            String date = request.getParameter("date");
	    
	            
	            Assignment assignment = new Assignment(aid, stdname, aName, date);
	            AssignmentDAO.updateAssignment(assignment);
	            
	            response.sendRedirect("listAssignment");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		
		private void delteAssignment(HttpServletRequest request, HttpServletResponse response) 
				throws SQLException, IOException {
			
			int aid = Integer.parseInt(request.getParameter("id"));
			
			AssignmentDAO.deleteAssignment(aid);
			response.sendRedirect("listAssignment");

		}


	

}