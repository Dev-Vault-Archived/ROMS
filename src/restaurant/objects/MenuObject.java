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
public final class MenuObject {
    private Integer id;
    private String nama;
    private Integer kategori;
    private Integer harga;
    private Boolean stock;
    
    public MenuObject(Integer id, String nama, Integer kategori, Integer harga, Boolean stock) {
        setId(id);
        setNama(nama);
        setKategori(kategori);
        setHarga(harga);
        setStock(stock);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return the kategori
     */
    public Integer getKategori() {
        return kategori;
    }

    /**
     * @param kategori the kategori to set
     */
    public void setKategori(Integer kategori) {
        this.kategori = kategori;
    }

    /**
     * @return the harga
     */
    public Integer getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    /**
     * @return the stock
     */
    public Boolean getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Boolean stock) {
        this.stock = stock;
    }
}
