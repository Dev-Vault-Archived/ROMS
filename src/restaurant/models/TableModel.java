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
import restaurant.objects.TableObject;

/**
 *
 * @author askaeks
 */
public final class TableModel extends javax.swing.table.AbstractTableModel
    implements ModelInterface {

    private final ArrayList<TableObject> data;
    
    private final String[] columns = {"Kode", "Lokasi"};
    
    public TableModel() {
        data = new ArrayList<>();
        
        initialize();
    }
    
    public static ArrayList<TableObject> getTable() {
        ArrayList<TableObject> o = new ArrayList<>();
        
        Connection conn = DBConnection.getConnection();
        
        try {
            Statement query = conn.createStatement();
            
            ResultSet tableInitialData = query.executeQuery("SELECT * FROM Tables ORDER BY kode ASC");
            while (tableInitialData.next()) {
                o.add(new TableObject(tableInitialData.getString("kode"), tableInitialData.getString("lokasi")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + e.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return o;
    }
    
    @Override
    public void initialize() {
        data.addAll(getTable());
    }
    
    public TableObject remove(int index) {
        // TODO: Remove from database!
        TableObject forReturn = data.remove(index);
        
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("DELETE FROM Tables WHERE kode = ?");
            
            statement.setString(1, forReturn.getKode());
            
            if (statement.executeUpdate() == 1) {
                fireTableRowsDeleted(index, index);
        
                return forReturn;
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + err.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    public TableObject get(int index) {
        return data.get(index);
    }
    
    public boolean add(TableObject e) {
        
        try {
            
            PreparedStatement selectKodeFromTables = DBConnection.getConnection().prepareStatement("SELECT kode FROM Tables WHERE kode = ?");
            selectKodeFromTables.setString(1, e.getKode());
            
            ResultSet resultSelectKode = selectKodeFromTables.executeQuery();
            if (resultSelectKode.first()) {
                JOptionPane.showMessageDialog(null, "Kode Table yang anda masukkan telah terdaftar. Cobalah memasukkan kode table yang lain!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            } else {
                PreparedStatement statement = DBConnection.getConnection().prepareStatement("INSERT INTO Tables VALUES (?, ?)");
            
                statement.setString(1, e.getKode());
                statement.setString(2, e.getLokasi());

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
                return data.get(arg0).getKode();
            case 1:
                return data.get(arg0).getLokasi();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int arg0) {
        return columns[arg0];
    }
    
}
