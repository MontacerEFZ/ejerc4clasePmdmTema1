package montacer.elfazazi.ejerc4clasepmdmtema1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import montacer.elfazazi.ejerc4clasepmdmtema1.databinding.ActivityEditInmuebleBinding;
import montacer.elfazazi.ejerc4clasepmdmtema1.modelos.Inmueble;

public class EditInmuebleActivity extends AppCompatActivity {

    private ActivityEditInmuebleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent(); //get en lugar de new para coger la informqcion enviada desde el main en el launcher
        Bundle bundle = intent.getExtras(); //get en lugar de new para sacar la info enviada desde el main
        Inmueble inmueble = (Inmueble) bundle.getSerializable("INMUEBLE");
        int posicion = bundle.getInt("POSICION");

        rellenarVista(inmueble);

        binding.btnEliminarEditInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoIntent = new Intent();
                Bundle nuevoBundle = new Bundle();
                nuevoBundle.putInt("POSICION", posicion);
                nuevoIntent.putExtras(nuevoBundle);
                setResult(RESULT_OK, nuevoIntent);
                finish();
            }
        });

        binding.btnEditarEditInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inmueble inmuebleNuevo = crearInmueble();

                if (inmuebleNuevo != null){
                    Intent intentNuevo = new Intent();
                    Bundle bundleNuevo = new Bundle();
                    bundleNuevo.putSerializable("INMUEBLE",inmuebleNuevo);
                    bundleNuevo.putSerializable("POSICION", posicion);
                    intentNuevo.putExtras(bundleNuevo);

                    setResult(RESULT_OK, intentNuevo);
                    finish();
                }else {
                    Toast.makeText(EditInmuebleActivity.this, "faltan datos", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void rellenarVista(Inmueble inmueble) {
        binding.txtDireccionEditInmueble.setText(inmueble.getDireccion());
        binding.txtNumeroEditInmueble.setText(String.valueOf(inmueble.getNumero()));
        binding.txtCiudadEditInmueble.setText(inmueble.getCiudad());
        binding.txtProvinciaEditInmueble.setText(inmueble.getProvincia());
        binding.txtCpEditInmueble.setText(String.valueOf(inmueble.getCp()));
        binding.rbValoracionEditInmueble.setRating(inmueble.getValoracion());
    }
    private Inmueble crearInmueble() {
        if (binding.txtDireccionEditInmueble.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtNumeroEditInmueble.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtCiudadEditInmueble.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtProvinciaEditInmueble.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtCpEditInmueble.getText().toString().isEmpty()){
            return null;
        }

        int numero = Integer.parseInt(binding.txtNumeroEditInmueble.getText().toString());
        int cp = Integer.parseInt(binding.txtCpEditInmueble.getText().toString());

        Inmueble inmueble = new Inmueble(binding.txtDireccionEditInmueble.getText().toString(),
                numero, binding.txtCiudadEditInmueble.getText().toString(),
                binding.txtProvinciaEditInmueble.getText().toString(),
                cp, binding.rbValoracionEditInmueble.getRating());

        return inmueble;
    }

}