package com.example.tcc_barbearia_cliente.ui.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_barbearia_cliente.ModeloBarbeiro;
import com.example.tcc_barbearia_cliente.R;
import com.example.tcc_barbearia_cliente.ModeloHorario;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter3 extends RecyclerView.Adapter<Adapter3.ModeloHorariosViewHolder>{
    private final List<ModeloHorario> horarios;
    private final Context context;
    private final Adapter3.ModeloHorarioOnClickListener onClickListener;

    public interface ModeloHorarioOnClickListener {
        void onClickModeloHorario(Adapter3.ModeloHorariosViewHolder holder, int idx);
    }

    public Adapter3(Context context, List<ModeloHorario> horarios, Adapter3.ModeloHorarioOnClickListener onClickListener) {
        this.context = context;
        this.horarios = horarios;
        this.onClickListener = onClickListener;
    }

    @Override
    public Adapter3.ModeloHorariosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_do_recycler_cliente, viewGroup, false);
        // Cria a classe do ViewHolder
        Adapter3.ModeloHorariosViewHolder holder = new Adapter3.ModeloHorariosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Adapter3.ModeloHorariosViewHolder holder, final int position) {
        // Este método recebe o índice do elemento, e atualiza as views que estão dentro do ViewHolder
        ModeloHorario c = horarios.get(position);
        // Atualizada os valores nas views
        //holder.tNome.setText(c.getNome());
        holder.tCabelo.setText(c.getCabelo());
        holder.tBarba.setText(c.getBarba());
        holder.tSobrancelha.setText(c.getSobrancelha());
        holder.tHorario.setText(c.getHorario());
        holder.tValor.setText(c.getValorTotal());
        holder.tData.setText(c.getData());
       /* if(c.getFoto() != null)
        {
            Picasso.with(this.context).load(c.getFoto()).into(holder.img);
            //Bitmap bitmap = BitmapFactory.decodeByteArray(c.getFoto(), 0, c.getFoto().length());
            //holder.img.setImageBitmap(bitmap);
        } else {
            holder.img.setImageResource(R.mipmap.ic_launcher);
        }


        // holder.img.setImageResource(c.foto);       // Click
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chama o listener para informar que clicou no Cachorro
                    onClickListener.onClickModeloBarbeiro(holder, position);
                }
            });
        }*/



    }

    @Override
    public int getItemCount() {
        return this.horarios != null ? this.horarios.size() : 0;
    }

    // Subclasse de RecyclerView.ViewHolder. Contém todas as views.
    public static class ModeloHorariosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        public ImageView img;
        public TextView tCabelo;
        public TextView tSobrancelha;
        public TextView tBarba;
        public TextView tHorario;
        public TextView tValor;
        public TextView tData;
        public Button ver;
        private View view;

        public ModeloHorariosViewHolder(View view) {
            super(view);
            this.view = view;
            tCabelo = (TextView) view.findViewById(R.id.tCabelo);
            tSobrancelha = (TextView) view.findViewById(R.id.tSobrancelha);
            tBarba = (TextView) view.findViewById(R.id.tBarba);
            tHorario = (TextView) view.findViewById(R.id.tHorario);
            tValor = (TextView) view.findViewById(R.id.tValor);
            tData = (TextView) view.findViewById(R.id.tData);
            // Cria as views para salvar no ViewHolder=

            //ver = (Button) view.findViewById(R.id.ver);
        }
    }
}
