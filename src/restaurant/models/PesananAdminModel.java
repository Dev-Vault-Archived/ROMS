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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import restaurant.interfaces.ModelInterface;
import restaurant.objects.MenuPesananObject;

/**
 *
 * @author askaeks
 */
public final class PesananAdminModel extends javax.swing.table.AbstractTableModel
    implements ModelInterface {
    
    private final ArrayList<MenuPesananObject> data;
    
    private final String[] columns = {"Pesanan Id", "Menu Id", "Nama", "Kategori"};
    private final String[] category;
    
    public PesananAdminModel() {
        data = new ArrayList<>();
        
        category = new String[]{"Hidangan Pembuka", "Hidangan Utama", "Hidangan Penutup", "Minuman"};
        
        initialize();
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
    public String getColumnName(int arg0) {
        return columns[arg0];
    }
    
    @Override
    public Class getColumnClass(int col) {
        switch (col) {
            case 0:
            case 1:
                return Integer.class;
            default:
                return String.class;
        }
    }
    
    @Override
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return data.get(arg0).getOrder();
            case 1:
                return data.get(arg0).getMenu().getId();
            case 2:
                return data.get(arg0).getMenu().getNama();
            case 3:
                return category[data.get(arg0).getMenu().getKategori()];
            default:
                return null;
        }
    }
    
    @Override
    public void initialize() {
        Connection conn = DBConnection.getConnection();
        
        try {
            Statement query = conn.createStatement();
            
            ResultSet tableInitialData = query.executeQuery("SELECT * FROM Pesanan");
            while (tableInitialData.next()) {
                data.add(new MenuPesananObject(
                        tableInitialData.getInt("id_order"),
                        MenuModel.getMenu(tableInitialData.getInt("id_menu"))
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + e.getSQLState(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
}
