package com.example.tcc_barbearia_cliente.ui.tools;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tcc_barbearia_cliente.ModeloBarbeiro;
import com.example.tcc_barbearia_cliente.ModeloHorario;
import com.example.tcc_barbearia_cliente.R;
import com.example.tcc_barbearia_cliente.ui.gallery.Adapter2;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityRecyclerView3 extends Fragment {
    private RecyclerView recyclerView;
    private List<ModeloHorario> horarios;
    private Adapter3 adapter;
    private ModeloHorario modeloHorario;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorage;
    private Button ver;


    public ActivityRecyclerView3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        getActivity().setTitle("Meus horários");
        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.recyclerView);
        //ver = (Button) recyclerView.findViewById(R.id.ver);
        modeloHorario = new ModeloHorario();
        //modeloEstilo = new ModeloEstilo();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new Adapter3(getContext(), horarios, onClickModeloHorario()));
        mStorage = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_activity_recycler_view3, container, false);
        return view;
    }

    public Fragment fragment() {
        return this;
    }

    private Adapter3.ModeloHorarioOnClickListener onClickModeloHorario() {
        final Intent intent = new Intent(getContext(), ModeloHorario.class);
        return new Adapter3.ModeloHorarioOnClickListener() {
            @Override
            public void onClickModeloHorario(Adapter3.ModeloHorariosViewHolder holder, int idx) {
                ModeloHorario p = horarios.get(idx);
                // Intent nova= new Intent(this, Editar_Excluir.class);
                intent.putExtra("Objeto", p); //putextraserializable
                startActivity(intent);
            }
        };
    }



    public void carregarRecyclerView(List<ModeloHorario> horarios) {
        //cria um objeto da classe ListAdapter, um adaptador List -> ListView
        //associa o adaptador a ListView
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        recyclerView.setAdapter(adapter = new Adapter3(getContext(), horarios, onClickModeloHorario()));
    }

    @Override
    public void onStart() {
        super.onStart();
        // carregarRecyclerView();
    }

}
