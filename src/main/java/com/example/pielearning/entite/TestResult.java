package com.example.pielearning.entite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {
    private int score;
    private boolean passed;
    private String certificateUrl;
}

