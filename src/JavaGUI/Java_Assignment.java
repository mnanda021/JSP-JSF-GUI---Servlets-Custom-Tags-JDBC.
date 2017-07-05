/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaGUI;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.event.TableModelEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author mnanda021
 */
public class Java_Assignment extends javax.swing.JFrame implements NewInterface {

    private JScrollPane jScrollPane4;
    int flagOR,flagAND,flagNOT;
    
    /**
     * Creates new form Java_Assignment
     */
    public Java_Assignment() {
        initComponents();
        CurrentDate();
        //FillCombo2();
        //itemStateChanged(ItemEvent e);
        init();
        JDBCconnection.DBConnection();
      
    }
private void init(){
    setLocationRelativeTo(null);
}

private void CurrentDate(){
        Calendar cal=new GregorianCalendar();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int Day=cal.get(Calendar.DAY_OF_MONTH);
        DateMenu.setText("Date :"+year+"/"+(month+1)+"/"+Day);
        int second=cal.get(Calendar.SECOND);
        int minute=cal.get(Calendar.MINUTE);
        int hour=cal.get(Calendar.HOUR);
        TimeMenu.setText("Time "+hour+":"+(minute)+":"+second);
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
				Changeradiobutton();
			}


public void Changeradiobutton() {
		if(OrRadio1.isSelected())
		{
			flagOR = 1;
			flagAND = 0;
			flagNOT = 0;
		}
		else if(AndRadio1.isSelected())
		{
			flagAND = 1;
			flagOR = 0;
			flagNOT = 0;
			
		}
		else if(NotRadio1.isSelected()){
			
			flagAND = 0;
			flagOR = 0;
			flagNOT = 1;
		}
		
	
}
private void AdvancedSearch(){
    String keyword = ComboBox2.getSelectedItem().toString();
    String keyword_next = ComboBox3.getSelectedItem().toString();
    String searchKey1=Section1Text.getText();
       String searchKey2=Section2Text.getText();
	System.out.println("Keyword"+keyword+"Serachvalue"+searchKey1);
						
	if(!searchKey1.equals("") || !searchKey2.equals(""))
	{
	if(flagOR == 0 && flagAND == 0 || flagNOT == 1)
	{
	if(!keyword.equals("Keyword"))
	{
	ResultSet rs= JDBCconnection.SearchTable1();
							
	Table8.setModel(DbUtils.resultSetToTableModel(rs));
	DefaultTableModel dm;
	dm  = (DefaultTableModel) Table8.getModel();
	TableRowSorter <DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
	Table8.setRowSorter(tr);
	if(flagNOT == 1)
	{
	tr.setRowFilter(RowFilter.notFilter(RowFilter.regexFilter(searchKey1)));
	}
	else
	tr.setRowFilter(RowFilter.regexFilter(searchKey1));
        }
	else if(!keyword_next.equals("Keyword"))
	{
	ResultSet rs = JDBCconnection.SearchTable1();
							
				Table8.setModel(DbUtils.resultSetToTableModel(rs));
				DefaultTableModel dm;
				dm  = (DefaultTableModel) Table8.getModel();
					TableRowSorter <DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
				Table8.setRowSorter(tr);
							
		tr.setRowFilter(RowFilter.regexFilter(searchKey2));


							}
						}
	else if(flagOR == 1 || flagAND == 1 )
	{
        ArrayList<RowFilter<Object,Object>> filters = null; // To store these filter.
        RowFilter<Object, Object> compoundRowFilter = null;
        ResultSet rs1 = JDBCconnection.SearchTable1();
        Table8.setModel(DbUtils.resultSetToTableModel(rs1));
	
		DefaultTableModel dm;
		dm  = (DefaultTableModel) Table8.getModel();
		TableRowSorter <DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
		Table8.setRowSorter(tr);			
		//	RowFilter<Object, Object> firstFilter = null;  // For Column One.				//RowFilter<Object, Object> secondFilter = null; // For Column 
		try{
					
		filters = new ArrayList<RowFilter<Object,Object>>();
                
		filters.add(RowFilter.regexFilter(searchKey1));
		filters.add(RowFilter.regexFilter(searchKey2));
		if(flagAND == 1)
		{
		compoundRowFilter = RowFilter.andFilter(filters);
    }
		else if(flagOR == 1)
{
compoundRowFilter = RowFilter.orFilter(filters);
	}
	else
	{
							
//.setRowFilter(RowFilter.notFilter(RowFilter.regexFilter(Serachvalue)));
	}
	//table.setRowSorter(tr);
	tr.setRowFilter(compoundRowFilter);
	}
                catch(Exception ex){
                    ex.printStackTrace();
                }
                
}
}
}
/*private void FillCombo2(){
    try{
        //String ComboItem=(String)ComboBox3.getSelectedItem();
         ResultSet rs=JDBCconnection.FillCombo2();
     
      while(rs.next()){
                ComboBox2.addItem(rs.getString(1));
            }
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
}*/

private void AddData(){
    
    try{
      
      //  JDBCconnection.DBConnection();
    
           String First_Name=FNameText.getText();
            String Last_Name=LNameText.getText();                               
            String Editionno=EditionText.getText();
            String ISBN=ISBNText.getText();
            String Title=BookNameText.getText();
            String CopyRight=CopyRightText.getText();
            JDBCconnection.AddData(ISBN, Title, Editionno, First_Name, Last_Name, CopyRight);
            JOptionPane.showMessageDialog(null, "Book added Successfully");
    }
    catch(Exception ex){
        ex.printStackTrace();
    }
    
}

    

private void TableClick1(){
   
            DefaultListModel m=new DefaultListModel();
            int row=Table1.getSelectedRow();
            String Table_Click=(String) (Table1.getModel().getValueAt(row, 0));
            String Add1=null;
            String Add2=null;
            String Add3=null;
        try{    
               ResultSet rs=JDBCconnection.TableClick(Table_Click);
               if(rs.next()){
                        Add1=rs.getString("AuthorID");
                        Add2=rs.getString("FirstName");
                        Add3=rs.getString("LastName");
       
        
                        m.addElement("AuthorID: "+Add1);
                        m.addElement("First Name: "+Add2);
                        m.addElement("Last Name: "+Add3);
           }
              
       
               else
               {
                 JOptionPane.showMessageDialog(null, "error!");
            
                 }
                List1.setModel(m);
        }

        catch(Exception ex){
            
          ex.printStackTrace();
        }
}
private void LoadData1(){
    String ComboItem=(String)ComboBox1.getSelectedItem();
    try{
        ResultSet rs=JDBCconnection.LoadData1(ComboItem);
        while(rs.next()){
            rs.previous();
            Table6.setModel(DbUtils.resultSetToTableModel(rs));
        }
    }
    catch(Exception ex){
        ex.printStackTrace();
    //JOptionPane.showMessageDialog(null,ex);
    }
}

private void searchTable(){
   try{
        
        ResultSet rs=JDBCconnection.SearchTable();
        while(rs.next()){
            rs.previous();
            Table2.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        
    }
  catch(Exception ex){
        JOptionPane.showMessageDialog(null,ex);
    }
    try{
        //String ComboItem=(String)ComboBox1.getSelectedItem();
        String searchKey=SearchText1.getText();
      
        ResultSet rs=JDBCconnection.SearchTable();
        DefaultTableModel dm=(DefaultTableModel) Table2.getModel();
        TableRowSorter <DefaultTableModel> tr= new TableRowSorter<DefaultTableModel>(dm);
        Table2.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchKey));
       
        /*(while(rs.next()){
            rs.previous();
            Table2.setModel(DbUtils.resultSetToTableModel(rs));
            
        }*/
        
    }
  catch(Exception ex){
        ex.printStackTrace();
      //JOptionPane.showMessageDialog(null,ex);
    }
    try{
           Vector columnNames=new Vector();
           Vector Data=new Vector();
           ResultSet rs=JDBCconnection.SearchTable2();
           ResultSetMetaData metadata=rs.getMetaData();
           int column=metadata.getColumnCount();
           for(int i=1;i<=column;i++){
               columnNames.addElement(metadata.getColumnName(i));
           }
           
           while(rs.next()){
               Vector row=new Vector(column);
               for(int i=1;i<=column;i++){
                   row.addElement(rs.getObject(i));
                   
               }
               Data.addElement(row);
           }
       NewClass model=new NewClass(Data,columnNames);
       Table2.setModel(model);
      
       }
       catch(Exception ex){
           ex.printStackTrace();
       }
}

