package com.example.uts.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "reservations")
public class Reservation
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "paket")
    private String paket;

    @ColumnInfo(name = "tanggal")
    private String tanggal;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "harga")
    private double harga;

    @ColumnInfo(name = "user_id")
    private int user_id;

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
