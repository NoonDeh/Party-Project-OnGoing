package com.example.apk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void Submit (View view) {
        EditText InputCC = findViewById(R.id.InputCC);
        EditText InputHarga = findViewById(R.id.editharga);
        EditText InputUsiaKendaraan = findViewById(R.id.editusia);
        TextView Hasilpkb = findViewById(R.id.hasilpkb);
        TextView HasilSWDKLLJ = findViewById(R.id.SWDKLLJ);
        TextView HasilSertif = findViewById(R.id.Sertif);
        TextView HasilTotal = findViewById(R.id.hasilTotal);

        Double Bobot = 0.0;

        RadioGroup JnsKnd = findViewById(R.id.JenisKendaraan);
        int HasilPilihJenisKendaraan = JnsKnd.getCheckedRadioButtonId();

        if (HasilPilihJenisKendaraan != -1) {
            RadioButton PilihanRadioButton = findViewById(HasilPilihJenisKendaraan);
            String JenisPilihan = PilihanRadioButton.getText().toString();

            if (JenisPilihan == "Mobil") {
                Bobot = 1.025;
            }
            else if (JenisPilihan == "Motor") {
                Bobot = 1.0;
            }
            else {
                Bobot = 1.3;
            }
            double TarifPajakDaerah = 0.02;

            int cc = Integer.parseInt(InputCC.getText().toString().trim());
            double PajakSTNK = Double.parseDouble(InputHarga.getText().toString().trim());
            int usiaKendaraan = Integer.parseInt(InputUsiaKendaraan.getText().toString().trim());
            double njkb = (PajakSTNK / TarifPajakDaerah) * 2; //Contoh pajak daerah = 2%. krn bnyk & berbeda tiap daerah
            double pkbPokok = njkb * Bobot * TarifPajakDaerah; //sm sprt sebelumnya tarif pajak daerah = 2%


            int swdkllj = 0;
            if (cc <= 250 && JenisPilihan == "Motor") {
                swdkllj = 35000;
            } else if (cc > 250 && JenisPilihan == "Motor") {
                swdkllj = 80000;
            } else if (cc < 2400 && JenisPilihan == "Mobil") {
                swdkllj = 70000;
            } else if (cc > 2400 && JenisPilihan == "Mobil") {
                swdkllj = 140000;
            }

            int Total_swdkllj = swdkllj + 3000;

            // 3. Biaya Administrasi / Sertifikat / Pengesahan STNK (Contoh flat)
            int biayaAdministrasi = 50000;

            // 4. Total Pajak Kendaraan
            double PKBtotal = pkbPokok + swdkllj + biayaAdministrasi;

            // Tampilkan hasil ke TextView
            Hasilpkb.setText("PKB Pokok: Rp " + String.format("%,.0f", pkbPokok));
            HasilSWDKLLJ.setText("SWDKLLJ: Rp " + String.format("%,d", swdkllj));
            HasilSertif.setText("Admin/Sertifikat: Rp " + String.format("%,d", biayaAdministrasi));
            HasilTotal.setText("Total Pajak: Rp " + String.format("%,.0f", PKBtotal));

        } else {
            Toast.makeText(this, "Pilih Jenis Kendaraan Terlebih dahulu", Toast.LENGTH_SHORT).show();
        }

    }

}

