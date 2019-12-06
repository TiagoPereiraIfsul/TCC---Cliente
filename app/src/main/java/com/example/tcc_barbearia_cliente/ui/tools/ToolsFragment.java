package com.example.tcc_barbearia_cliente.ui.tools;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_barbearia_cliente.ModeloBarbeiro;
import com.example.tcc_barbearia_cliente.ModeloHorario;
import com.example.tcc_barbearia_cliente.R;
import com.example.tcc_barbearia_cliente.ui.gallery.Adapter2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ToolsFragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private List<ModeloHorario> modeloHorarios;
    private Adapter3 adapter;
    ModeloHorario modeloHorario;
    private Button ver;

    //private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //toolsViewModel =
               // ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activity_recycler_view3, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        //ver = (Button) root.findViewById(R.id.ver);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getActivity().setTitle("Meus horários");
        //modeloHorarios = new ModeloHorario();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new Adapter3(getContext(), modeloHorarios, null));

        FirebaseDatabase.getInstance().getReference("HorariosMarcados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloHorario> lista = new ArrayList<>();

                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloHorario m = d.getValue(ModeloHorario.class);
//                    if(m.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    //if(m.getNome().contains("Manhã"))
                    lista.add(m);
                }

                //lista = (List<ModeloCliente>) lista.stream().filter(e->e.get().equals("")).collect(Collectors.toList());
                System.out.println(lista);
                //modeloCliente = lista;
                carregarRecyclerView(lista);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //final TextView textView = root.findViewById(R.id.text_tools);
        //toolsViewModel.getText().observe(this, new Observer<String>() {
            //@Override
            //public void onChanged(@Nullable String s) {
            //    textView.setText(s);
            //}
       // });
        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);

    }

    private Adapter3.ModeloHorarioOnClickListener onClickModeloHorario() {
        final Intent intent = new Intent(getContext(), ModeloHorario.class);
        return new Adapter3.ModeloHorarioOnClickListener() {
            @Override
            public void onClickModeloHorario(Adapter3.ModeloHorariosViewHolder holder, int idx) {
                ModeloHorario p = modeloHorarios.get(idx);

            }
        };
    }

    public void carregarRecyclerView(List<ModeloHorario> modeloHorarios) {
        recyclerView.setAdapter(adapter = new Adapter3(getContext(), modeloHorarios, onClickModeloHorario()));


    }
}