package com.mandavitaque.economais.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mandavitaque.economais.R.layout;
import com.mandavitaque.economais.R;
import com.mandavitaque.economais.models.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    private List<Categoria> categorias;
    private Context mCtx;
    int selectedPos;

    public CategoriaAdapter(List<Categoria> categorias, Context mCtx){
        this.categorias = categorias;
        this.mCtx = mCtx;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_prodcategoria, parent, false);
        return new CategoriaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriaAdapter.ViewHolder holder, int position) {

        Categoria categoria = categorias.get(position);
        holder.txtNomeCategoriaL.setText(categoria.getNome());

        //holder.itemView.setBackgroundColor(selectedPos == position ? Color.GREEN : Color.TRANSPARENT);


    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtNomeCategoriaL;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txtNomeCategoriaL = itemView.findViewById(R.id.txtNomeCategoriaListaID);

        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION)
                return;

            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);
            abreCategoria();

        }

        public void abreCategoria(){
            Categoria selecionada = categorias.get(selectedPos);
            Toast.makeText(mCtx, selecionada.getNome() + " " + selecionada.getId(), Toast.LENGTH_SHORT).show();

        }
    }
}
