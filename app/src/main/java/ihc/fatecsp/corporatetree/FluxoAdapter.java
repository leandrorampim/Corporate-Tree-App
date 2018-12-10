package ihc.fatecsp.corporatetree;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class FluxoAdapter extends RecyclerView.Adapter {

    private List<Fluxo_Investimento> lFluxos;
    private Context context;
    BancoController crud;
    Locale mLocale;
    String tipoInvestida;

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public FluxoAdapter(List<Fluxo_Investimento> fluxo,Context context,String tipoInvestida) {
        this.lFluxos = fluxo;
        this.context = context;
        this.tipoInvestida = tipoInvestida;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View fluxoView = inflater.inflate(R.layout.item_fluxo, parent, false);

        FluxoViewHolder holder = new FluxoViewHolder(fluxoView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FluxoViewHolder holder = (FluxoViewHolder) viewHolder;
        mLocale = new Locale("pt", "BR");
        final Fluxo_Investimento fluxo_lista  = lFluxos.get(i);
        holder.mesAno.setText(fluxo_lista.getMesAnoInvestimentoString());
        holder.qtdCotaAcaoOrd.setText(fluxo_lista.getQtdCotaAcaoOrdString());
        holder.qtdAcaoPref.setText(fluxo_lista.getQtdAcaoPrefString());
        holder.participacao.setText(fluxo_lista.getParticipacaoString());
        holder.totalInvestimento.setText(fluxo_lista.getTotalInvestimento());
        if(tipoInvestida.equals("L")){
            holder.labelQtdAcaoPref.setVisibility(TextView.INVISIBLE);
            holder.qtdAcaoPref.setVisibility(TextView.INVISIBLE);
            holder.labelQtdCotaAcaoOrd.setText("Cotas");
        }
        else {
            holder.labelQtdAcaoPref.setVisibility(TextView.VISIBLE);
            holder.qtdAcaoPref.setVisibility(TextView.VISIBLE);
            holder.labelQtdCotaAcaoOrd.setText("Ações Ordinárias");
        }

        holder.btEditar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, FRMCadastro_Investimento.class);
                intent.putExtra("idInvestidora",fluxo_lista.getIdInvestidora().toString() );
                intent.putExtra("idInvestida",fluxo_lista.getIdInvestida().toString() );
                intent.putExtra("fluxo","S" );
                intent.putExtra("mesAno",fluxo_lista.getMesAnoInvestimentoString() );

                context.startActivity(intent);
            }
        });

        holder.btExcluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                if(getItemCount()>1){
                    new AlertDialog.Builder(context).setTitle("Excluindo Fluxo de Investimento").setMessage("Tem certeza que deseja excluir Fluxo de Investimento de "+fluxo_lista.getMesAnoInvestimentoString()+" ?").setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Activity activity = getActivity(v);
                                    Intent intent = activity.getIntent();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    intent.putExtra("idInvestidoraExcluir",fluxo_lista.getIdInvestidoraString() );
                                    intent.putExtra("idInvestidaExcluir",fluxo_lista.getIdInvestidaString() );
                                    intent.putExtra("mesAnoExclui",fluxo_lista.getMesAnoInvestimentoString() );
                                    activity.finish();
                                    activity.startActivity(intent);
                                }
                            }).setNegativeButton("Não", null).show();
                }
                else {
                    Toast.makeText(context, "ERRO: Último Fluxo de Investimento", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return lFluxos.size();
    }
}
