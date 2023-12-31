package com.project.TTT.models.mct;

import com.project.TTT.models.ttt.TttBoard;
import com.project.TTT.models.uttt.UtttBoard;
import com.project.TTT.services.ttt.TttMethods;
import com.project.TTT.services.uttt.UtttMethods;
import lombok.Data;


import java.util.HashMap;
import java.util.Map;
@Data
public class TreeNode {
    UtttMethods utttMethods;
    TttMethods tttMethods;
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
        this.isTerminal = utttMethods.isWin(utttBoard.getMainBoard(),utttBoard.getPlayer1()) || utttMethods.isDraw(utttBoard.getMainBoard(), utttBoard.getEmptySquare());
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
