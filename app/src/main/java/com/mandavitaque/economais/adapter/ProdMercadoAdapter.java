package com.mandavitaque.economais.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mandavitaque.economais.R.layout;
import com.mandavitaque.economais.R;
import com.mandavitaque.economais.models.ProdMercado;
import com.tapadoo.alerter.Alerter;


import java.util.ArrayList;
import java.util.List;

public class ProdMercadoAdapter extends RecyclerView.Adapter<ProdMercadoAdapter.ViewHolder> {

    private List<ProdMercado> prodMercados;
    private Context mCtx;

    public ProdMercadoAdapter(List<ProdMercado> prodMercados, Context mCtx){
        this.prodMercados = prodMercados;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layout.list_prodmercado, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProdMercadoAdapter.ViewHolder holder, int position) {
            final ProdMercado prodMercado = prodMercados.get(position);
            float precotemp = prodMercado.getPrecoProduto();
            String precoProd = Float.toString(precotemp);

            holder.txtNomeProdutoL.setText(prodMercado.getNomeProduto());
            holder.txtPesoProdutoL.setText(prodMercado.getPesoProduto()+ 'g');
            holder.txtPrecoProdutoL.setText("R$: "+ precoProd);
            holder.txtNomeMercadoL.setText(prodMercado.getNomeMercado());

            holder.btnAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


    }

    @Override
    public int getItemCount() {
            return prodMercados.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txtNomeProdutoL;
            public TextView txtPesoProdutoL;
            public TextView txtPrecoProdutoL;
            public TextView txtNomeMercadoL;
            public Button btnAdicionarCarrinho;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNomeProdutoL = itemView.findViewById(R.id.txtNomeProdutoListaID);
            txtPesoProdutoL = itemView.findViewById(R.id.txtPesoProdutoListaID);
            txtPrecoProdutoL = itemView.findViewById(R.id.txtPrecoProdutoListaID);
            txtNomeMercadoL = itemView.findViewById(R.id.txtNomeMercadoListaID);
            btnAdicionarCarrinho = itemView.findViewById(R.id.btnAdicionarCarrinhoListaID);
        }
    }
}
