package ihc.fatecsp.corporatetree;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class FRMArvore extends AppCompatActivity {
    private int nodeCount = 1;
    public static BancoController crud;
    String idEmpresa;
    Integer mesAno;
    Integer niveis;
    String sentido;
    Integer orientacao;

    Cursor c;
    Cursor r;
    Node node1;
    Node node2;
    ArrayList<String> nodos = new ArrayList<>();
    ArrayList<String> nodos_aux = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmarvore);
        GraphView graphView = findViewById(R.id.graph);
        Intent intent = getIntent();
        if(intent.hasExtra("id")) {
            Bundle extras = getIntent().getExtras();
            idEmpresa = extras.getString("id");
            String mesAnoString = extras.getString("mesano");
            String niveisString = extras.getString("niveis");
            String sentido = extras.getString("sentido");
            mesAno = Integer.parseInt(mesAnoString);
            niveis = Integer.parseInt(niveisString);
            orientacao = Integer.parseInt(sentido);
        }

        final Graph graph = new Graph();
        crud = new BancoController(getBaseContext());
        r = crud.carregaDadosEmpresa(Integer.parseInt(idEmpresa));
        node1 = new Node(r.getString(r.getColumnIndex(CriaBanco.NOME_EMPRESA)));
        graph.addNode(node1);
        nodos_aux.add(idEmpresa);
        if(orientacao==1) {
            for (int i = 0; i < niveis; i++) {
                nodos = new ArrayList<>(nodos_aux);
                nodos_aux.clear();
                for (int k = 0; k < nodos.size(); k++) {
                    r = crud.carregaDadosEmpresa(Integer.parseInt(nodos.get(k)));
                    node1 = new Node(r.getString(r.getColumnIndex(CriaBanco.NOME_EMPRESA)));
                    c = crud.carregaDadosInvestimentoInvestidora(nodos.get(k));
                    if (c.getCount() > 0) {
                        do {
                            String idInvestida = c.getString(c.getColumnIndex(CriaBanco.ID_INVESTIDA));
                            nodos_aux.add(idInvestida);
                            r = crud.carregaDadosEmpresa(Integer.parseInt(idInvestida));
                            String nomeInvestida = r.getString(r.getColumnIndex(CriaBanco.NOME_EMPRESA));
                            node2 = new Node(nomeInvestida);
                            graph.addEdge(node1, node2);
                        } while (c.moveToNext());
                    }
                }
            }
        }
        else{
            for (int i = 0; i < niveis; i++) {
                nodos = new ArrayList<>(nodos_aux);
                nodos_aux.clear();
                for (int k = 0; k < nodos.size(); k++) {
                    r = crud.carregaDadosEmpresa(Integer.parseInt(nodos.get(k)));
                    node1 = new Node(r.getString(r.getColumnIndex(CriaBanco.NOME_EMPRESA)));
                    c = crud.carregaDadosInvestimentoInvestida(nodos.get(k));
                    if (c.getCount() > 0) {
                        do {
                            String idInvestidora = c.getString(c.getColumnIndex(CriaBanco.ID_INVESTIDORA));
                            nodos_aux.add(idInvestidora);
                            r = crud.carregaDadosEmpresa(Integer.parseInt(idInvestidora));
                            String nomeInvestidora = r.getString(r.getColumnIndex(CriaBanco.NOME_EMPRESA));
                            node2 = new Node(nomeInvestidora);
                            graph.addEdge(node1, node2);
                        } while (c.moveToNext());
                    }
                }
            }
        }


        // you can set the graph via the constructor or use the adapter.setGraph(Graph) method
        final BaseGraphAdapter<ArvoreViewHolder> adapter = new BaseGraphAdapter<ArvoreViewHolder>(this, R.layout.node, graph) {

            @Override
            public ArvoreViewHolder onCreateViewHolder(View view) {
                return new ArvoreViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ArvoreViewHolder viewHolder, Object data, int position) {
                viewHolder.mTextView.setText(data.toString());
            }

        };
        graphView.setAdapter(adapter);

        // set the algorithm here
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(orientacao)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
    }

    private String getNodeText() {
        return "Node " + nodeCount++;
    }
}
