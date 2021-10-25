package com.example.uts;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.adapter.ReservationAdapter;
import com.example.uts.database.DatabaseClient;
import com.example.uts.fragment.FragmentDetail;
import com.example.uts.model.Reservation;
import com.example.uts.preferences.UserPreferences;

import java.util.List;

public class Detail extends AppCompatActivity
{
    public RecyclerView rv_reservasi;
    public UserPreferences userPreferences;

    public List<Reservation> reservationList;
    public ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        changeFragment(new FragmentDetail());
        setTitle("Detail Reservasi");
    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment,fragment)
                .commit();
    }
}
