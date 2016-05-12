import java.io.*;
import java.servlet.*;
import java.servlet.http.*; //import

import java.util.*; //import TreeMap

import java.sql.*; //import JDBC

import treePopTool.*; //I AM NOT SURE IF IMPORTS WORK THIS WAY

public class searchTree extends HttpServlet {
	
	private String message;
	
	//Python connect line for reference
	//_mysql.connect("beatlist.cbifx5hgposw.us-west-2.rds.amazonaws.com","blessing","temppassword",db="stage")
	static final String JDBC_Driver = null; //Not sure yet, should be something like "com.mysql.jdbc.driver"
	static final String DB_URL = "jdbc:mysql://beatlist.cbifx5hgposw.us-west-2.rds.amazonaws.com/main";
	static final String USER = "blessing";
	static final String PASS = "temppassword";
	
	TreeMap searchMap = null;
	
	public void init() throws ServletException
	{
		//LOAD DATA STRUCTURE
		searchMap = generate();	
	}
	
	public static TreeMap generate(){
		treePopTool.generate();
	}

	public void doGet(httpServletRequest request, HttpServletResponse response) throws ServletExceptoin, IOException{	
		//Setting response type
		response.setContentType("test/html");
		//The traversal will go here
		System.out.println(searchMap.entrySet().toString());
	}
	
	public void destroy()
	{
		//pass- pretty sure
	}
}