package com.example.tcc_barbearia_cliente.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_barbearia_cliente.ModeloBarbeiro;
import com.example.tcc_barbearia_cliente.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.ModeloBarbeirosViewHolder>{
    private final List<ModeloBarbeiro> barbeiros;
    private final Context context;
    private final ModeloBarbeiroOnClickListener onClickListener;

    public interface ModeloBarbeiroOnClickListener {
        void onClickModeloBarbeiro(ModeloBarbeirosViewHolder holder, int idx);
    }

    public HorarioAdapter(Context context, List<ModeloBarbeiro> barbeiros, ModeloBarbeiroOnClickListener onClickListener) {
        this.context = context;
        this.barbeiros = barbeiros;
        this.onClickListener = onClickListener;
    }

    @Override
    public ModeloBarbeirosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_do_recycler_horario, viewGroup, false);
        // Cria a classe do ViewHolder
        ModeloBarbeirosViewHolder holder = new ModeloBarbeirosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ModeloBarbeirosViewHolder holder, final int position) {
        // Este método recebe o índice do elemento, e atualiza as views que estão dentro do ViewHolder
        ModeloBarbeiro c = barbeiros.get(position);
        // Atualizada os valores nas views
        holder.tNome.setText(c.getNome());
        if(c.getFoto() != null)
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
        }

    }

    @Override
    public int getItemCount() {
        return this.barbeiros != null ? this.barbeiros.size() : 0;
    }

    // Subclasse de RecyclerView.ViewHolder. Contém todas as views.
    public static class ModeloBarbeirosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        public CircleImageView img;
        private View view;

        public ModeloBarbeirosViewHolder(View view) {
            super(view);
            this.view = view;
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.tNome);
            img = (CircleImageView) view.findViewById(R.id.img);
        }
    }
}
