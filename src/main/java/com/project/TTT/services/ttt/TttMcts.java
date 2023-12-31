package com.project.TTT.services.ttt;


import com.project.TTT.models.mct.TreeNode;
import com.project.TTT.models.ttt.TttBoard;

import java.util.Objects;
import java.util.Random;

public class TttMcts {
    TreeNode root;
    private Random random = new Random();

    // search for the best move in the current position
    public TreeNode search(TttBoard initialBoard) {
        // create root node
        this.root = new TreeNode(initialBoard, null);

        // walk through 1000 iterations
        for (int iteration = 0; iteration < 1000; iteration++) {
            // select a node (selection phase)
            TreeNode node = select(this.root);

            // score current node (simulation phase)
            int score = rollout(node.getTttBoard());

            // backpropagation results
            backpropagate(node, score);
        }

        // pick up the best move in the current position
        try {
            return getBestMove(this.root, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private TreeNode expand(TreeNode node){
        return null;
    }
    private TreeNode select(TreeNode node){
        //node is not terminal
        while (!Objects.requireNonNull(node).isTerminal()){
            if (node.isFullyExpanded()) {
                node = getBestMove(node, 2);
            } else {
                return expand(node);
            }
        }
        return node;
    }
    private int rollout(TttBoard board){

        return 0;
    }
    private void backpropagate(TreeNode node, int score){

    }
    private TreeNode getBestMove(TreeNode node , int explorationConstant){
        return null;
    }


}
