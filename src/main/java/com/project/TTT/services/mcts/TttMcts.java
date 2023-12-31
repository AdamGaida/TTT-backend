package com.project.TTT.services.mcts;


import com.project.TTT.models.mct.TreeNode;
import com.project.TTT.models.boards.TttBoard;
import com.project.TTT.services.boards.TttMethods;

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

    private int rollout(TttBoard board) {
        int i = 0;
        while (!methods.isWin(board) && !methods.isDraw(board)) {
            try {
                List<int[]> legalStates = methods.generateStates(board);
                int[] state = legalStates.get(random.nextInt(legalStates.size())); // You need to implement this method
                methods.makeMove(state[0], state[1], board);
                i++;
            } catch (Exception e) {
                return 0;
            }
            if (methods.isWin(board)) {
                return board.getPlayer2() == 'o' ? 1 : -1;
            } else {
                return 0;
            }
        }

        // Return score from the player "x" perspective
        if (board.getPlayer2() == 'x') {
            return 1;
        } else if (board.getPlayer2() == 'o') {
            return -1;
        }

        return 0;
    }
/// TODO : complete the methods 
    public TreeNode expand(TreeNode node) {
        List<int[]> legalMoves = methods.generateStates(node.getTttBoard());
        for (int[] move : legalMoves) {
            TttBoard newState = methods.makeMove(move[0], move[1], node.getTttBoard());

        }
        return null;
    }

    private TreeNode getBestMove(TreeNode root, int i) {
        return null;
    }

    private void backPropagate(TreeNode node, int score) {
    }



}
