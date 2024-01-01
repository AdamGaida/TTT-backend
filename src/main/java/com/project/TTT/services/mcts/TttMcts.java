package com.project.TTT.services.mcts;


import com.project.TTT.models.mct.TreeNode;
import com.project.TTT.models.boards.TttBoard;
import com.project.TTT.services.boards.TttMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TttMcts {
    TttMethods methods;
    TreeNode root;
    private final Random random = new Random();

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
            backPropagate(node, score);
        }

        // pick up the best move in the current position
        try {
            return getBestMove(this.root, 0);
        } catch (Exception e) {
            return null;
        }
    }

    public TreeNode select(TreeNode node) {
        //node is not terminal
        while (!Objects.requireNonNull(node).isTerminal()) {
            if (node.isFullyExpanded()) {
                node = getBestMove(node, 2);
            } else {
                return expand(node);
            }
        }
        return node;
    }

    public TreeNode expand(TreeNode node) {
        List<TttBoard> states = methods.generateStates(node.getTttBoard());
        for (TttBoard state : states) {
            if ( !node.getChildren().containsKey(state.getPosition().toString())){
                TreeNode newNode = new TreeNode(state, node);
                node.getChildren().replace(state.getPosition().toString(), newNode);
                if (states.size() == node.getChildren().size()){
                    node.setFullyExpanded(true);
                }
                return newNode;
            }
        }
        return null;
    }
    private int rollout(TttBoard board) {
        while (!methods.isWin(board)){
            try {
                List<TttBoard> possibleStates = methods.generateStates(board);
                board = possibleStates.get(random.nextInt(possibleStates.size()));


            }
            catch(Exception e){
                return 0;
            }
        }
        if (board.getPlayer2() == 'x') return 1;
        else if(board.getPlayer2() == 'o') return -1;
        return 0;
    }

    private void backPropagate(TreeNode node, int score) {
        while (node != null){
            node.setVisits(node.getVisits()+1);
            node.setScore(node.getScore()+score);
            node = node.getParent();
        }
    }

    private TreeNode getBestMove(TreeNode root, int explorationConstant) {
        double  bestScore = Double.NEGATIVE_INFINITY;
        List<TreeNode> bestMoves = new ArrayList<>();
        int currentPlayer=0;
        for (TreeNode childNode : root.getChildren().values()){
            if (root.getTttBoard().getPlayer2() == 'x'){
                currentPlayer = 1;
            }
            else if(root.getTttBoard().getPlayer2() == 'o'){
                currentPlayer = -1;
            }
            double moveScore = currentPlayer * childNode.getScore()/childNode.getVisits() + explorationConstant *
                    Math.sqrt(Math.log((double) root.getVisits() /childNode.getVisits()));
            if (moveScore>bestScore){
                bestScore = moveScore;
                bestMoves.clear();
                bestMoves.add(childNode);
            } else if (moveScore == bestScore) {
                bestMoves.add(childNode);
            }

        }
        return  bestMoves.get(random.nextInt(bestMoves.size()));
    }



}
