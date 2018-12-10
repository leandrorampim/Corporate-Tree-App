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


public class EmpresaAdapter extends RecyclerView.Adapter {
    private List<Empresa> lEmpresas;
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

    public EmpresaAdapter(List<Empresa> empresa, Context context) {
        this.lEmpresas = empresa;
        this.context = context;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View empresaView = inflater.inflate(R.layout.item_empresa, parent, false);

        EmpresaViewHolder holder = new EmpresaViewHolder(empresaView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        EmpresaViewHolder holder = (EmpresaViewHolder) viewHolder;

        final Empresa empresa_lista  = lEmpresas.get(i);
        holder.nome.setText(empresa_lista.getNomeEmpresa());
        holder.id.setText(empresa_lista.getIdEmpresa());
        holder.cnpj.setText(empresa_lista.getCNPJ());
        holder.tipo.setText(empresa_lista.getTipoFrase());
        holder.btEditar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, FRMAltera_Empresa.class);
                intent.putExtra("codigo", empresa_lista.getIdEmpresa());
                context.startActivity(intent);
            }
        });
        holder.btExcluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                new AlertDialog.Builder(context).setTitle("Excluindo Empresa").setMessage("Tem certeza que deseja excluir "+empresa_lista.getNomeEmpresa()+" - CNPJ "+empresa_lista.getCNPJ()+" ?").setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Activity activity = getActivity(v);
                                Intent intent = activity.getIntent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.putExtra("idExcluir",empresa_lista.getIdEmpresa() );
                                activity.finish();
                                activity.startActivity(intent);
                            }
                        }).setNegativeButton("Não", null).show();
            }
        });
        holder.btCapitalSocial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context,FRMCapitalSocial.class);
                intent.putExtra("idEmpresa",empresa_lista.getIdEmpresa() );
                intent.putExtra("nomeEmpresa",empresa_lista.getNomeEmpresa());
                intent.putExtra("cnpjEmpresa",empresa_lista.getCNPJ());
                intent.putExtra("tipoEmpresa",empresa_lista.getTipoFrase());
                context.startActivity(intent);}
        });
    }

    @Override
    public int getItemCount() {
        return lEmpresas.size();
    }




}




