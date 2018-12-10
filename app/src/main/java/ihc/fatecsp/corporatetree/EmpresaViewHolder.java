package ihc.fatecsp.corporatetree;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import static android.support.constraint.Constraints.TAG;

public class EmpresaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    final TextView id;
    final TextView nome;
    final TextView cnpj;
    final TextView tipo;
    final ImageButton btEditar;
    final ImageButton btExcluir;
    final ImageButton btCapitalSocial;

    public EmpresaViewHolder(@NonNull View itemView) {
        super(itemView);
        id = (TextView) itemView.findViewById(R.id.item_empresa_id);
        nome = (TextView) itemView.findViewById(R.id.item_empresa_nome);
        cnpj = (TextView) itemView.findViewById(R.id.item_empresa_cnpj);
        tipo = (TextView) itemView.findViewById(R.id.item_empresa_tipo);
        btEditar = (ImageButton) itemView.findViewById(R.id.editarEmpresa);
        btExcluir = (ImageButton) itemView.findViewById(R.id.excluirEmpresa);
        btCapitalSocial = (ImageButton) itemView.findViewById(R.id.capitalSocial);

    }
    public EmpresaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View empresaView = inflater.inflate(R.layout.item_empresa, parent, false);

        EmpresaViewHolder holder = new EmpresaViewHolder(empresaView);

        return holder;
    }

    public void onClick(View v) {
        Log.d(TAG,"Elemento "+getAdapterPosition() + "clicado.");
    }
}
