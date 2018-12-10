package ihc.fatecsp.corporatetree;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public class Fluxo_Investimento {
    private Integer idInvestida;
    private Integer idInvestidora;
    private Integer mesAnoInvestimento;
    private Double participacao;
    private BigInteger totalInvestimento;
    private BigInteger qtdCotaAcaoOrd;
    private BigInteger qtdAcaoPref;
    Locale mLocale = new Locale("pt", "BR");


    public Fluxo_Investimento(Integer idInvestidora,Integer idInvestida, Integer mesAnoCapital,
                          BigInteger qtdCotaAcaoOrd,BigInteger qtdAcaoPref,
                          Double participacao, BigInteger totalInvestimento){
        this.idInvestidora = idInvestidora;
        this.idInvestida = idInvestida;
        this.mesAnoInvestimento = mesAnoCapital;
        this.qtdCotaAcaoOrd = qtdCotaAcaoOrd;
        this.qtdAcaoPref = qtdAcaoPref;
        this.participacao = participacao;
        this.totalInvestimento = totalInvestimento;
    }

    public Integer getMesAnoInvestimento() { return this.mesAnoInvestimento;}

    public String getMesAnoInvestimentoString() {
        StringBuilder stringBuilder = new StringBuilder(new AjustaData(this.mesAnoInvestimento.toString()).Desinverte_Data());
        stringBuilder.insert(2, '/');
        return stringBuilder.toString();
    }



    public Integer getIdInvestida() {
        return idInvestida;
    }

    public Integer getIdInvestidora() {
        return idInvestidora;
    }

    public String getIdInvestidaString() {
        return String.valueOf(idInvestida);
    }

    public String getIdInvestidoraString() {
        return String.valueOf(idInvestidora);
    }


    public String getTotalInvestimento() {
        return retornaMascaraNumerica(totalInvestimento);
    }

    public String getParticipacaoString() {
        return retornaMascaraPercentual(participacao);
    }


    public void setTotalInvestimento(BigInteger totalInvestimento) {
        this.totalInvestimento = totalInvestimento;
    }

    public BigInteger getQtdCotaAcaoOrd() {
        return qtdCotaAcaoOrd;
    }

    public String getQtdCotaAcaoOrdString() {
        return retornaMascaraSemDecimal(qtdCotaAcaoOrd);
    }

    public void setQtdCotaAcaoOrd(BigInteger qtdCotaAcaoOrd) {
        this.qtdCotaAcaoOrd = qtdCotaAcaoOrd;
    }

    public BigInteger getQtdAcaoPref() {
        return qtdAcaoPref;
    }

    public String getQtdAcaoPrefString() {
        return retornaMascaraSemDecimal(qtdAcaoPref);
    }

    public void setQtdAcaoPref(BigInteger qtdAcaoPref) {
        this.qtdAcaoPref = qtdAcaoPref;
    }

    private String retornaMascaraNumerica(BigInteger value) {
        String cleanString = value.toString().replaceAll("[R$,.]", "");

        BigDecimal parsed = new BigDecimal(cleanString);
        return NumberFormat.getCurrencyInstance().format((parsed.divide(new BigDecimal("100"))));
    }



    private String retornaMascaraPercentual(Double value) {
        NumberFormat defaultFormat = NumberFormat.getPercentInstance();
        defaultFormat.setMinimumFractionDigits(5);

        return defaultFormat.format(value/100);


    }

    private String retornaMascaraSemDecimal(BigInteger value) {
        String cleanString = value.toString().replaceAll("[R$,.]", "");

        BigDecimal parsed = new BigDecimal(cleanString);
        return NumberFormat.getNumberInstance().format((parsed));


    }
}
