package com.project.pojo;

import com.project.common.constants.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer reportId;
    private ReportType reportType;
    private String reportName;
    private String reportFilePath;
}
