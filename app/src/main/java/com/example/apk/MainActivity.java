package com.example.apk; // Menentukan lokasi package aplikasi

// Mengimpor library Android yang dibutuhkan
import android.app.AlertDialog; // Kelas untuk membuat jendela Pop-Up Dialog
import android.os.Bundle; // Kelas untuk menyimpan status data aktivitas
import android.view.View; // Kelas umum untuk penanganan tampilan UI
import android.widget.EditText; // Komponen input teks
import android.widget.RadioButton; // Komponen pilihan radio button
import android.widget.RadioGroup; // Komponen grup radio button
import android.widget.TextView; // Komponen penampil teks
import android.widget.Toast; // Komponen penampil pesan singkat (Toast)
import androidx.appcompat.app.AppCompatActivity; // Kelas dasar untuk activity modern
import androidx.appcompat.app.AppCompatDelegate; // Kelas pengatur tema/mode aplikasi

import java.util.Locale; // Library format bahasa/negara (dipakai untuk format angka Rupiah)

public class MainActivity extends AppCompatActivity {

    // Variable global untuk menyimpan nilai hitungan lama parkir
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Memaksa aplikasi selalu memakai mode terang agar tidak gelap saat HP dalam Dark Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        // Menghubungkan file Java ini dengan layout XML utamanya
        setContentView(R.layout.activity_main);
    }

    // Method yang dipanggil saat tombol (-) diklik
    public void Kurang_waktu(View view) {
        // Menghubungkan ke EditText jam di XML
        EditText InputJam = findViewById(R.id.InputJam);
        
        if (InputJam == null) return;

        // Jika hitungan lebih dari 0, kurangi 1
        if (count > 0) {
            count = count - 1;
        } else {
            // Jika sudah 0, tahan agar tidak menjadi minus
            count = 0;
        }

        // Tampilkan angka terbaru ke dalam EditText
        InputJam.setText(String.valueOf(count));
    }

    // Method yang dipanggil saat tombol (+) diklik
    public void Tambah_waktu(View view) {
        // Menghubungkan ke EditText jam di XML
        EditText InputJam = findViewById(R.id.InputJam);
        
        if (InputJam == null) return;

        // Jika hitungan sudah 36 atau lebih, kunci di angka 36 (Maksimal)
        if (count >= 36) {
            count = 36;
        } else {
            // Jika belum mencapai 36, tambahkan 1
            count++;
        }

        // Tampilkan angka terbaru ke dalam EditText
        InputJam.setText(String.valueOf(count));
    }

    // Method utama yang dipanggil saat tombol "PROSES PEMBAYARAN" diklik
    public void prosesPembayaran(View view) {

        // Inisialisasi komponen-komponen input dari XML
        EditText InputNama = findViewById(R.id.InputNama);
        EditText InputNomorPlat = findViewById(R.id.InputNomorPlat);
        EditText InputMerkKendaraan = findViewById(R.id.InputMerkKendaraan);
        EditText InputJam = findViewById(R.id.InputJam);
        RadioGroup JnsKnd = findViewById(R.id.JenisKendaraan);
        
        if (InputNama == null || InputNomorPlat == null || InputMerkKendaraan == null || 
            InputJam == null || JnsKnd == null) {
            Toast.makeText(this, "Error: Komponen UI tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mengambil isi teks inputan dan menghapus spasi di awal/akhir
        String nama = InputNama.getText().toString().trim();
        String plat = InputNomorPlat.getText().toString().trim();
        String merk = InputMerkKendaraan.getText().toString().trim();
        String jamString = InputJam.getText().toString().trim();
        // Mendapatkan ID dari RadioButton yang sedang dipilih
        int HasilPilihJenisKendaraan = JnsKnd.getCheckedRadioButtonId();

        // Deklarasi variabel pembatas dan harga tarif
        int maksJamParkir = 36;
        int tarifAwal;
        int tarifLanjutan;
        int tarifMaks;

        // Validasi Bertingkat (If-Else)
        // 1. Cek apakah Nama tidak kosong
        if (!nama.isEmpty()) {
            // 2. Cek apakah Plat Nomor tidak kosong
            if (!plat.isEmpty()) {
                // 3. Cek apakah Merk Kendaraan tidak kosong
                if (!merk.isEmpty()) {
                    // 4. Cek apakah Lama Parkir tidak kosong
                    if (!jamString.isEmpty()) {
                        // Mengubah teks input jam menjadi tipe angka (Integer)
                        int jam = Integer.parseInt(jamString);
                        // 5. Cek apakah jam lebih dari 0
                        if (jam > 0) {
                            // 6. Cek apakah jam tidak melebihi batas maksimal (36 jam)
                            if (jam <= maksJamParkir) {
                                // 7. Cek apakah jenis kendaraan sudah dipilih (tidak bernilai -1)
                                if (HasilPilihJenisKendaraan != -1) {

                                    // Mengambil elemen RadioButton yang dipilih
                                    RadioButton PilihanRadioButton = findViewById(HasilPilihJenisKendaraan);
                                    String jenisPilihan = PilihanRadioButton.getText().toString();

                                    // Hitung tarif berdasarkan jenis kendaraan
                                    if (jenisPilihan.equals("Mobil")) {
                                        tarifAwal = 10000;      // Tarif jam pertama Mobil
                                        tarifLanjutan = 5000;   // Tarif jam berikutnya Mobil
                                        tarifMaks = 30000;      // Batas tarif maksimal Mobil
                                    } else {
                                        tarifAwal = 5000;       // Tarif jam pertama Motor
                                        tarifLanjutan = 2000;   // Tarif jam berikutnya Motor

                                    }

                                    // Rumus Hitung Total Bayar: Tarif Awal + (Tarif Lanjutan x (Total Jam - 1))
                                    int totalBayar = tarifAwal;
                                    if (jam > 1) {
                                        totalBayar += tarifLanjutan * (jam - 1);
                                    }




                                    // --- PROSES MENAMPILKAN HASIL PADA POP UP DIALOG ---

                                    // 1. Inflate / tiup layout XML 'dialog_karcis' menjadi objek View
                                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_karcis, null);

                                    // 2. Hubungkan elemen TextView yang ada di dalam layout dialog_karcis.xml
                                    TextView JudulKarcis = dialogView.findViewById(R.id.JudulKarcis);
                                    TextView KarcisNama = dialogView.findViewById(R.id.KarcisNama);
                                    TextView KarcisKendaraan = dialogView.findViewById(R.id.KarcisKendaraan);
                                    TextView KarcisTotal = dialogView.findViewById(R.id.KarcisTotal);

                                    // 3. Masukkan data hasil perhitungan ke dalam TextView Pop-Up
                                    JudulKarcis.setText("RINGKASAN KARCIS PARKIR");
                                    KarcisNama.setText("Nama Pengguna: " + nama);
                                    KarcisKendaraan.setText("Kendaraan: " + jenisPilihan + " - " + merk + " (" + plat + ")");
                                    KarcisTotal.setText("Total Bayar: Rp " + String.format(Locale.GERMANY, "%,d", totalBayar));

                                    // 4. Buat dan tampilkan Jendela Pop-Up Dialog
                                    new AlertDialog.Builder(this)
                                            .setView(dialogView) // Set tampilan dialog memakai dialogView
                                            .setPositiveButton("TUTUP", (dialog, which) -> dialog.dismiss()) // Tombol untuk menutup dialog
                                            .create() // Membuat objek dialog
                                            .show(); // Menampilkan ke layar

                                } else {
                                    // Pesan jika belum memilih jenis kendaraan
                                    Toast.makeText(this, "Silahkan Pilih Jenis Kendaraan Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Pesan jika jam parkir melebihi 36 jam
                                Toast.makeText(this, "Lama Parkir Maksimal " + maksJamParkir + " Jam!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Pesan jika jam dimasukkan angka 0
                            Toast.makeText(this, "Jam tidak boleh 0(nol)!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Pesan jika jam parkir belum diisi
                        Toast.makeText(this, "Masukan Lama Parkir Terlebih dahulu!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Pesan jika merk kendaraan belum diisi
                    Toast.makeText(this, "Masukan Merek Kendaraan Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Pesan jika plat nomor belum diisi
                Toast.makeText(this, "Masukan Nomor Plat Terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Pesan jika nama pengguna belum diisi
            Toast.makeText(this, "Masukan Nama Pengguna Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
        }
    }
}