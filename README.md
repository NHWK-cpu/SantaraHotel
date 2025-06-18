# SANTARA

Santara adalah aplikasi berbasis java yang berfungsi untuk memudahkan proses reservasi pada hotel. Aplikasi ini bisa dijalankan di OS Windows, Linux, dan Mac dengan java [JDK 11](https://www.oracle.com/java/technologies/downloads/) keatas.

## Instalasi

## Setup database

(pastikan sudah terinstall software database di perangkat anda)

### Otomatis dengan file php

1. Copy atau cut file install_db.php pada web server (jika menggunakan xampp pindahkan file install_db.php ke C:/xampp/htdocs/)
2. Masuk ke browser, pada search bar ketik localhost/install_db.php
3. Jika pesan berbunyi berhasil maka database telah selesai dibuat dan siap digunakan

### Import file sql

1. Pergi ke phpMyAdmin lalu masuk ke menu import
2. Pada "file to import" masukkan file "db_santara.sql" yang ada dalam folder ini
3. Biarkan opsi lain secara default lalu klik tombol "import" pada bagian paling bawah
4. Tunggu proses hingga selesai dan database akan berhasil dibuat

### Membuat secara manual

1. Buat database dengan nama "db_santarahotel"
2. Masuk ke db_santarahotel lalu buat tabel dengan nama "bulan_ref"
3. Tambahkan satu kolon lalu ubah nama menjadi "bulan" dan tipe data int(11) kemudian terapkan
4. Masukkan value sebanyak 12 rows pada kolom "bulan" yang mana berisi urutan dari row pertama isinya 1, row kedua isinya 2, dan seterusnya
5. Kembali ke db_santarahotel lalu buat tabel lain dengan nama "customer"
6. Tambahkan kolom pertama ubah nama kolom menjadi "nama" dan tipe data varchar yang panjangnya 1000
7. Tambahkan kolom kedua lalu ubah nama kolom menjadi "email" dan tipe data varchar yang panjangnya 100
8. Tambahkan kolom ketiga lalu ubah nama kolom menjadi "nomor_telepon" dan tipe data varchar yang panjangnya 16
9. Tambahkan kolom keempat lalu ubah nama kolom menjadi "tanggal_check_in" dan tipe data datetime
10. Tambahkan kolom kelima lalu ubah nama kolom menjadi "tanggal_check_out" dan tipe data datetime
11. Tambahkan kolom keenam lalu ubah nama kolom menjadi "tipe_kamar" dan tipe data text
12. Tambahkan kolom ketujuh lalu ubah nama kolom menjadi "nomor_kamar" dan tipe data integer dengan panjang character 11 digit
13. Tambahkan kolom kedelapan lalu ubah nama kolom menjadi "status" dan tipe data text
14. Tambahkan kolom kesembilan lalu ubah nama kolom menjadi "id_pesanan" dan tipe data varchar yang panjangnya 8 lalu checklist atau centang sebagai PRIMARY KEY
15. Tambahkan kolom kesepuluh lalu ubah nama kolom menjadi "varian_kamar" dan tipe data varchar yang panjangnya 1000
16. Tambahkan kolom kesebelas lalu ubah nama kolom menjadi "harga" dan tipe data bigint yang panjangnya 20
17. Setelah itu konfirmasi pembuatan tabel dan database siap digunakan

## Menjalankan

(Komputer anda harus sudah terinstall JDK java untuk menjalankan)

### Menjalankan dengan menggunakan run-app

- Jika anda ingin menjalankan aplikasi untuk admin maka anda bisa menjalankan run-app(ADMIN).bat
- Jika ingin menjalankan sisi customer maka jalankan run-app(CUSTOMER).bat
- Untuk menghentikan aplikasi anda bisa menekan `alt+f4` atau jika masih belum mati anda bisa tekan `ctrl+c` pada command prompt yang menjalankan aplikasi tadi.

### Menjalankan melalui command prompt

1. Buka command prompt lalu masuk ke path folder ini (folder deploy) dengan command `cd {path folder}`
2. Jika anda ingin menjalankan aplikasi untuk tampilan customer jalankan perintah berikut:
   `java --module-path ".\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml -jar SantaraHotel-1.0-SNAPSHOT.jar`
   Jika anda ingin menjalankan aplikasi untuk tampilan admin jalankan perintah berikut:
   `java --module-path ".\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml -jar SantaraHotelAdmin-1.0-SNAPSHOT.jar`
3. Untuk menghentikan aplikasi anda bisa menekan `alt+f4` atau jika masih belum mati anda bisa tekan `ctrl+c` pada command prompt yang menjalankan aplikasi tadi.