private void searchTable1(){
 try{
        
        ResultSet rs=JDBCconnection.SearchTable1();
        while(rs.next()){
            rs.previous();
            Table8.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        
    }
  catch(Exception ex){
       ex.printStackTrace();
      //JOptionPane.showMessageDialog(null,ex);
    }
     try{
         JDBCconnection.SearchTable1();
        String searchkey1=SearchText2.getText();
        DefaultTableModel dm=(DefaultTableModel) Table8.getModel();
        TableRowSorter <DefaultTableModel> tr= new TableRowSorter<DefaultTableModel>(dm);
        Table8.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchkey1));
        
    }catch(Exception ex){
        ex.printStackTrace();
    }
}

private void TableClick2(){
    //String selectTable=(String)ComboBox2.getSelectedItem();
     int row=Table2.getSelectedRow();
            String Table_Click=(String) (Table2.getModel().getValueAt(row, 0));
           
            try{
            ResultSet rs=JDBCconnection.TableClick2(Table_Click);
         while(rs.next()){
             //rs.previous();
                String add1=rs.getString("Title");
                String add2=rs.getString("EditionNumber");
                String add3=rs.getString("Copyright");
                String add4=rs.getString("FirstName");
                String add5=rs.getString("LastName");
                
                BookNameText2.setText(add1);
            EditionText2.setText(add2);
            CopyRightText2.setText(add3);
            FNameText2.setText(add4);
            LNameText2.setText(add5);
                
   
          
           
         }
           
            }
            catch(Exception ex){
ex.printStackTrace();
//  JOptionPane.showMessageDialog(null, ex);
          }
          
            
            
}
private void UpdateData(){
    
    //String params[]=new String[10];
    int row= Table2.getSelectedRow();
    String ISBN = (String)(Table2.getModel().getValueAt(row, 0));
     String Bookname =BookNameText2.getText();
     String Edition =EditionText2.getText();
     String CopyRight =CopyRightText2.getText();
     String Fname =FNameText2.getText();
     String Lname =LNameText2.getText();
     
    
    
   
    int i = 0;
   String names[] ={ISBN,Bookname,Edition,CopyRight,Fname,Lname};
    
     
     
    JDBCconnection.UpdateData(names);
    
    
}
private void DeleteData(){
    int row=Table2.getSelectedRow();
    String Table_Click=(String)Table2.getModel().getValueAt(row, 0);
    JDBCconnection.DeleteData(Table_Click);
}
   /* This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        Table7 = new javax.swing.JTable();
        TabbedPanel1 = new javax.swing.JTabbedPane();
        MaintainPanel = new javax.swing.JPanel();
        TabbedPanel2 = new javax.swing.JTabbedPane();
        InsertPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        EditionLabel = new javax.swing.JLabel();
        BookNameText = new javax.swing.JTextField();
        CopyRightLabel = new javax.swing.JLabel();
        BookNameLabel = new javax.swing.JLabel();
        EditionText = new javax.swing.JTextField();
        CopyRightText = new javax.swing.JTextField();
        LNameLabel = new javax.swing.JLabel();
        ISBNText = new javax.swing.JTextField();
        ISBNLabel = new javax.swing.JLabel();
        FNameLabel = new javax.swing.JLabel();
        LNameText = new javax.swing.JTextField();
        FNameText = new javax.swing.JTextField();
        AddButton = new javax.swing.JButton();
        ClearButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        DisplayButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        List1 = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        UpdatePanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        SearchButton1 = new javax.swing.JButton();
        SearchText1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        LNameText2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        FNameText2 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        EditionText2 = new javax.swing.JTextField();
        BookNameText2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        CopyRightText2 = new javax.swing.JTextField();
        UpdateButton = new javax.swing.JButton();
        ClearButton2 = new javax.swing.JButton();
        LoadButton = new javax.swing.JButton();
        SelectLabel2 = new javax.swing.JLabel();
        DeleteButton = new javax.swing.JButton();
        ComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        Table6 = new javax.swing.JTable();
        BrowsePanel = new javax.swing.JPanel();
        TabbedPanel3 = new javax.swing.JTabbedPane();
        SimpleSearchPanel = new javax.swing.JPanel();
        SearchText2 = new javax.swing.JTextField();
        FilterSearchButton = new javax.swing.JButton();
        ExitButton2 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        Table8 = new javax.swing.JTable();
        ConstraintPanel1 = new javax.swing.JLabel();
        ShowAll = new javax.swing.JButton();
        AdvancedSearch = new javax.swing.JButton();
        ResetButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        AndRadio1 = new javax.swing.JRadioButton();
        TablePanel = new javax.swing.JLabel();
        ComboBox3 = new javax.swing.JComboBox<>();
        Section1Text = new javax.swing.JTextField();
        Section2Text = new javax.swing.JTextField();
        NotRadio1 = new javax.swing.JRadioButton();
        SectionPanel = new javax.swing.JLabel();
        ComboBox2 = new javax.swing.JComboBox<>();
        OrRadio1 = new javax.swing.JRadioButton();
        CheckBox1 = new javax.swing.JCheckBox();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        NewMenu = new javax.swing.JMenuItem();
        CloseMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        DateMenu = new javax.swing.JMenu();
        TimeMenu = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        Table7.setFont(new java.awt.Font("Palatino", 3, 12)); // NOI18N
        Table7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TabbedPanel1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        MaintainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Maintain Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Palatino", 3, 14))); // NOI18N
        MaintainPanel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        TabbedPanel2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        InsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Book", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Palatino", 3, 14))); // NOI18N

        jPanel4.setForeground(new java.awt.Color(255, 153, 153));
        jPanel4.setFont(new java.awt.Font("Palatino", 3, 18)); // NOI18N

        EditionLabel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        EditionLabel.setText("Edition Number");

        BookNameText.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        CopyRightLabel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        CopyRightLabel.setText("Copy Right");

        BookNameLabel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        BookNameLabel.setText("Book Name");

        EditionText.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        CopyRightText.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        CopyRightText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopyRightTextActionPerformed(evt);
            }
        });

        LNameLabel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        LNameLabel.setText("Author LastName");

        ISBNText.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ISBNText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ISBNTextActionPerformed(evt);
            }
        });

        ISBNLabel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ISBNLabel.setText("Book ISBN");

        FNameLabel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        FNameLabel.setText("Author FirstName");

        LNameText.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        FNameText.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ISBNLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BookNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CopyRightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FNameText)
                    .addComponent(EditionText)
                    .addComponent(BookNameText)
                    .addComponent(ISBNText)
                    .addComponent(CopyRightText)
                    .addComponent(LNameText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ISBNLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ISBNText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BookNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BookNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditionText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CopyRightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CopyRightText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AddButton.setFont(new java.awt.Font("Palatino", 3, 18)); // NOI18N
        AddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/application-form-add-icon.png"))); // NOI18N
        AddButton.setText("Add Book");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        ClearButton.setFont(new java.awt.Font("Palatino", 3, 18)); // NOI18N
        ClearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/arrow-refresh-icon.png"))); // NOI18N
        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Palatino", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Enter Information");

        DisplayButton.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        DisplayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/projection-screen-arrow-icon.png"))); // NOI18N
        DisplayButton.setText("Display Data");
        DisplayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisplayButtonActionPerformed(evt);
            }
        });

        List1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Author Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Palatino", 3, 14))); // NOI18N
        List1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        jScrollPane3.setViewportView(List1);

        Table1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(Table1);

        javax.swing.GroupLayout InsertPanelLayout = new javax.swing.GroupLayout(InsertPanel);
        InsertPanel.setLayout(InsertPanelLayout);
        InsertPanelLayout.setHorizontalGroup(
            InsertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InsertPanelLayout.createSequentialGroup()
                .addGroup(InsertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InsertPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(ClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InsertPanelLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InsertPanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(InsertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(InsertPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InsertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(InsertPanelLayout.createSequentialGroup()
                            .addGap(163, 163, 163)
                            .addComponent(DisplayButton))
                        .addGroup(InsertPanelLayout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        InsertPanelLayout.setVerticalGroup(
            InsertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InsertPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(InsertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DisplayButton)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(InsertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InsertPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InsertPanelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(InsertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddButton)
                            .addComponent(ClearButton))))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        TabbedPanel2.addTab("Insert Book", InsertPanel);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Update", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Palatino", 3, 12))); // NOI18N

        SearchButton1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        SearchButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/find.png"))); // NOI18N
        SearchButton1.setText("Load Books");
        SearchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButton1ActionPerformed(evt);
            }
        });

        SearchText1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        SearchText1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchText1ActionPerformed(evt);
            }
        });

        LNameText2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Palatino", 3, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/gnome_mime_text_x_authors.png"))); // NOI18N
        jLabel4.setText("Author Details");

        jLabel7.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        jLabel7.setText("First Name");

        jLabel6.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        jLabel6.setText("Last Name");

        FNameText2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(LNameText2)
                            .addComponent(FNameText2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FNameText2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LNameText2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Palatino", 3, 18)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/book_stack.png"))); // NOI18N
        jLabel11.setText("Book Details");

        jLabel5.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        jLabel5.setText("Copy Right");

        EditionText2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        EditionText2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditionText2ActionPerformed(evt);
            }
        });

        BookNameText2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        jLabel3.setText("Edition Number");

        jLabel2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        jLabel2.setText("Book Name");

        CopyRightText2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(EditionText2)
                            .addComponent(CopyRightText2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(BookNameText2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BookNameText2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditionText2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CopyRightText2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        UpdateButton.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        UpdateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/notebook-pencil-icon.png"))); // NOI18N
        UpdateButton.setText("Update");
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButtonActionPerformed(evt);
            }
        });

        ClearButton2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ClearButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/arrow-refresh-icon.png"))); // NOI18N
        ClearButton2.setText("Reset");
        ClearButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButton2ActionPerformed(evt);
            }
        });

        LoadButton.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        LoadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/load.png"))); // NOI18N
        LoadButton.setText("Load Table");
        LoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadButtonActionPerformed(evt);
            }
        });

        SelectLabel2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        SelectLabel2.setText("Display Tables");

        DeleteButton.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        DeleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/trashcan_delete2_17.png"))); // NOI18N
        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        ComboBox1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Titles", "Authors", "AuthorISBN" }));

        Table2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        Table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table2MouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(Table2);

        Table6.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        Table6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(Table6);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(SearchText1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(SearchButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(SelectLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(LoadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(13, 13, 13))
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(UpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(ClearButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ClearButton2)
                            .addComponent(UpdateButton)
                            .addComponent(DeleteButton)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SearchButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchText1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LoadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SelectLabel2))
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout UpdatePanelLayout = new javax.swing.GroupLayout(UpdatePanel);
        UpdatePanel.setLayout(UpdatePanelLayout);
        UpdatePanelLayout.setHorizontalGroup(
            UpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdatePanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        UpdatePanelLayout.setVerticalGroup(
            UpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabbedPanel2.addTab("Update/Delete", UpdatePanel);

        javax.swing.GroupLayout MaintainPanelLayout = new javax.swing.GroupLayout(MaintainPanel);
        MaintainPanel.setLayout(MaintainPanelLayout);
        MaintainPanelLayout.setHorizontalGroup(
            MaintainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        MaintainPanelLayout.setVerticalGroup(
            MaintainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MaintainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabbedPanel1.addTab("Maintain Library", MaintainPanel);

        TabbedPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BrowseData", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Palatino", 3, 14))); // NOI18N
        TabbedPanel3.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        SearchText2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        FilterSearchButton.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        FilterSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/find.png"))); // NOI18N
        FilterSearchButton.setText("Filter Search");
        FilterSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterSearchButtonActionPerformed(evt);
            }
        });

        ExitButton2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ExitButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/cross.png"))); // NOI18N
        ExitButton2.setText("Exit");
        ExitButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButton2ActionPerformed(evt);
            }
        });

        Table8.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        Table8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Table8.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Table8AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane7.setViewportView(Table8);

        ConstraintPanel1.setFont(new java.awt.Font("Palatino", 3, 18)); // NOI18N
        ConstraintPanel1.setText("Advanced Search");

        ShowAll.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ShowAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/book_stack.png"))); // NOI18N
        ShowAll.setText("Show All Books");
        ShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowAllActionPerformed(evt);
            }
        });

        AdvancedSearch.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        AdvancedSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/find.png"))); // NOI18N
        AdvancedSearch.setText("Search Book");
        AdvancedSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdvancedSearchActionPerformed(evt);
            }
        });

        ResetButton2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ResetButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/Button-Sync-icon.png"))); // NOI18N
        ResetButton2.setText("Reset");
        ResetButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        buttonGroup2.add(AndRadio1);
        AndRadio1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        AndRadio1.setText("AND");
        AndRadio1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AndRadio1ItemStateChanged(evt);
            }
        });

        TablePanel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        TablePanel.setText("Select Section");

        ComboBox3.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Title", "EditionNumber", "CopyRight", "FirstName", "LastName" }));
        ComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox3ActionPerformed(evt);
            }
        });

        Section1Text.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        Section2Text.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        buttonGroup2.add(NotRadio1);
        NotRadio1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        NotRadio1.setText("NOT");
        NotRadio1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NotRadio1ItemStateChanged(evt);
            }
        });

        SectionPanel.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        SectionPanel.setText("Select Section");

        ComboBox2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        ComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Title", "EditionNumber", "CopyRight", "FirstName", "LastName" }));
        ComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboBox2MouseClicked(evt);
            }
        });
        ComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox2ActionPerformed(evt);
            }
        });

        buttonGroup2.add(OrRadio1);
        OrRadio1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        OrRadio1.setText("OR");
        OrRadio1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                OrRadio1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(TablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(SectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Section1Text)
                            .addComponent(Section2Text, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(AndRadio1)
                        .addGap(35, 35, 35)
                        .addComponent(OrRadio1)
                        .addGap(36, 36, 36)
                        .addComponent(NotRadio1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Section1Text, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AndRadio1)
                    .addComponent(OrRadio1)
                    .addComponent(NotRadio1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Section2Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        CheckBox1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        CheckBox1.setText("Click Me");
        CheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CheckBox1ItemStateChanged(evt);
            }
        });
        CheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SimpleSearchPanelLayout = new javax.swing.GroupLayout(SimpleSearchPanel);
        SimpleSearchPanel.setLayout(SimpleSearchPanelLayout);
        SimpleSearchPanelLayout.setHorizontalGroup(
            SimpleSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SimpleSearchPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(SimpleSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SimpleSearchPanelLayout.createSequentialGroup()
                        .addComponent(ConstraintPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckBox1)
                        .addGap(42, 42, 42)
                        .addComponent(AdvancedSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SimpleSearchPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(SimpleSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ResetButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ExitButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ShowAll, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SimpleSearchPanelLayout.createSequentialGroup()
                        .addComponent(SearchText2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(FilterSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 23, Short.MAX_VALUE))
        );
        SimpleSearchPanelLayout.setVerticalGroup(
            SimpleSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SimpleSearchPanelLayout.createSequentialGroup()
                .addGroup(SimpleSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SimpleSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ConstraintPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CheckBox1))
                    .addComponent(AdvancedSearch, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(13, 13, 13)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(SimpleSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchText2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FilterSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(SimpleSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SimpleSearchPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ShowAll)
                        .addGap(18, 18, 18)
                        .addComponent(ResetButton2)
                        .addGap(21, 21, 21)
                        .addComponent(ExitButton2)
                        .addGap(111, 111, 111))
                    .addGroup(SimpleSearchPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        TabbedPanel3.addTab("Simple Browse", SimpleSearchPanel);

        javax.swing.GroupLayout BrowsePanelLayout = new javax.swing.GroupLayout(BrowsePanel);
        BrowsePanel.setLayout(BrowsePanelLayout);
        BrowsePanelLayout.setHorizontalGroup(
            BrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BrowsePanelLayout.createSequentialGroup()
                .addComponent(TabbedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );
        BrowsePanelLayout.setVerticalGroup(
            BrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BrowsePanelLayout.createSequentialGroup()
                .addComponent(TabbedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        TabbedPanel1.addTab("Browse Library", BrowsePanel);

        MenuBar.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        NewMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.META_MASK));
        NewMenu.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        NewMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/1455158458_129.png"))); // NOI18N
        NewMenu.setText("New +");
        jMenu1.add(NewMenu);

        CloseMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.META_MASK));
        CloseMenu.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        CloseMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JavaGUI/cross.png"))); // NOI18N
        CloseMenu.setText("Close ");
        CloseMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseMenuActionPerformed(evt);
            }
        });
        jMenu1.add(CloseMenu);

        MenuBar.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("Red Color");
        jMenu2.add(jRadioButtonMenuItem1);

        jRadioButtonMenuItem2.setSelected(true);
        jRadioButtonMenuItem2.setText("Blue Color");
        jMenu2.add(jRadioButtonMenuItem2);

        jRadioButtonMenuItem3.setSelected(true);
        jRadioButtonMenuItem3.setText("No Color");
        jMenu2.add(jRadioButtonMenuItem3);

        MenuBar.add(jMenu2);

        DateMenu.setText("Date");
        DateMenu.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        MenuBar.add(DateMenu);

        TimeMenu.setText("Time");
        TimeMenu.setFont(new java.awt.Font("Palatino", 3, 14)); // NOI18N
        MenuBar.add(TimeMenu);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(TabbedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1016, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(TabbedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CopyRightTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopyRightTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CopyRightTextActionPerformed

    private void ISBNTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ISBNTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ISBNTextActionPerformed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        
     AddData();
     ISBNText.setText("");
            FNameText.setText("");
            LNameText.setText("");
            BookNameText.setText("");
            EditionText.setText("");
            CopyRightText.setText("");
        ResultSet rs=JDBCconnection.DisplayData();
        Table1.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
    }//GEN-LAST:event_AddButtonActionPerformed

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
       ISBNText.setText("");
       BookNameText.setText("");
       EditionText.setText("");
       CopyRightText.setText("");
       FNameText.setText("");
       LNameText.setText("");
       DefaultTableModel dm=new DefaultTableModel(0,0);
       Table1.setModel(dm);

    }//GEN-LAST:event_ClearButtonActionPerformed

    private void DisplayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisplayButtonActionPerformed
        ResultSet rs=JDBCconnection.DisplayData();
        Table1.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
        
       
    }//GEN-LAST:event_DisplayButtonActionPerformed

    private void SearchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButton1ActionPerformed
       searchTable();
       
      Table2.getModel().addTableModelListener(new TableModelListener() 
	{
            
            
	@Override
            public void tableChanged(TableModelEvent evt) 
            {
                System.out.println("Row changed:"+evt.getFirstRow());
                System.out.println("Column Changed"+evt.getColumn());
		System.out.println("Event Type:"+evt.getType());
                if(evt.getType() == 0)
                { 
		int row = evt.getFirstRow();
		int col = evt.getColumn();
                String ISBN=(String) (Table2.getModel().getValueAt(row, 0));
                TableModel model = (TableModel)evt.getSource();
                String columnName = model.getColumnName(col);
                Object data = model.getValueAt(row, col);
		String [] params = {columnName,data.toString(),ISBN};
		//Connectionclass.Update(params);
                JDBCconnection.UpdateData(params);
		 //JOptionPane.showMessageDialog(null, "Updated book sucessfully");
	 }
	int row = evt.getFirstRow();
        int col = evt.getColumn();
	TableModel model = (TableModel)evt.getSource();
        String columnName = model.getColumnName(col);
	Object data = model.getValueAt(row, col);
        System.out.println("value changed is"+data.toString());
	System.out.println("Column Name is"+columnName);
        System.out.println("Editable Option"+Table2.isCellEditable(0, 0));
				
				        
         }
					
					
});
      
    }//GEN-LAST:event_SearchButton1ActionPerformed

    private void EditionText2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditionText2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EditionText2ActionPerformed

    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButtonActionPerformed
        UpdateData();
        FNameText2.setText(" ");
            LNameText2.setText(" ");
            BookNameText2.setText(" ");
            EditionText2.setText(" ");
            CopyRightText2.setText(" ");
           searchTable();
    }//GEN-LAST:event_UpdateButtonActionPerformed

    private void ClearButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButton2ActionPerformed
       SearchText2.setText("");
        BookNameText2.setText("");
       EditionText2.setText("");
       CopyRightText2.setText("");
       FNameText2.setText("");
       LNameText2.setText("");
       SearchText1.setText("");
       DefaultTableModel model = new DefaultTableModel(0,0);
       Table2.setModel(model);
       Table6.setModel(model);
        
    }//GEN-LAST:event_ClearButton2ActionPerformed

    private void LoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadButtonActionPerformed
        LoadData1();
        
    }//GEN-LAST:event_LoadButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        DeleteData();
        searchTable();
        BookNameText2.setText("");
       EditionText2.setText("");
       CopyRightText2.setText("");
       FNameText2.setText("");
       LNameText2.setText("");
       SearchText2.setText("");
       
        
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void CloseMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseMenuActionPerformed
        //JOptionPane.showMessageDialog(null, "Do you want to Exit?");
        WindowEvent windowEvent=new WindowEvent(this, WindowEvent.WINDOW_CLOSED);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowEvent);
    System.exit(0);
    }//GEN-LAST:event_CloseMenuActionPerformed

    private void SearchText1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchText1ActionPerformed
        
    }//GEN-LAST:event_SearchText1ActionPerformed

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
       TableClick1();
    }//GEN-LAST:event_Table1MouseClicked

    private void Table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MouseClicked
        
        TableClick2();
    }//GEN-LAST:event_Table2MouseClicked

    private void Table8AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Table8AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_Table8AncestorAdded

    private void ExitButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButton2ActionPerformed
        WindowEvent windowEvent=new WindowEvent(this, WindowEvent.WINDOW_CLOSED);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowEvent);
        System.exit(0);
    }//GEN-LAST:event_ExitButton2ActionPerformed

    private void FilterSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterSearchButtonActionPerformed

        searchTable1();

    }//GEN-LAST:event_FilterSearchButtonActionPerformed

    private void ShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowAllActionPerformed
       /* try{
        
        ResultSet rs=JDBCconnection.SearchTable1();
        while(rs.next()){
            rs.previous();
            Table8.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        
    }
  catch(Exception ex){
        JOptionPane.showMessageDialog(null,ex);
    }*/searchTable1();
    }//GEN-LAST:event_ShowAllActionPerformed

    private void AdvancedSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdvancedSearchActionPerformed
        
        AdvancedSearch();
    }//GEN-LAST:event_AdvancedSearchActionPerformed

    private void ResetButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButton2ActionPerformed
       DefaultTableModel model=new DefaultTableModel(0,0);
       Table8.setModel(model);
     // AndRadio1 rd=new AndRadio();
      ButtonGroup bd=new ButtonGroup();
      bd.add(AndRadio1);
      bd.add(OrRadio1);
      bd.add(NotRadio1);
      bd.clearSelection();
      Section1Text.setText("");
      Section2Text.setText("");
      SearchText2.setText("");
    }//GEN-LAST:event_ResetButton2ActionPerformed

    private void OrRadio1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_OrRadio1ItemStateChanged
        Changeradiobutton();
    }//GEN-LAST:event_OrRadio1ItemStateChanged

    private void ComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox2ActionPerformed
        /* try{
            int itemCount = ComboBox3.getItemCount();
            for(int i=0;i<itemCount;i++){
                ComboBox3.removeItemAt(0);
            }
            String ComboItem=(String)ComboBox2.getSelectedItem();
            ResultSet rs=JDBCconnection.FillCombo3(ComboItem);

            while(rs.next()){
                String table=rs.getString(1);
                ComboBox3.addItem(table);
            }
            rs.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        */
    }//GEN-LAST:event_ComboBox2ActionPerformed

    private void ComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboBox2MouseClicked

    }//GEN-LAST:event_ComboBox2MouseClicked

    private void NotRadio1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NotRadio1ItemStateChanged
        Changeradiobutton();
    }//GEN-LAST:event_NotRadio1ItemStateChanged

    private void ComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox3ActionPerformed

    }//GEN-LAST:event_ComboBox3ActionPerformed

    private void AndRadio1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AndRadio1ItemStateChanged
        Changeradiobutton();
    }//GEN-LAST:event_AndRadio1ItemStateChanged

    private void CheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CheckBox1ItemStateChanged
      // if(evt.getStateChange()==ItemEvent.SELECTED)
           
    }//GEN-LAST:event_CheckBox1ItemStateChanged

    private void CheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBox1ActionPerformed
       JCheckBox cb=(JCheckBox) evt.getSource();
       if(cb.isSelected()){
           AdvancedSearch.setEnabled(true);
           FilterSearchButton.setEnabled(false);
       }
       else{
           AdvancedSearch.setEnabled(false);
           FilterSearchButton.setEnabled(true);
       }
       
    }//GEN-LAST:event_CheckBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Java_Assignment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Java_Assignment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Java_Assignment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Java_Assignment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Java_Assignment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton AdvancedSearch;
    private javax.swing.JRadioButton AndRadio1;
    private javax.swing.JLabel BookNameLabel;
    private javax.swing.JTextField BookNameText;
    private javax.swing.JTextField BookNameText2;
    private javax.swing.JPanel BrowsePanel;
    private javax.swing.JCheckBox CheckBox1;
    private javax.swing.JButton ClearButton;
    private javax.swing.JButton ClearButton2;
    private javax.swing.JMenuItem CloseMenu;
    private javax.swing.JComboBox<String> ComboBox1;
    private javax.swing.JComboBox<String> ComboBox2;
    private javax.swing.JComboBox<String> ComboBox3;
    private javax.swing.JLabel ConstraintPanel1;
    private javax.swing.JLabel CopyRightLabel;
    private javax.swing.JTextField CopyRightText;
    private javax.swing.JTextField CopyRightText2;
    private javax.swing.JMenu DateMenu;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JButton DisplayButton;
    private javax.swing.JLabel EditionLabel;
    private javax.swing.JTextField EditionText;
    private javax.swing.JTextField EditionText2;
    private javax.swing.JButton ExitButton2;
    private javax.swing.JLabel FNameLabel;
    private javax.swing.JTextField FNameText;
    private javax.swing.JTextField FNameText2;
    private javax.swing.JButton FilterSearchButton;
    private javax.swing.JLabel ISBNLabel;
    private javax.swing.JTextField ISBNText;
    private javax.swing.JPanel InsertPanel;
    private javax.swing.JLabel LNameLabel;
    private javax.swing.JTextField LNameText;
    private javax.swing.JTextField LNameText2;
    private javax.swing.JList<String> List1;
    private javax.swing.JButton LoadButton;
    private javax.swing.JPanel MaintainPanel;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem NewMenu;
    private javax.swing.JRadioButton NotRadio1;
    private javax.swing.JRadioButton OrRadio1;
    private javax.swing.JButton ResetButton2;
    private javax.swing.JButton SearchButton1;
    private javax.swing.JTextField SearchText1;
    private javax.swing.JTextField SearchText2;
    private javax.swing.JTextField Section1Text;
    private javax.swing.JTextField Section2Text;
    private javax.swing.JLabel SectionPanel;
    private javax.swing.JLabel SelectLabel2;
    private javax.swing.JButton ShowAll;
    private javax.swing.JPanel SimpleSearchPanel;
    private javax.swing.JTabbedPane TabbedPanel1;
    private javax.swing.JTabbedPane TabbedPanel2;
    private javax.swing.JTabbedPane TabbedPanel3;
    private javax.swing.JTable Table1;
    private javax.swing.JTable Table2;
    private javax.swing.JTable Table6;
    private javax.swing.JTable Table7;
    private javax.swing.JTable Table8;
    private javax.swing.JLabel TablePanel;
    private javax.swing.JMenu TimeMenu;
    private javax.swing.JButton UpdateButton;
    private javax.swing.JPanel UpdatePanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    // End of variables declaration//GEN-END:variables


}

