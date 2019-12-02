package com.example.tcc_barbearia_cliente.ui.slideshow;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.tcc_barbearia_cliente.ModeloBarbeiro;
import com.example.tcc_barbearia_cliente.ModeloCadastro;
import com.example.tcc_barbearia_cliente.ModeloEstilo;
import com.example.tcc_barbearia_cliente.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;

import javax.xml.transform.sax.SAXSource;

import static android.app.Activity.RESULT_OK;

public class SlideshowFragment extends Fragment {
    private Spinner combobox, combobox2, combobox3, combobox4, combobox5;
    private CalendarView calendario;
    private static final int PICK_IMAGE_REQUEST = 1;
    private List<ModeloEstilo> modeloEstilos;
    private List<ModeloCadastro> modeloCadastros;
    private ImageView foto;
    private int IMAGEM_ID = -1;
    private FirebaseAuth auth;
    private CardView salvar;
    private Button botao;
    private Uri mImageUri;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private Button data;
    Date birthDate;
    DatePickerDialog datePickerDialog;
    private String[] add = new String[]
            {};

    ModeloEstilo modeloEstilo;
    ModeloBarbeiro modeloBarbeiro;
    ModeloCadastro modeloCadastro;

    static final int DATE_DIALOG_ID = 0;


    public SlideshowFragment() {
        // Required empty public constructor
    }


    //    private SlideshowViewModel slideshowViewModel;
    @SuppressWarnings("newApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //slideshowViewModel =
        //      ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        combobox = (Spinner) root.findViewById(R.id.spinner);
        combobox2 = (Spinner) root.findViewById(R.id.spinner2);
        combobox3 = (Spinner) root.findViewById(R.id.spinner3);
        combobox4 = (Spinner) root.findViewById(R.id.spinner4);
        combobox5 = (Spinner) root.findViewById(R.id.spinner5);
        salvar = (CardView) root.findViewById(R.id.cardView);
        data = (Button) root.findViewById(R.id.data);

       // calendario = (CalendarView) root.findViewById(R.id.calendarView2);
        //foto = (ImageView) root.findViewById(R.id.imageView3);


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        /*ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, "");
        combobox.setAdapter(adaptador);*/

        getActivity().setTitle("Marcar horário");

        modeloEstilo = new ModeloEstilo();
        modeloBarbeiro = new ModeloBarbeiro();
        modeloCadastro = new ModeloCadastro();



        //combobox = (CalendarView) root.findViewById(R.id.spinner);

        birthDate = new Date();

        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(root.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = day + "/" + (month + 1) + "/" + year;

                        data.setText(date);

                        try {
                            birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            getHorariosDisponiveis(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });





        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, add);
        combobox.setAdapter(adaptador);

        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloBarbeiro> lista = new ArrayList<ModeloBarbeiro>();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloBarbeiro m = d.getValue(ModeloBarbeiro.class);
                    lista.add(m);
                }

                ArrayAdapter<ModeloBarbeiro> adaptador =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lista);
                combobox2.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Estilos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloEstilo> lista2 = new ArrayList<>();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloEstilo m = d.getValue(ModeloEstilo.class);
                    lista2.add(m);
                }

                lista2 = (List<ModeloEstilo>) lista2.stream().filter(e -> e.getCategoria().equals("Cabelo - R$ 25,00")).collect(Collectors.toList());
                lista2.add(new ModeloEstilo("Nenhum"));
                System.out.println(lista2);
                modeloEstilos = lista2;

                ArrayAdapter<ModeloEstilo> adaptador =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lista2);
                combobox3.setAdapter(adaptador);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Estilos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloEstilo> lista3 = new ArrayList<>();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloEstilo m = d.getValue(ModeloEstilo.class);
                    lista3.add(m);
                }

                lista3 = (List<ModeloEstilo>) lista3.stream().filter(e -> e.getCategoria().equals("Barba - R$ 10,00")).collect(Collectors.toList());
                lista3.add(new ModeloEstilo("Nenhum"));
                System.out.println(lista3);
                modeloEstilos = lista3;

                ArrayAdapter<ModeloEstilo> adaptador =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lista3);
                combobox4.setAdapter(adaptador);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Estilos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloEstilo> lista4 = new ArrayList<>();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloEstilo m = d.getValue(ModeloEstilo.class);
                    lista4.add(m);
                }

                lista4 = (List<ModeloEstilo>) lista4.stream().filter(e -> e.getCategoria().equals("Sobrancelha - R$ 5,00")).collect(Collectors.toList());
                lista4.add(new ModeloEstilo("Nenhum"));
                System.out.println(lista4);
                modeloEstilos = lista4;

                ArrayAdapter<ModeloEstilo> adaptador =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lista4);
                combobox5.setAdapter(adaptador);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //##############
        FirebaseDatabase.getInstance().getReference("clientes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ModeloCadastro> listaclientes = new ArrayList<>();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloCadastro m = d.getValue(ModeloCadastro.class);
                    listaclientes.add(m);
                }

              listaclientes = (List<ModeloCadastro>) listaclientes.stream().filter(e -> e.get_id().equals(FirebaseAuth.getInstance().getUid())).collect(Collectors.toList());
                listaclientes .add(new ModeloCadastro());
               System.out.println(listaclientes);
                modeloCadastros = listaclientes;
                // pega 1} da lista
                modeloCadastro=modeloCadastros.get(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //###############

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificao

                editarperfil();

                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setTitle("Horário Marcado com Sucesso");
                alerta.setMessage("Você selecionou: \n" + "Barbeiro: "+modeloEstilo.getBarbeiro() + "\n" + "Horário: "+modeloEstilo.getHorario() +
                                                    "\n" + "Cabelo: "+modeloEstilo.getCabelo() + "\n" + "Barba: "+modeloEstilo.getBarba() + "\n" + "Sobrancelha: "+modeloEstilo.getSobrancelha() +
                                                    "\n" + "Data: "+modeloEstilo.getData() +
                "\n" + "Valor total do serviço: R$ " + modeloEstilo.getValorTotal());
                //alerta.setMessage("Valor total do serviço: ");
                alerta.show();
                //Toast.makeText(getContext(), "Horário marcado com sucesso!", Toast.LENGTH_SHORT).show();


            }

        });




