package com.project.pojo;

import com.project.common.constants.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {
    private Integer submissionId;
    private Integer reportId;
    private Integer studentId;
    private Integer moduleId;
    private LocalDateTime submissionDueDate;
    private ReportStatus reportStatus;
    private String reportType;
    private Double reportResult;
    private LocalDateTime submittedAt;
    private LocalDateTime markedAt;
    private Integer firstMarker;
    private Integer secondMarker;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
