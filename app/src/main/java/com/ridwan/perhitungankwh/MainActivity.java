package com.ridwan.perhitungankwh;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutVertical;
    private Spinner spinner;
    private Button btnHitung;
    private EditText textKuota;

    private ArrayList<EditText> kwhArray = new ArrayList<>();
    private ArrayList<EditText> jamArray = new ArrayList<>();
    private ArrayList<String> listHasil = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textKuota = (EditText) findViewById(R.id.editKuota);
        layoutVertical = (LinearLayout) findViewById(R.id.layoutVertical);

        final String[] jml = new String[15];
        int x = 1;
        for (String n : jml) {
            jml[x-1]=String.valueOf(x++);
        }
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, jml);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kwhArray.clear();
                jamArray.clear();
                layoutVertical.removeAllViews();
                for (int i=0; i<=position; i++) {
                    TextView textNo = new TextView(MainActivity.this);
                    textNo.setLayoutParams(new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textNo.setText(String.valueOf(i+1));
                    textNo.setTextSize(14);
                    textNo.setGravity(Gravity.CENTER);

                    EditText textKwh = new EditText(MainActivity.this);
                    textKwh.setLayoutParams(new LinearLayout.LayoutParams(240, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textKwh.setTextSize(14);
                    textKwh.setInputType(InputType.TYPE_CLASS_NUMBER);
                    textKwh.setHint("Watt");
                    kwhArray.add(i, textKwh);

                    EditText textJam = new EditText(MainActivity.this);
                    textJam.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textJam.setInputType(InputType.TYPE_CLASS_NUMBER);
                    textJam.setTextSize(14);
                    textJam.setHint("Menit");
                    jamArray.add(i, textJam);

                    LinearLayout layoutHorizontal = new LinearLayout(MainActivity.this);
                    layoutHorizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
                    layoutHorizontal.addView(textNo);
                    layoutHorizontal.addView(textKwh);
                    layoutHorizontal.addView(textJam);

                    layoutVertical.addView(layoutHorizontal);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnHitung = (Button) findViewById(R.id.btnHitung);
        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt((String)spinner.getSelectedItem());
                double hasil = 0.0;
                listHasil.clear();
                for (int x=0; x<i; x++) {
                    hasil = (Double.parseDouble(kwhArray.get(x).getText().toString()) / 1000) * (Double.parseDouble(jamArray.get(x).getText().toString()) / 60);
                    listHasil.add(x,String.valueOf(hasil));
                }

                Intent intent = new Intent(MainActivity.this, HasilActivity.class);
                intent.putExtra("ITEM_KUOTA", textKuota.getText().toString());
                intent.putExtra("ITEM_HASIL", listHasil);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Apa anda ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            startActivity(new Intent(MainActivity.this, BantuanActivity.class));
        } else if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, TentangActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