//        foto1.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View v) {
              //  IMAGEM_ID = 1;
               // openFileChooser();
           // }
        //});




        return root;
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            switch (IMAGEM_ID) {
                case 1:
                    Picasso.with(getContext()).load(mImageUri).into(foto);
                    break;


                default://se der merda

            }
        }

    }

    protected void getHorariosDisponiveis(String data) {

        ////////////////////////////////
        ////    Mostra o loading    ////
        ////////////////////////////////

        ////////////////////////////////

        // Inicializa a lista com todos os horários possíveis
        final List<String> _horarios = new ArrayList<>(
                Arrays.asList("Manhã - 9h:00", "Manhã - 9h:30min", "Manhã - 10h:00", "Manhã - 10h:30min",
                        "Manhã - 11h:00", "Manhã - 11h:30min", "Manhã - 12h:00", "Manhã - 12h:30min",

                        "Tarde - 14h:00", "Tarde - 14h:30min", "Tarde - 15h:00", "Tarde - 15h:30min", "Tarde - 16h:00",
                        "Tarde - 16h:30min", "Tarde - 17h:00", "Tarde - 17h:30min",

                        "Noite - 18h:00", "Noite - 18h:30min", "Noite - 19h:00", "Noite - 19h:30min", "Noite - 20h:00",
                        "Noite - 20h:30min", "Noite - 21h:00"
                )
        );
        System.out.println("AAAALAAAHUKBA");
        System.out.println(data);
        // Faz a consulta no firebase pegando todos horários /*equalsTo*/
        FirebaseDatabase.getInstance().getReference("HorariosMarcados").orderByChild("data").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressWarnings("newApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Lista dos horários marcados na data
                List<String> _marcados = new ArrayList<>();

                // Add os horários na lista
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    ModeloEstilo m = d.getValue(ModeloEstilo.class);
                    _marcados.add(m.getHorario());
                }

                System.out.println(_marcados);

                // Filtra os horários que estão disponíveis
                /*final*/
                /*final List<ModeloEstilo>*/ add = _horarios.stream()
                        .filter(e -> !_marcados.contains(e))
                        .toArray(String[]::new);

                // Atualiza o spinner com os horários
                //add = _horarios.stream().toArray(String[]::new);
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, add);
                combobox.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();

                ////////////////////////////////
                ////    Oculta o loading    ////
                ////////////////////////////////

                ////////////////////////////////
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Erro
            }
        });
    }






    private void editarperfil() {

        //estilo.setNome(nome.getText().toString());
        //modeloEstilo.setNome(nome.getText().toString());
        modeloEstilo.setFoto(modeloCadastro.getFotoperfil());
        modeloEstilo.setNome(modeloCadastro.getNome());
        //modeloEstilo.setNome(((ModeloBarbeiro) modeloBarbeiro.getNome().toString()));
        //modeloEstilo.setNome(((ModeloBarbeiro) modeloBarbeiro.getNome().toString()));
        modeloEstilo.setBarbeiro(((ModeloBarbeiro) combobox2.getSelectedItem()).toString());
        modeloEstilo.setHorario((String) combobox.getSelectedItem());
        modeloEstilo.setCabelo(((ModeloEstilo) combobox3.getSelectedItem()).getNome());
        modeloEstilo.setBarba(((ModeloEstilo) combobox4.getSelectedItem()).getNome());
        modeloEstilo.setSobrancelha(((ModeloEstilo) combobox5.getSelectedItem()).getNome());
        modeloEstilo.setId(FirebaseDatabase.getInstance().getReference().push().getKey());
        modeloEstilo.setData(data.getText().toString());
        // modeloEstilo.setData(new Date(calendario.getDate()));
        //modeloEstilo.setFoto(url);


        FirebaseDatabase.getInstance().getReference("HorariosMarcados")
                .child(modeloEstilo.getId()).setValue(modeloEstilo);


    }


}




