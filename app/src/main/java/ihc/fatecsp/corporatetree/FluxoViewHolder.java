package ihc.fatecsp.corporatetree;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class FluxoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    final TextView mesAno;
    final TextView participacao;
    final TextView totalInvestimento;
    final TextView qtdCotaAcaoOrd;
    final TextView qtdAcaoPref;
    final TextView labelQtdCotaAcaoOrd;
    final TextView labelQtdAcaoPref;
    final ImageButton btEditar;
    final ImageButton btExcluir;

    public FluxoViewHolder(@NonNull View itemView) {
        super(itemView);
        mesAno = (TextView) itemView.findViewById(R.id.item_investimento_mesano_fluxo);
        totalInvestimento = (TextView) itemView.findViewById(R.id.item_investimento_total_fluxo);
        participacao = (TextView) itemView.findViewById(R.id.item_percentual_investimento_fluxo);
        qtdCotaAcaoOrd = (TextView) itemView.findViewById(R.id.item_fluxo_qtd_cotaacao_ord);
        qtdAcaoPref = (TextView) itemView.findViewById(R.id.item_fluxo_qtd_acao_pref);
        btEditar = (ImageButton) itemView.findViewById(R.id.editar_fluxo);
        btExcluir = (ImageButton) itemView.findViewById(R.id.excluir_fluxo);
        labelQtdCotaAcaoOrd = (TextView) itemView.findViewById(R.id.label_fluxo_qtd_cotaacao_ord);
        labelQtdAcaoPref = (TextView) itemView.findViewById(R.id.label_fluxo_qtd_acao_pref);
    }

    public FluxoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View fluxoView = inflater.inflate(R.layout.item_fluxo, parent, false);

        FluxoViewHolder holder = new FluxoViewHolder(fluxoView);

        return holder;
    }

    @Override
    public void onClick(View v) {

    }
}
