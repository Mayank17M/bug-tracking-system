package org.example.bug_tracking_system.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.bug_tracking_system.service.AuthService;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		AuthService as = new AuthService();

		if (as.registerUser(req.getParameter("email"), req.getParameter("password"), req.getParameter("role")))
			req.setAttribute("registerMessage", "Registration successful");
		else
			req.setAttribute("registerMessage", "Registration failed!" +
					"\nCheck if the user is imported and not already registered");

		req.getRequestDispatcher("WEB-INF/registermsg.jsp").forward(req, res);
	}

}
