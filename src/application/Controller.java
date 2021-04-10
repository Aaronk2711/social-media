package application;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import social.User;
import social.UserDAO;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	
	private UserDAO dao;
	private User current;
	
	public void init() {
		final String url = getServletContext().getInitParameter("JDBC-URL");
		final String username = getServletContext().getInitParameter("JDBC-USERNAME");
		final String password = getServletContext().getInitParameter("JDBC-PASSWORD");
		
		dao = new UserDAO(url, username, password);
		current = null;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getServletPath();
		
		try {
			switch (action) {
			case "/login": login(request, response, false); break;
			case "/view": viewUsers(request, response); break;
			case "/edit": showEditForm(request, response); break;
			case "/update": updateUser(request, response); break;
			case "/pic": updatePic(request, response); break;
			case "/logout": logout(request, response); break;
			default: login(request, response, true); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response, boolean first) throws SQLException, ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			User user = dao.login(email, password);
			String dest = "login.jsp";
			
			if (user != null) {
				dao.updateLoginTime(user);
				current = user;
				response.sendRedirect(request.getContextPath() + "/view?id=" + user.getId());
			} else {
				String message = (first) ? "" : "Invalid email/password";
				
				request.setAttribute("message", message);
				RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
				dispatcher.forward(request, response);
			}
		} catch (SQLException y) {
			throw new ServletException(y);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		current = null;
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void updatePic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		if(ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> parts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for(FileItem thing : parts) {
					if(thing.isFormField() == false) {
						byte[] info = thing.get();
						current.setProfilePic(info);
					}
				}
			} catch (Exception y) {
				request.setAttribute("message", "File upload failed due to " + y);
			}
		} else {
			request.setAttribute("message", "Sorry, please try again");
		}
		dao.updateUser(current);
		viewUsers(request, response);
	}
	

	private void viewUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final int userId = current.getId();
		 
		
		List<User> allUsers = dao.getOthers(userId);
		List<User> users = new ArrayList<User>();
		users.add(dao.getUser(userId));
		List<User> leftUsers = new ArrayList<User>();
		List<User> rightUsers = new ArrayList<User>();
		for (int x = 0; x < allUsers.size(); x++) {
			if (x % 2 == 0) leftUsers.add(allUsers.get(x));
			if (x % 2 == 1) rightUsers.add(allUsers.get(x));
		}
		
		request.setAttribute("users", users);
		request.setAttribute("leftUsers", leftUsers);
		request.setAttribute("rightUsers", rightUsers);
		
		request.setAttribute("lastLogTime", current.formatInstant(current.getLastLogin()));
		request.setAttribute("lastModTime", current.formatInstant(current.getLastMod()));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
		dispatcher.forward(request, response);
	}
	
	
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
				? request.getParameter("action")
				: request.getParameter("submit").toLowerCase();
		final int id = Integer.parseInt(request.getParameter("id"));
		
		User user = dao.getUser(id);
		switch (action) {
		case "save":
			String firstName = request.getParameter("first_name");
			String lastName = request.getParameter("last_name");
			String college = request.getParameter("college");
			String school = request.getParameter("school");
			String hometown = request.getParameter("hometown");
			LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
			String email = request.getParameter("email");
			Long phone = Long.parseLong(request.getParameter("phone"));
			Instant lastMod = Instant.now();
			String password = request.getParameter("password");
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setCollege(college);
			user.setSchool(school);
			user.setBirthday(birthday);
			user.setHometown(hometown);
			user.setEmail(email);
			user.setPhone(phone);
			user.setLastMod(lastMod);
			user.setPassword(password);
			
			dao.updateUser(user);
			
			
			response.sendRedirect(request.getContextPath() + "/view?id=" + id);
			
			break;
			
			
		case "back":
			response.sendRedirect(request.getContextPath() + "/view?id=" + id);
			break;
		}
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id = current.getId();
		User user = dao.getUser(id);
		
		request.setAttribute("user",  user);
		RequestDispatcher dispatcher = request.getRequestDispatcher("editform.jsp");
		dispatcher.forward(request, response);
		
	
}
}
