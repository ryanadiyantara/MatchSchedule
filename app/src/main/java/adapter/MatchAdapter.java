package adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.premierleague.R;
import com.example.premierleague.Update;
import com.example.premierleague.ViewMatch;

import java.io.Serializable;
import java.util.ArrayList;

import db.DbHelper;
import model.Match;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.OrderViewHolder>{
    private ArrayList<Match> listMatch = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public MatchAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public ArrayList<Match> getListMatch() {
        return listMatch;
    }

    public void setListMatch(ArrayList<Match> listNotes) {
        if (listNotes.size() > 0) {
            this.listMatch.clear();
        }
        this.listMatch.addAll(listNotes);
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView clubName1, clubName2;
        private ImageView logo1, logo2;
        private TextView tanggal, waktu, deskripsi;
        final Button btn_view, btn_edit;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            clubName1 = itemView.findViewById(R.id.clubname_1);
            clubName2 = itemView.findViewById(R.id.clubname_2);
            logo1 = itemView.findViewById(R.id.clublogo_1);
            logo2 = itemView.findViewById(R.id.clublogo_2);
            tanggal = itemView.findViewById(R.id.tanggal);
            waktu = itemView.findViewById(R.id.waktu);
            deskripsi = itemView.findViewById(R.id.deskripsi);

            btn_view = itemView.findViewById(R.id.view);
            btn_edit = itemView.findViewById(R.id.edit);
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_adapter, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        holder.clubName1.setText(listMatch.get(position).getMatch_clubname_1());
        holder.clubName2.setText(listMatch.get(position).getMatch_clubname_2());

        byte[] Blogo1 = listMatch.get(position).getMatch_clublogo_1();
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(Blogo1, 0, Blogo1.length);
        holder.logo1.setImageBitmap(bitmap1);

        byte[] Blogo2 = listMatch.get(position).getMatch_clublogo_2();
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(Blogo2, 0, Blogo2.length);
        holder.logo2.setImageBitmap(bitmap2);

        holder.tanggal.setText(listMatch.get(position).getMatch_tanggal());
        holder.waktu.setText(listMatch.get(position).getMatch_waktu());
        holder.deskripsi.setText(listMatch.get(position).getMatch_deskripsi());

        holder.btn_view.setOnClickListener((View v) -> {
            Intent intentview = new Intent(activity, ViewMatch.class);
            intentview.putExtra("matchview", (Serializable) listMatch.get(position));
            activity.startActivity(intentview);
        });

        holder.btn_edit.setOnClickListener((View v) -> {
            Intent intentedit = new Intent(activity, Update.class);
            intentedit.putExtra("matchedit", (Serializable) listMatch.get(position));
            activity.startActivity(intentedit);
        });

    }

    @Override
    public int getItemCount() {
        return listMatch.size();
    }
}