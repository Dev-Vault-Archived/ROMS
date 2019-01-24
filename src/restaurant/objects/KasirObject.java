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

package restaurant.objects;

/**
 *
 * @author askaeks
 */
public final class KasirObject {
    private String nama;
    private String username;
    private String password;
    private String terakhirLogin;
    
    public KasirObject(String nama, String username, String password, String terakhirLogin) {
        setNama(nama);
        setUsername(username);
        setPassword(password);
        setTerakhirLogin(terakhirLogin);
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the terakhirLogin
     */
    public String getTerakhirLogin() {
        return terakhirLogin;
    }

    /**
     * @param terakhirLogin the terakhirLogin to set
     */
    public void setTerakhirLogin(String terakhirLogin) {
        this.terakhirLogin = terakhirLogin;
    }
}
