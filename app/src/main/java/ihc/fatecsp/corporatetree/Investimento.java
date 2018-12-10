package ihc.fatecsp.corporatetree;

import java.text.NumberFormat;

public class Investimento {
    private Integer idInvestidora;
    private Integer idInvestida;
    private String nomeInvestidora;
    private String nomeInvestida;
    private String tipoInvestida;

    public Investimento(Integer idInvestidora, Integer idInvestida) {

        this.idInvestidora = idInvestidora;
        this.idInvestida = idInvestida;

    }

    public Integer getIdInvestidora() {
        return idInvestidora;
    }

    public Integer getIdInvestida() {
        return idInvestida;
    }

    public String getIdInvestidoraString() {
        return idInvestidora.toString();
    }

    public String getIdInvestidaString() {
        return idInvestida.toString();
    }

    public String getNomeInvestidora() {
        return nomeInvestidora;
    }

    public void setNomeInvestidora(String nomeInvestidora) {
        this.nomeInvestidora = nomeInvestidora;
    }

    public String getNomeInvestida() {
        return nomeInvestida;
    }

    public void setNomeInvestida(String nomeInvestida) {
        this.nomeInvestida = nomeInvestida;
    }


    public String getTipoInvestida() {
        return tipoInvestida;
    }

    public void setTipoInvestida(String tipoInvestida) {
        this.tipoInvestida = tipoInvestida;
    }
}
