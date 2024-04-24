package com.project.pojo;

import com.project.common.constants.PresentationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Presentation {
    private Integer presentationId;
    private Integer moduleId;
    private Integer lecturerId;
    private Integer studentId;
    private LocalDateTime presentationDueDate;
    private LocalDateTime presentationDateTime;
    private PresentationStatus presentationStatus;
    private Double presentationResult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
