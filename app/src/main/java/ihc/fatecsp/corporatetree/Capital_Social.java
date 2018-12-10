package ihc.fatecsp.corporatetree;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Capital_Social {
    private Integer idEmpresa;
    private Integer mesAnoCapital;
    private BigInteger qtdCotaAcaoOrd;
    private BigInteger vlrCotaAcaoOrd;
    private BigInteger qtdAcaoPref;
    private BigInteger vlrAcaoPref;
    Locale mLocale = new Locale("pt", "BR");


    public Capital_Social(Integer idEmpresa, Integer mesAnoCapital,
                          BigInteger qtdCotaAcaoOrd, BigInteger vlrCotaAcaoOrd,
                          BigInteger qtdAcaoPref , BigInteger vlrAcaoPref) {

        this.idEmpresa = idEmpresa;
        this.mesAnoCapital = mesAnoCapital;
        this.qtdCotaAcaoOrd = qtdCotaAcaoOrd;
        this.vlrCotaAcaoOrd = vlrCotaAcaoOrd;
        this.qtdAcaoPref = qtdAcaoPref;
        this.vlrAcaoPref = vlrAcaoPref;
    }

    public Integer getIdCapitalSOcial() {
        return this.idEmpresa;
    }

    public Integer getMesAnoCapital() { return this.mesAnoCapital;}

    public BigInteger getQtdCotaAcaoOrd() {
        return this.qtdCotaAcaoOrd;
    }

    public BigInteger getQtdAcaoPref() {
        return this.qtdAcaoPref;
    }

    public BigDecimal getVlrCotaAcaoOrd() {
        BigDecimal valor = new BigDecimal(this.vlrCotaAcaoOrd.divide(new BigInteger("100")).toString());
        return valor.movePointLeft(2);
    }

    public BigDecimal getVlrAcaoPref() {
        BigDecimal valor = new BigDecimal(this.vlrAcaoPref.divide(new BigInteger("100")).toString());
        return valor.movePointLeft(2);
    }

    public String getIdCapitalSOcialString() {
        return String.valueOf(this.idEmpresa);
    }

    public String getMesAnoCapitalString() {
        StringBuilder stringBuilder = new StringBuilder(new AjustaData(this.mesAnoCapital.toString()).Desinverte_Data());
        stringBuilder.insert(2, '/');

        return stringBuilder.toString();
    }

    public String getQtdCotaAcaoOrdString() {
        return retornaMascaraSemDecimal(this.qtdCotaAcaoOrd);
    }

    public String getQtdAcaoPrefString() {
        return retornaMascaraSemDecimal(this.qtdAcaoPref);
    }

    public String getVlrCotaAcaoOrdString() {
        return retornaMascaraNumerica(this.vlrCotaAcaoOrd);

    }

    public String getVlrAcaoPrefString() {
        return retornaMascaraNumerica(this.vlrAcaoPref);
    }

    public String getTotalCapitalMes(){
        BigDecimal capital_total;
        BigDecimal qtdPref = new BigDecimal(this.qtdAcaoPref);
        BigDecimal vlrPref = new BigDecimal(this.vlrAcaoPref).movePointLeft(2);
        BigDecimal qtdCotaOrd = new BigDecimal(this.qtdCotaAcaoOrd);
        BigDecimal vlrCotaOrd = new BigDecimal(this.vlrCotaAcaoOrd).movePointLeft(2);
        BigDecimal totalPref = qtdPref.multiply(vlrPref);
        BigDecimal totalCotaOrd = qtdCotaOrd.multiply(vlrCotaOrd);
        capital_total = totalPref.add(totalCotaOrd).movePointLeft(2);

        return retornaMascaraNumerica(capital_total);

    }

    private String retornaMascaraNumerica(BigDecimal value) {
        String cleanString = value.toString().replaceAll("[R$,.]", "");

        BigDecimal parsed = new BigDecimal(cleanString);
        return NumberFormat.getCurrencyInstance().format((parsed.divide(new BigDecimal("100"))));


    }

    private String retornaMascaraNumerica(BigInteger value) {
        String cleanString = value.toString().replaceAll("[R$,.]", "");

        BigDecimal parsed = new BigDecimal(cleanString);
        return NumberFormat.getCurrencyInstance().format((parsed.divide(new BigDecimal("100"))));


    }

    private String retornaMascaraSemDecimal(BigInteger value) {
        String cleanString = value.toString().replaceAll("[R$,.]", "");

        BigDecimal parsed = new BigDecimal(cleanString);
        return NumberFormat.getNumberInstance().format((parsed));


    }





}