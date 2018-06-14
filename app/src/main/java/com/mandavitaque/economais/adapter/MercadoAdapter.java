package com.mandavitaque.economais.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.mandavitaque.economais.R;
import com.mandavitaque.economais.activities.MercadoActivity;
import com.mandavitaque.economais.models.Mercado;

import java.util.List;

public class MercadoAdapter extends RecyclerView.Adapter<MercadoAdapter.ViewHolder>{

        private List<Mercado> mercados;
        private Context mCtx;
        int selectedPos = 0;
        private View.OnClickListener clickListener;

        public MercadoAdapter(List<Mercado> mercados, Context mCtx){
        this.mercados = mercados;
        this.mCtx = mCtx;
        }

    @Override
    public MercadoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_mercadosdistancia, parent, false);
        return new MercadoAdapter.ViewHolder(v);


    }

    @Override
    public int getItemCount() {
        return mercados.size();
    }

    @Override
    public void onBindViewHolder(MercadoAdapter.ViewHolder holder, int position) {
            final Mercado mercado = mercados.get(position);
            float valorDistancia = mercado.getDistancia();
            if (valorDistancia >= 1000){
                valorDistancia = valorDistancia/1000;
                String distancia = String.valueOf(valorDistancia);
                holder.txtNomeMercadoLista.setText(mercado.getNome());
                holder.txtDistanciaMercado.setText(distancia + "km");
            }
            else {
                String distancia = String.valueOf(valorDistancia);
                holder.txtNomeMercadoLista.setText(mercado.getNome());
                holder.txtDistanciaMercado.setText(distancia + "m");
            }
            }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtNomeMercadoLista;
        public TextView txtDistanciaMercado;
        private AdapterView.OnItemClickListener itemClickListener;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txtNomeMercadoLista = itemView.findViewById(R.id.txtNomeMercadoID);
            txtDistanciaMercado = itemView.findViewById(R.id.txtDistanciaMercadoID);


        }



        public void abreMercado(){
            Mercado selecionada = mercados.get(selectedPos);
            Toast.makeText(mCtx, selecionada.getNome() + " " + selecionada.getId(), Toast.LENGTH_SHORT).show();
            //Intent mercado = new Intent(mCtx, MercadoActivity.class);
           // return selecionada;
        }



        @Override
        public void onClick(View v) {

             if (getAdapterPosition() == RecyclerView.NO_POSITION)
                return;

            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);
            abreMercado();
            Intent intent = new Intent(mCtx,MercadoActivity.class);
            intent.putExtra("teste", String.valueOf(txtNomeMercadoLista));
            mCtx.startActivity(intent);

        }
    }
}
