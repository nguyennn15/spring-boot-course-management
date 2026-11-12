package com.ccnlthd.course_management.controller;

import com.ccnlthd.course_management.dto.request.CourseRequest;
import com.ccnlthd.course_management.dto.response.CourseFeeResponse;
import com.ccnlthd.course_management.dto.response.CourseResponse;
import com.ccnlthd.course_management.service.CourseService;
import com.ccnlthd.course_management.service.impl.CourseFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Course", description = "Quản lý khóa học")
public class CourseController {

    private final CourseService courseService;
    private final CourseFeeService courseFeeService;

    @PostMapping
    @Operation(summary = "Tạo khóa học", description = "Tạo khóa học trong danh mục đã tồn tại.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tạo thành công",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu khóa học không hợp lệ",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục (CATEGORY_NOT_FOUND)",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Lỗi không mong đợi",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class)))
    })
    public ResponseEntity<CourseResponse> createCourse(
            @Valid @RequestBody CourseRequest request
    ) {
        CourseResponse response = courseService.createCourse(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem chi tiết khóa học", description = "Tìm khóa học theo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tìm thấy khóa học",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy khóa học (COURSE_NOT_FOUND)",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Lỗi không mong đợi",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class)))
    })
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping
    @Operation(summary = "Xem danh sách khóa học", description = "Trả về tất cả khóa học.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Lỗi không mong đợi",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class)))
    })
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật khóa học", description = "Sửa thông tin khóa học và danh mục của khóa học.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu khóa học không hợp lệ",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy khóa học (COURSE_NOT_FOUND) hoặc danh mục (CATEGORY_NOT_FOUND)",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Lỗi không mong đợi",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class)))
    })
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request
    ) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa khóa học", description = "Chỉ xóa khóa học chưa có lượt ghi danh.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Xóa thành công, không có nội dung trả về"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy khóa học (COURSE_NOT_FOUND)",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "Khóa học có ít nhất một lượt ghi danh: COURSE_HAS_ENROLLMENTS; giữ nguyên khóa học và các lượt ghi danh. "
                    + "Vi phạm ràng buộc CSDL, kể cả khi ghi đồng thời: DATA_CONFLICT.",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class),
                            examples = {
                                    @ExampleObject(name = "COURSE_HAS_ENROLLMENTS", value = "{\"code\":\"COURSE_HAS_ENROLLMENTS\",\"message\":\"Course has enrollments\",\"result\":null,\"timestamp\":\"2026-10-15T10:00:00\"}"),
                                    @ExampleObject(name = "DATA_CONFLICT", value = "{\"code\":\"DATA_CONFLICT\",\"message\":\"Data conflicts with an existing record\",\"result\":null,\"timestamp\":\"2026-10-15T10:00:00\"}")
                            })),
            @ApiResponse(responseCode = "500", description = "Lỗi không mong đợi",
                    content = @Content(schema = @Schema(implementation = com.ccnlthd.course_management.dto.response.ApiResponse.class)))
    })
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/calculate-fee")
    @Operation(summary = "Tính học phí theo chính sách")
    public ResponseEntity<com.ccnlthd.course_management.dto.response.ApiResponse<CourseFeeResponse>> calculateFee(@PathVariable("id") Long id, @RequestParam(name = "policy", defaultValue = "standard") String policy) {
        CourseFeeResponse result = courseFeeService.calculateFee(id, policy);

        return ResponseEntity.ok(
                new com.ccnlthd.course_management.dto.response.ApiResponse<>(
                        "SUCCESS",
                        "Tính toán học phí thành công",
                        result,
                        LocalDateTime.now()
                )
        );
    }
}
