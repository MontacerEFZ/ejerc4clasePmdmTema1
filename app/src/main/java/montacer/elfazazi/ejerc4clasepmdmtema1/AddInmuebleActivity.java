package montacer.elfazazi.ejerc4clasepmdmtema1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import montacer.elfazazi.ejerc4clasepmdmtema1.databinding.ActivityAddInmuebleBinding;
import montacer.elfazazi.ejerc4clasepmdmtema1.databinding.ActivityMainBinding;
import montacer.elfazazi.ejerc4clasepmdmtema1.modelos.Inmueble;

public class AddInmuebleActivity extends AppCompatActivity {

    private ActivityAddInmuebleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.btnCancelarAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        
        binding.btnInsertarAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inmueble inmueble = crearInmueble();


                if (inmueble == null){
                    Toast.makeText(AddInmuebleActivity.this, "faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("INMUEBLE", inmueble);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private Inmueble crearInmueble() {
        if (binding.txtDireccionAddInmueble.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtNumeroAddInmueble.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtCiudadAddInmueble.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtProvinciaAddInmueble.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtCpAddInmueble.getText().toString().isEmpty()){
            return null;
        }

        int numero = Integer.parseInt(binding.txtNumeroAddInmueble.getText().toString());
        int cp = Integer.parseInt(binding.txtCpAddInmueble.getText().toString());

        Inmueble inmueble = new Inmueble(binding.txtDireccionAddInmueble.getText().toString(),
                numero, binding.txtCiudadAddInmueble.getText().toString(),
                binding.txtProvinciaAddInmueble.getText().toString(),
                cp, binding.rbValoracionAddInmueble.getRating());

        return inmueble;
    }
}