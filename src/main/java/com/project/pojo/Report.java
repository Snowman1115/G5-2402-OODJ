package com.project.pojo;

import com.project.common.constants.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    protected Integer reportId;
    protected String reportName;
    protected ReportType reportType;
    protected String reportPath;
}
