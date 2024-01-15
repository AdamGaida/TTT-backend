package com.project.TTT.services.mcts;
import com.project.TTT.models.mct.TreeNode;
import com.project.TTT.models.boards.UtttBoard;
import com.project.TTT.services.boards.UtttMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UtttMcts {
    private final UtttMethods utttMethods = new UtttMethods();
    private final Random random = new Random();

    public TreeNode search(UtttBoard initialState) {
        TreeNode root = new TreeNode(initialState, null);
        for (int i = 0; i < 1000; i++) {
            TreeNode node = select(root);
            int score = rollout(node.getUtttBoard());
            backpropagate(node, score);
        }
        return getBestMove(root, 0);
    }

    private TreeNode select(TreeNode node) {
        while (!node.isTerminal()) {
            if (node.isFullyExpanded()) {
                node = getBestMove(node, 2);
            } else {
                return expand(node);
            }
        }
        return node;
    }

    private TreeNode expand(TreeNode node) {
        List<int[]> legalMoves = utttMethods.generateStates(node.getUtttBoard());
        for (int[] move : legalMoves) {
            UtttBoard newState = utttMethods.makeMove(node.getUtttBoard(),move[0], move[1], move[2], move[3]);
            String key = Arrays.deepToString(newState.getSubBoards()); // assuming this method returns the string representation
            if (!node.getChildren().containsKey(key)) {
                TreeNode newNode = new TreeNode(newState, node);
                node.getChildren().put(key, newNode);
                if (legalMoves.size() == node.getChildren().size()) {
                    node.setFullyExpanded(true) ;
                }
                return newNode;
            }
        }
        return null; // or handle this case appropriately
    }

    private int rollout(UtttBoard board) {
        int i = 0;
        while (utttMethods.isWin(board.getMainBoard(),board.getPlayer2()) && utttMethods.isDraw(board.getMainBoard(),board.getEmptySquare()) && i <= 100) {
            try {
                List<int[]> legalStates = utttMethods.generateStates(board);
                int[] state = legalStates.get(random.nextInt(legalStates.size())); // You need to implement this method
                utttMethods.makeMove(board,state[0], state[1], state[2], state[3]);
                i++;
            } catch (Exception e) {
                return 0;
            }
        }
        if (utttMethods.isWin(board.getMainBoard(),board.getPlayer2())) {
            return board.getPlayer2().equals('o') ? 1 : -1;
        } else {
            return 0;
        }
    }

    private void backpropagate(TreeNode node, int score) {
        while (node != null) {
            node.setVisits(node.getVisits()+1);;
            node.setScore(node.getScore()+1);
            node = node.getParent();
        }
    }

    private TreeNode getBestMove(TreeNode node, int explorationConstant) {
        double bestScore = Double.NEGATIVE_INFINITY;
        List<TreeNode> bestMoves = new ArrayList<>();
        for (TreeNode childNode : node.getChildren().values()) {
            int currentPlayer = childNode.getUtttBoard().getPlayer2().equals('x')  ? 1 : -1;
            double moveScore = currentPlayer * ((double) childNode.getScore() / childNode.getVisits()) +
                    explorationConstant * Math.sqrt(Math.log(node.getVisits()) / childNode.getVisits());
            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestMoves.clear();
                bestMoves.add(childNode);
            } else if (moveScore == bestScore) {
                bestMoves.add(childNode);
            }
        }
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }
}
