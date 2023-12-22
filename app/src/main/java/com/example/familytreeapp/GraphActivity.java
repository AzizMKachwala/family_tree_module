package com.example.familytreeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import dev.bandb.graphview.AbstractGraphAdapter;
import dev.bandb.graphview.graph.Graph;
import dev.bandb.graphview.graph.Node;

public abstract class GraphActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected AbstractGraphAdapter<NodeViewHolder> adapter;

    private FloatingActionButton floatingActionButton;
    MyDataBaseHandler myDataBaseHandler;
    private Node currentNode;
    private int nodeCount = 1;
    List<MyDbDataModelFamily> myDbDataModelFamilies;

    protected abstract Graph createGraph();

    protected abstract void setLayoutManager();

    protected abstract void setEdgeDecoration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Graph graph = createGraph();
        recyclerView = findViewById(R.id.recycler);
        setLayoutManager();
        setEdgeDecoration();

        setupGraphView(graph);
        setupFab(graph);
        setupToolbar();

        myDataBaseHandler = new MyDataBaseHandler(GraphActivity.this);
    }

    private void setupToolbar() {
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupFab(Graph graph) {
        floatingActionButton = findViewById(R.id.addNode);
        floatingActionButton.setOnClickListener(view -> {

//            Intent intent = new Intent(GraphActivity.this, AddNewMemberActivity.class);
//            startActivity(intent);

            Node newNode = new Node(getNodeText());
            if (currentNode != null) {
                graph.addEdge(currentNode, newNode);
            } else {
                graph.addNode(newNode);
            }
            adapter.notifyDataSetChanged();
        });

        floatingActionButton.setOnLongClickListener(view -> {
            if (currentNode != null) {
                graph.removeNode(currentNode);
                currentNode = null;
                adapter.notifyDataSetChanged();
                floatingActionButton.hide();
            }
            return true;
        });
    }

    private void setupGraphView(Graph graph) {
        adapter = new AbstractGraphAdapter<NodeViewHolder>() {
            @Override
            public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node_item, parent, false);
                return new NodeViewHolder(view);
            }

            @Override
            public void onBindViewHolder(NodeViewHolder holder, int position) {
                holder.txtName.setText(myDataBaseHandler.getAllMembers().get(position).userName);
                holder.txtDob.setText(myDataBaseHandler.getAllMembers().get(position).userDob);
                Tools.DisplayImage(GraphActivity.this, holder.imgProfile, myDataBaseHandler.getAllMembers().get(position).userImage);
            }
        };

        adapter.submitGraph(graph);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    class NodeViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDob;
        ImageView imgProfile;

        NodeViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtDob = itemView.findViewById(R.id.txtDob);
            imgProfile = itemView.findViewById(R.id.imgProfile);

            itemView.setOnClickListener(view -> {
                if (!floatingActionButton.isShown()) {
                    floatingActionButton.show();
                }
                currentNode = adapter.getNode(getBindingAdapterPosition());
//                Snackbar.make(itemView, "Clicked on " + Objects.requireNonNull(adapter.getNodeData(getBindingAdapterPosition())).toString(), Snackbar.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(), "Click " + Objects.requireNonNull(adapter.getNodeData(getBindingAdapterPosition())).toString(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    protected String getNodeText() {
        return "Node " + nodeCount++;
    }

}