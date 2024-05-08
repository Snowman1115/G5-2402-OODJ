package com.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FinalYearReport extends Report {
    @Override
    public String toString() {
        return "FinalYearReport{" +
                "reportId=" + reportId +
                ", reportName='" + reportName + '\'' +
                ", reportType=" + reportType +
                ", reportPath='" + reportPath + '\'' +
                '}';
    }
}
