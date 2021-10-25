package com.example.uts.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.Detail;
import com.example.uts.R;
import com.example.uts.ReservationActivity;
import com.example.uts.database.DatabaseClient;
import com.example.uts.fragment.FragmentDetail;
import com.example.uts.model.Reservation;
import com.example.uts.preferences.UserPreferences;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder>
{
    private List<Reservation> reservationList;
    private Context context;
    private DatabaseClient databaseClient;
    private UserPreferences userPreferences;

    public ReservationAdapter(List<Reservation> reservationList, Context context) {
        this.reservationList = reservationList;
        this.context = context;
        this.userPreferences = new UserPreferences(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //init view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.tv_tanggal.setText(reservation.getTanggal());
        holder.tv_paket.setText(reservation.getPaket());
        holder.tv_note.setText(reservation.getNote());
        holder.tv_harga.setText(Double.toString(reservation.getHarga()));
        databaseClient = DatabaseClient.getInstance(context);
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReservation(reservation);
                Toast.makeText(context.getApplicationContext(), "Reservation Cancelled", Toast.LENGTH_SHORT).show();
                if(context instanceof Detail)
                {
                    Detail detail = (Detail) context;
                    detail.changeFragment(new FragmentDetail());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tanggal, tv_paket, tv_note, tv_harga;
        private Button btnCancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_paket = itemView.findViewById(R.id.tv_paket);
            tv_note = itemView.findViewById(R.id.tv_note);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }

    private void cancelReservation(Reservation reservation) {
        class CancelReservation extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(context.getApplicationContext())
                        .getDatabase()
                        .reservationDao()
                        .deleteReservation(reservation);

                return null;
            }
        }
        CancelReservation cancelReservation = new CancelReservation();
        cancelReservation.execute();
    }
}
