package com.example.apk;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    int count = 0;
    public void Kurang_waktu (View view) {
        EditText InputJam = findViewById(R.id.InputJam);

        if (count > 0) {
            count = count -1;
        }
        else {
            count = 0;
        }

        InputJam.setText(count+"");
    }

    public void Tambah_waktu (View view) {
        EditText InputJam = findViewById(R.id.InputJam);

        if (count >= 36) {
            count = 36;
        }
        else {

            count++;
        }

        InputJam.setText(count+"");
    }
    public void prosesPembayaran(View view) {

        EditText InputNama = findViewById(R.id.InputNama);
        EditText InputNomorPlat = findViewById(R.id.InputNomorPlat);
        EditText InputMerkKendaraan = findViewById(R.id.InputMerkKendaraan);
        EditText InputJam = findViewById(R.id.InputJam);

        LinearLayout LayoutKarcis = findViewById(R.id.LayoutKarcis);

        TextView JudulKarcis = findViewById(R.id.JudulKarcis);
        TextView KarcisNama = findViewById(R.id.KarcisNama);
        TextView KarcisKendaraan = findViewById(R.id.KarcisKendaraan);
        TextView KarcisTotal = findViewById(R.id.KarcisTotal);

        String nama = InputNama.getText().toString().trim();
        String plat = InputNomorPlat.getText().toString().trim();
        String merk = InputMerkKendaraan.getText().toString().trim();
        String jamString = InputJam.getText().toString().trim();

        RadioGroup JnsKnd = findViewById(R.id.JenisKendaraan);
        int HasilPilihJenisKendaraan = JnsKnd.getCheckedRadioButtonId();

        int maksJamParkir = 36;
        int tarifAwal;
        int tarifLanjutan;
        int tarifMaks;

        if (!nama.isEmpty()) {
            if (!plat.isEmpty()) {
                if (!merk.isEmpty()) {
                    if (!jamString.isEmpty()) {
                        int jam = Integer.parseInt(jamString);
                        if (jam > 0) {
                            if (jam <= maksJamParkir) {
                                if (HasilPilihJenisKendaraan != -1) {

                                    RadioButton PilihanRadioButton = findViewById(HasilPilihJenisKendaraan);
                                    String jenisPilihan = PilihanRadioButton.getText().toString();

                                    if (jenisPilihan.equals("Mobil")) {
                                        tarifAwal = 10000;
                                        tarifLanjutan = 5000;
                                        tarifMaks = 30000;
                                    } else {
                                        tarifAwal = 5000;
                                        tarifLanjutan = 2000;
                                        tarifMaks = 15000;
                                    }

                                    int totalBayar = tarifAwal + tarifLanjutan * (jam - 1);

                                    if (totalBayar > tarifMaks) {
                                        totalBayar = tarifMaks;
                                    }

                                    //bgn HasilTampil
                                    JudulKarcis.setText("Ringkasan Karcis Parkir");

                                    KarcisNama.setText("Nama Pengguna: " + nama);
                                    KarcisKendaraan.setText("Kendaraan: " + jenisPilihan + " - " + merk + " (" + plat + ")");
                                    KarcisTotal.setText("Total Bayar: Rp " + String.format(java.util.Locale.GERMANY, "%,d", totalBayar));

                                    Toast.makeText(this, "Total Bayar : Rp" + String.format(java.util.Locale.GERMANY,"%,d", totalBayar), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this,"Silahkan Pilih Jenis Kendaraan Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Lama Parkir Maksimal" + maksJamParkir +"Jam!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Jam tidak boleh 0(nol)!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Masukan Lama Parkir Terlebih dahulu!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this,"Masukan Merek Kendaraan Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Masukan Nomor Plat  Terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Masukan Nama Pengguna Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
        }

    }

}

