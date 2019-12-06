package com.example.tcc_barbearia_cliente.ui.gallery;

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
import com.example.tcc_barbearia_cliente.ui.slideshow.HorarioAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ModeloBarbeirosViewHolder>{
    private final List<ModeloBarbeiro> barbeiros;
    private final Context context;
    private final Adapter2.ModeloBarbeiroOnClickListener onClickListener;

    public interface ModeloBarbeiroOnClickListener {
        void onClickModeloBarbeiro(Adapter2.ModeloBarbeirosViewHolder holder, int idx);
    }

    public Adapter2(Context context, List<ModeloBarbeiro> barbeiros, Adapter2.ModeloBarbeiroOnClickListener onClickListener) {
        this.context = context;
        this.barbeiros = barbeiros;
        this.onClickListener = onClickListener;
    }

    @Override
    public Adapter2.ModeloBarbeirosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_do_recycler_horario, viewGroup, false);
        // Cria a classe do ViewHolder
        Adapter2.ModeloBarbeirosViewHolder holder = new Adapter2.ModeloBarbeirosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Adapter2.ModeloBarbeirosViewHolder holder, final int position) {
        // Este método recebe o índice do elemento, e atualiza as views que estão dentro do ViewHolder
        ModeloBarbeiro c = barbeiros.get(position);
        System.out.println(c.toString());
        // Atualizada os valores nas views
        holder.tNome.setText(c.getNome());
        if(c.getFotoperfil() != null)
        {
            Picasso.with(this.context).load(c.getFotoperfil()).into(holder.img);
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
        public ImageView img;
        public Button ver;
        private View view;

        public ModeloBarbeirosViewHolder(View view) {
            super(view);
            this.view = view;
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.nome);
            img = (ImageView) view.findViewById(R.id.imagemm);
            //ver = (Button) view.findViewById(R.id.ver);
        }
    }
}
