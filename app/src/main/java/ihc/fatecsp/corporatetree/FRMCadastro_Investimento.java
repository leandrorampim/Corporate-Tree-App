package ihc.fatecsp.corporatetree;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class FRMCadastro_Investimento extends AppCompatActivity {
    String idInvestidora;
    Integer idInvestidoraNum;
    String idInvestida;
    Integer idInvestidaNum;
    String tipoInvestida;
    String data_alteracao = "";
    String qtdCotaAcaoOrd = "";
    String qtdAcaoPref = "";
    TextView textInvestidora;
    TextView textInvestida;
    TextView textoQtd;
    TextView textoQtdPref;
    TextView textCadastrar_Alterar;
    TextView investeCentralizado;
    TextView investeCadastro;
    EditText editQtdAcaoPref;
    EditText editQtdCotaAcaoOrdinaria;
    EditText editMesAno;
    TextView textBuscaInvestidora;
    TextView textBuscaInvestida;
    TextView textInvestimentoOuFluxo;
    SimpleDateFormat formataData;
    String fluxo_ou_investimento = "";
    Button btCadastrar_Alterar;
    BancoController crud;
    Cursor c;
    Long resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        crud = new BancoController(getBaseContext());

        if(intent.hasExtra("idInvestidora")) {
            Bundle extras = getIntent().getExtras();
            idInvestidora = extras.getString("idInvestidora");
            idInvestida = extras.getString("idInvestida");
            fluxo_ou_investimento = extras.getString("fluxo");
            idInvestidoraNum = Integer.parseInt(idInvestidora);
            idInvestidaNum = Integer.parseInt(idInvestida);
        }
        if(intent.hasExtra("mesAno")) {
            Bundle extras = getIntent().getExtras();
            data_alteracao = extras.getString("mesAno");
        }

        setContentView(R.layout.activity_frmcadastro__investimento);
        textInvestidora = findViewById(R.id.textInvestidora);
        textInvestida = findViewById(R.id.textInvestida);
        textInvestimentoOuFluxo = findViewById(R.id.textInvestimento_ou_Fluxo);
        textBuscaInvestidora = findViewById(R.id.editBuscaInvestidora);
        textBuscaInvestida = findViewById(R.id.editBuscaInvestida);
        investeCadastro = findViewById(R.id.investeCadastro);
        investeCentralizado = findViewById(R.id.investeCentralizado);
        if(fluxo_ou_investimento.equals("S")) {
            fluxo_ou_investimento = "Fluxo";
            textBuscaInvestidora.setVisibility(View.INVISIBLE);
            textBuscaInvestida.setVisibility(View.INVISIBLE);
            textInvestidora.setVisibility(View.VISIBLE);
            textInvestida.setVisibility(View.VISIBLE);
            investeCentralizado.setVisibility(View.VISIBLE);
            investeCadastro.setVisibility(View.INVISIBLE);
        }else{
            fluxo_ou_investimento = "Investimento";
            textBuscaInvestidora.setVisibility(View.VISIBLE);
            textBuscaInvestida.setVisibility(View.VISIBLE);
            textInvestidora.setVisibility(View.INVISIBLE);
            textInvestida.setVisibility(View.INVISIBLE);
            investeCentralizado.setVisibility(View.INVISIBLE);
            investeCadastro.setVisibility(View.VISIBLE);
        }
        textInvestimentoOuFluxo.setText(fluxo_ou_investimento);
        formataData = new SimpleDateFormat("MM-yyyy");
        String dataFormatada;
        editMesAno = findViewById(R.id.editMesAno_investimento);
        btCadastrar_Alterar = findViewById(R.id.bt_cad_investimento);
        textCadastrar_Alterar = findViewById(R.id.textCadastrarFluxo);
        editQtdAcaoPref = findViewById(R.id.editQtdAPref_Invest);
        editQtdAcaoPref.addTextChangedListener(new ThousandTextWatcher(editQtdAcaoPref));
        editQtdCotaAcaoOrdinaria = findViewById(R.id.editQtdCAOrd_Invest);
        editQtdCotaAcaoOrdinaria.addTextChangedListener(new ThousandTextWatcher(editQtdCotaAcaoOrdinaria));
        if(data_alteracao.equals("")) {
            dataFormatada = formataData.format(new Date());
            editMesAno.setText(dataFormatada);
        }else{
            btCadastrar_Alterar.setText("Salvar Alterações");
            editMesAno.setText(data_alteracao);
            editMesAno.setEnabled(false);
            textCadastrar_Alterar.setText("Alterar");
            c = crud.carregaDadosFluxo(idInvestidoraNum,idInvestidaNum,Integer.parseInt(new AjustaData(editMesAno.getText().toString()).Inverte_Data()));
            qtdCotaAcaoOrd = c.getString(c.getColumnIndex(CriaBanco.QTD_COTA_ACAO_ORD_F));
            editQtdCotaAcaoOrdinaria.setText(qtdCotaAcaoOrd);
            qtdAcaoPref = c.getString(c.getColumnIndex(CriaBanco.QTD_ACAO_PREF_F));
            editQtdAcaoPref.setText(qtdAcaoPref);
        }

        textoQtd = findViewById(R.id.textQtdCAOrd_Invest);
        textoQtdPref = findViewById(R.id.textQtdAcaoPref_Invest);
        if(idInvestidoraNum!=null) {
            atualizaDadosInvestidora();
        }
        if(idInvestidaNum!=null) {
            atualizaDadosInvestida();
        }
    }

    public boolean validaEmpresasDigitadas(){
        if(idInvestidoraNum!=null && idInvestidaNum!=null) {
            Long resultado = crud.retornaInvestimentoJaCadastrado(idInvestidora, idInvestida);
            if (resultado > 0) {
                Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Investimento já cadastrado", Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                if(idInvestidoraNum==idInvestidaNum) {
                    Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Empresa Investida não pode ser a mesma que a Empresa Investidora", Toast.LENGTH_LONG).show();
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        else{
            if (idInvestidoraNum == null) {
                Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Empresa Investidora não escolhida", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Empresa Investida não escolhida", Toast.LENGTH_LONG).show();
            }
            return false;
        }

    }


    public void cadastrarInvestimento(View view) {
        Date mesAno = null;
        BigInteger qtdCotaAcoesOrdinarias = new BigInteger("0");
        BigInteger qtdAcoesPreferenciais = new BigInteger("0");
        Boolean validou;
        if(fluxo_ou_investimento.equals("Fluxo")){
            validou = true;
        }
        else {
            validou = validaEmpresasDigitadas();
        }
        if(validou) {
            if (!data_alteracao.equals("") && editQtdCotaAcaoOrdinaria.getText().toString().equals(qtdCotaAcaoOrd) && editQtdAcaoPref.getText().toString().equals(qtdAcaoPref)) {
                Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Nenhuma informação foi alterada", Toast.LENGTH_LONG).show();
            } else {
                if (editMesAno.getText().toString().length() == 7) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    try {
                        mesAno = sdf.parse("01/" + editMesAno.getText().toString());
                    } catch (ParseException e) {
                        Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Mês/Ano inválido ou não preenchido", Toast.LENGTH_LONG).show();
                    }
                    if (mesAno != null) {
                        if (!editQtdCotaAcaoOrdinaria.getText().toString().equals("")) {
                            qtdCotaAcoesOrdinarias = new BigInteger(editQtdCotaAcaoOrdinaria.getText().toString().replaceAll("[R$,.]", ""));
                        }
                        if (!editQtdAcaoPref.getText().toString().equals("")) {
                            qtdAcoesPreferenciais = new BigInteger(editQtdAcaoPref.getText().toString().replaceAll("[R$,.]", ""));
                        }
                        if (qtdAcoesPreferenciais.equals(0) && qtdCotaAcoesOrdinarias.equals(0) && fluxo_ou_investimento.equals("Investimento")) {
                            if (tipoInvestida.equals("Limitada")) {
                                Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Nenhuma Cota preenchida", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Nenhuma Ação preenchida", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (fluxo_ou_investimento.equals("Fluxo")) {
                                resultado = 1L;
                            } else {
                                resultado = crud.insereDadoInvestimento(Integer.parseInt(idInvestidora), Integer.parseInt(idInvestida));
                            }
                            if (resultado != -1) {
                                Integer mesAnoSQL = Integer.parseInt(new AjustaData(editMesAno.getText().toString()).Inverte_Data());
                                resultado = crud.retornaFluxoJaCadastrado(idInvestidora, idInvestida, mesAnoSQL.toString());
                                int cadastrou_ou_alterou = 0;
                                if (data_alteracao.equals("")) {
                                    if (resultado == 0) {
                                        resultado = crud.insereDadoFluxo(Integer.parseInt(idInvestidora), Integer.parseInt(idInvestida), mesAnoSQL, qtdCotaAcoesOrdinarias, qtdAcoesPreferenciais);
                                        cadastrou_ou_alterou = 1;
                                    } else {
                                        Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Fluxo de Investimento já cadastrado", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    resultado = crud.alteraFluxo(Integer.parseInt(idInvestidora), Integer.parseInt(idInvestida), mesAnoSQL, qtdCotaAcoesOrdinarias, qtdAcoesPreferenciais);
                                    cadastrou_ou_alterou = 1;
                                }
                                if (cadastrou_ou_alterou == 1) {
                                    if (resultado != -1) {
                                        if (data_alteracao.equals("")) {
                                            if (fluxo_ou_investimento.equals("Fluxo")) {
                                                Toast.makeText(FRMCadastro_Investimento.this, "Fluxo de Investimento cadastrado com sucesso", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(FRMCadastro_Investimento.this, "Investimento cadastrado com sucesso", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(FRMCadastro_Investimento.this, "Fluxo de Investimento alterado com sucesso", Toast.LENGTH_LONG).show();
                                        }
                                        finish();
                                    } else {
                                        if (data_alteracao.equals("")) {
                                            Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Não foi possível cadastrar Fluxo de Investimento", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Não foi possível alterar Fluxo de Investimento", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            } else {
                                if (resultado != -1) {
                                    Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Não foi possível cadastrar Investimento", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(FRMCadastro_Investimento.this, "ERRO: Mes/Ano inválido ou não preenchido ", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    public void atualizaDadosInvestidora(){
        c = crud.carregaDadosEmpresa(this.idInvestidoraNum);
        textInvestidora.setText(c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA)));
        textBuscaInvestidora.setText(c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA)));
        textBuscaInvestidora.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
    }

    public void atualizaDadosInvestida(){
        c = crud.carregaDadosEmpresa(this.idInvestidaNum);
        textInvestida.setText(c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA)));
        textBuscaInvestida.setText(c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA)));
        tipoInvestida = c.getString(c.getColumnIndex(CriaBanco.TIPO_EMPRESA));
        textBuscaInvestida.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        this.editQtdAcaoPref.setText("0");
        if (tipoInvestida.equals("L")) {
            textoQtd.setText("Quantidade de Cotas");
            textoQtdPref.setVisibility(View.INVISIBLE);
            editQtdAcaoPref.setVisibility(View.INVISIBLE);
        } else {
            textoQtd.setText("Quantidade de Ações Ordínárias");
            textoQtdPref.setVisibility(View.VISIBLE);
            editQtdAcaoPref.setVisibility(View.VISIBLE);
        }
    }

    public void pesquisaInvestidora(View view) {
        Intent intent = new Intent(this, FRMPesquisaEmpresa.class);
        intent.putExtra("tipoPesquisa","1" );
        this.startActivityForResult(intent,1);
    }
    public void pesquisaInvestida(View view) {
        Intent intent = new Intent(this, FRMPesquisaEmpresa.class);
        intent.putExtra("tipoPesquisa","2" );
        this.startActivityForResult(intent,2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==1){
            if(data.hasExtra("id")) {
                this.idInvestidora = data.getStringExtra("id");
                this.idInvestidoraNum = Integer.parseInt(idInvestidora);
                atualizaDadosInvestidora();
            }
        }
        if(requestCode==2 && resultCode==2){
            if(data.hasExtra("id")) {
                this.idInvestida = data.getStringExtra("id");
                this.idInvestidaNum = Integer.parseInt(idInvestida);
                atualizaDadosInvestida();
            }
        }

    }
}