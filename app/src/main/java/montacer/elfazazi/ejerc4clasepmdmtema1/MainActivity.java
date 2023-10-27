package montacer.elfazazi.ejerc4clasepmdmtema1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import montacer.elfazazi.ejerc4clasepmdmtema1.databinding.ActivityMainBinding;
import montacer.elfazazi.ejerc4clasepmdmtema1.modelos.Inmueble;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcherAddInmueble;
    private ActivityResultLauncher<Intent> launcherEditInmueble;

    private ArrayList<Inmueble> listaInmuebles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherAddInmueble.launch(new Intent(MainActivity.this, AddInmuebleActivity.class));
            }
        });

        listaInmuebles = new ArrayList<>();
        inicializarLaunchers();
    }

    private void inicializarLaunchers() {
        launcherAddInmueble = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //resultado de volver de la actividad add inmueble
                if (result.getResultCode() == RESULT_OK){
                    if (result.getData() != null && result.getData().getExtras() != null){
                        Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable("INMUEBLE");
                        listaInmuebles.add(inmueble);
                        //Toast.makeText(MainActivity.this, inmueble.toString(), Toast.LENGTH_SHORT).show();
                        mostrarInmueble();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "accion cancelada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        launcherEditInmueble = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData() != null && result.getData().getExtras() != null) {
                                //pulsaron editar
                                Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable("INMUEBLE");
                                int posicion = result.getData().getExtras().getInt("POSICION");
                                
                                if (inmueble == null){
                                    listaInmuebles.remove(posicion);
                                }else {
                                    listaInmuebles.set(posicion, inmueble);
                                    
                                }
                                    mostrarInmueble();
                            }else{
                                //pulsaron borrar
                                Toast.makeText(MainActivity.this, "accion canceladas", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "has vuelto atras", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void mostrarInmueble() {

        binding.contentMain.contenedor.removeAllViews();

        for (int i = 0; i < listaInmuebles.size(); i++) {
            Inmueble inmueble = listaInmuebles.get(i);

            View inmuebleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_model_view, null);

            TextView lbDireccion = inmuebleView.findViewById(R.id.lbDireccionInmuebleModelView);
            TextView lbNumero = inmuebleView.findViewById(R.id.lbNumeroInmuebleModelView);
            TextView lbCiudad = inmuebleView.findViewById(R.id.lbCiudadInmuebleModelView);
            RatingBar rbValoracion = inmuebleView.findViewById(R.id.rbValoracionInmuebleModelView);

            lbDireccion.setText(inmueble.getDireccion());
            lbNumero.setText(String.valueOf(inmueble.getNumero()));
            lbCiudad.setText(inmueble.getCiudad());
            rbValoracion.setRating(inmueble.getValoracion());

            int posicion = i;
            inmuebleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //enviar inmueble
                    Intent intent = new Intent(MainActivity.this, EditInmuebleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("INMUEBLE", inmueble);
                    bundle.putInt("POSICION", posicion);
                    intent.putExtras(bundle);

                    launcherEditInmueble.launch(intent);
                }
            });

            binding.contentMain.contenedor.addView(inmuebleView);
        }
    }
}