/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.santarahoteladmin;

/**
 *
 * @author MyBook Hype AMD
 */
public class Customer {
    private String nama;
    private String email;
    private String noTelp;
    private String checkIn;
    private String checkOut;
    private String tipe;
    private String noKamar;
    private String status;
    private String id;
    private String varian;
    private String harga;

    // Constructor
    public Customer(String nama, String email, String harga, String noTelp, String checkIn, String checkOut, String tipe, String noKamar, String status, String id, String varian) {
        this.nama = nama;
        this.email = email;
        this.harga = harga;
        this.noTelp = noTelp;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.tipe = tipe;
        this.noKamar = noKamar;
        this.status = status;
        this.id = id;
        this.varian = varian;
    }

    // Getter dan Setter
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    /**
     * @return the noTelp
     */
    public String getNoTelp() {
        return noTelp;
    }

    /**
     * @param noTelp the noTelp to set
     */
    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    /**
     * @return the checkIn
     */
    public String getCheckIn() {
        return checkIn;
    }

    /**
     * @param checkIn the checkIn to set
     */
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * @return the checkOut
     */
    public String getCheckOut() {
        return checkOut;
    }

    /**
     * @param checkOut the checkOut to set
     */
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    /**
     * @return the tipe
     */
    public String getTipe() {
        return tipe;
    }

    /**
     * @param tipe the tipe to set
     */
    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    /**
     * @return the noKamar
     */
    public String getNoKamar() {
        return noKamar;
    }

    /**
     * @param noKamar the noKamar to set
     */
    public void setNoKamar(String noKamar) {
        this.noKamar = noKamar;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the varian
     */
    public String getVarian() {
        return varian;
    }

    /**
     * @param varian the varian to set
     */
    public void setVarian(String varian) {
        this.varian = varian;
    }
    
    
}
