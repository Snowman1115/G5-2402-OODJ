package com.project.pojo;

import com.project.common.constants.ConsultationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {
    private Integer consultationId;
    private Integer lecturerId;
    private Integer studentId;
    private LocalDateTime consultationDateTime;
    private ConsultationStatus consultationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
