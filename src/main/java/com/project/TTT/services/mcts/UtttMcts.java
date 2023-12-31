package com.project.TTT.services.mcts;
import com.project.TTT.models.mct.TreeNode;
import com.project.TTT.models.boards.UtttBoard;
import com.project.TTT.services.boards.UtttMethods;

import java.util.List;
import java.util.Random;

public class UtttMcts {
    private UtttMethods utttMethods;
    private TreeNode root;
    private Random random = new Random();

    public TreeNode search(UtttBoard initialState) {
        root = new TreeNode(initialState, null);
        for (int i = 0; i < 1000; i++) {
            TreeNode node = select(root);
            int score = rollout(node.board);
            backpropagate(node, score);
        }
        return getBestMove(root, 0);
    }

    private TreeNode select(TreeNode node) {
        while (!node.getIsTerminal) {
            if (node.getIsFullyExpanded) {
                node = getBestMove(node, 2);
            } else {
                return expand(node);
            }
        }
        return node;
    }

    private TreeNode expand(TreeNode node) {
        List<int[]> legalMoves = utttMethods.generateStates(node.utttBoard);
        for (int[] move : legalMoves) {
            UtttBoard newState = utttMethods.makeMove(node.utttBoard,move[0], move[1], move[2], move[3]);
            String key = newState.getSubBoardsString(); // assuming this method returns the string representation
            if (!node.children.containsKey(key)) {
                TreeNode newNode = new TreeNode(newState, node);
                node.children.put(key, newNode);
                if (legalMoves.size() == node.children.size()) {
                    node.isFullyExpanded = true;
                }
                return newNode;
            }
        }
        return null; // or handle this case appropriately
    }

    private int rollout(UTTTBoard board) {
        int i = 0;
        while (!board.isWin(board.getMainBoard()) && !board.isDraw(board.getMainBoard()) && i <= 100) {
            try {
                int[] state = board.randomState(); // You need to implement this method
                board.makeMove(state[0], state[1], state[2], state[3]);
                i++;
            } catch (Exception e) {
                return 0;
            }
        }
        if (board.isWin(board.getMainBoard())) {
            return board.getPlayer2().equals('o') ? 1 : -1;
        } else {
            return 0;
        }
    }

    private void backpropagate(TreeNode node, int score) {
        while (node != null) {
            node.visits++;
            node.score += score;
            node = node.parent;
        }
    }

    private TreeNode getBestMove(TreeNode node, int explorationConstant) {
        double bestScore = Double.NEGATIVE_INFINITY;
        List<TreeNode> bestMoves = new ArrayList<>();
        for (TreeNode childNode : node.children.values()) {
            int currentPlayer = childNode.board.getPlayer2() == 'x' ? 1 : -1;
            double moveScore = currentPlayer * ((double) childNode.score / childNode.visits) +
                    explorationConstant * Math.sqrt(Math.log(node.visits) / childNode.visits);
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
