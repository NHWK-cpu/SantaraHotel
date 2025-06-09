<?php
$host = "localhost";
$user = "root";
$pass = ""; // Ganti jika password MySQL kamu tidak kosong
$dbname = "db_santarahotel";

// Koneksi ke MySQL
$conn = new mysqli($host, $user, $pass);

// Cek koneksi
if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}

// Buat database jika belum ada
$sql = "CREATE DATABASE IF NOT EXISTS `$dbname`";
if ($conn->query($sql) === TRUE) {
    echo "✅ Database '$dbname' berhasil dibuat atau sudah ada.<br>";
} else {
    die("❌ Gagal membuat database: " . $conn->error);
}

// Gunakan database
$conn->select_db($dbname);

// Buat tabel `customer`
$sqlCustomer = "CREATE TABLE IF NOT EXISTS customer (
    nama VARCHAR(1000) NOT NULL,
    email VARCHAR(100),
    nomor_telepon VARCHAR(16),
    tanggal_check_in DATETIME,
    tanggal_check_out DATETIME,
    tipe_kamar TEXT,
    nomor_kamar INT(11),
    status TEXT,
    id_pesanan VARCHAR(8) PRIMARY KEY,
    varian_kamar VARCHAR(1000),
    harga BIGINT(20)
)";
if ($conn->query($sqlCustomer) === TRUE) {
    echo "✅ Tabel 'customer' berhasil dibuat.<br>";
} else {
    echo "❌ Gagal membuat tabel 'customer': " . $conn->error . "<br>";
}

// Buat tabel `bulan_ref`
$sqlBulanRef = "CREATE TABLE IF NOT EXISTS bulan_ref (
    bulan INT(11)
)";
if ($conn->query($sqlBulanRef) === TRUE) {
    echo "✅ Tabel 'bulan_ref' berhasil dibuat.<br>";
} else {
    echo "❌ Gagal membuat tabel 'bulan_ref': " . $conn->error . "<br>";
}

// Tutup koneksi
$conn->close();
?>
