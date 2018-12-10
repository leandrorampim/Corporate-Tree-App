package ihc.fatecsp.corporatetree;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FRMPesquisaEmpresa extends AppCompatActivity {
    public static BancoController crud;
    Cursor c;
    EditText editEmpresa;
    TextView textoInvestida_Investidora;
    private ListView lViewEmpresa;
    ArrayList<Empresa> empresa;
    private ArrayList<String> pesquisa = new ArrayList<String >();
    String tipoPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmpesquisa_empresa);
        crud = new BancoController(getBaseContext());
        editEmpresa = findViewById(R.id.editEmpresaBusca);
        lViewEmpresa = (ListView) findViewById(R.id.lViewEmpresa);
        textoInvestida_Investidora = (TextView) findViewById(R.id.text_Investidora_Investida);
        Intent intent = getIntent();
        if(intent.hasExtra("tipoPesquisa")) {
            Bundle extras = getIntent().getExtras();
            tipoPesquisa = extras.getString("tipoPesquisa");
        }
        else{
            finish();
        }

        if(tipoPesquisa.equals("1")){
            textoInvestida_Investidora.setText("Investidora");
        }
        else {
            if(tipoPesquisa.equals("2")){
                textoInvestida_Investidora.setText("Investida");
            }
            else {
                textoInvestida_Investidora.setText("Empresa");
            }
        }

        lViewEmpresa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("id", empresa.get(position).getIdEmpresa());
                setResult(Integer.parseInt(tipoPesquisa), i);
                finish();
            }
        });




        editEmpresa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pesquisar(editEmpresa);
                lViewEmpresa.setAdapter(new ArrayAdapter<String>(FRMPesquisaEmpresa.this,android.R.layout.simple_list_item_1,pesquisa));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void Pesquisar(EditText campo) {
        pesquisa.clear();
        this.empresa = retornarEmpresas(campo.getText().toString());
        for (int i = 0; i < this.empresa.size(); i++ ) {

            pesquisa.add(empresa.get(i).getNomeEmpresa());
        }
    }

    public ArrayList<Empresa> retornarEmpresas(String texto) {
        ArrayList<Empresa> empresas = new ArrayList<>();

        c = crud.carregaDadosEmpresa(texto);
        if(c.getCount()>0){
            do {
                String id = c.getString(c.getColumnIndex(CriaBanco.ID_EMPRESA));
                String nome = c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA));
                String cnpj = c.getString(c.getColumnIndex(CriaBanco.CNPJ_EMPRESA));
                String tipo = c.getString(c.getColumnIndex(CriaBanco.TIPO_EMPRESA));
                empresas.add(new Empresa(Integer.parseInt(id), nome, cnpj, tipo));
            } while (c.moveToNext());
        }
        return empresas;
    }

    public void onResume(){
        super.onResume();
        Pesquisar(editEmpresa);
        if (this.pesquisa != null && !this.pesquisa.isEmpty()){
            lViewEmpresa.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pesquisa));
        }
        else {

            Toast.makeText(FRMPesquisaEmpresa.this, "ERRO: Nenhuma empresa cadastrada", Toast.LENGTH_LONG).show();
            finish();
        }

    }

}
