package com.mycompany.santarahotel;

/**
 * Class ini bertindak sebagai container (penyimpanan data sementara)
 * untuk digunakan antar scene dalam aplikasi.
 * Data disimpan dalam variabel static agar mudah diakses.
 */

public class DataContainerBetweenScene {
    private static String data, pilihanVarianKamar, search; // Variabel static private untuk enkapsulasi
    
// Getter dan Setter untuk setiap properti
    public static void setData(String value) {
        data = value;
    }
    
    public static void setVarian(String value) {
        pilihanVarianKamar = value;
    }
    
    public static String getData() {
        return data;
    }
    
    public static String getVarian() {
        return pilihanVarianKamar;
    }
    
    public static void setSearch(String value) {
        search = value;
    }
    
    public static String getSearch() {
        return search;
    }
}
