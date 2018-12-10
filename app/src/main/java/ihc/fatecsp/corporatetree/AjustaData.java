package ihc.fatecsp.corporatetree;

public class AjustaData {
    String data;
    public AjustaData(String data){
        this.data = data;
    }

    public String Inverte_Data(){
        StringBuilder stringBuilder = new StringBuilder(this.data);
        String retorno;
        if(stringBuilder.length()>6){
            retorno = stringBuilder.substring(3,7)+stringBuilder.substring(0,2);
        }
        else{
            retorno = stringBuilder.substring(2,6)+stringBuilder.substring(0,2);
        }
        return retorno;
    }

    public String Desinverte_Data(){
        StringBuilder stringBuilder = new StringBuilder(this.data);
        String retorno;
        retorno = stringBuilder.substring(4,6)+stringBuilder.substring(0,4);
        return retorno;
    }


}
