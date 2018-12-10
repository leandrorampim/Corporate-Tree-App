package ihc.fatecsp.corporatetree;


public class Empresa {
    private Integer idEmpresa;
    private String nomeEmpresa;
    private String cnpj;
    private String tipo;

    public Empresa(Integer idEmpresa, String nomeEmpresa,
                     String cnpj, String tipo) {

        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.tipo = tipo;
    }

    public String getIdEmpresa() {
        return Integer.toString(this.idEmpresa);
    }

    public Integer getId() {
        return this.idEmpresa;
    }

    public String getNomeEmpresa() {

        return this.nomeEmpresa;
    }

    public String getCNPJ() {
        return this.cnpj;
    }

    public String getTipoFrase() {
        if(this.tipo.equals("L"))
            return "Limitada";
        else
            return "Sociedade Anônima";
    }
    public String getTipo() {
        return tipo;
    }

}
