package ihc.fatecsp.corporatetree;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static ihc.fatecsp.corporatetree.ValidacaoCNPJ.isCNPJ;

public class FRMAltera_Empresa extends AppCompatActivity {
    BancoController crud;
    Cursor c;
    EditText editNome;
    EditText editCNPJ;
    TextView textViewTipo;
    Empresa empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmaltera__empresa);
        Intent it = getIntent();
        String info = it.getStringExtra("codigo");
        int id = Integer.valueOf(info);
        crud = new BancoController(getBaseContext());
        c = crud.carregaDadosEmpresa(id);
        textViewTipo = findViewById(R.id.textViewTipo);
        editNome = findViewById(R.id.editNome);
        editCNPJ = findViewById(R.id.editCnpj);
        empresa = new Empresa(c.getInt(c.getColumnIndex(CriaBanco.ID_EMPRESA)),c.getString(c.getColumnIndex(CriaBanco.NOME_EMPRESA)),c.getString(c.getColumnIndex(CriaBanco.CNPJ_EMPRESA)),c.getString(c.getColumnIndex(CriaBanco.TIPO_EMPRESA)));

        textViewTipo.setText(empresa.getTipoFrase());
        editNome.setText(empresa.getNomeEmpresa());
        editCNPJ.setText(empresa.getCNPJ());
    }

    public void alterarEmpresa(View view) {
        if (editNome.getText().toString().equals(empresa.getNomeEmpresa()) && editCNPJ.getText().toString().equals(empresa.getCNPJ())) {
            Toast.makeText(FRMAltera_Empresa.this, "ERRO: Nenhuma informação foi alterada", Toast.LENGTH_LONG).show();
        } else {
            if (editNome.getText().toString().equals("")) {
                Toast.makeText(FRMAltera_Empresa.this, "ERRO: Nome não preenchido", Toast.LENGTH_LONG).show();
            } else {
                if (!isCNPJ(editCNPJ.getText().toString())) {
                    Toast.makeText(FRMAltera_Empresa.this, "ERRO: CNPJ inválido", Toast.LENGTH_LONG).show();
                } else {
                    Long resultadoCNPJ = crud.retornaCnpjJaCadastrado(editCNPJ.getText().toString());
                    if (resultadoCNPJ > 0 && !editCNPJ.getText().toString().equals(empresa.getCNPJ())) {
                        Toast.makeText(FRMAltera_Empresa.this, "ERRO: CNPJ já cadastrado", Toast.LENGTH_LONG).show();
                    } else {
                        Long resultado = crud.alteraEmpresa(empresa.getId(), editNome.getText().toString(), editCNPJ.getText().toString());
                        if(resultado!=-1) {
                            Toast.makeText(FRMAltera_Empresa.this, "Empresa alterada com sucesso", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(FRMAltera_Empresa.this, "ERRO: Não foi possível alterar Empresa", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }



}
