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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import restaurant.interfaces.ModelInterface;
import restaurant.objects.MenuObject;
import restaurant.objects.OrderObject;
import restaurant.objects.PelayanObject;
import restaurant.objects.TableObject;
import restaurant.states.ApplicationState;

/**
 *
 * @author askaeks
 */
public final class OrderKasirModel extends javax.swing.table.AbstractTableModel
        implements ModelInterface {

    private final ArrayList<OrderObject> data;

    private final String[] columns = {"ID Pesanan", "Meja", "Pelayan", "Harga"};

    public OrderKasirModel() {
        data = new ArrayList<>();

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
    public Object getValueAt(int arg0, int arg1) {
        switch (arg1) {
            case 0:
                return data.get(arg0).getIdPesanan();
            case 1:
                return data.get(arg0).getMeja().getKode();
            case 2:
                return data.get(arg0).getPelayan().getNama();
            case 3:
                return data.get(arg0).getHarga();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int arg0) {
        return columns[arg0];
    }

    public OrderObject get(int index) {
        return data.get(index);
    }

    public void add(OrderObject dialog, HashMap<Integer, MenuObject> due) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("INSERT INTO `Orders`(`meja`, `pelayan`, `kasir`, `harga`) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, dialog.getMeja().getKode());
            statement.setString(2, dialog.getPelayan().getKode());
            statement.setString(3, ApplicationState.getUsername());
            statement.setInt(4, dialog.getHarga());

            if (statement.executeUpdate() == 1) {

                int candidateId = 0;

                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    candidateId = rs.getInt(1);
                }

                for (Map.Entry<Integer, MenuObject> m : due.entrySet()) {
                    PreparedStatement st = DBConnection.getConnection().prepareStatement("INSERT INTO `Pesanan`(`id_menu`, `id_order`) VALUES (?, ?)");
                    
                    dialog.menuList.add(m.getValue());
                    
                    st.setInt(1, m.getValue().getId());
                    st.setInt(2, candidateId);

                    st.executeUpdate();
                }
                
                dialog.setIdPesanan(candidateId);
                
                data.add(dialog);

                fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + err.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize() {
        Connection conn = DBConnection.getConnection();

        try {

            HashMap<Integer, MenuObject> hMap = new HashMap<>();

            MenuModel.getMenu(false).forEach((m) -> {
                hMap.put(m.getId(), m);
            });

            HashMap<String, PelayanObject> hMapPelayan = new HashMap<>();

            PelayanModel.getPelayan().forEach((m) -> {
                hMapPelayan.put(m.getKode(), m);
            });

            Statement query = conn.createStatement();

            ResultSet tableInitialData = query.executeQuery("SELECT * FROM Orders WHERE kasir = '" + ApplicationState.getUsername() + "'");
            while (tableInitialData.next()) {
                // every orders, get the orders

                OrderObject or = new OrderObject();

                or.setHarga(tableInitialData.getInt("harga"));
                or.setMeja(new TableObject(tableInitialData.getString("meja"), null));
                or.setPelayan(hMapPelayan.get(tableInitialData.getString("pelayan")));
                or.setKasir(null);
                or.setIdPesanan(tableInitialData.getInt("idPesanan"));

                Statement q = conn.createStatement();
                ResultSet tableGetORders = q.executeQuery("SELECT id_menu FROM Pesanan WHERE id_order = '" + tableInitialData.getInt("idPesanan") + "'");

                while (tableGetORders.next()) {
                    // there is got the pesanan
                    or.menuList.add(hMap.get(tableGetORders.getInt("id_menu")));
                }

                data.add(or);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi masalah ketika mengeksekusi perintah. State : " + e.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

}
