package com.ccnlthd.course_management.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BatchEnrollmentRequest {

    @NotNull(message = "studentId không được rỗng")
    @Positive(message = "studentId phải lớn hơn 0")
    private Long studentId;

    @NotEmpty(message = "Danh sách khóa học không được rỗng")
    private List<@NotNull @Positive Long> courseIds;
}
