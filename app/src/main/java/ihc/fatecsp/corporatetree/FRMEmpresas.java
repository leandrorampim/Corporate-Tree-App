package ihc.fatecsp.corporatetree;


import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;


public class FRMEmpresas extends AppCompatActivity {
    public static BancoController crud;
    Cursor c;

    private RecyclerView recyclerView;
    ArrayList<Empresa> empresa;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmempresas);
        Intent intent = getIntent();
        crud = new BancoController(getBaseContext());
        if(intent.hasExtra("idExcluir")) {
            Bundle extras = getIntent().getExtras();
            String idClienteExcluir = extras.getString("idExcluir");
            long resultado;
            int idExcluir = Integer.parseInt(idClienteExcluir);
            resultado = crud.deletaRegistroEmpresa(idExcluir);
            if(resultado!=-1){
                crud.deletaCapitalEmpresa(idExcluir);
                crud.deletaInvestimentoEmpresaInvestidora(idExcluir);
                crud.deletaInvestimentoEmpresaInvestida(idExcluir);
                crud.deletaFluxoEmpresaInvestidora(idExcluir);
                crud.deletaFluxoEmpresaInvestida(idExcluir);
                Toast.makeText(FRMEmpresas.this, "Empresa excluída com sucesso", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(FRMEmpresas.this, "ERRO: Não foi possível excluir Empresa", Toast.LENGTH_LONG).show();
            }

        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerEmpresas);
        empresa = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


    }
    public ArrayList<Empresa> retornarTodasEmpresas() {
        ArrayList<Empresa> empresas = new ArrayList<>();

        c = crud.carregaDadosEmpresa();
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


    public void startCadastroEmpresa(View view) {
        Intent intent = new Intent(this, FRMCadastro_Empresa.class);
        startActivity(intent);
    }

    public void onResume(){
        super.onResume();
        empresa.clear();
        empresa = retornarTodasEmpresas();
        recyclerView.setAdapter(new EmpresaAdapter(empresa,this));
        recyclerView.setLayoutManager(linearLayoutManager);
        // coloque sua busca novamente aqui

    }

}
