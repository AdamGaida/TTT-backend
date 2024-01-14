package com.project.TTT.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class TwoPlayerResponse {
    private String[][] board;
    private String value;

}
