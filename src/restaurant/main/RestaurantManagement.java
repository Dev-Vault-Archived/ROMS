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
package restaurant.main;

import db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

import restaurant.frames.Masuk;

/**
 *
 * @author askaeks
 */
public class RestaurantManagement {
    
    public static void main(String[] args) {
        
        try (Connection c = DBConnection.getConnection()) {
            
            if (c != (null)) {
                System.out.println("Connected to database " + c.getCatalog());
            
                java.awt.EventQueue.invokeLater(() -> {
                    new Masuk(null).setVisible(true);
                });
            }
            
        } catch (SQLException e) {
            
        }
        
    }
    
}
