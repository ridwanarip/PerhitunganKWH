package com.ridwan.perhitungankwh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HasilActivity extends AppCompatActivity {

    private List<String> itemHasil = new ArrayList<>();
    private double total = 0.0;

    private LinearLayout layoutHasil;
    private TextView textKuota, textTotal, textKesimpulan;

    private DecimalFormat df0 = new DecimalFormat("#");
    private DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        setTitle("Hasil");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        itemHasil.clear();
        itemHasil = bundle.getStringArrayList("ITEM_HASIL");

        layoutHasil = (LinearLayout) findViewById(R.id.layoutHasil);
        layoutHasil.removeAllViews();
        for (int i=0; i < itemHasil.size(); i++) {
            TextView textItem = new TextView(HasilActivity.this);
            textItem.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
            textItem.setText(String.valueOf(i+1));
            textItem.setTextSize(14);
            textItem.setGravity(Gravity.CENTER);

            TextView textHasil = new TextView(HasilActivity.this);
            textHasil.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textHasil.setText(df2.format(Double.parseDouble(itemHasil.get(i).toString())));
            textHasil.setTextSize(14);
            textHasil.setGravity(Gravity.CENTER);

            LinearLayout layoutHorizontal = new LinearLayout(HasilActivity.this);
            layoutHorizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            layoutHorizontal.addView(textItem);
            layoutHorizontal.addView(textHasil);

            layoutHasil.addView(layoutHorizontal);
            total += Double.parseDouble(itemHasil.get(i).toString());
        }

        String kuota = bundle.getString("ITEM_KUOTA");
        textKuota = (TextView) findViewById(R.id.textKuota);
        textKuota.setText(kuota);

        String strTotal = df2.format(total);
        textTotal = (TextView) findViewById(R.id.textTotal);
        textTotal.setText(strTotal);

        double hari = Double.parseDouble(kuota) / Double.parseDouble(strTotal);
        textKesimpulan = (TextView) findViewById(R.id.textKesimpulan);
        textKesimpulan.setText("Dari jumlah kuota "+ kuota +" KWH,\n" +
                "Pemakaian total item per-hari "+ strTotal +" KWH,\n" +
                "Maka dapat mencukupi untuk "+df0.format(hari)+" hari.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
