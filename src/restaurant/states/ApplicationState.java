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
package restaurant.states;

/**
 * This class handle the application state
 * like the user has been logged in or not and others
 * 
 * @author askaeks
 */
public class ApplicationState {
    private static Boolean isLoggedIn;
    private static Boolean isAdministrator;
    private static String username;
    
    public static Boolean getIsLoggedIn() {
        return ApplicationState.isLoggedIn;
    }
    
    public static Boolean getIsAdministrator() {
        return ApplicationState.isAdministrator;
    }
    
    public static void setLoggedIn() {
        ApplicationState.isLoggedIn = true;
    }
    
    public static void setLoggedIn(Boolean isAdministrator) {
        ApplicationState.setLoggedIn();
        
        ApplicationState.isAdministrator = isAdministrator;
    }
    
    public static void logout() {
        ApplicationState.isLoggedIn = false;
        ApplicationState.isAdministrator = false;
    }

    /**
     * @return the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * @param aUsername the username to set
     */
    public static void setUsername(String aUsername) {
        username = aUsername;
    }
}
