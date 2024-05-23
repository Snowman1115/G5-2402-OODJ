package com.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleFeedback {
    private Integer feedbackId;
    private Integer moduleId;
    private Integer studentId;
    private String comments;
}
