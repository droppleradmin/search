package com.dropplermusic.search;

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
	
	public static String[] concat(String[] a, String[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   String[] c= new String[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}
	
	public static TreeMap<String, String[]> generate(){
		Connection db = null;
		Statement query = null;
		TreeMap<String, String[]> newMap = new TreeMap<String, String[]>(); 
		try{
			//Registering JDBC driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Opening connection
			db = DriverManager.getConnection(DB_URL, USER, PASS);
			
			//Query
			
			
			//String prefix = "";
			//Query = "select * from artist where name Like '"+prefix+"%' Order By Popularity DESC LIMIT 10000000"
			
			for(char prefix = 'A'; prefix <= 'Z';prefix++) {
				query = db.createStatement();
				ResultSet artistResult = query.executeQuery("select * from artist where name Like '"+prefix+"%' Order By Popularity DESC LIMIT 10");
				ResultSet trackResult = query.executeQuery("select * from track where name Like '"+prefix+"%' Order By Popularity DESC LIMIT 10");
				ResultSet albumResult = query.executeQuery("select * from album where name Like '"+prefix+"%' Order By Popularity DESC LIMIT 10");
				String[] topArtist = new String[3];
				String[] topTrack = new String[3];
				String[] topAlbum = new String[3];
				/*while(result.next()){
					String Artist = result.getString("name");
					newMap.put(Track.substring(0,4),result.getInt("id"));
					newMap.put(Artist.substring(0,1),result.getString("name"));
					
				}*/
				for(int a=0; a<3;a++){
					artistResult.next();
					trackResult.next();
					albumResult.next();

					topArtist[a] = artistResult.getString("name");
					topTrack[a] = trackResult.getString("name");
					topAlbum[a] = albumResult.getString("name");
				}	
				newMap.put(String.valueOf(prefix),concat(concat(topArtist,topTrack),topAlbum));
				artistResult.close();
				trackResult.close();
				albumResult.close();
				query.close();
			}
			
			
			/*for(char alphabet = 'A'; alphabet <= 'Z';alphabet++) {
			    System.out.println(alphabet);
			}*/
				
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
		return newMap;
	}
	
	
}
