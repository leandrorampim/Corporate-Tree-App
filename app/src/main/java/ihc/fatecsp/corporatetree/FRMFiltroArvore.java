package ihc.fatecsp.corporatetree;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FRMFiltroArvore extends AppCompatActivity {
    String idEmpresa;
    Integer idEmpresaNum;
    TextView nomeEmpresa;
    BancoController crud;
    Cursor c;
    SimpleDateFormat formataData;
    EditText editMesAno;
    EditText editNiveis;
    RadioButton radioInvestimentos;
    RadioButton radioInvestidores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmfiltro_arvore);
        crud = new BancoController(getBaseContext());
        nomeEmpresa = findViewById(R.id.editBuscaArvore);
        formataData = new SimpleDateFormat("MM-yyyy");
        String dataFormatada;
        editMesAno = findViewById(R.id.editMesAno_Arvore);
        dataFormatada = formataData.format(new Date());
        editMesAno.setText(dataFormatada);
        editNiveis = findViewById(R.id.edit_Niveis_arvore);
        radioInvestimentos = findViewById(R.id.radioInvestimento);
        radioInvestidores = findViewById(R.id.radioInvestidores);
    }


    public void verArvore(View view) {
        if(idEmpresaNum==null){
            Toast.makeText(FRMFiltroArvore.this, "ERRO: Empresa não escolhida", Toast.LENGTH_LONG).show();
        }else {
            if (editMesAno.getText().toString().length() == 7) {
                Date mesAno = null;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                try {
                    mesAno = sdf.parse("01/" + editMesAno.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(FRMFiltroArvore.this, "ERRO: Mês/Ano inválido ou não preenchido", Toast.LENGTH_LONG).show();
                }
                if (mesAno != null) {
                    if(editNiveis.getText().toString().equals("")){
                        Toast.makeText(FRMFiltroArvore.this, "ERRO: Níveis não preenchido", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Integer mesAnoSQL = Integer.parseInt(new AjustaData(editMesAno.getText().toString()).Inverte_Data());
                        Intent intent = new Intent(this, FRMArvore.class);
                        intent.putExtra("id", idEmpresa);
                        intent.putExtra("mesano", mesAnoSQL.toString());
                        intent.putExtra("niveis", editNiveis.getText().toString());
                        if(radioInvestimentos.isChecked()){
                            intent.putExtra("sentido", "1");
                        }
                        else{
                            intent.putExtra("sentido", "2");
                        }

                        startActivity(intent);
                    }
                }
            }
            else {
                Toast.makeText(FRMFiltroArvore.this, "ERRO: Mes/Ano inválido ou não preenchido ", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void pesquisaInvestidora(View view) {
        Intent intent = new Intent(this, FRMPesquisaEmpresa.class);
        intent.putExtra("tipoPesquisa","3" );
        this.startActivityForResult(intent,3);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3 && resultCode==3){
            if(data.hasExtra("id")) {
                this.idEmpresa = data.getStringExtra("id");
                this.idEmpresaNum = Integer.parseInt(idEmpresa);
                c = crud.carregaDadosEmpresa(this.idEmpresaNum);
                nomeEmpresa.setText(c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA)));
                nomeEmpresa.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));

            }
        }

    }
}
