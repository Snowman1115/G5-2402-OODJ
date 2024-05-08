package com.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Capstone2Report extends Report {
    @Override
    public String toString() {
        return "Capstone2Report{" +
                "reportId=" + reportId +
                ", reportName='" + reportName + '\'' +
                ", reportType=" + reportType +
                ", reportPath='" + reportPath + '\'' +
                '}';
    }
}
