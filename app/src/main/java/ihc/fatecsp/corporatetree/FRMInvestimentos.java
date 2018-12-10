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

public class FRMInvestimentos extends AppCompatActivity {

    public static BancoController crud;
    Cursor c;

    private RecyclerView recyclerView;
    ArrayList<Investimento> investimento;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frminvestimentos);
        Intent intent = getIntent();
        if(intent.hasExtra("idExcluirInvestidora")) {
            Bundle extras = getIntent().getExtras();
            String idExcluirInvestidora = extras.getString("idExcluirInvestidora");
            String idExcluirInvestida = extras.getString("idExcluirInvestida");
            Long resultado = crud.deletaRegistroInvestimento(idExcluirInvestidora,idExcluirInvestida);
            if(resultado!=-1){
                crud.deletaFluxosInvestimento(idExcluirInvestidora,idExcluirInvestida);
                Toast.makeText(FRMInvestimentos.this, "Investimento excluído com sucesso", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(FRMInvestimentos.this, "ERRO: Não foi possível excluir Investimento", Toast.LENGTH_LONG).show();
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerInvestimentos);
        investimento = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


    }
    public ArrayList<Investimento> retornarTodosInvestimentos() {
        ArrayList<Investimento> investimentos = new ArrayList<>();
        crud = new BancoController(getBaseContext());
        c = crud.carregaDadosInvestimento();
        if(c.getCount()>0){
            do {
                String idInvestidora = c.getString(c.getColumnIndex(CriaBanco.ID_INVESTIDORA));
                String idInvestida = c.getString(c.getColumnIndex(CriaBanco.ID_INVESTIDA));
                investimentos.add(new Investimento(Integer.parseInt(idInvestidora),Integer.parseInt(idInvestida)));
            } while (c.moveToNext());

        }
        if (!investimentos.isEmpty()){
            for (int i =0 ;i<investimentos.size();i++) {
                c = crud.carregaDadosEmpresa(investimentos.get(i).getIdInvestidora());
                investimentos.get(i).setNomeInvestidora(c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA)));
                c = crud.carregaDadosEmpresa(investimentos.get(i).getIdInvestida());
                investimentos.get(i).setNomeInvestida(c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA)));
                investimentos.get(i).setTipoInvestida(c.getString(c.getColumnIndex(CriaBanco.TIPO_EMPRESA)));
            }
        }
        return investimentos;
    }


    public void onResume(){
        super.onResume();
        investimento.clear();
        investimento = retornarTodosInvestimentos();
        recyclerView.setAdapter(new InvestimentosAdapter(investimento,this));
        recyclerView.setLayoutManager(linearLayoutManager);
        // coloque sua busca novamente aqui

    }

    public void startCadastroInvestimento(View view) {
        Intent intent = new Intent(this, FRMCadastro_Investimento.class);
        startActivity(intent);
    }
}
