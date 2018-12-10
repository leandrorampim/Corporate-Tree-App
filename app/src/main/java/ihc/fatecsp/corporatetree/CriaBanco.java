package ihc.fatecsp.corporatetree;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "CorporateTree.db";
    public static final String TABELA_EMPRESA = "empresa";
    public static final String TABELA_CAPITAL = "capital_social";
    public static final String TABELA_INVESTIMENTO = "investimento";
    public static final String TABELA_FLUXO = "fluxo_investimento";
    public static final String ID_EMPRESA = "id";
    public static final String NOME_EMPRESA = "nome";
    public static final String CNPJ_EMPRESA = "cnpj";
    public static final String TIPO_EMPRESA = "tipo";
    public static final String ID_EMPRESA_CAPITAL = "Id_Emp_Capital";
    public static final String MES_ANO_CAPITAL = "Mes_Ano_Capital";
    public static final String QTD_COTA_ACAO_ORD = "Qtd_Cota_Ord";
    public static final String VLR_COTA_ACAO_ORD = "Vlr_Cota_Ord";
    public static final String QTD_ACAO_PREF = "Qtd_Preferencial";
    public static final String VLR_ACAO_PREF = "Vlr_Preferencial";
    public static final String ID_INVESTIDORA = "id_investidora";
    public static final String ID_INVESTIDA = "id_investida";
    public static final String ID_INVESTIDORA_FLUXO = "Id_Investidora_Fluxo";
    public static final String ID_INVESTIDA_FLUXO = "Id_Investida_Fluxo";
    public static final String MES_ANO_FLUXO = "Mes_Ano_Fluxo";
    public static final String QTD_COTA_ACAO_ORD_F = "Qtd_F_Cota_Ord";
    public static final String QTD_ACAO_PREF_F = "Qtd_F_Preferencial";

    private static final int VERSAO = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA_EMPRESA+"("
                + ID_EMPRESA + " integer primary key autoincrement,"
                + NOME_EMPRESA + " text,"
                + CNPJ_EMPRESA + " text,"
                + TIPO_EMPRESA + " text"
                +")";
        db.execSQL(sql);

        sql = "CREATE TABLE "+TABELA_INVESTIMENTO+"("
                + ID_INVESTIDORA + " integer ,"
                + ID_INVESTIDA + " integer, PRIMARY KEY("
                + ID_INVESTIDORA + ","
                + ID_INVESTIDA + "), FOREIGN KEY("
                + ID_INVESTIDORA + ") REFERENCES "
                + TABELA_EMPRESA + "("
                + ID_EMPRESA + "), FOREIGN KEY("
                + ID_INVESTIDA + ") REFERENCES "
                + TABELA_EMPRESA + "("
                + ID_EMPRESA + "))";
        db.execSQL(sql);

        sql = "CREATE TABLE "+TABELA_CAPITAL+"("
                + ID_EMPRESA_CAPITAL + " integer ,"
                + MES_ANO_CAPITAL + " integer ,"
                + QTD_COTA_ACAO_ORD + " integer ,"
                + VLR_COTA_ACAO_ORD + " integer ,"
                + QTD_ACAO_PREF + " integer ,"
                + VLR_ACAO_PREF + " integer , PRIMARY KEY("
                + ID_EMPRESA_CAPITAL + ","
                + MES_ANO_CAPITAL + "), FOREIGN KEY("
                + ID_EMPRESA_CAPITAL + ") REFERENCES "
                + TABELA_EMPRESA + "("
                + ID_EMPRESA + "))";
        db.execSQL(sql);

        sql = "CREATE TABLE "+TABELA_FLUXO+"("
                + ID_INVESTIDORA_FLUXO + " integer ,"
                + ID_INVESTIDA_FLUXO + " integer ,"
                + MES_ANO_FLUXO + " integer ,"
                + QTD_COTA_ACAO_ORD_F + " integer ,"
                + QTD_ACAO_PREF_F + " integer , PRIMARY KEY("
                + ID_INVESTIDORA_FLUXO + ","
                + ID_INVESTIDA_FLUXO + ","
                + MES_ANO_FLUXO + "), FOREIGN KEY("
                + ID_INVESTIDORA_FLUXO + ") REFERENCES "
                + TABELA_INVESTIMENTO + "("
                + ID_INVESTIDORA + "), FOREIGN KEY("
                + ID_INVESTIDA_FLUXO + ") REFERENCES "
                + TABELA_INVESTIMENTO + "("
                + ID_INVESTIDORA + "))";
        db.execSQL(sql);

    }
    public CriaBanco(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CAPITAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_INVESTIMENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_FLUXO);

        onCreate(db);
    }
}
