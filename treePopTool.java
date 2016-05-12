import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*; //For Treemap

import java.sql.*;

public class treePopTool{
	
	static final String JDBC_Driver = null;
	static final String DB_URL = "jdbc:dev2.cbifx5hgposw.us-west-2.rds.amazonaws.com/main";
	static final String USER = "blessing";
	static final String PASS = "temppassword";
	
	public static TreeMap generate(){
		Connection db = null;
		Statement query = null;
		TreeMap newMap = new TreeMap(); 
		try{
			//Registering JDBC driver
			//class.forName(JDBC_Driver);
			
			//Opening connection
			db = DriverManager.getConnection(DB_URL, USER, PASS);
			
			//Query
			query = db.createStatement();
			ResultSet result = query.executeQuery("select * from track where name like '%' LIMIT 1000");
			while(result.next()){
				String Track = result.getString("name");
				newMap.put(Track.subString(0,4),result.getInt("id"));
			}
			result.close();
			query.close();
			db.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for class.forName
			e.printStackTrace();
		}finally{
			//Closing all resources
			try{
				if(query!=null){
					query.close();
				}
			}catch(SQLException se2){
				//null;
			}//This is solutionless
			try{
				if(db!=null){
					db.close();
				}
			}catch(SQLException se){
					se.printStackTrace();
			}
		}
	}
	
	
}