package com.example.uts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.uts.database.DatabaseClient;
import com.example.uts.fragment.FragmentDetail;
import com.example.uts.model.Reservation;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.uts.preferences.UserPreferences;

public class ReservationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Spinner spinnerPaket;
    private static final String[] paketGunting = {"Gunting", "Gunting + Cuci", "Gunting + Cuci + Pijat"};
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String paket;
    private String tanggal;
    private String note;
    private double harga;
    private UserPreferences userPreferences;
    TextView hargaPaket;
    EditText tglReservasi;
    TextInputEditText noteText;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

         hargaPaket = findViewById(R.id.hargaPaket);
         tglReservasi= findViewById(R.id.tglReservasi);
         noteText = findViewById(R.id.note);

        userPreferences = new UserPreferences(this);
        spinnerPaket = (Spinner)findViewById(R.id.spinnerPaket);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(ReservationActivity.this, android.R.layout.simple_spinner_item, paketGunting);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaket.setAdapter(adapter);
        spinnerPaket.setOnItemSelectedListener(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(tglReservasi, myCalendar);
            }

        };

        tglReservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReservationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String newHarga;
        switch (position) {
            case 0:
                paket = spinnerPaket.getSelectedItem().toString();
                harga = 15000;
                newHarga = Double.toString(harga);
                hargaPaket.setText(newHarga);
                break;
            case 1:
                paket = spinnerPaket.getSelectedItem().toString();
                harga = 18000;
                newHarga = Double.toString(harga);
                hargaPaket.setText(newHarga);
                break;
            case 2:
                paket = spinnerPaket.getSelectedItem().toString();
                harga = 20000;
                newHarga = Double.toString(harga);
                hargaPaket.setText(newHarga);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void updateLabel(EditText tglReservasi, Calendar myCalendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tglReservasi.setText(sdf.format(myCalendar.getTime()));
    }

    public void btnSave (View view) {
        tanggal = tglReservasi.getText().toString();
        note = noteText.getText().toString();
        saveReservation(paket, tanggal, note, harga);
    };

    public void btnBack (View view) {
        finish();
    };

    private void saveReservation(String paket, String tanggal, String note, double harga)
    {
        class SaveReservation extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                Reservation reservation = new Reservation();
                reservation.setPaket(paket);
                reservation.setTanggal(tanggal);
                reservation.setNote(note);
                reservation.setHarga(harga);
                reservation.setUser_id(userPreferences.getUserLogin().getId());

                DatabaseClient.getInstance(ReservationActivity.this)
                        .getDatabase()
                        .reservationDao()
                        .addReservation(reservation);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(ReservationActivity.this, "Reservation Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        SaveReservation saveReservation = new SaveReservation();
        saveReservation.execute();
    }
}
