package ihc.fatecsp.corporatetree;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class FRMCadastro_CapitalSocial extends AppCompatActivity {
    TextView textoQtd;
    TextView textoVlr;
    TextView textoQtdPref;
    TextView textoVlrPref;
    EditText editQtdAcaoPref;
    EditText editQtdCotaAcaoOrdinaria;
    EditText editVlrAcaoPref;
    EditText editVlrCotaAcaoOrdinaria;
    EditText editMesAno;
    SimpleDateFormat formataData;
    Locale mLocale;
    Integer idEmpresa;
    TextView textNomeEmpresa;
    TextView textCnpj;
    TextView textTipo;
    String nomeEmpresa;
    String cnpj;
    String tipo;
    String cadastro_ou_alteracao;
    Button cad_ou_alt;
    TextView label_tela;
    String mesAno;
    String qtdCotaAcaoOrd;
    String vlrCotaAcaoOrd;
    String qtdAcaoPref;
    String vlrAcaoPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmcadastro__capital_social);
        mLocale = new Locale("pt", "BR");

        Intent intent = getIntent();
        if(intent.hasExtra("idEmpresa")) {
            Bundle extras = getIntent().getExtras();
            idEmpresa = Integer.parseInt(extras.getString("idEmpresa"));
            nomeEmpresa = extras.getString("nomeEmpresa");
            cnpj = extras.getString("cnpjEmpresa");
            tipo = extras.getString("tipoEmpresa");
            cadastro_ou_alteracao = extras.getString("cadastro_ou_alteracao");
            if(cadastro_ou_alteracao.equals("A"))
            {
                mesAno = extras.getString("mesAno");
                qtdCotaAcaoOrd = extras.getString("qtdCotaAcaoOrd");
                vlrCotaAcaoOrd = extras.getString("vlrCotaAcaoOrd");
                qtdAcaoPref = extras.getString("qtdAcaoPref");
                vlrAcaoPref = extras.getString("vlrAcaoPref");
            }
        }
        if(idEmpresa==0){
            finish();
        }
        editVlrAcaoPref = findViewById(R.id.edit_CadCap_VlrAcaoPreferencial);
        editVlrAcaoPref.addTextChangedListener(new MoneyTextWatcher(editVlrAcaoPref, mLocale));
        editVlrCotaAcaoOrdinaria = findViewById(R.id.edit_CadCap_VlrCotaAcaoOrdinaria);
        editVlrCotaAcaoOrdinaria.addTextChangedListener(new MoneyTextWatcher(editVlrCotaAcaoOrdinaria, mLocale));
        formataData = new SimpleDateFormat("MM-yyyy");
        String dataFormatada = formataData.format(new Date());
        editMesAno = findViewById(R.id.edit_CadCap_MesAno);
        editMesAno.setText(dataFormatada);
        textoQtd = findViewById(R.id.text_CadCap_QtdCotaOuAcaoOrdinaria);
        textoVlr = findViewById(R.id.text_CadCap_VlrCotaOuAcaoOrdinaria);
        textoQtdPref = findViewById(R.id.text_CadCap_QtdAcaoPreferencial);
        textoVlrPref = findViewById(R.id.text_CadCap_VlrAcaoPreferencial);
        editMesAno = findViewById(R.id.edit_CadCap_MesAno);
        cad_ou_alt = findViewById(R.id.bt_cad_alt_capital);
        editQtdAcaoPref = findViewById(R.id.edit_CadCap_QtdAcaoPreferencial);
        editQtdAcaoPref.addTextChangedListener(new ThousandTextWatcher(editQtdAcaoPref));
        editQtdCotaAcaoOrdinaria = findViewById(R.id.edit_CadCap_QtdCotaAcaoOrdinaria);
        editQtdCotaAcaoOrdinaria.addTextChangedListener(new ThousandTextWatcher(editQtdCotaAcaoOrdinaria));
        textNomeEmpresa = findViewById(R.id.text_Cad_CapitalEmpresa);
        textCnpj = findViewById(R.id.text_Cad_CapitalCNPJ);
        textTipo = findViewById(R.id.text_Cad_CapitalTipo);
        textNomeEmpresa.setText(nomeEmpresa);
        textCnpj.setText(cnpj);
        textTipo.setText(tipo);
        label_tela = findViewById(R.id.text_Cad_Ou_Alt_Capital);
        if(cadastro_ou_alteracao.equals("A"))
        {
            editMesAno.setEnabled(false);
            cad_ou_alt.setText("Salvar Alterações");
            label_tela.setText("Alterar");
            editMesAno.setText(mesAno);
            editQtdAcaoPref.setText(qtdAcaoPref);
            editQtdCotaAcaoOrdinaria.setText(qtdCotaAcaoOrd);
            editVlrAcaoPref.setText(vlrAcaoPref);
            editVlrCotaAcaoOrdinaria.setText(vlrCotaAcaoOrd);
        }
        else {
            editMesAno.setEnabled(true);
            cad_ou_alt.setText("Cadastrar");
            label_tela.setText("Cadastrar");
        }
        if(tipo.equals("Limitada")){
            mudarLabelCotas();
        }else{
            mudarLabelAcao();
        }

    }

    public void cadastrarCapital(View view) {
        Date mesAno = null;
        BigInteger qtdCotaAcoesOrdinarias = new BigInteger("0");
        BigInteger qtdAcoesPreferenciais = new BigInteger("0");
        BigDecimal vlrCotaAcoesOrdinarias;
        BigDecimal vlrAcoesPreferenciais;
        if (editQtdCotaAcaoOrdinaria.getText().toString().equals(this.qtdCotaAcaoOrd) && editVlrCotaAcaoOrdinaria.getText().toString().equals(this.vlrCotaAcaoOrd)
                && editQtdAcaoPref.getText().toString().equals(this.qtdAcaoPref) && editVlrAcaoPref.getText().toString().equals(this.vlrAcaoPref) && cadastro_ou_alteracao.equals("A")) {
            Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Nenhuma informação foi alterada", Toast.LENGTH_LONG).show();
        }
        else{
            if (editMesAno.getText().toString().length() == 7) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                try {
                    mesAno = sdf.parse("01/" + editMesAno.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Mês/Ano inválido ou não preenchido", Toast.LENGTH_LONG).show();
                }
                if (mesAno != null) {
                    if (!editQtdCotaAcaoOrdinaria.getText().toString().equals("")) {
                        qtdCotaAcoesOrdinarias = new BigInteger(editQtdCotaAcaoOrdinaria.getText().toString().replaceAll("[R$,.]", ""));
                    }
                    if (!editQtdAcaoPref.getText().toString().equals("")) {
                        qtdAcoesPreferenciais = new BigInteger(editQtdAcaoPref.getText().toString().replaceAll("[R$,.]", ""));
                    }
                    if (qtdAcoesPreferenciais.equals(0) && qtdCotaAcoesOrdinarias.equals(0)) {
                        if (textTipo.getText().toString().equals("Sociedade Anônima")) {
                            Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Nenhuma Ação preenchida", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Nenhuma Cota preenchida", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                            vlrAcoesPreferenciais = parseToBigDecimal(editVlrAcaoPref.getText().toString(), mLocale);
                            vlrCotaAcoesOrdinarias = parseToBigDecimal(editVlrCotaAcaoOrdinaria.getText().toString(), mLocale);
                            if ((qtdCotaAcoesOrdinarias.intValue() > 0 && vlrCotaAcoesOrdinarias.compareTo(new BigDecimal(0)) == 0) || (qtdAcoesPreferenciais.intValue() > 0 && vlrAcoesPreferenciais.compareTo(new BigDecimal(0)) == 0)) {
                                if (!tipo.equals("Limitada")) {
                                    Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Valor não preenchido para Ação", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Valor não preenchido para Cota", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                BancoController crud = new BancoController(getBaseContext());
                                Long resultado;


                                Integer mesAnoSQL = Integer.parseInt(new AjustaData(editMesAno.getText().toString()).Inverte_Data());

                                if (cadastro_ou_alteracao.equals("C")) {
                                    resultado = crud.retornaCapitalJaCadastrado(idEmpresa.toString(), mesAnoSQL.toString());
                                    if (resultado > 0) {
                                        Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Movimento de Capital Social já cadastrado", Toast.LENGTH_LONG).show();
                                    } else {
                                        resultado = crud.insereDadoCapital(idEmpresa, mesAnoSQL, qtdCotaAcoesOrdinarias, vlrCotaAcoesOrdinarias, qtdAcoesPreferenciais, vlrAcoesPreferenciais);
                                        if (resultado != -1) {
                                            Toast.makeText(FRMCadastro_CapitalSocial.this, "Movimento de Capital Social cadastrado com sucesso", Toast.LENGTH_LONG).show();
                                            finish();
                                        } else {
                                            Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Não foi possível cadastrar Movimento de Capital Social", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } else {
                                    resultado = crud.alteraCapital(idEmpresa, mesAnoSQL, qtdCotaAcoesOrdinarias, vlrCotaAcoesOrdinarias, qtdAcoesPreferenciais, vlrAcoesPreferenciais);
                                    if (resultado != -1) {
                                        Toast.makeText(FRMCadastro_CapitalSocial.this, "Movimento de Capital Social alterado com sucesso", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Não foi possível alterar Movimento de Capital Social", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                     }
                }

            }
            else {
                Toast.makeText(FRMCadastro_CapitalSocial.this, "ERRO: Mes/Ano inválido ou não preenchido ", Toast.LENGTH_LONG).show();
            }
        }

    }


    public void mudarLabelCotas() {
        textoQtd.setText("Quantidade de Cotas");
        textoVlr.setText("Valor da Cota");
        textoQtdPref.setVisibility(View.INVISIBLE);
        textoVlrPref.setVisibility(View.INVISIBLE);
        editQtdAcaoPref.setVisibility(View.INVISIBLE);
        editVlrAcaoPref.setVisibility(View.INVISIBLE);
    }

    public void mudarLabelAcao() {
        textoQtd.setText("Quantidade de Ações Ordinárias");
        textoVlr.setText("Valor da Ação Ordinária");
        textoQtdPref.setVisibility(View.VISIBLE);
        textoVlrPref.setVisibility(View.VISIBLE);
        editQtdAcaoPref.setVisibility(View.VISIBLE);
        editVlrAcaoPref.setVisibility(View.VISIBLE);
    }
    private BigDecimal parseToBigDecimal(String value, Locale locale) {
        String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());

        String cleanString = value.replaceAll(replaceable, "");

        return new BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR
        );
    }
}
