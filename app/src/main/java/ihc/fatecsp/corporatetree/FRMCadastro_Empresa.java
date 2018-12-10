package ihc.fatecsp.corporatetree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static ihc.fatecsp.corporatetree.ValidacaoCNPJ.isCNPJ;
import static java.lang.Integer.parseInt;

public class FRMCadastro_Empresa extends AppCompatActivity {
    TextView textoQtd;
    TextView textoVlr;
    TextView textoQtdPref;
    TextView textoVlrPref;
    EditText editQtdAcaoPref;
    EditText editQtdCotaAcaoOrdinaria;
    EditText editVlrAcaoPref;
    EditText editVlrCotaAcaoOrdinaria;
    EditText editNome;
    EditText editCNPJ;
    RadioButton radioSA;
    RadioButton radioLTDA;
    EditText editMesAno;
    SimpleDateFormat formataData;
    Locale mLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmcadastro__empresa);
        mLocale = new Locale("pt", "BR");
        EditText editVlwAcaoPreferencial = findViewById(R.id.editVlrAcaoPreferencial);
        editVlwAcaoPreferencial.addTextChangedListener(new MoneyTextWatcher(editVlwAcaoPreferencial, mLocale));
        EditText editVlwCotaAcaoOrdinaria = findViewById(R.id.editVlrCotaAcaoOrdinaria);
        editVlwCotaAcaoOrdinaria.addTextChangedListener(new MoneyTextWatcher(editVlwCotaAcaoOrdinaria, mLocale));
        formataData = new SimpleDateFormat("MM-yyyy");
        String dataFormatada = formataData.format(new Date());
        editMesAno = findViewById(R.id.editMesAno);
        editMesAno.setText(dataFormatada);
        textoQtd = findViewById(R.id.textQtdCotaOuAcaoOrdinaria);
        textoVlr = findViewById(R.id.textVlrCotaOuAcaoOrdinaria);
        textoQtdPref = findViewById(R.id.textQtdAcaoPreferencial);
        textoVlrPref = findViewById(R.id.textVlrAcaoPreferencial);
        editNome = findViewById(R.id.editNome);
        editCNPJ = findViewById(R.id.editCnpj);
        radioSA = findViewById(R.id.radioSA);
        radioLTDA = findViewById(R.id.radioLTDA);
        editMesAno = findViewById(R.id.editMesAno);
        editQtdAcaoPref = findViewById(R.id.editQtdAcaoPreferencial);
        editQtdAcaoPref.addTextChangedListener(new ThousandTextWatcher(editQtdAcaoPref));
        editQtdCotaAcaoOrdinaria = findViewById(R.id.editQtdCotaAcaoOrdinaria);
        editQtdCotaAcaoOrdinaria.addTextChangedListener(new ThousandTextWatcher(editQtdCotaAcaoOrdinaria));
        editVlrAcaoPref = findViewById(R.id.editVlrAcaoPreferencial);
        editVlrCotaAcaoOrdinaria = findViewById(R.id.editVlrCotaAcaoOrdinaria);

    }

    public void cadastrarEmpresa(View view) {
        Date mesAno = null;
        BigInteger qtdCotaAcoesOrdinarias = new BigInteger("0");
        BigInteger qtdAcoesPreferenciais = new BigInteger("0");
        BigDecimal vlrCotaAcoesOrdinarias;
        BigDecimal vlrAcoesPreferenciais;
        if(editNome.getText().toString().equals("")) {
            Toast.makeText(FRMCadastro_Empresa.this, "ERRO: Nome não preenchido", Toast.LENGTH_LONG).show();
        }
        else{
            if(!isCNPJ(editCNPJ.getText().toString())){
                Toast.makeText(FRMCadastro_Empresa.this, "ERRO: CNPJ inválido", Toast.LENGTH_LONG).show();
            }
            else {
                if (editMesAno.getText().toString().length() == 7) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    try {
                        mesAno = sdf.parse("01/" + editMesAno.getText().toString());
                    } catch (ParseException e) {
                        Toast.makeText(FRMCadastro_Empresa.this, "ERRO: Mes/Ano inválido ou não preenchido", Toast.LENGTH_LONG).show();
                    }


                    if (mesAno != null) {
                        if (!editQtdCotaAcaoOrdinaria.getText().toString().equals("")) {
                            qtdCotaAcoesOrdinarias = new BigInteger(editQtdCotaAcaoOrdinaria.getText().toString().replaceAll("[R$,.]", ""));
                        }
                        if (!editQtdAcaoPref.getText().toString().equals("")) {
                            qtdAcoesPreferenciais = new BigInteger(editQtdAcaoPref.getText().toString().replaceAll("[R$,.]", ""));
                        }

                        if (qtdAcoesPreferenciais.equals(0) && qtdCotaAcoesOrdinarias.equals(0)) {
                            if (radioSA.isChecked()) {
                                Toast.makeText(FRMCadastro_Empresa.this, "ERRO: Nenhuma Ação preenchida", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(FRMCadastro_Empresa.this, "ERRO: Nenhuma Cota preenchida", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            vlrAcoesPreferenciais = parseToBigDecimal(editVlrAcaoPref.getText().toString(), mLocale);
                            vlrCotaAcoesOrdinarias = parseToBigDecimal(editVlrCotaAcaoOrdinaria.getText().toString(), mLocale);
                            if ((qtdCotaAcoesOrdinarias.intValue() > 0 && vlrCotaAcoesOrdinarias.compareTo(new BigDecimal(0)) == 0) || (qtdAcoesPreferenciais.intValue() > 0 && vlrAcoesPreferenciais.compareTo(new BigDecimal(0)) == 0)) {
                                if (radioSA.isChecked()) {
                                    Toast.makeText(FRMCadastro_Empresa.this, "ERRO: Valor não preenchido para Ação", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(FRMCadastro_Empresa.this, "ERRO: Valor não preenchido para Cota", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                BancoController crud = new BancoController(getBaseContext());
                                String nome = editNome.getText().toString();
                                String cnpj = editCNPJ.getText().toString();

                                Long resultadoCNPJ = crud.retornaCnpjJaCadastrado(cnpj);
                                if(resultadoCNPJ>0){

                                    Toast.makeText(FRMCadastro_Empresa.this, "ERRO: CNPJ já cadastrado", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    String tipo;
                                    if(radioSA.isChecked()){
                                        tipo="S";
                                    }
                                    else{
                                        tipo="L";
                                    }
                                    Long resultado = crud.insereDadoEmpresa(nome,cnpj,tipo);
                                    if(resultado!=-1){
                                        int mesAnoSQL = Integer.parseInt(new AjustaData(editMesAno.getText().toString()).Inverte_Data());
                                        resultado = crud.insereDadoCapital(Integer.parseInt(resultado.toString()),mesAnoSQL,qtdCotaAcoesOrdinarias,vlrCotaAcoesOrdinarias,qtdAcoesPreferenciais,vlrAcoesPreferenciais);
                                        Toast.makeText(FRMCadastro_Empresa.this, "Empresa cadastrada com  sucesso", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(FRMCadastro_Empresa.this, "ERRO: Não foi possível cadastrar Empresa", Toast.LENGTH_LONG).show();
                                    }


                                }
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(FRMCadastro_Empresa.this, "ERRO: Mes/Ano inválido ou não preenchido ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public void mudarLabelCotas(View view) {
        textoQtd.setText("Quantidade de Cotas");
        textoVlr.setText("Valor da Cota");
        textoQtdPref.setVisibility(View.INVISIBLE);
        textoVlrPref.setVisibility(View.INVISIBLE);
        editQtdAcaoPref.setVisibility(View.INVISIBLE);
        editVlrAcaoPref.setVisibility(View.INVISIBLE);
    }

    public void mudarLabelAcao(View view) {
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
