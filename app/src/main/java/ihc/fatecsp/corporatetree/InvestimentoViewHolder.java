package ihc.fatecsp.corporatetree;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class InvestimentoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    final TextView nomeInvestidora;
    final TextView nomeInvestida;
    final ImageButton btExcluir;
    final ImageButton btFluxoInvestimento;
    public InvestimentoViewHolder(@NonNull View itemView) {
        super(itemView);
        nomeInvestidora = (TextView) itemView.findViewById(R.id.item_investidora_nome);
        nomeInvestida = (TextView) itemView.findViewById(R.id.item_investida_nome);
        btExcluir = (ImageButton) itemView.findViewById(R.id.excluirInvestimento);
        btFluxoInvestimento = (ImageButton) itemView.findViewById(R.id.fluxoInvestimento);
    }

    @Override
    public void onClick(View v) {

    }
}
