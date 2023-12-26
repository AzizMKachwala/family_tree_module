package com.example.familytreeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import dev.bandb.graphview.AbstractGraphAdapter;
import dev.bandb.graphview.graph.Graph;
import dev.bandb.graphview.graph.Node;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerConfiguration;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerLayoutManager;
import dev.bandb.graphview.layouts.tree.TreeEdgeDecoration;

public class FamilyTreeFragment extends Fragment {

    RecyclerView recyclerView;
    AbstractGraphAdapter<NodeViewHolder> adapter;
    FloatingActionButton floatingActionButton;
    MyDataBaseHandler myDataBaseHandler;
    Node currentNode;
    int nodeCount = 1;

    protected Graph createGraph() {
        Graph graph = new Graph();

        return graph;
    }

    protected void setLayoutManager() {
        BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(100)
                .setSubtreeSeparation(100)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        recyclerView.setLayoutManager(new BuchheimWalkerLayoutManager(getContext(), configuration));
    }

    protected void setEdgeDecoration() {
        recyclerView.addItemDecoration(new TreeEdgeDecoration());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_family_tree, container, false);

        Graph graph = createGraph();
        recyclerView = view.findViewById(R.id.recycler);
        setLayoutManager();
        setEdgeDecoration();

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
                Tools.DisplayImage(getContext(), holder.imgProfile, myDataBaseHandler.getAllMembers().get(position).userImage);
            }
        };

        adapter.submitGraph(graph);
        recyclerView.setAdapter(adapter);
        
        floatingActionButton = view.findViewById(R.id.addNode);
        floatingActionButton.setOnClickListener(view1 -> {

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

        floatingActionButton.setOnLongClickListener(view1 -> {
            if (currentNode != null) {
                graph.removeNode(currentNode);
                currentNode = null;
                adapter.notifyDataSetChanged();
                floatingActionButton.hide();
            }
            return true;
        });

        myDataBaseHandler = new MyDataBaseHandler(getContext());

        return view;
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

                Toast.makeText(getContext(), "Clicked on " + Objects.requireNonNull(adapter.getNodeData(getBindingAdapterPosition())).toString(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    protected String getNodeText() {
        return "Node " + nodeCount++;
    }

}