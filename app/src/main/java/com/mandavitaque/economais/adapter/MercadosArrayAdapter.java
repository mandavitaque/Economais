package com.mandavitaque.economais.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mandavitaque.economais.R;
import com.mandavitaque.economais.models.Mercado;
import com.mandavitaque.economais.models.Mercados;

import java.util.List;

import retrofit2.Callback;

public class MercadosArrayAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private List<Mercado> mercados;
    private Context mCtx;

    public MercadosArrayAdapter(List<Mercado> mercados, Context mCtx){
        this.mercados = mercados;
        layoutInflater =(LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mCtx = mCtx;
    }

    @Override
    public int getCount() {
        return mercados.size();
    }


    @Override
    public Object getItem(int position) {
        return mercados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder spinnerHolder;
        if(convertView == null){
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_list, parent, false);
            spinnerHolder.spinnerItemList = (TextView)convertView.findViewById(R.id.spinner_list_item);
            convertView.setTag(spinnerHolder);
        }else{
            spinnerHolder = (ViewHolder)convertView.getTag();
        }
        spinnerHolder.spinnerItemList.setText(mercados.get(position).getNome());

        return convertView;
    }
    class ViewHolder{
        TextView spinnerItemList;
        TextView mercadoID;
    }
}

