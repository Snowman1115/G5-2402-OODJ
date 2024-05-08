package com.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Capstone1Report extends Report {
    @Override
    public String toString() {
        return "Capstone1Report{" +
                "reportId=" + reportId +
                ", reportName='" + reportName + '\'' +
                ", reportType=" + reportType +
                ", reportPath='" + reportPath + '\'' +
                '}';
    }
}
