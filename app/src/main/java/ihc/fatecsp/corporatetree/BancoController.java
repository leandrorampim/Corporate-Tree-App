package ihc.fatecsp.corporatetree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.math.BigInteger;


public class BancoController {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context){
        banco = new CriaBanco(context);
    }

    public long insereDadoEmpresa(String titulo, String cnpj, String editora){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.NOME_EMPRESA, titulo);
        valores.put(CriaBanco.CNPJ_EMPRESA, cnpj);
        valores.put(CriaBanco.TIPO_EMPRESA, editora);

        resultado = db.insert(CriaBanco.TABELA_EMPRESA, null, valores);
        db.close();

        return resultado;
    }


    public long insereDadoCapital(Integer idEmp, Integer mesAno, BigInteger qtdCotaAcaoOrd, BigDecimal vlrCotaAcaoOrd, BigInteger qtdAcaoPref , BigDecimal vlrAcaoPref){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.ID_EMPRESA_CAPITAL, idEmp);
        valores.put(CriaBanco.MES_ANO_CAPITAL, mesAno);
        valores.put(CriaBanco.QTD_COTA_ACAO_ORD, qtdCotaAcaoOrd.toString());
        valores.put(CriaBanco.VLR_COTA_ACAO_ORD, ((BigDecimal)vlrCotaAcaoOrd).multiply(new BigDecimal(100)).toString());
        valores.put(CriaBanco.QTD_ACAO_PREF, qtdAcaoPref.toString());
        valores.put(CriaBanco.VLR_ACAO_PREF, ((BigDecimal)vlrAcaoPref).multiply(new BigDecimal(100)).toString());

        resultado = db.insert(CriaBanco.TABELA_CAPITAL, null, valores);
        db.close();

        return resultado;
    }

    public long insereDadoInvestimento(Integer idInvestidora, Integer idInvestida){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.ID_INVESTIDORA, idInvestidora);
        valores.put(CriaBanco.ID_INVESTIDA, idInvestida);

        resultado = db.insert(CriaBanco.TABELA_INVESTIMENTO, null, valores);
        db.close();

        return resultado;
    }

    public long insereDadoFluxo(Integer idInvestidora,Integer idInvestida , Integer mesAno, BigInteger qtdCotaAcaoOrd, BigInteger qtdAcaoPref){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.ID_INVESTIDORA_FLUXO, idInvestidora);
        valores.put(CriaBanco.ID_INVESTIDA_FLUXO, idInvestida);
        valores.put(CriaBanco.MES_ANO_FLUXO, mesAno);
        valores.put(CriaBanco.QTD_COTA_ACAO_ORD_F, qtdCotaAcaoOrd.toString());
        valores.put(CriaBanco.QTD_ACAO_PREF_F, qtdAcaoPref.toString());

        resultado = db.insert(CriaBanco.TABELA_FLUXO, null, valores);
        db.close();

        return resultado;
    }

    public long retornaCnpjJaCadastrado(String cnpj){
        Cursor cursor;
        String[] campos =  {banco.ID_EMPRESA,banco.CNPJ_EMPRESA};
        String where = CriaBanco.CNPJ_EMPRESA + "='" + cnpj +"'";
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_EMPRESA, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor.getCount();
    }
    public long retornaInvestimentoJaCadastrado(String idInvestidora,String idInvestida){
        Cursor cursor;
        String[] campos =  {banco.ID_INVESTIDORA,banco.ID_INVESTIDA};
        String where = CriaBanco.ID_INVESTIDORA + "=? AND " + CriaBanco.ID_INVESTIDA + "=?";
        String[] selecargs = new String[]{idInvestidora,idInvestida};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_INVESTIMENTO, campos, where, selecargs, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor.getCount();
    }

    public long retornaCapitalJaCadastrado(String idEmpresa,String mesANO){

        Cursor cursor;
        String[] campos =  {banco.ID_EMPRESA_CAPITAL,banco.MES_ANO_CAPITAL};
        String where = CriaBanco.ID_EMPRESA_CAPITAL + "=? AND " + CriaBanco.MES_ANO_CAPITAL + "=?";
        String[] selecargs = new String[]{idEmpresa,mesANO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CAPITAL, campos, where, selecargs, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor.getCount();
    }

    public long retornaFluxoJaCadastrado(String idInvestidora,String idInvestida,String mesANO){

        Cursor cursor;
        String[] campos =  {banco.ID_INVESTIDORA_FLUXO,banco.ID_INVESTIDA_FLUXO,banco.MES_ANO_FLUXO};
        String where = CriaBanco.ID_INVESTIDORA_FLUXO + "=? AND " + CriaBanco.ID_INVESTIDA_FLUXO + "=? AND "+ CriaBanco.MES_ANO_FLUXO + "=?";
        String[] selecargs = new String[]{idInvestidora,idInvestida,mesANO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_FLUXO, campos, where, selecargs, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor.getCount();
    }

    public Cursor carregaDadosEmpresa(Integer id){
        Cursor cursor;
        String[] campos =  {banco.ID_EMPRESA,banco.NOME_EMPRESA,banco.CNPJ_EMPRESA,banco.TIPO_EMPRESA};
        String where = CriaBanco.ID_EMPRESA + "=?";
        String[] selecargs = new String[]{id.toString()};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_EMPRESA, campos, where, selecargs, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosEmpresa(String nome){
        Cursor cursor;
        String[] campos =  {banco.ID_EMPRESA,banco.NOME_EMPRESA,banco.CNPJ_EMPRESA,banco.TIPO_EMPRESA};
        String where = CriaBanco.NOME_EMPRESA + " LIKE ?";
        String[] wherargs = new String[]{"%" + nome + "%"};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_EMPRESA, campos, where, wherargs, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosEmpresa(){
        Cursor cursor;
        String[] campos =  {banco.ID_EMPRESA,banco.NOME_EMPRESA,banco.CNPJ_EMPRESA,banco.TIPO_EMPRESA};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_EMPRESA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosCapital(int id){
        Cursor cursor;
        String[] campos =  {banco.ID_EMPRESA_CAPITAL,banco.MES_ANO_CAPITAL,banco.QTD_COTA_ACAO_ORD,banco.VLR_COTA_ACAO_ORD,banco.QTD_ACAO_PREF,banco.VLR_ACAO_PREF};
        String where = CriaBanco.ID_EMPRESA_CAPITAL + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CAPITAL, campos, where, null, null, null,banco.MES_ANO_CAPITAL+" DESC", null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosCapital(int id,int mesAno){
        Cursor cursor;
        String[] campos =  {banco.ID_EMPRESA_CAPITAL,banco.MES_ANO_CAPITAL,banco.QTD_COTA_ACAO_ORD,banco.VLR_COTA_ACAO_ORD,banco.QTD_ACAO_PREF,banco.VLR_ACAO_PREF};
        String where = CriaBanco.ID_EMPRESA_CAPITAL + "=" + id + "AND" + CriaBanco.MES_ANO_CAPITAL + "=" + mesAno;
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CAPITAL, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosCapitalPeriodo(Integer id,Integer mesAno){
        Cursor cursor;
        String[] campos =  {banco.ID_EMPRESA_CAPITAL,banco.MES_ANO_CAPITAL,banco.QTD_COTA_ACAO_ORD,banco.VLR_COTA_ACAO_ORD,banco.QTD_ACAO_PREF,banco.VLR_ACAO_PREF};
        String where = CriaBanco.ID_EMPRESA_CAPITAL + "=? AND " + CriaBanco.MES_ANO_CAPITAL  + "<=?";
        String[] selecargs = new String[]{id.toString(),mesAno.toString()};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CAPITAL, campos, where, selecargs, null, null, banco.MES_ANO_CAPITAL+" DESC", "1");

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosFluxo(Integer idInvestidora,Integer idInvestida){
        Cursor cursor;
        String[] campos =  {banco.ID_INVESTIDORA_FLUXO,banco.ID_INVESTIDA_FLUXO,banco.MES_ANO_FLUXO,banco.QTD_COTA_ACAO_ORD_F,banco.QTD_ACAO_PREF_F};
        String where = CriaBanco.ID_INVESTIDORA_FLUXO + "=? AND " + CriaBanco.ID_INVESTIDA_FLUXO + "=?";
        String[] selecargs = new String[]{idInvestidora.toString(),idInvestida.toString()};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_FLUXO, campos, where, selecargs, null, null, banco.MES_ANO_FLUXO+" DESC", null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosFluxo(Integer idInvestidora,Integer idInvestida,Integer mesAno){
        Cursor cursor;
        String[] campos =  {banco.ID_INVESTIDORA_FLUXO,banco.ID_INVESTIDA_FLUXO,banco.MES_ANO_FLUXO,banco.QTD_COTA_ACAO_ORD_F,banco.QTD_ACAO_PREF_F};
        String where = CriaBanco.ID_INVESTIDORA_FLUXO + "=? AND " + CriaBanco.ID_INVESTIDA_FLUXO + "=? AND  "+ CriaBanco.MES_ANO_FLUXO + "=?";
        String[] selecargs = new String[]{idInvestidora.toString(),idInvestida.toString(),mesAno.toString()};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_FLUXO, campos, where, selecargs, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosInvestimento(){
        Cursor cursor;
        String[] campos =  {banco.ID_INVESTIDORA,banco.ID_INVESTIDA};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_INVESTIMENTO, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosInvestimentoInvestidora(String idInvestidora){
        Cursor cursor;
        String[] campos =  {banco.ID_INVESTIDORA,banco.ID_INVESTIDA};
        String where = CriaBanco.ID_INVESTIDORA + "=?";
        String[] selecargs = new String[]{idInvestidora};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_INVESTIMENTO, campos, where, selecargs, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosInvestimentoInvestida(String idInvestidora){
        Cursor cursor;
        String[] campos =  {banco.ID_INVESTIDORA,banco.ID_INVESTIDA};
        String where = CriaBanco.ID_INVESTIDA + "=?";
        String[] selecargs = new String[]{idInvestidora};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_INVESTIMENTO, campos, where, selecargs, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    public long alteraEmpresa(int id, String nome, String cnpj){
        ContentValues valores;
        String where;

        long resultado;

        db = banco.getWritableDatabase();

        where = CriaBanco.ID_EMPRESA + "=" + id;

        valores = new ContentValues();
        valores.put(CriaBanco.NOME_EMPRESA, nome);
        valores.put(CriaBanco.CNPJ_EMPRESA, cnpj);

        resultado = db.update(CriaBanco.TABELA_EMPRESA,valores,where,null);
        db.close();
        return resultado;
    }

    public long alteraCapital(Integer idEmp, Integer mesAno, BigInteger qtdCotaAcaoOrd, BigDecimal vlrCotaAcaoOrd, BigInteger qtdAcaoPref , BigDecimal vlrAcaoPref){
        ContentValues valores;

        long resultado;

        db = banco.getWritableDatabase();

        String where = CriaBanco.ID_EMPRESA_CAPITAL + "=? AND "+ CriaBanco.MES_ANO_CAPITAL + "=?";
        String[] wherargs = new String[]{idEmp.toString(),mesAno.toString()};

        valores = new ContentValues();
        valores.put(CriaBanco.QTD_COTA_ACAO_ORD, qtdCotaAcaoOrd.toString());
        valores.put(CriaBanco.VLR_COTA_ACAO_ORD, ((BigDecimal)vlrCotaAcaoOrd).multiply(new BigDecimal(100)).toString());
        valores.put(CriaBanco.QTD_ACAO_PREF, qtdAcaoPref.toString());
        valores.put(CriaBanco.VLR_ACAO_PREF, ((BigDecimal)vlrAcaoPref).multiply(new BigDecimal(100)).toString());

        resultado = db.update(CriaBanco.TABELA_CAPITAL,valores,where,wherargs);
        db.close();
        return resultado;
    }

    public long alteraFluxo(Integer idInvestidora,Integer idInvestida , Integer mesAno, BigInteger qtdCotaAcaoOrd, BigInteger qtdAcaoPref){
        ContentValues valores;

        long resultado;

        db = banco.getWritableDatabase();

        String where = CriaBanco.ID_INVESTIDORA_FLUXO + "=? AND "+ CriaBanco.ID_INVESTIDA_FLUXO + "=? AND "+ CriaBanco.MES_ANO_FLUXO + "=?";
        String[] wherargs = new String[]{idInvestidora.toString(),idInvestida.toString(),mesAno.toString()};

        valores = new ContentValues();
        valores.put(CriaBanco.QTD_COTA_ACAO_ORD_F, qtdCotaAcaoOrd.toString());
        valores.put(CriaBanco.QTD_ACAO_PREF_F, qtdAcaoPref.toString());

        resultado = db.update(CriaBanco.TABELA_FLUXO,valores,where,wherargs);
        db.close();
        return resultado;

    }

    public long deletaRegistroEmpresa(int id){
        long resultado;
        String where = CriaBanco.ID_EMPRESA + "=" + id;
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_EMPRESA,where,null);
        db.close();
        return resultado;
    }

    public long deletaRegistroCapital(Integer id, Integer mesAno){
        long resultado;
        String where = CriaBanco.ID_EMPRESA_CAPITAL + "=? AND "+ CriaBanco.MES_ANO_CAPITAL + "=?";

        String[] wherargs = new String[]{id.toString(),mesAno.toString()};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_CAPITAL,where,wherargs);
        db.close();
        return resultado;
    }

    public long deletaCapitalEmpresa(Integer id){
        long resultado;
        String where = CriaBanco.ID_EMPRESA_CAPITAL + "=?";

        String[] wherargs = new String[]{id.toString()};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_CAPITAL,where,wherargs);
        db.close();
        return resultado;
    }

    public long deletaInvestimentoEmpresaInvestidora(Integer id){
        long resultado;
        String where = CriaBanco.ID_INVESTIDORA + "=?";

        String[] wherargs = new String[]{id.toString()};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_INVESTIMENTO,where,wherargs);
        db.close();
        return resultado;
    }

    public long deletaInvestimentoEmpresaInvestida(Integer id){
        long resultado;
        String where = CriaBanco.ID_INVESTIDA + "=?";

        String[] wherargs = new String[]{id.toString()};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_INVESTIMENTO,where,wherargs);
        db.close();
        return resultado;
    }

    public long deletaFluxoEmpresaInvestidora(Integer id){
        long resultado;
        String where = CriaBanco.ID_INVESTIDORA_FLUXO + "=?";

        String[] wherargs = new String[]{id.toString()};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_FLUXO,where,wherargs);
        db.close();
        return resultado;
    }

    public long deletaFluxoEmpresaInvestida(Integer id){
        long resultado;
        String where = CriaBanco.ID_INVESTIDA_FLUXO + "=?";

        String[] wherargs = new String[]{id.toString()};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_FLUXO,where,wherargs);
        db.close();
        return resultado;
    }

    public long deletaRegistroInvestimento(String idInvestidora, String idInvestida){
        long resultado;
        String where = CriaBanco.ID_INVESTIDORA + "=? AND "+ CriaBanco.ID_INVESTIDA + "=?";
        String[] wherargs = new String[]{idInvestidora,idInvestida};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_INVESTIMENTO,where,wherargs);
        db.close();
        return resultado;
    }

    public long deletaRegistroFluxo(String idInvestidora, String idInvestida, String mesAno){
        long resultado;
        String where = CriaBanco.ID_INVESTIDORA_FLUXO + "=? AND "+ CriaBanco.ID_INVESTIDA_FLUXO + "=? AND "+ CriaBanco.MES_ANO_FLUXO + "=?";
        String[] wherargs = new String[]{idInvestidora,idInvestida,mesAno};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_FLUXO,where,wherargs);
        db.close();
        return resultado;
    }
    public long deletaFluxosInvestimento(String idInvestidora, String idInvestida){
        long resultado;
        String where = CriaBanco.ID_INVESTIDORA_FLUXO + "=? AND "+ CriaBanco.ID_INVESTIDA_FLUXO + "=?";
        String[] wherargs = new String[]{idInvestidora,idInvestida};
        db = banco.getWritableDatabase();
        resultado = db.delete(CriaBanco.TABELA_FLUXO,where,wherargs);
        db.close();
        return resultado;
    }

}


