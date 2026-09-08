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

    private EditText etPkbDasar, etKeterlambatan;
    private RadioGroup rgJenisKendaraan, rgJenisPajak;
    private RadioButton rbMotor, rbTahunan;
    private Button btnHitung;
    private TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Komponen UI
        etPkbDasar = findViewById(R.id.etPkbDasar);
        etKeterlambatan = findViewById(R.id.etKeterlambatan);
        rgJenisKendaraan = findViewById(R.id.rgJenisKendaraan);
        rgJenisPajak = findViewById(R.id.rgJenisPajak);
        rbMotor = findViewById(R.id.rbMotor);
        rbTahunan = findViewById(R.id.rbTahunan);
        btnHitung = findViewById(R.id.btnHitung);
        tvHasil = findViewById(R.id.tvHasil);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitungPajak();
            }
        });
    }

    private void hitungPajak() {
        String inputPkb = etPkbDasar.getText().toString().trim();
        String inputBulan = etKeterlambatan.getText().toString().trim();

        if (inputPkb.isEmpty()) {
            Toast.makeText(this, "Masukkan nilai PKB dasar terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        double pkbDasar = Double.parseDouble(inputPkb);
        int bulanTerlambat = inputBulan.isEmpty() ? 0 : Integer.parseInt(inputBulan);

        boolean isMotor = rbMotor.isChecked();
        boolean isTahunan = rbTahunan.isChecked();

        // 1. Penentuan Nilai Konstanta
        double swdkllj = isMotor ? 32000 : 143000;
        double dendaSwdklljFlat = isMotor ? 32000 : 100000;
        double pnbpStnk = isMotor ? 100000 : 200000;
        double pnbpPlat = isMotor ? 60000 : 100000;

        // 2. Hitung Denda Keterlambatan
        double dendaPkb = 0;
        double dendaSwdkllj = 0;

        if (bulanTerlambat > 0) {
            dendaPkb = pkbDasar * 0.02 * bulanTerlambat;
            dendaSwdkllj = dendaSwdklljFlat;
        }

        // 3. Hitung Total Pembayaran
        double totalTahunan = pkbDasar + swdkllj + dendaPkb + dendaSwdkllj;
        double totalBayar = totalTahunan;

        if (!isTahunan) {
            totalBayar += (pnbpStnk + pnbpPlat);
        }

        // Format Angka ke Rupiah
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String hasilFormatted = rupiahFormat.format(totalBayar);

        // Tampilkan Rincian
        StringBuilder rincian = new StringBuilder();
        rincian.append("Rincian Pajak:\n");
        rincian.append("- PKB Dasar: ").append(rupiahFormat.format(pkbDasar)).append("\n");
        rincian.append("- SWDKLLJ: ").append(rupiahFormat.format(swdkllj)).append("\n");

        if (bulanTerlambat > 0) {
            rincian.append("- Denda PKB: ").append(rupiahFormat.format(dendaPkb)).append("\n");
            rincian.append("- Denda SWDKLLJ: ").append(rupiahFormat.format(dendaSwdkllj)).append("\n");
        }

        if (!isTahunan) {
            rincian.append("- Cetak STNK 5 Thn: ").append(rupiahFormat.format(pnbpStnk)).append("\n");
            rincian.append("- Cetak Plat TNKB: ").append(rupiahFormat.format(pnbpPlat)).append("\n");
        }

        rincian.append("\nTOTAL BAYAR: ").append(hasilFormatted);

        tvHasil.setText(rincian.toString());
    }
}

