package com.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class InvestigateReport extends Report {
    @Override
    public String toString() {
        return "InvestigateReport{" +
                "reportId=" + reportId +
                ", reportName='" + reportName + '\'' +
                ", reportType=" + reportType +
                ", reportPath='" + reportPath + '\'' +
                '}';
    }
}
