package com.example.ejereuskalmet.MasBalizas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ejereuskalmet.DB.Balizas;
import com.example.ejereuskalmet.DB.Datos;
import com.example.ejereuskalmet.MainActivity;
import com.example.ejereuskalmet.R;
import com.example.ejereuskalmet.ui.main.SectionsPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MasBalizasRVAdapter extends RecyclerView.Adapter<MasBalizasRVAdapter.ViewHolder> {

    public static List<Balizas> balizas;
    private LayoutInflater mInflater;
    private MainActivity main;
    private SectionsPagerAdapter sectionsPagerAdapter;

    public MasBalizasRVAdapter() {
    }

    public MasBalizasRVAdapter(Context context, SectionsPagerAdapter sectionsPagerAdapter) {
        this.mInflater = LayoutInflater.from(context);
        this.main = (MainActivity) context;
        this.sectionsPagerAdapter = sectionsPagerAdapter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nombre;
        private final Switch aswitch;

        public ViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.tvNombre);
            aswitch = (Switch) view.findViewById(R.id.swc1);
        }

        public TextView getNombre() {
            return nombre;
        }

        public Switch getSwitch() {
            return aswitch;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.masbalizas, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.getNombre().setText(balizas.get(position).name);

        if (viewHolder.getSwitch() == null) {
        } else {
            if (balizas.get(position).activated.equals("true")) {
                viewHolder.getSwitch().setChecked(true);
            }
            viewHolder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sectionsPagerAdapter.FuncionAdapter(isChecked, balizas.get(position), main);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (balizas == null) {
            return 0;
        } else {
            return balizas.size();
        }
    }

    public void setTrue(Balizas baliza) {

        HandlerThread ht = new HandlerThread("HandleThread");
        ht.start();

        Handler handlerLeer = new Handler(ht.getLooper());

        handlerLeer.post(new Runnable() {
            @Override
            public void run() {
                MainActivity.db.balizasDao().setTrue(baliza.id);
            }
        });
        notifyDataSetChanged();
    }

    public void setFalse(Balizas baliza) {
        HandlerThread ht = new HandlerThread("HandleThread");
        ht.start();

        Handler handlerLeer = new Handler(ht.getLooper());

        handlerLeer.post(new Runnable() {
            @Override
            public void run() {
                MainActivity.db.balizasDao().setFalse(baliza.id);
            }
        });
        notifyDataSetChanged();
    }

    public void setBalizas(List<Balizas> balizas) {
        this.balizas = balizas;
        notifyDataSetChanged();
    }

}
