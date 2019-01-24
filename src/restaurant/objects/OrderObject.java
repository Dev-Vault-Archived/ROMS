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

import java.util.ArrayList;

/**
 *
 * @author askaeks
 */
public final class OrderObject {
    private Integer idPesanan;
    private TableObject meja;
    private PelayanObject pelayan;
    private KasirObject kasir;
    private Integer harga;
    
    public ArrayList<MenuObject> menuList = new ArrayList<>();
    
    public OrderObject() {}
    
    public OrderObject(Integer idPesanan, TableObject nomorMeja, PelayanObject idPelayan, KasirObject idKasir, Integer harga) {
        setIdPesanan(idPesanan);
        setMeja(nomorMeja);
        setPelayan(idPelayan);
        setKasir(idKasir);
        setHarga(harga);
    }

    /**
     * @return the idPesanan
     */
    public Integer getIdPesanan() {
        return idPesanan;
    }

    /**
     * @param idPesanan the idPesanan to set
     */
    public void setIdPesanan(Integer idPesanan) {
        this.idPesanan = idPesanan;
    }

    /**
     * @return the meja
     */
    public TableObject getMeja() {
        return meja;
    }

    /**
     * @param meja the meja to set
     */
    public void setMeja(TableObject meja) {
        this.meja = meja;
    }

    /**
     * @return the pelayan
     */
    public PelayanObject getPelayan() {
        return pelayan;
    }

    /**
     * @param pelayan the pelayan to set
     */
    public void setPelayan(PelayanObject pelayan) {
        this.pelayan = pelayan;
    }

    /**
     * @return the kasir
     */
    public KasirObject getKasir() {
        return kasir;
    }

    /**
     * @param kasir the kasir to set
     */
    public void setKasir(KasirObject kasir) {
        this.kasir = kasir;
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
     * @return the menuList
     */
    public ArrayList<MenuObject> getMenuList() {
        return menuList;
    }

    /**
     * @param menuList the menuList to set
     */
    public void setMenuList(ArrayList<MenuObject> menuList) {
        this.menuList = menuList;
    }

}
