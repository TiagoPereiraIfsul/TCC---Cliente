package com.example.tcc_barbearia_cliente.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_barbearia_cliente.ModeloBarbeiro;
import com.example.tcc_barbearia_cliente.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private List<ModeloBarbeiro> modeloBarbeiros;
    private Adapter2 adapter;
    ModeloBarbeiro modeloBarbeiro;
    private Button ver;

    //private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //galleryViewModel =
               // ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activity_recycler_view2, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        //ver = (Button) root.findViewById(R.id.ver);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        getActivity().setTitle("Marca horário");
        modeloBarbeiro = new ModeloBarbeiro();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new Adapter2(getContext(), modeloBarbeiros, null));



        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloBarbeiro> lista = new ArrayList<>();

                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloBarbeiro m = d.getValue(ModeloBarbeiro.class);
//                    if(m.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    //if(m.getNome().contains("Manhã"))
                        lista.add(m);
                }

                //lista = (List<ModeloCliente>) lista.stream().filter(e->e.getHorClienteAdapterario().equals("")).collect(Collectors.toList());
                System.out.println(lista);
                //modeloCliente = lista;
                carregarRecyclerView(lista);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //final TextView textView = root.findViewById(R.id.text_gallery);
       // galleryViewModel.getText().observe(this, new Observer<String>() {
           // @Override
            //public void onChanged(@Nullable String s) {
             //   textView.setText(s);
            //}
        //});



        return root;
    }

    /*ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ListaPerfil listaPerfil = new ListaPerfil();
                ft.replace(R.id.conteiner, listaPerfil);
                ft.commit();

                //editarperfil();


            }
        });*/

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);

    }

    private Adapter2.ModeloBarbeiroOnClickListener onClickModeloBarbeiro() {
        final Intent intent = new Intent(getContext(), ModeloBarbeiro.class);
        return new Adapter2.ModeloBarbeiroOnClickListener() {
            @Override
            public void onClickModeloBarbeiro(Adapter2.ModeloBarbeirosViewHolder holder, int idx) {
                ModeloBarbeiro p = modeloBarbeiros.get(idx);

            }
        };
    }

    public void carregarRecyclerView(List<ModeloBarbeiro> modeloClientes) {
        recyclerView.setAdapter(adapter = new Adapter2(getContext(), modeloClientes, onClickModeloBarbeiro()));


    }
}