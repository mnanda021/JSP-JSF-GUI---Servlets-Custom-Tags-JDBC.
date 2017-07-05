/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaGUI;
import javax.swing.JOptionPane;
/**
 *
 * @author mnanda021
 */
public class TestMainClass {
    
    public static void main(String []args){
        JDBCconnection.DBConnection();
        //String []params={"0982345","My Book","7","Lui","Jiang","2000"};
        //JDBCconnection.AddData("123490567", "My Data", "8", "Lui_new", "Jiang_new", "2000");
        //JDBCconnection.UpdateData(params);
        JDBCconnection.DeleteData("123490567");
        System.out.println("Deleted SuccessFully");
        JOptionPane.showMessageDialog(null, "Deleted Book");
    }
}
