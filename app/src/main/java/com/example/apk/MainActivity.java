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
        EditText InputPajakSTNK = findViewById(R.id.InputPajakSTNK);
        EditText InputUsiaKendaraan = findViewById(R.id.editusia);
        TextView Hasilpkb = findViewById(R.id.hasilpkb);
        TextView HasilSWDKLLJ = findViewById(R.id.SWDKLLJ);
        TextView HasilSertif = findViewById(R.id.Sertif);
        TextView hasilAdministrasi = findViewById(R.id.Administrasi);
        TextView HasilTotal = findViewById(R.id.hasilTotal);
        TextView Tekspkbp = findViewById(R.id.pkbp);
        TextView Teksswd = findViewById(R.id.swd);
        TextView Teksbad = findViewById(R.id.bad);
        TextView Tekssod = findViewById(R.id.sod);
        TextView Tekstotal = findViewById(R.id.total);

        String ccString = InputCC.getText().toString().trim();
        String pjkstnkString = InputPajakSTNK.getText().toString().trim();
        String usiaString = InputUsiaKendaraan.getText().toString().trim();


        Double Bobot = 0.0;

        RadioGroup JnsKnd = findViewById(R.id.JenisKendaraan);
        int HasilPilihJenisKendaraan = JnsKnd.getCheckedRadioButtonId();




        if (HasilPilihJenisKendaraan != -1) {
            if (!ccString.isEmpty()) {
                if (!pjkstnkString.isEmpty()) {
                    if (!usiaString.isEmpty()) {

                        RadioButton PilihanRadioButton = findViewById(HasilPilihJenisKendaraan);
                        String JenisPilihan = PilihanRadioButton.getText().toString();

                        if (JenisPilihan == "Mobil") {
                            Bobot = 1.025;
                        } else if (JenisPilihan == "Motor") {
                            Bobot = 1.0;
                        } else {
                            Bobot = 1.3;
                        }
                        double TarifPajakDaerah = 0.02;

                        int cc = Integer.parseInt(InputCC.getText().toString().trim());
                        double PajakSTNK = Double.parseDouble(InputPajakSTNK.getText().toString().trim());
                        int usiaKendaraan = Integer.parseInt(InputUsiaKendaraan.getText().toString().trim());
                        double awalnjkb = (PajakSTNK / TarifPajakDaerah) * 2; //Contoh pajak daerah = 2%. krn bnyk & berbeda tiap daerah


                        double penguranganUsiaK = 0.0;

                        if (usiaKendaraan >= 10) {
                            penguranganUsiaK = 0.5;
                        } else if (usiaKendaraan > 0) {
                            penguranganUsiaK = 0.05;
                            for (int i = 1; i < usiaKendaraan; i++) {
                                if (penguranganUsiaK >= 0.5) {
                                    penguranganUsiaK = 0.5;
                                }
                                else {
                                    penguranganUsiaK = 0.05 + penguranganUsiaK;
                                }

                            }
                        } else {
                            penguranganUsiaK = 0.00;

                        }

                        double njkb = awalnjkb - (awalnjkb * penguranganUsiaK);
                        double pkbPokok = njkb * Bobot * TarifPajakDaerah; //sm sprt sebelumnya tarif pajak daerah = 2%


                        int swdkllj = 5000;
                        if (cc <= 250 && JenisPilihan.equals("Motor")) {
                            swdkllj = 35000;
                        } else if (cc > 250 && JenisPilihan.equals("Motor")) {
                            swdkllj = 80000;
                        } else if (cc < 2400 && JenisPilihan.equals("Mobil")) {
                            swdkllj = 70000;
                        } else if (cc > 2400 && JenisPilihan.equals("Mobil")) {
                            swdkllj = 140000;
                        } else {
                            swdkllj = 0;
                        }

                        int sertif = 3000;

                        int Total_swdkllj = swdkllj + sertif;

                        int biayaAdministrasi = 50000;

                        double PKBtotal = pkbPokok + swdkllj + biayaAdministrasi;

                        Tekspkbp.setText("PKB Pokok:");
                        Teksswd.setText("SWDKLLJ Pokok");
                        Teksbad.setText("Biaya Administrasi:");
                        Tekssod.setText("Sertifikat/Dana:");
                        Tekstotal.setText("ESTIMASI:");


                        Hasilpkb.setText("Rp " + String.format("%,.0f", pkbPokok));
                        HasilSWDKLLJ.setText("Rp " + String.format("%,d", swdkllj));
                        hasilAdministrasi.setText("Rp " + String.format("%,d", biayaAdministrasi));
                        HasilSertif.setText("Rp " + String.format("%,d", sertif));
                        HasilTotal.setText("Rp " + String.format("%,.0f", PKBtotal));

                        Toast.makeText(this, "Estimasi bayar : Rp" + String.format("%,.0f", PKBtotal), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "Masukan Usia Kendaraan Terlebih dahulu!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Masukan Pajak STNK Terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Masukan CC Kendaraan Terlebih dahulu!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Pilih Jenis Kendaraan Terlebih dahulu", Toast.LENGTH_SHORT).show();
        }

    }

}

