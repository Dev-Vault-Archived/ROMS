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
import restaurant.objects.KasirObject;

/**
 *
 * @author askaeks
 */
public final class KasirModel extends javax.swing.table.AbstractTableModel
    implements ModelInterface {
    
    private final ArrayList<KasirObject> data;
    
    private final String[] columns = {"Nama", "Username", "Password", "Terakhir Login"};
    
    public KasirModel() {
        data = new ArrayList<>();
        
        initialize();
    }
    
    public KasirObject update(int index, KasirObject newData) {
        try {
            KasirObject oldData = data.get(index);
            
            data.set(index, newData);
            
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("UPDATE Users SET nama=?, password=? WHERE username = ?");
            
            statement.setString(1, newData.getNama());
            statement.setString(2, newData.getPassword());
            statement.setString(3, oldData.getUsername());
            
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
    
    public KasirObject remove(int index) {
        // TODO: Remove from database!
        KasirObject forReturn = data.remove(index);
        
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("DELETE FROM Users WHERE username = ?");
            
            statement.setString(1, forReturn.getUsername());
            
            if (statement.executeUpdate() == 1) {
                fireTableRowsDeleted(index, index);
        
                return forReturn;
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + err.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    public boolean add(KasirObject e) {
        
        try {
            
            PreparedStatement selectKodeFromTables = DBConnection.getConnection().prepareStatement("SELECT kode FROM Pelayan WHERE kode = ?");
            selectKodeFromTables.setString(1, e.getUsername());
            
            ResultSet resultSelectKode = selectKodeFromTables.executeQuery();
            if (resultSelectKode.first()) {
                JOptionPane.showMessageDialog(null, "Username yang anda masukkan telah terdaftar. Cobalah memasukkan username kasir yang lain!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            } else {
                PreparedStatement statement = DBConnection.getConnection().prepareStatement("INSERT INTO Users VALUES (?, ?, 0, ?, NULL)");
            
                statement.setString(1, e.getUsername());
                statement.setString(2, e.getPassword());
                statement.setString(3, e.getNama());

                if (statement.executeUpdate() == 1) {
                    boolean forReturn = data.add(e);

                    fireTableRowsInserted(data.size() - 1, data.size() - 1);

                    return forReturn;
                }
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + err.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return false;
    }
    
    public KasirObject get(int index) {
        return data.get(index);
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
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return data.get(arg0).getNama();
            case 1:
                return data.get(arg0).getUsername();
            case 2:
                return data.get(arg0).getPassword();
            case 3:
                String terakhirLogin = data.get(arg0).getTerakhirLogin();
                
                if (terakhirLogin != (null)) return terakhirLogin;
                else {
                    return "Belum Login";
                }
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int arg0) {
        return columns[arg0];
    }
    
    public static ArrayList<KasirObject> getKasir() {
        
        ArrayList<KasirObject> forReturn = new ArrayList<>();
        
        Connection conn = DBConnection.getConnection();
        
        try {
            Statement query = conn.createStatement();
            
            ResultSet tableInitialData = query.executeQuery("SELECT * FROM Users WHERE is_administrator <> 1 ORDER BY username ASC");
            while (tableInitialData.next()) {
                String terakhirLogin = tableInitialData.getString("terakhir_login");
                
                forReturn.add(new KasirObject(tableInitialData.getString("nama"), tableInitialData.getString("username"), tableInitialData.getString("password"), (terakhirLogin == (null)) ? "Belum Login" : terakhirLogin));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + e.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return forReturn;
    }
    
    @Override
    public void initialize() {
        data.addAll(getKasir());
    }
    
}
