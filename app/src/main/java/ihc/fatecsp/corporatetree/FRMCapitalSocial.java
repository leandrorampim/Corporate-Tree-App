package ihc.fatecsp.corporatetree;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Locale;

public class FRMCapitalSocial extends AppCompatActivity {
    public static BancoController crud;
    Cursor c;
    Integer idEmpresa;
    String nomeEmpresa;
    String cnpj;
    String tipo;
    TextView textNomeEmpresa;
    TextView textCnpj;
    TextView textTipo;


    private RecyclerView recyclerView;
    ArrayList<Capital_Social> capitalSocial;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmcapitalsocial);
        Intent intent = getIntent();
        if(intent.hasExtra("idEmpresa")) {
            Bundle extras = getIntent().getExtras();
            idEmpresa = Integer.parseInt(extras.getString("idEmpresa"));
            nomeEmpresa = extras.getString("nomeEmpresa");
            cnpj = extras.getString("cnpjEmpresa");
            tipo = extras.getString("tipoEmpresa");
        }
        if(idEmpresa==0){
            finish();
        }
        if(intent.hasExtra("idExcluirCapital")) {
            Bundle extras = getIntent().getExtras();
            String idClienteExcluir = extras.getString("idExcluirCapital");
            String mesAno = extras.getString("mesAnoExcluirCapital");

            long resultado = crud.deletaRegistroCapital(Integer.parseInt(idClienteExcluir),Integer.parseInt(new AjustaData(mesAno).Inverte_Data()));
            if(resultado!=-1){
                Toast.makeText(FRMCapitalSocial.this, "Movimento de Capital Social excluído com sucesso", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(FRMCapitalSocial.this, "ERRO: Não foi possível excluir Movimento de Capital Social", Toast.LENGTH_LONG).show();
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerCapitalSocial);
        capitalSocial = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        textNomeEmpresa = findViewById(R.id.text_CapitalEmpresa);
        textCnpj = findViewById(R.id.text_CapitalCNPJ);
        textTipo = findViewById(R.id.text_CapitalTipo);
        textNomeEmpresa.setText(nomeEmpresa);
        textCnpj.setText(cnpj);
        textTipo.setText(tipo);
    }

    public ArrayList<Capital_Social> retornarTodosCapitais(int id) {
        ArrayList<Capital_Social> capitais = new ArrayList<>();
        crud = new BancoController(getBaseContext());
        c = crud.carregaDadosCapital(id);
        if(c.getCount()>0){
            do {
                String mesAno = c.getString(c.getColumnIndex(CriaBanco.MES_ANO_CAPITAL));
                String qtdCotaAcaoOrd =   c.getString(c.getColumnIndex(CriaBanco.QTD_COTA_ACAO_ORD));
                String vlrCotaAcaoOrd =   c.getString(c.getColumnIndex(CriaBanco.VLR_COTA_ACAO_ORD));
                String qtdAcaoPref =   c.getString(c.getColumnIndex(CriaBanco.QTD_ACAO_PREF));
                String vlrAcaoPref =   c.getString(c.getColumnIndex(CriaBanco.VLR_ACAO_PREF));
                capitais.add(new Capital_Social(id,Integer.parseInt(mesAno),new BigInteger(qtdCotaAcaoOrd),
                        new BigInteger(vlrCotaAcaoOrd),new BigInteger(qtdAcaoPref),new BigInteger(vlrAcaoPref)));
            } while (c.moveToNext());
        }
        return capitais;
    }

    public void onResume(){
        super.onResume();
        capitalSocial.clear();
        capitalSocial = retornarTodosCapitais(idEmpresa);
        recyclerView.setAdapter(new CapitalAdapter(capitalSocial,this,tipo,cnpj,nomeEmpresa));
        recyclerView.setLayoutManager(linearLayoutManager);
        // coloque sua busca novamente aqui

    }

    public void startCadastroCapital(View view) {
        Intent intent = new Intent(this, FRMCadastro_CapitalSocial.class);
        intent.putExtra("idEmpresa",idEmpresa.toString() );
        intent.putExtra("nomeEmpresa",nomeEmpresa);
        intent.putExtra("cnpjEmpresa",cnpj);
        intent.putExtra("tipoEmpresa",tipo);
        intent.putExtra("cadastro_ou_alteracao","C");
        startActivity(intent);
    }

}
