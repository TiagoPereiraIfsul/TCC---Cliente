package com.example.tcc_barbearia_cliente.ui.slideshow;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tcc_barbearia_cliente.ModeloBarbeiro;
import com.example.tcc_barbearia_cliente.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityRecyclerView extends Fragment {
    private RecyclerView recyclerView;
    private List<ModeloBarbeiro> barbeiros;
    private HorarioAdapter adapter;
    private ModeloBarbeiro modeloBarbeiro;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorage;


    public ActivityRecyclerView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.recyclerView);
        modeloBarbeiro = new ModeloBarbeiro();
        //modeloEstilo = new ModeloEstilo();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new HorarioAdapter(getContext(), barbeiros, onClickModeloBarbeiro()));
        mStorage = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        View view = inflater.inflate(R.layout.fragment_activity_recycler_view, container, false);


        return view;
    }

    public Fragment fragment() {
        return this;
    }

    private HorarioAdapter.ModeloBarbeiroOnClickListener onClickModeloBarbeiro() {
        final Intent intent = new Intent(getContext(), ModeloBarbeiro.class);
        return new HorarioAdapter.ModeloBarbeiroOnClickListener() {
            @Override
            public void onClickModeloBarbeiro(HorarioAdapter.ModeloBarbeirosViewHolder holder, int idx) {
                ModeloBarbeiro p = barbeiros.get(idx);
                // Intent nova= new Intent(this, Editar_Excluir.class);
                intent.putExtra("Objeto", p); //putextraserializable
                startActivity(intent);
            }
        };
    }

    public void carregarRecyclerView(List<ModeloBarbeiro> barbeiros) {
        //cria um objeto da classe ListAdapter, um adaptador List -> ListView
        //associa o adaptador a ListView
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        recyclerView.setAdapter(adapter = new HorarioAdapter(getContext(), barbeiros, onClickModeloBarbeiro()));
    }

    @Override
    public void onStart() {
        super.onStart();
        // carregarRecyclerView();
    }

}
