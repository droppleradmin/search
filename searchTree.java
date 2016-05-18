package com.dropplermusic.search;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.sql.*;

import com.dropplermusic.search.treePopTool.*;

/**
 * Servlet implementation class quickSearch
 */
@WebServlet("/quickSearch")
public class quickSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private String message;
	
	static final String JDBC_Driver = null; //Not sure yet, should be something like "com.mysql.jdbc.driver"
	static final String DB_URL = "jdbc:mysql://beatlist.cbifx5hgposw.us-west-2.rds.amazonaws.com/main";
	static final String USER = "blessing";
	static final String PASS = "temppassword";
	
	TreeMap searchMap = null;
	
    public quickSearch() {
        super();
        // TODO Auto-generated constructor stub
        
        searchMap = generate();	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: Test: Cherry Slurpee: ").append(request.getContextPath());
		//System.out.println(searchMap.entrySet().toString());
		
		response.getWriter().append(String.valueOf(searchMap.size())); //For Testing
		
		//for response.getWriter().append(searchMap.get(request.getHeader("search").toString());
	}
	
	public static TreeMap generate(){
		return treePopTool.generate();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
