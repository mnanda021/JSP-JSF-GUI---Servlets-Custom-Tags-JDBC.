/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaGUI;

import java.sql.*;
import javax.swing.*;

public class JDBCconnection {
    private static final String url="jdbc:mysql://cs99.bradley.edu:3306/s_mnyamagouda";
    private static final String name="s_mnyamagouda";
    private static final String password="iUfH4LkF";
    ResultSet rs=null;
    Connection con=null;
    PreparedStatement pst=null;
    Object[][] data=null;
    String [] ColumnNames=new String[6];
    public static Connection DBConnection(){
       Connection con=null;     
        try{
            con=DriverManager.getConnection(url,name,password);
            JOptionPane.showMessageDialog(null,"Database Connected Successfully..!!");
            return con;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    public static ResultSet FillCombo2(){
        Connection con;
        try{
            con=DriverManager.getConnection(url,name,password);
            String sql="SHOW TABLES";
            PreparedStatement pst2=con.prepareStatement(sql);
            ResultSet rs=pst2.executeQuery();
           return rs;
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
   /* public static ResultSet FillCombo3(String ComboItem){
        Connection con;
        try{
            con=DriverManager.getConnection(url,name,password);
            String sql="SHOW COLUMNS FROM "+ComboItem+"";
            PreparedStatement pst2=con.prepareStatement(sql);
            ResultSet rs=pst2.executeQuery();
            
           return rs;
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }*/
    public static void AddData(String ISBN,String Title,String Editionno,String Fname,String Lname,String Copyright){    
    Connection con=null;
    try{
        con=DriverManager.getConnection(url,name,password);
        String query1 = "INSERT INTO Titles(ISBN, TITLE, EditionNumber,Copyright) values('"+ISBN+"','"+Title+"','"+Editionno+"','"+Copyright+"')";
        PreparedStatement pst1 = con.prepareStatement(query1);	
        String query2 = "INSERT INTO Authors(FirstName, LastName) values('"+Fname+"' , '"+Lname+"' ) ";
        PreparedStatement pst2 = con.prepareStatement(query2);
        
        //JOptionPane.showMessageDialog(null, "executed1!");
         String sql1=" SELECT ISBN from Titles where Title='"+Title+"' and EditionNumber='"+Editionno+"'";
          Statement st1=con.createStatement();
            ResultSet rs=st1.executeQuery(sql1);
            int Book_isbn;
            if(rs.next()){
                Book_isbn=rs.getInt("ISBN");
               // JOptionPane.showMessageDialog(null, "Executed 1!"+Book_isbn);
              //JOptionPane.showMessageDialog(null, "Executed 1..Book written by!");
                
            String sql2= "SELECT AuthorID from Authors WHERE FirstName= '"+Fname+"' AND LastName='"+Lname+"' ";
            Statement st2=con.createStatement();
            ResultSet rs1=st2.executeQuery(sql2);
            //JOptionPane.showMessageDialog(null, "Executed2!");
            int authId;
                int autoIncKeyFromFunc = -1;
                if(rs1.next()){
         
                    authId=rs1.getInt("AuthorID");
                    String Query3=" INSERT INTO AuthorISBN(AuthorID,ISBN) VALUES ('"+authId+"','"+Book_isbn+"')";
                    st2.executeUpdate(Query3);
                   // JOptionPane.showMessageDialog(null, "executed3!");
        //JOptionPane.showMessageDialog(null, "Executed 1..Book read by!");
                }
                else{
                     pst2.executeUpdate();           
                    ResultSet rs3 =pst2.executeQuery("SELECT LAST_INSERT_ID()");
                    if (rs3.next()) {
                     autoIncKeyFromFunc = rs3.getInt(1);
                    String Query4=" INSERT INTO AuthorISBN(AuthorID,ISBN) VALUES ('"+autoIncKeyFromFunc+"','"+Book_isbn+"')";
                   PreparedStatement st3=con.prepareStatement(Query4);
                    st3.executeUpdate();
                   // JOptionPane.showMessageDialog(null, "executed4!");
                }
             }
            // JOptionPane.showMessageDialog(null, "Book Added Successfully!");
            }
            else 
             {
                 
                 pst1.executeUpdate();
                    String sql2= "SELECT AuthorID from Authors WHERE FirstName= '"+Fname+"' AND LastName='"+Lname+"' ";
                    Statement st2=con.createStatement();
                    ResultSet rs10=st2.executeQuery(sql2);
                    //JOptionPane.showMessageDialog(null, "Executed5!");
                    int authId;
                    int autoIncKeyFromFunc = -1;
                    if(rs10.next()){
         
                            authId=rs10.getInt("AuthorID");
                             String Query5=" INSERT INTO AuthorISBN(AuthorID,ISBN) VALUES ('"+authId+"','"+ISBN+"')";
                            st2.executeUpdate(Query5);
                           // JOptionPane.showMessageDialog(null, "executed6!");
        
                    }
                  
            else{
        
                    pst2.executeUpdate();           
                    Statement st3=con.createStatement();
                    ResultSet rs3 =pst2.executeQuery("SELECT LAST_INSERT_ID()");
                    // JOptionPane.showMessageDialog(null, "executed7!");


                if (rs3.next()) {
                        autoIncKeyFromFunc = rs3.getInt(1);
                        String Query6=" INSERT INTO AuthorISBN(AuthorID,ISBN) VALUES ('"+autoIncKeyFromFunc+"','"+ISBN+"')";
                         st3.executeUpdate(Query6);
       //JOptionPane.showMessageDialog(null, "executed8!");
       }
                    
        }
               
                
            }
            
            
            
       // JOptionPane.showMessageDialog(null, "Book Added Successfully!");
        
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    
    
    }
    public static ResultSet DisplayData(){
            
            Connection con;
            ResultSet rs;
            try{
            con=DriverManager.getConnection(url,name,password);
            String sql="SELECT * FROM Titles";
            PreparedStatement pst=con.prepareStatement(sql);
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            return rs;
           
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
   
   public static ResultSet TableClick(String Table_Click)
        {
            Connection con;
            ResultSet rs;
            try{
            con=DriverManager.getConnection(url,name,password);           
            String sql="SELECT * FROM AuthorISBN where ISBN='"+Table_Click+"' ";
            PreparedStatement pst;
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            int authID;
            if(rs.next()){
                authID=rs.getInt(1);
                 String sql1="select *from Authors where AuthorID='"+authID+"' ";
                  pst=con.prepareStatement(sql1);
                  rs=pst.executeQuery(); 
        
            }
             else{
                 JOptionPane.showMessageDialog(null, "error!");
            }  
            return rs;
            }
        
        catch(SQLException ex){
           ex.printStackTrace();
            return null;
        }
        
        
        }
        
   
   public static ResultSet LoadData1(String ComboItem){
       Connection con;
       try{          
           con=DriverManager.getConnection(url,name,password);
           String sql="SELECT * FROM s_mnyamagouda."+ComboItem;
           PreparedStatement pst=con.prepareStatement(sql);
           ResultSet rs=pst.executeQuery();
           return rs;
           
       }
       catch(SQLException ex){
           JOptionPane.showMessageDialog(null, ex);
           return null;
           
       }
   } 
       public static ResultSet SearchTable(){
           Connection con;
         
           try{
               con=DriverManager.getConnection(url,name,password);
               String sql="select T.ISBN,T.Title,T.EditionNumber,T.Copyright,A.FirstName,A.LastName from Titles as T join AuthorISBN as I on T.ISBN=I.ISBN join Authors as A on I.AuthorID=A.AuthorID ";
               PreparedStatement pst=con.prepareStatement(sql);
               //pst.setString(1, "%"+searchKey+"%");
               ResultSet rs=pst.executeQuery();
               return rs;
               
               }
           catch(SQLException ex){
               JOptionPane.showMessageDialog(null, ex);
           return null;
           
           }
       }
         
       public static ResultSet SearchTable1(){
           Connection con;
         
           try{
               con=DriverManager.getConnection(url,name,password);
               String sql="select T.ISBN,T.Title,T.EditionNumber,T.Copyright,A.AuthorID,A.FirstName,A.LastName from Titles as T join AuthorISBN as I on T.ISBN=I.ISBN join Authors as A on I.AuthorID=A.AuthorID ";
               PreparedStatement pst=con.prepareStatement(sql);
               //pst.setString(1, "%"+searchKey1+"%");
               ResultSet rs=pst.executeQuery();
               return rs;
               
               }
           catch(SQLException ex){
               JOptionPane.showMessageDialog(null, ex);
           return null;
           
           }
       }  
          public static ResultSet SearchTable2(){
           Connection con;
         
           try{
               con=DriverManager.getConnection(url,name,password);
               String sql="select T.ISBN,T.Title,T.EditionNumber,T.Copyright,A.FirstName,A.LastName from Titles as T join AuthorISBN as I on T.ISBN=I.ISBN join Authors as A on I.AuthorID=A.AuthorID ";
               PreparedStatement pst=con.prepareStatement(sql);
               //pst.setString(1, "%"+searchKey1+"%");
               ResultSet rs=pst.executeQuery();
               return rs;
               
               }
           catch(SQLException ex){
               JOptionPane.showMessageDialog(null, ex);
           return null;
           
           }
       }   
       
 public static ResultSet TableClick2(String Table_Click){
       Connection con; 
       try{
            con=DriverManager.getConnection(url,name,password);
           
            String sql="select T.Title,T.EditionNumber,T.Copyright,A.FirstName,A.LastName from Titles as T join AuthorISBN as I on T.ISBN=I.ISBN join Authors as A on I.AuthorID=A.AuthorID where T.ISBN="+Table_Click+"";
           PreparedStatement pst=con.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
          return rs;
            
       } 
       
       catch(SQLException ex){
                     JOptionPane.showMessageDialog(null, ex);
           return null;
                    }


}
// public static void UpdateData(String Table_Click,String BookName,String Edition,String CopyRight,String FirstName,String LastName){
 public static void UpdateData(String params[]){ 
 Connection con;
     try{
         con=DriverManager.getConnection(url,name,password);
         if(params.length==3){
             System.out.println("Length is 3");
		int i = 0;
		String names[] = new String[10];
		for (String name:params)
		{
			names[i] = name;
			System.out.println("values in string array I:"+i+" "+names[i++]);
					
		}
				
		if(names[0].equals("Copyright")|| names[0].equals("EditionNumber")||names[0].equals("Title"))
				{
					
		String query1 = "UPDATE Titles set "+names[0]+"  =?"+" where ISBN = ?";
		System.out.println("Query"+query1);
					//int Authid = Connectionclass.getAuthId(names[0]);
					//System.out.println("AuthorId:"+Authid);
		PreparedStatement pst1 = con.prepareStatement(query1);
		pst1.setString(1, names[1]);
		pst1.setString(2, names[2]);
		pst1.execute();
				}
                else if(names[0].equals("FirstName")|| names[0].equals("LastName"))
		{
                  //  String sql="SELECT AuthorID from Authors as A JOIN AuthorISBN as I on A.AuthorID=I.AuthorID JOIN Titles as T on T.ISBN=I.ISBN where ISBN=?";
		 String sql="SELECT AuthorID FROM AuthorISBN where ISBN= ? ";
                    PreparedStatement pst3=con.prepareStatement(sql);
                pst3.setString(1, names[2]);
                ResultSet rs=pst3.executeQuery();
                int Authid;
                if(rs.next()){
                    
                 Authid=rs.getInt("AuthorID");
                
                //int Authid = Connectionclass.getAuthId(names[2]);
		System.out.println("AuthorId:"+Authid);
		String query2 = "UPDATE Authors set "+names[0]+"= ? where AuthorID = ? ";
		PreparedStatement pst2 = con.prepareStatement(query2);
		pst2.setString(1, names[1]);
		pst2.setInt(2, Authid);
		pst2.execute();
                }
                }
                JOptionPane.showMessageDialog(null, "Updated Successfully");
         }
         else{
             int i = 0;
			String names[] = new String[10];
			for (String name:params)
			{
				names[i] = name;
				System.out.println("values in string array I:"+i+" "+names[i++]);
			}
         System.out.println("values in string array I:"+i+" "+names[i++]);
         String sql="SELECT AuthorID FROM AuthorISBN where ISBN='"+names[0]+"'";
        System.out.println("values in string array I: "+names[0]);
		PreparedStatement pst3=con.prepareStatement(sql);
                //pst3.setString(1, names[0]);
                ResultSet rs=pst3.executeQuery();
                int Authid=0;
                if(rs.next()){
                    
                 Authid=rs.getInt("AuthorID");
                }
             String sql1=" Update Titles SET Title= ? ,EditionNumber= ?,Copyright=? WHERE ISBN= '"+names[0]+"' ";
            // JOptionPane.showMessageDialog(null," Executed  Successfully...!!");
             String sql2=" Update Authors SET FirstName=?,LastName=? WHERE AuthorID=?";
             //JOptionPane.showMessageDialog(null," Second Executed  Successfully...!!");
             System.out.println("values in string array I: "+names[1]);
             PreparedStatement pst1=con.prepareStatement(sql1);
            // PreparedStatement pst1 = conn.prepareStatement(query1);
			pst1.setString(1, names[1]);
			pst1.setString(2,names[2]);
			pst1.setString(3,names[3]);
			//pst1.setString(4,names[0]);
			 pst1.executeUpdate();
             PreparedStatement pst2=con.prepareStatement(sql2);
             pst2.setString(1, names[4]);
            // JOptionPane.showMessageDialog(null," Third Executed  Successfully...!!");
            pst2.setString(2,names[5]);
            pst2.setInt(3, Authid);
            
             pst2.executeUpdate();
             JOptionPane.showMessageDialog(null," Information Updated Successfully...!!");
        }
            
               
            }
         
     
     catch(SQLException ex){
        ex.printStackTrace();
     }
 }
 public static void DeleteData(String Table_Click){
     Connection con;
     try{
         con=DriverManager.getConnection(url,name,password);
         String sql1="DELETE from AuthorISBN where ISBN='"+Table_Click+"'";
             String sql2="DELETE from Titles WHERE ISBN='"+Table_Click+"'";
             
             PreparedStatement pst1=con.prepareStatement(sql1);
             PreparedStatement pst2=con.prepareStatement(sql2);
           
         String sql="SELECT * FROM AuthorISBN WHERE ISBN='"+Table_Click+"'";
         PreparedStatement pst=con.prepareStatement(sql);
         
         ResultSet rs= pst.executeQuery();
         int AuthID;
         //double BookISBN;
         if(rs.next()){
             AuthID=rs.getInt(1);
             //ookISBN=rs.getDouble(2);
             String sql3="DELETE from Authors where AuthorID='"+AuthID+"'"; 
             PreparedStatement pst3=con.prepareStatement(sql3);
                String Query1="SELECT COUNT(ISBN) FROM AuthorISBN WHERE AuthorID='"+AuthID+"'";
                PreparedStatement pst4=con.prepareStatement(Query1);
                ResultSet rs1=pst4.executeQuery();
                if(rs1.next()){
               int isbn=rs1.getInt(1);
              // System.out.println(isbn);
                    if(isbn==0){
                        int confirm = JOptionPane.showConfirmDialog(null, "No books found for this Author, Do you really want to delete this Author?", "Delete", JOptionPane.YES_NO_OPTION);
			if(confirm == 0)
			{
				pst3.execute();
				pst3.close();
                                 JOptionPane.showMessageDialog(null," Book Deleted Successfully...!!");
			}
                    }
                    else if (isbn==1){
                        int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to delete this Author?", "Delete", JOptionPane.YES_NO_OPTION);
			if(confirm == 0)
			{
                                pst1.executeUpdate();
                                pst2.executeUpdate();
				pst3.executeUpdate();
				pst3.close();
                                
                                pst2.close();
                                
                                pst1.close();
                                 JOptionPane.showMessageDialog(null," Book Deleted Successfully...!!");
			}
                    }
                    else
                    {
                        
                 int confirm = JOptionPane.showConfirmDialog(null, "Another Book found for this Author, Do you really want to delete this Author?", "Delete", JOptionPane.YES_NO_OPTION);
			if(confirm == 0)
			{
				pst1.execute();
				pst3.close();
                                pst2.execute();
                                pst2.close();
                          JOptionPane.showMessageDialog(null," Book Deleted Successfully...!!");       
			}
                }
                    
                }
        }
            else{
                JOptionPane.showMessageDialog(null, "Error!!");
            }
             
        
         }
     catch(SQLException ex){
          ex.printStackTrace();
     }
 }
 
 
}
