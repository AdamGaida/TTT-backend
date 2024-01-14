package com.project.TTT.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OnePlayerResponse {
    private String[][] board;
    private String value;
    private int[] aiMove;
}
