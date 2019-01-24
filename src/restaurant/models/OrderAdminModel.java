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
import java.util.HashMap;
import javax.swing.JOptionPane;
import restaurant.interfaces.ModelInterface;
import restaurant.objects.KasirObject;
import restaurant.objects.MenuObject;
import restaurant.objects.OrderObject;
import restaurant.objects.PelayanObject;
import restaurant.objects.TableObject;

/**
 *
 * @author askaeks
 */
public final class OrderAdminModel extends javax.swing.table.AbstractTableModel
        implements ModelInterface {
    
    private final ArrayList<OrderObject> data;

    private final String[] columns = {"ID Pesanan", "Meja", "Pelayan", "Kasir", "Harga"};

    public OrderAdminModel() {
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
                return data.get(arg0).getKasir().getNama();
            case 4:
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
            
            HashMap<String, KasirObject> hMapKasir = new HashMap<>();

            KasirModel.getKasir().forEach((KasirObject m) -> {
                hMapKasir.put(m.getUsername(), m);
            });

            Statement query = conn.createStatement();

            ResultSet tableInitialData = query.executeQuery("SELECT * FROM Orders WHERE 1");
            while (tableInitialData.next()) {
                // every orders, get the orders

                OrderObject or = new OrderObject();

                or.setHarga(tableInitialData.getInt("harga"));
                or.setMeja(new TableObject(tableInitialData.getString("meja"), null));
                or.setPelayan(hMapPelayan.get(tableInitialData.getString("pelayan")));
                or.setKasir(hMapKasir.get(tableInitialData.getString("kasir")));
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
