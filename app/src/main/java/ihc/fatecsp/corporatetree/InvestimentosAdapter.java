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

import java.util.List;

public class InvestimentosAdapter extends RecyclerView.Adapter{
    private List<Investimento> lInvestimento;
    private Context context;

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

    public InvestimentosAdapter(List<Investimento> investimento,Context context) {
        this.lInvestimento = investimento;
        this.context = context;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View investimentoView = inflater.inflate(R.layout.item_investimento, parent, false);

        InvestimentoViewHolder holder = new InvestimentoViewHolder(investimentoView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        InvestimentoViewHolder holder = (InvestimentoViewHolder) viewHolder;

        final Investimento investimento_lista  = lInvestimento.get(i);
        holder.nomeInvestidora.setText(investimento_lista.getNomeInvestidora());
        holder.nomeInvestida.setText(investimento_lista.getNomeInvestida());
        holder.btExcluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                new AlertDialog.Builder(context).setTitle("Excluindo Investimento").setMessage("Tem certeza que deseja excluir "+investimento_lista.getNomeInvestidora()+" investe em "+investimento_lista.getNomeInvestida()+" ?").setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Activity activity = getActivity(v);
                                Intent intent = activity.getIntent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.putExtra("idExcluirInvestidora",investimento_lista.getIdInvestidoraString() );
                                intent.putExtra("idExcluirInvestida",investimento_lista.getIdInvestidaString() );
                                activity.finish();
                                activity.startActivity(intent);
                            }
                        }).setNegativeButton("Não", null).show();
            }
        });
        holder.btFluxoInvestimento.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context,FRMFluxos.class);
                intent.putExtra("idInvestidora",investimento_lista.getIdInvestidoraString());
                intent.putExtra("idInvestida",investimento_lista.getIdInvestidaString());
                intent.putExtra("nomeInvestidora",investimento_lista.getNomeInvestidora());
                intent.putExtra("nomeInvestida",investimento_lista.getNomeInvestida());
                intent.putExtra("tipoInvestida",investimento_lista.getTipoInvestida());
                context.startActivity(intent);}
        });
    }

    @Override
    public int getItemCount() {
        return lInvestimento.size();
    }



}
