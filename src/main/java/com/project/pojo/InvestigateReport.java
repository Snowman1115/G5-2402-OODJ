package com.project.pojo;

import com.project.common.constants.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestigateReport {
    private Integer reportId;
    private ReportType reportType;
    private String reportName;
    private String reportFilePath;
}
