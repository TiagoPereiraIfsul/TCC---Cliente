package com.example.tcc_barbearia_cliente.ui.slideshow;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tcc_barbearia_cliente.ModeloBarbeiro;
import com.example.tcc_barbearia_cliente.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSelecionar extends Fragment {
    private RecyclerView recyclerView;
    private List<ModeloBarbeiro> modeloBarbeiros;
    private HorarioAdapter adapter;
    ModeloBarbeiro modeloBarbeiro;
    private Button excluir;

    @Override
    @SuppressWarnings("newApi")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_selecionar, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modeloBarbeiro = new ModeloBarbeiro();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new HorarioAdapter(getContext(), modeloBarbeiros, onClickModeloBarbeiro()));

        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloBarbeiro> lista = new ArrayList<>();

                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloBarbeiro m = d.getValue(ModeloBarbeiro.class);
                    if(m.getNome().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        lista.add(m);
                }

                //lista = (List<ModeloBarbeiro>) lista.stream().filter(e->e.getNome().equals("")).collect(Collectors.toList());
                System.out.println(lista);
                modeloBarbeiros = lista;
                carregarRecyclerView(lista);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    protected HorarioAdapter.ModeloBarbeiroOnClickListener onClickModeloBarbeiro() {
        final Intent intent = new Intent(getContext(), ModeloBarbeiro.class);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        return new HorarioAdapter.ModeloBarbeiroOnClickListener() {
            @Override
            public void onClickModeloBarbeiro(HorarioAdapter.ModeloBarbeirosViewHolder holder, int idx) {
//                ModeloEstilo p = modeloEstilos.get(idx);
//                intent.putExtra("Objeto", p); //putextraserializable


                /*System.out.println("entrou pora");
                ModeloEstilo p = modeloEstilos.get(idx);

                FragmentTransaction ft = fm.beginTransaction();
                FragmentAddEstilo fragmentAddEstilo = new FragmentAddEstilo();

                Bundle bundle = new Bundle();
                bundle.putSerializable("objeto", p);
                fragmentAddEstilo.setArguments(bundle);

                ft.replace(R.id.conteiner, fragmentAddEstilo);
                ft.commit();*/

            }
        };

    }

    public void carregarRecyclerView(List<ModeloBarbeiro> modeloBarbeiros) {
        recyclerView.setAdapter(adapter = new HorarioAdapter(getContext(), modeloBarbeiros, onClickModeloBarbeiro()));

    }

}
