package com.example.familytreeapp;

import dev.bandb.graphview.graph.Graph;
import dev.bandb.graphview.graph.Node;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerConfiguration;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerLayoutManager;
import dev.bandb.graphview.layouts.tree.TreeEdgeDecoration;

public class BuchheimWalkerActivity extends GraphActivity {

    Node currentNode;

    @Override
    protected Graph createGraph() {
        Graph graph = new Graph();

//        Node node1 = new Node(getNodeText());
//        Node node2 = new Node(getNodeText());
//        Node node3 = new Node(getNodeText());
//        Node node4 = new Node(getNodeText());
//        Node node5 = new Node(getNodeText());
//        Node node6 = new Node(getNodeText());
//        Node node8 = new Node(getNodeText());
//        Node node7 = new Node(getNodeText());
//        Node node9 = new Node(getNodeText());
//        Node node10 = new Node(getNodeText());
//        Node node11 = new Node(getNodeText());
//        Node node12 = new Node(getNodeText());
//        graph.addEdge(node1, node2);
//        graph.addEdge(node1, node3);
//        graph.addEdge(node1, node4);
//        graph.addEdge(node2, node5);
//        graph.addEdge(node2, node6);
//        graph.addEdge(node6, node7);
//        graph.addEdge(node6, node8);
//        graph.addEdge(node4, node9);
//        graph.addEdge(node4, node10);
//        graph.addEdge(node4, node11);
//        graph.addEdge(node11, node12);

        return graph;
    }

    @Override
    protected void setLayoutManager() {
        BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(100)
                .setSubtreeSeparation(100)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        recyclerView.setLayoutManager(new BuchheimWalkerLayoutManager(this, configuration));

    }

    @Override
    protected void setEdgeDecoration() {
        recyclerView.addItemDecoration(new TreeEdgeDecoration());
    }
}
