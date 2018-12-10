package ihc.fatecsp.corporatetree;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textQtdEmpresas, textQtdInvestimentos;
    BancoController crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        crud = new BancoController(getBaseContext());
        textQtdEmpresas = findViewById(R.id.textQtdEmpresas);
        textQtdInvestimentos = findViewById(R.id.textQtdInvestimentos);


    }

    public void startEmpresas(View view) {
        Intent intent = new Intent(this, FRMEmpresas.class);
        startActivity(intent);
    }

    public void startInvestimentos(View view) {
        Intent intent2 = new Intent(this, FRMInvestimentos.class);
        startActivity(intent2);
    }

    public void startRelatorios(View view) {
        Intent intent3 = new Intent(this, Relatorios.class);
        startActivity(intent3);
    }

    public void onResume(){
        super.onResume();
        String qtdEmpresas;
        String qtdInvestimentos;
        textQtdEmpresas.setText(String.valueOf(crud.carregaDadosEmpresa().getCount()));
        textQtdInvestimentos.setText(String.valueOf(crud.carregaDadosInvestimento().getCount()));

    }


    public void startArvores(View view) {
        Intent intent4 = new Intent(this, FRMFiltroArvore.class);
        startActivity(intent4);
    }
}
