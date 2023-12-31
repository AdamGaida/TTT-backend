package com.project.TTT.models.mct;

import com.project.TTT.models.ttt.TttBoard;
import com.project.TTT.models.uttt.UtttBoard;
import com.project.TTT.services.ttt.TttMethodes;
import com.project.TTT.services.uttt.UtttMethodes;


import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    UtttMethodes utttMethodes;
    TttMethodes tttMethodes;
    TttBoard tttBoard;
    UtttBoard utttBoard;
    boolean isTerminal;
    boolean isFullyExpanded;
    TreeNode parent;
    int visits;
    double score;
    Map<String,TreeNode> children;

    public TreeNode(UtttBoard utttBoard, TreeNode parent) {
        this.utttBoard = utttBoard;
        this.isTerminal = utttMethodes.isWin(utttBoard.getMainBoard(),utttBoard.getPlayer1()) || utttMethodes.isDraw(utttBoard.getMainBoard(), utttBoard.getEmptySquare());
        this.isFullyExpanded = this.isTerminal;
        this.parent = parent;
        this.visits = 0;
        this.score = 0;
        this.children = new HashMap<>();
    }
    public TreeNode(TttBoard tttBoard, TreeNode parent) {
        this.tttBoard = tttBoard;
//        this.isTerminal = tttMethodes.isWin(tttBoard.getBoard(),tttBoard.getPlayer1) || tttMethodes.isDraw(tttBoard.mainBoard);
        this.isFullyExpanded = this.isTerminal;
        this.parent = parent;
        this.visits = 0;
        this.score = 0;
        this.children = new HashMap<>();
    }
}
