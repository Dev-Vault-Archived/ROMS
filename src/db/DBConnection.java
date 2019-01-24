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
package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author askaeks
 */
public final class DBConnection {
    
    private static Properties prop;
    
    public static Connection getConnection() {
        try {
            
            return getConnectionHandler();
            
        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, "Database not connected.", "Database Exception", JOptionPane.ERROR_MESSAGE);
            
        }
        
        return null;
    }
    
    private static Connection getConnectionHandler() throws SQLException {
        
        /**
         * If prop is empty, load it first
         */
        if (prop == null) {
            try (FileInputStream f = new FileInputStream("db.properties")) {
                
                prop = new Properties();
                
                prop.load(f);
                
            } catch (IOException e) {}
        }
        
        return DriverManager.getConnection(
                    prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
    }
    
}
