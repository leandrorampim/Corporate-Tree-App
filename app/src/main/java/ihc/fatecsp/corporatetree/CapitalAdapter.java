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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class CapitalAdapter extends RecyclerView.Adapter {

    private List<Capital_Social> lCapitais;
    private Context context;
    BancoController crud;
    Locale mLocale;
    String tipoEmpresa;
    String nomeEmpresa;
    String cnpjEmpresa;

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

    public CapitalAdapter(List<Capital_Social> capital,Context context,String tipoEmpresa,String cnpj,String nomeEmpresa) {
        this.lCapitais = capital;
        this.context = context;
        this.tipoEmpresa = tipoEmpresa;
        this.cnpjEmpresa = cnpj;
        this.nomeEmpresa = nomeEmpresa;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View capitalView = inflater.inflate(R.layout.item_capital, parent, false);

        CapitalViewHolder holder = new CapitalViewHolder(capitalView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CapitalViewHolder holder = (CapitalViewHolder) viewHolder;
        mLocale = new Locale("pt", "BR");
        final Capital_Social capital_lista  = lCapitais.get(i);
        holder.mesAno.setText(capital_lista.getMesAnoCapitalString());
        holder.qtdCotaAcaoOrd.setText(capital_lista.getQtdCotaAcaoOrdString());
        holder.vlrCotaAcaoOrd.setText(capital_lista.getVlrCotaAcaoOrdString());
        holder.qtdAcaoPref.setText(capital_lista.getQtdAcaoPrefString());
        holder.vlrAcaoPref.setText(capital_lista.getVlrAcaoPrefString());
        holder.capitalTotal.setText(capital_lista.getTotalCapitalMes());
        if(tipoEmpresa.equals("Limitada")){
            holder.labelQtdAcoesPref.setVisibility(TextView.INVISIBLE);
            holder.labelVlrAcoesPref.setVisibility(TextView.INVISIBLE);
            holder.qtdAcaoPref.setVisibility(TextView.INVISIBLE);
            holder.vlrAcaoPref.setVisibility(TextView.INVISIBLE);
            holder.labelQtdCotaAcoesOrd.setText("Cotas");
            holder.labelVlrCotaAcoesOrd.setText("Valor Cota");
        }
        else {
            holder.labelQtdAcoesPref.setVisibility(TextView.VISIBLE);
            holder.labelVlrAcoesPref.setVisibility(TextView.VISIBLE);
            holder.qtdAcaoPref.setVisibility(TextView.VISIBLE);
            holder.vlrAcaoPref.setVisibility(TextView.VISIBLE);
            holder.labelQtdCotaAcoesOrd.setText("Ações Ordinárias");
            holder.labelVlrCotaAcoesOrd.setText("Valor Ação");
        }

        holder.btEditar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, FRMCadastro_CapitalSocial.class);
                intent.putExtra("idEmpresa",capital_lista.getIdCapitalSOcialString() );
                intent.putExtra("nomeEmpresa",nomeEmpresa);
                intent.putExtra("cnpjEmpresa",cnpjEmpresa);
                intent.putExtra("tipoEmpresa",tipoEmpresa);
                intent.putExtra("cadastro_ou_alteracao","A");
                intent.putExtra("mesAno", capital_lista.getMesAnoCapitalString());
                intent.putExtra("qtdCotaAcaoOrd", capital_lista.getQtdCotaAcaoOrdString());
                intent.putExtra("vlrCotaAcaoOrd", capital_lista.getVlrCotaAcaoOrdString());
                intent.putExtra("qtdAcaoPref", capital_lista.getQtdAcaoPrefString());
                intent.putExtra("vlrAcaoPref", capital_lista.getVlrAcaoPrefString());

                context.startActivity(intent);
            }
        });
        holder.btExcluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                if(getItemCount()>1){
                    new AlertDialog.Builder(context).setTitle("Excluindo Movimento").setMessage("Tem certeza que deseja excluir Movimento de Capital Social de "+capital_lista.getMesAnoCapitalString()+" ?").setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Activity activity = getActivity(v);
                                    Intent intent = activity.getIntent();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    intent.putExtra("idExcluirCapital",capital_lista.getIdCapitalSOcialString() );
                                    intent.putExtra("mesAnoExcluirCapital",capital_lista.getMesAnoCapitalString() );
                                    activity.finish();
                                    activity.startActivity(intent);
                                }
                            }).setNegativeButton("Não", null).show();
                }
                else {
                    Toast.makeText(context, "ERRO: Último Movimento de Capital Social", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return lCapitais.size();
    }

}
