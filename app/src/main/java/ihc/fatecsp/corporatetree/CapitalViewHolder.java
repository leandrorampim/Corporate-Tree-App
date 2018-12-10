package ihc.fatecsp.corporatetree;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class CapitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    final TextView mesAno;
    final TextView capitalTotal;
    final TextView qtdCotaAcaoOrd;
    final TextView vlrCotaAcaoOrd;
    final TextView qtdAcaoPref;
    final TextView vlrAcaoPref;
    final TextView labelQtdCotaAcoesOrd;
    final TextView labelVlrCotaAcoesOrd;
    final TextView labelQtdAcoesPref;
    final TextView labelVlrAcoesPref;
    final ImageButton btEditar;
    final ImageButton btExcluir;


    public CapitalViewHolder(@NonNull View itemView) {
        super(itemView);
        mesAno = (TextView) itemView.findViewById(R.id.item_capital_mesano);
        capitalTotal = (TextView) itemView.findViewById(R.id.item_capital_total);
        qtdCotaAcaoOrd = (TextView) itemView.findViewById(R.id.item_capital_qtd_cotaacao_ord);
        vlrCotaAcaoOrd = (TextView) itemView.findViewById(R.id.item_capital_vlr_cotaacao_ord);
        qtdAcaoPref = (TextView) itemView.findViewById(R.id.item_capital_qtd_acao_pref);
        vlrAcaoPref = (TextView) itemView.findViewById(R.id.item_capital_vlr_acao_pref);
        btEditar = (ImageButton) itemView.findViewById(R.id.editarEmpresa);
        btExcluir = (ImageButton) itemView.findViewById(R.id.excluirEmpresa);
        labelQtdAcoesPref = (TextView) itemView.findViewById(R.id.label_capital_qtd_acao_pref);
        labelVlrAcoesPref = (TextView) itemView.findViewById(R.id.label_capital_vlr_acao_pref);
        labelQtdCotaAcoesOrd = (TextView) itemView.findViewById(R.id.label_capital_qtd_cotaacao_ord);
        labelVlrCotaAcoesOrd = (TextView) itemView.findViewById(R.id.label_capital_vlr_cotaacao_ord);
    }

    public CapitalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View capitalView = inflater.inflate(R.layout.item_capital, parent, false);

        CapitalViewHolder holder = new CapitalViewHolder(capitalView);

        return holder;
    }

    @Override
    public void onClick(View v) {

    }
}
