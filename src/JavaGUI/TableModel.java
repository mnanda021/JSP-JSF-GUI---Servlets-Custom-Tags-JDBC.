/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaGUI;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 *
 * @author mnanda021
 */

public class TableModel extends DefaultTableModel{
    
    public TableModel(Vector Data,Vector ColumnNames){
        
        super(Data, ColumnNames);
    }
    public boolean isCellEditable(int row, int column)
    {
        if(column==0){
      return false;//This causes all cells to be not editable
    }
        else{
            return true;
        }
    }
}
