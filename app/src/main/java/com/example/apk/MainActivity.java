package com.example.apk; // SESUAIKAN DENGAN NAMA PACKAGE KAMU

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    // Deklarasi TextInputLayout (Error Handling)
    private TextInputLayout tilNamaPengguna, tilMerkKendaraan, tilNomorPlat, tilLamaParkir;

    // Deklarasi TextInputEditText
    private TextInputEditText etNamaPengguna, etMerkKendaraan, etNomorPlat, etLamaParkir;

    // Deklarasi Komponen Pilihan & Tombol
    private RadioGroup rgJenisKendaraan;
    private RadioButton rbMotor;
    private Button btnBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi TextInputLayout
        tilNamaPengguna = findViewById(R.id.tilNamaPengguna);
        tilMerkKendaraan = findViewById(R.id.tilMerkKendaraan);
        tilNomorPlat = findViewById(R.id.tilNomorPlat);
        tilLamaParkir = findViewById(R.id.tilLamaParkir);

        // Inisialisasi TextInputEditText
        etNamaPengguna = findViewById(R.id.etNamaPengguna);
        etMerkKendaraan = findViewById(R.id.etMerkKendaraan);
        etNomorPlat = findViewById(R.id.etNomorPlat);
        etLamaParkir = findViewById(R.id.etLamaParkir);

        // Inisialisasi Pilihan & Tombol
        rgJenisKendaraan = findViewById(R.id.rgJenisKendaraan);
        rbMotor = findViewById(R.id.rbMotor);
        btnBayar = findViewById(R.id.btnBayar);

        // Aksi Tombol Proses Pembayaran
        btnBayar.setOnClickListener(v -> prosesDataValidasi());
    }

    private void prosesDataValidasi() {
        // Ambil data teks input
        String nama = etNamaPengguna.getText().toString().trim();
        String merk = etMerkKendaraan.getText().toString().trim();
        String plat = etNomorPlat.getText().toString().trim();
        String lamaStr = etLamaParkir.getText().toString().trim();

        boolean isValid = true;

        // 1. Validasi Nama Pengguna (Tidak boleh kosong & harus huruf)
        if (nama.isEmpty()) {
            tilNamaPengguna.setError("Nama pengguna tidak boleh kosong!");
            isValid = false;
        } else if (!nama.matches("[a-zA-Z\\s]+")) {
            tilNamaPengguna.setError("Nama hanya boleh berisi huruf!");
            isValid = false;
        } else {
            tilNamaPengguna.setError(null);
        }

        // 2. Validasi Merk Kendaraan
        if (merk.isEmpty()) {
            tilMerkKendaraan.setError("Merk kendaraan wajib diisi!");
            isValid = false;
        } else {
            tilMerkKendaraan.setError(null);
        }

        // 3. Validasi Nomor Plat (Format Plat Indonesia)
        if (plat.isEmpty()) {
            tilNomorPlat.setError("Nomor plat wajib diisi!");
            isValid = false;
        } else if (!plat.matches("^[A-Z]{1,2}\\s?\\d{1,4}\\s?[A-Z]{1,3}$")) {
            tilNomorPlat.setError("Format plat salah! (Cth: AD 1234 AB)");
            isValid = false;
        } else {
            tilNomorPlat.setError(null);
        }

        // 4. Validasi Lama Parkir
        int n = 0; // Lama parkir dalam jam (n)
        if (lamaStr.isEmpty()) {
            tilLamaParkir.setError("Lama parkir wajib diisi!");
            isValid = false;
        } else {
            try {
                n = Integer.parseInt(lamaStr);
                if (n <= 0) {
                    tilLamaParkir.setError("Minimal lama parkir adalah 1 jam!");
                    isValid = false;
                } else {
                    tilLamaParkir.setError(null);
                }
            } catch (NumberFormatException e) {
                tilLamaParkir.setError("Input jam tidak valid!");
                isValid = false;
            }
        }

        // Jika semua input lolos validasi (Tidak ada error)
        if (isValid) {
            String jenisKendaraan = rbMotor.isChecked() ? "Sepeda Motor" : "Mobil";

            /* LOGIKA DERET ARITMATIKA (Sn)
             * Motor : Jam ke-1 (a) = 3000, Beda kenaikan per jam (b) = 1000
             * Mobil : Jam ke-1 (a) = 5000, Beda kenaikan per jam (b) = 2000
             */
            int a = rbMotor.isChecked() ? 3000 : 5000;
            int b = rbMotor.isChecked() ? 1000 : 2000;

            // Rumus Sn = (n / 2) * (2a + (n - 1)b)
            int totalBayar = (n * (2 * a + (n - 1) * b)) / 2;

            // PANGGIL POP UP DIALOG
            tampilkanPopUpKarcis(nama, jenisKendaraan, merk, plat, totalBayar);
        }
    }

    // Method khusus untuk menampilkan Jendela Pop Up Karcis
    private void tampilkanPopUpKarcis(String nama, String jenis, String merk, String plat, int total) {
        // 1. Inflate / tiup layout dialog_karcis.xml menjadi bentuk View
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_karcis, null);

        // 2. Hubungkan TextView yang ada di dalam layout dialog_karcis.xml
        TextView KarcisNama = dialogView.findViewById(R.id.KarcisNama);
        TextView KarcisKendaraan = dialogView.findViewById(R.id.KarcisKendaraan);
        TextView KarcisTotal = dialogView.findViewById(R.id.KarcisTotal);

        // 3. Set text hasil perhitungan ke komponen Pop Up Dialog
        KarcisNama.setText("Nama Pengguna: " + nama);
        KarcisKendaraan.setText("Kendaraan: " + jenis + " (" + merk + " - " + plat.toUpperCase() + ")");
        KarcisTotal.setText("Total Bayar: Rp " + String.format("%,d", total).replace(',', '.'));

        // 4. Buat dan Tampilkan AlertDialog Pop Up
        new AlertDialog.Builder(MainActivity.this)
                .setView(dialogView)
                .setPositiveButton("TUTUP / CETAK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}