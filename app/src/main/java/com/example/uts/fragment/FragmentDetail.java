package com.example.uts.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.Detail;
import com.example.uts.R;
import com.example.uts.adapter.ReservationAdapter;
import com.example.uts.database.DatabaseClient;
import com.example.uts.model.Reservation;
import com.example.uts.preferences.UserPreferences;

import java.util.ArrayList;
import java.util.List;

public class FragmentDetail extends Fragment {
    private RecyclerView rv_reservasi;
    private UserPreferences userPreferences;
    private Context context;

    private List<Reservation> reservationList;
    private ReservationAdapter reservationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_reservation_detail, container, false);
        rv_reservasi = root.findViewById(R.id.rv_reservasi);

        userPreferences = new UserPreferences(getContext());

        rv_reservasi.setLayoutManager(new LinearLayoutManager(getContext()));

        getReservations();

        reservationList = new ArrayList<>();

        return root;
    }

    public void getReservations() {
        class GetReservations extends AsyncTask<Void, Void, List<Reservation>> {

            @Override
            protected List<Reservation> doInBackground(Void... voids) {
                List<Reservation> reservationList = DatabaseClient.getInstance(getContext())
                        .getDatabase()
                        .reservationDao()
                        .getReservationsByUserId(userPreferences.getUserLogin().getId());
                return reservationList;
            }

            @Override
            protected void onPostExecute(List<Reservation> reservations) {
                super.onPostExecute(reservations);
                reservationAdapter = new ReservationAdapter(reservations, getContext());
                rv_reservasi.setAdapter(reservationAdapter);
            }
        }

        GetReservations getReservations = new GetReservations();
        getReservations.execute();
    }

}
