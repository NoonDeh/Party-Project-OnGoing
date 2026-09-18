package com.example.apk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

Button bayar;
String nama, plat, jenisPilihanParkir, jamString, jenisPilihan, area;
int totalBayar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bayar = (Button) findViewById(R.id.Bayar);


        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    EditText InputNama = findViewById(R.id.InputNama);
                    EditText InputNomorPlat = findViewById(R.id.InputNomorPlat);

                    EditText InputJam = findViewById(R.id.InputJam);

                    LinearLayout LayoutKarcis = findViewById(R.id.LayoutKarcis);

                    TextView JudulKarcis = findViewById(R.id.JudulKarcis);
                    TextView KarcisNama = findViewById(R.id.KarcisNama);
                    TextView KarcisKendaraan = findViewById(R.id.KarcisKendaraan);
                    TextView KarcisTotal = findViewById(R.id.KarcisTotal);

                     nama = InputNama.getText().toString().trim();
                     plat = InputNomorPlat.getText().toString().trim();
                     jamString = InputJam.getText().toString().trim();

                    RadioGroup JnsKnd = findViewById(R.id.JenisKendaraan);
                    int HasilPilihJenisKendaraan = JnsKnd.getCheckedRadioButtonId();

                    RadioGroup JnsPrk = findViewById(R.id.JenisParkir);
                    int HasilPilihJenisParkir = JnsPrk.getCheckedRadioButtonId();

                    int maksJamParkir = 36;
                    int tarifAwal;
                    int tarifLanjutan;


                    if (!nama.isEmpty()) {
                        if (!plat.isEmpty()) {
                            if (HasilPilihJenisParkir != -1) {
                                if (!jamString.isEmpty()) {
                                    int jam = Integer.parseInt(jamString);
                                    if (jam > 0) {
                                        if (jam <= maksJamParkir) {
                                            if (HasilPilihJenisKendaraan != -1) {

                                                RadioButton PilihanRadioButton = findViewById(HasilPilihJenisKendaraan);
                                                jenisPilihan = PilihanRadioButton.getText().toString();

                                                RadioButton PilihanRadioButton_parkir = findViewById(HasilPilihJenisParkir);
                                                jenisPilihanParkir = PilihanRadioButton_parkir.getText().toString();

                                                if (jenisPilihanParkir.equals("Luar")) {
                                                    area = "Luar";
                                                } else {
                                                    area = "Dalam";
                                                }

                                                if (jenisPilihan.equals("Mobil")) {
                                                    tarifAwal = 10000;
                                                    tarifLanjutan = 5000;
                                                } else {
                                                    tarifAwal = 5000;
                                                    tarifLanjutan = 2000;
                                                }

                                                totalBayar = tarifAwal + tarifLanjutan * (jam - 1);
                                                jenisPilihanParkir = area;


                                                //bgn HasilTampil
                                                JudulKarcis.setText("Ringkasan Karcis Parkir");

                                                KarcisNama.setText("Nama Pengguna: " + nama);
                                                KarcisKendaraan.setText("Kendaraan: " + jenisPilihan + " - " + "(" + jenisPilihanParkir +")"+ " (" + plat + ")");
                                                KarcisTotal.setText("Total Bayar: Rp " + String.format(java.util.Locale.GERMANY, "%,d", totalBayar));
                                                AlertDialog dialog = createDialog();
                                                dialog.show();


                                            } else {
                                                Toast.makeText(MainActivity.this,"Silahkan Pilih Jenis Kendaraan Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(MainActivity.this, "Lama Parkir Maksimal " + maksJamParkir +" Jam!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Jam tidak boleh 0(nol)!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Masukan Lama Parkir Terlebih dahulu!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this,"Pilih Area Parkir Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Masukan Nomor Plat  Terlebih dahulu!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Masukan Nama Pengguna Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
                    }
                    return;


                }




        });



    }





    AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Total Pembayaran");
        builder.setMessage("Nama Pengguna: " + nama + "\n\n" + "Kendaraan: " + jenisPilihan + "\n\n" + "Plat Motor: " + plat + "\n\n" + "Area Parkir: " + jenisPilihanParkir + " Swalayan" + "\n\n" + "Total Bayar: Rp " + String.format(java.util.Locale.GERMANY, "%,d", totalBayar ));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create(); }


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




}

