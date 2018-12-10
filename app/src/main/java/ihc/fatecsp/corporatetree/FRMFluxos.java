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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class FRMFluxos extends AppCompatActivity {
    public static BancoController crud;
    Cursor c;
    String idInvestidora;
    String nomeInvestidora;
    String idInvestida;
    String nomeInvestida;
    String tipoInvestida;
    TextView textInvestidora;
    TextView textInvestida;

    private RecyclerView recyclerView;
    ArrayList<Fluxo_Investimento> fluxo;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmfluxos);
        Intent intent = getIntent();
        if(intent.hasExtra("idInvestidora")) {
            Bundle extras = getIntent().getExtras();
            idInvestidora = extras.getString("idInvestidora");
            idInvestida = extras.getString("idInvestida");
            nomeInvestidora = extras.getString("nomeInvestidora");
            nomeInvestida = extras.getString("nomeInvestida");
            tipoInvestida = extras.getString("tipoInvestida");
        }
        else {
            finish();
        }
        if(intent.hasExtra("idInvestidoraExcluir")) {
            Bundle extras = getIntent().getExtras();
            String idInvestidoraExcluir = extras.getString("idInvestidoraExcluir");
            String idInvestidaExcluir = extras.getString("idInvestidaExcluir");
            String mesAno = extras.getString("mesAnoExclui");

            long resultado = crud.deletaRegistroFluxo(idInvestidoraExcluir,idInvestidaExcluir,new AjustaData(mesAno).Inverte_Data());
            if(resultado!=-1){
                Toast.makeText(FRMFluxos.this, "Fluxo de Investimento excluído com sucesso", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(FRMFluxos.this, "ERRO: Não foi possível excluir Fluxo de Investimento", Toast.LENGTH_LONG).show();
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerFluxos);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fluxo = new ArrayList<Fluxo_Investimento>();
        textInvestidora = findViewById(R.id.textInvestidora);
        textInvestida = findViewById(R.id.textInvestida);
        textInvestidora.setText(nomeInvestidora);
        textInvestida.setText(nomeInvestida);
    }
    public ArrayList<Fluxo_Investimento> retornarTodosFluxos(Integer Investidora,Integer Investida) {
        ArrayList<Fluxo_Investimento> fluxos = new ArrayList<>();
        crud = new BancoController(getBaseContext());
        c = crud.carregaDadosFluxo(Investidora,Investida);
        if(c.getCount()>0){
            do {
                String mesAno = c.getString(c.getColumnIndex(CriaBanco.MES_ANO_FLUXO));
                String qtdCotaAcaoOrd =   c.getString(c.getColumnIndex(CriaBanco.QTD_COTA_ACAO_ORD_F));
                String qtdAcaoPref =   c.getString(c.getColumnIndex(CriaBanco.QTD_ACAO_PREF_F));
                Cursor r = crud.carregaDadosCapitalPeriodo(Investida,Integer.parseInt(mesAno));
                BigInteger qtdCapitalCotaAcaoOrdInvestida = new BigInteger("0");
                BigInteger qtdCapitalAcaoPref = new BigInteger("0");
                BigInteger vlrCapitalCotaAcaoOrdInvestida = new BigInteger("0");
                BigInteger vlrCapitalAcaoPref = new BigInteger("0");
                BigInteger totalInvestimento = new BigInteger("0");
                Double participacao = 0.0;
                if(r.getCount()>0) {
                    String sQtdCapitalCotaAcaoOrdInvestida = r.getString(r.getColumnIndex(CriaBanco.QTD_COTA_ACAO_ORD));
                    String sQtdCapitalAcaoPref = r.getString(r.getColumnIndex(CriaBanco.QTD_ACAO_PREF));
                    String svlrCapitalCotaAcaoOrdInvestida = r.getString(r.getColumnIndex(CriaBanco.VLR_COTA_ACAO_ORD));
                    String svlrCapitalAcaoPref = r.getString(r.getColumnIndex(CriaBanco.VLR_ACAO_PREF));
                    qtdCapitalCotaAcaoOrdInvestida = new BigInteger(sQtdCapitalCotaAcaoOrdInvestida);
                    qtdCapitalAcaoPref = new BigInteger(sQtdCapitalAcaoPref);
                    vlrCapitalCotaAcaoOrdInvestida = new BigInteger(svlrCapitalCotaAcaoOrdInvestida);
                    vlrCapitalAcaoPref = new BigInteger(svlrCapitalAcaoPref);
                    BigDecimal qtdPref = new BigDecimal(qtdAcaoPref);
                    BigDecimal vlrCapitalPref = new BigDecimal(vlrCapitalAcaoPref);
                    BigDecimal qtdCotaOrd = new BigDecimal(qtdCotaAcaoOrd);
                    BigDecimal vlrCapitalCotaOrd = new BigDecimal(vlrCapitalCotaAcaoOrdInvestida);
                    BigDecimal totalPref = qtdPref.multiply(vlrCapitalPref);
                    BigDecimal totalCotaOrd = qtdCotaOrd.multiply(vlrCapitalCotaOrd);
                    BigDecimal capital_total = totalPref.add(totalCotaOrd);
                    totalInvestimento = new BigInteger(capital_total.toString());

                    participacao = (qtdPref.doubleValue()+qtdCotaOrd.doubleValue())*100.0/(qtdCapitalCotaAcaoOrdInvestida.doubleValue()+qtdCapitalAcaoPref.doubleValue());

                }
                fluxos.add(new Fluxo_Investimento(Investidora, Investida, Integer.parseInt(mesAno), new BigInteger(qtdCotaAcaoOrd), new BigInteger(
                qtdAcaoPref), participacao, totalInvestimento));

            } while (c.moveToNext());
        }
        return fluxos;
    }

    public void onResume(){
        super.onResume();
        this.fluxo.clear();
        this.fluxo = retornarTodosFluxos(Integer.parseInt(this.idInvestidora),Integer.parseInt(this.idInvestida));
        this.recyclerView.setAdapter(new FluxoAdapter(this.fluxo,this,tipoInvestida));
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        // coloque sua busca novamente aqui

    }

    public void startCadastroFluxo(View view) {
        Intent intent = new Intent(this, FRMCadastro_Investimento.class);
        intent.putExtra("idInvestidora",idInvestidora );
        intent.putExtra("idInvestida",idInvestida );
        intent.putExtra("fluxo","S" );

        startActivity(intent);
    }
}
