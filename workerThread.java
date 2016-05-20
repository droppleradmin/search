package com.dropplermusic.concurrent;

import com.dropplermusic.search.*;
//import com.mysql.jdbc.Connection;
import java.sql.*;
import java.util.TreeMap;

public class workerThread implements Runnable{
	
	static boolean debug = true;
	Connection db;
	
	public workerThread(){
		//PopulatorTool.connectToDb();
		db = PopulatorTool.connectToDb();
	}
	public void run() {
		TreeMap<String, String[][]> newMap = generateStep(PopulatorTool.populatorQueue.poll(),db);
	}
	
	public static TreeMap<String, String[][]> generateStep(String prefix, Connection db){
			
			TreeMap<String, String[][]> newMap = new TreeMap<String, String[][]>();
			
			try{
				Statement artistQuery = db.createStatement();
				Statement trackQuery = db.createStatement();
				Statement albumQuery = db.createStatement();
				
				for(char postfix = 'a'; postfix <= 'z'; postfix++){
					long begining = System.currentTimeMillis();
					//String artistQ = "SELECT name, popularity FROM artist WHERE MATCH (`name`) AGAINST (\"%s\" IN NATURAL LANGUAGE MODE) ORDER BY popularity DESC LIMIT 5;";
					String artistQ = "select * from artist where name Like \"%s\" Order By Popularity DESC LIMIT 5";
					artistQ = String.format(artistQ,"%"+prefix+postfix+"%");
					PreparedStatement artStatement = db.prepareStatement(artistQ);
					
					//String trackQ = "SELECT name, popularity FROM track WHERE MATCH (`name`) AGAINST (\"%s\" IN NATURAL LANGUAGE MODE) ORDER BY popularity DESC LIMIT 5;";
					String trackQ = "select * from track where name Like '%s' Order By Popularity DESC LIMIT 5";
					trackQ = String.format(trackQ,"%"+prefix+postfix+"%");
					PreparedStatement trackStatement = db.prepareStatement(trackQ);
					
					//String albumQ = "SELECT name, popularity FROM album WHERE MATCH (`name`) AGAINST (\"%s\" IN NATURAL LANGUAGE MODE) ORDER BY popularity DESC LIMIT 5;";
					String albumQ = "select * from album where name Like '%s' Order By Popularity DESC LIMIT 5";
					albumQ = String.format(albumQ,"%"+prefix+postfix+"%");
					PreparedStatement albumStatement = db.prepareStatement(albumQ);
					
					ResultSet artistResult = artStatement.executeQuery(); 
					ResultSet trackResult = trackStatement.executeQuery();
					ResultSet albumResult = albumStatement.executeQuery();
					
					String[] topArtist = new String[5];
					String[] topTrack = new String[5];
					String[] topAlbum = new String[5];
					
					for(int a=0; a<5;a++){
						if(artistResult.next()){
							topArtist[a] = artistResult.getString("name");
							//System.out.println("adding to array");
						}
						if(trackResult.next()) topTrack[a] = trackResult.getString("name");
						if(albumResult.next()) topAlbum[a] = albumResult.getString("name");
					}
					
					newMap.put(String.valueOf(prefix+postfix),new String[][]{topArtist,topTrack,topAlbum});
					//System.out.println(newMap.values());
					if(debug){
						System.out.println(System.currentTimeMillis()-begining);
						for(String[] ss: newMap.get(prefix+postfix)){
							//for(String[] ss: s){
								System.out.println("Entry for search string '"+prefix+postfix+"': ");
								for(String b: ss){
									System.out.print(b+", ");
								}
								System.out.println("");
								System.out.println(ss.length);
							//}
							//System.out.println(s.length);
						}
					}
					
					topArtist=null;
					topTrack=null;
					topAlbum=null;
					
					
					artistResult.close();
					trackResult.close();
					albumResult.close();
				}
				
				artistQuery.close();
				trackQuery.close();
				albumQuery.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			finally{
			}
			return newMap;
		}
}
