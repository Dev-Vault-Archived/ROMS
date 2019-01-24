/*
 * Copyright (C) 2018 askaeks
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package restaurant.models;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import restaurant.interfaces.ModelInterface;
import restaurant.objects.MenuObject;

/**
 *
 * @author askaeks
 */
public final class MenuModel extends javax.swing.table.AbstractTableModel
    implements ModelInterface {
    
    private final ArrayList<MenuObject> data;
    
    private final String[] columns = {"Menu Id", "Nama", "Kategori", "Harga", "Stock Kosong"};
    private final String[] category;
    
    public MenuModel() {
        
        category = new String[]{"Hidangan Pembuka", "Hidangan Utama", "Hidangan Penutup", "Minuman"};
        
        data = new ArrayList<>();
        
        initialize();
    }
    
    public MenuObject update(int index, MenuObject newData) {
        try {
            MenuObject oldData = data.get(index);
            
            data.set(index, newData);
            
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("UPDATE Menu SET nama =?, kategori = ?, harga = ?, stock = ? WHERE id = ?");
            
            statement.setString(1, newData.getNama());
            statement.setInt(2, newData.getKategori());
            statement.setInt(3, newData.getHarga());
            statement.setBoolean(4, newData.getStock());
            statement.setInt(5, newData.getId());
            
            if (statement.executeUpdate() == 1) {
                fireTableRowsUpdated(index, index);
        
                return newData;
            }
            
        } catch (ArrayIndexOutOfBoundsException e) {
            
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + err.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    public MenuObject remove(int index) {
        // TODO: Remove from database!
        MenuObject forReturn = data.remove(index);
        
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("DELETE FROM Menu WHERE id = ?");
            
            statement.setInt(1, forReturn.getId());
            
            if (statement.executeUpdate() == 1) {
                fireTableRowsDeleted(index, index);
        
                return forReturn;
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + err.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    public MenuObject get(int index) {
        return data.get(index);
    }
    
    public boolean add(MenuObject e) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("INSERT INTO `Menu`(`nama`, `kategori`, `harga`, `stock`) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, e.getNama());
            statement.setInt(2, e.getKategori());
            statement.setInt(3, e.getHarga());
            statement.setBoolean(4, e.getStock());

            if (statement.executeUpdate() == 1) {
                
                int candidateId = 0;
                
                ResultSet rs = statement.getGeneratedKeys();
                
                if(rs.next())
                    candidateId = rs.getInt(1);
                
                e.setId(candidateId);
                
                boolean forReturn = data.add(e);

                fireTableRowsInserted(data.size() - 1, data.size() - 1);

                return forReturn;
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + err.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return false;
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public Class getColumnClass(int col) {
        switch (col) {
            case 0:
            case 3:
                return Integer.class;
            case 4:
                return Boolean.class;
            default:
                return String.class;
        }
    }
    
    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return data.get(arg0).getId();
            case 1:
                return data.get(arg0).getNama();
            case 2:
                return category[data.get(arg0).getKategori()];
            case 3:
                return data.get(arg0).getHarga();
            case 4:
                return !data.get(arg0).getStock();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int arg0) {
        return columns[arg0];
    }
    
    public static ArrayList<MenuObject> getMenu(boolean onlyOnStock) {
        ArrayList<MenuObject> forReturn = new ArrayList<>();
        
        Connection conn = DBConnection.getConnection();
        
        try {
            Statement query = conn.createStatement();
            
            ResultSet tableInitialData = query.executeQuery("SELECT * FROM Menu");
            while (tableInitialData.next()) {
                if (onlyOnStock && !tableInitialData.getBoolean("stock")) continue;
                
                forReturn.add(new MenuObject(tableInitialData.getInt("Id"), tableInitialData.getString("nama"),
                    tableInitialData.getInt("kategori"), tableInitialData.getInt("harga"), tableInitialData.getBoolean("stock")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + e.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return forReturn;
    }
    
    public static MenuObject getMenu(Integer id) {
        Connection conn = DBConnection.getConnection();
        
        try {
            Statement query = conn.createStatement();
            
            ResultSet tableInitialData = query.executeQuery("SELECT * FROM Menu WHERE id = '"+ id +"'");
            while (tableInitialData.next()) {
                
                return new MenuObject(tableInitialData.getInt("Id"), tableInitialData.getString("nama"),
                    tableInitialData.getInt("kategori"), tableInitialData.getInt("harga"), tableInitialData.getBoolean("stock"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + e.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    @Override
    public void initialize() {
        data.addAll(getMenu(false));
    }
    
}
