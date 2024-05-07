package com.project.pojo;

import com.project.common.constants.ReportStatus;
import com.project.common.constants.ReportType;
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
    private Integer moduleId;
    private Integer studentId;
    private LocalDateTime submissionDueDate;
    private ReportStatus reportStatus;
    private ReportType reportType;
    private Double reportResult;
    private LocalDateTime submittedAt;
    private LocalDateTime markedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
