package com.ccnlthd.course_management.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegistrationSessionResponse(
        @JsonProperty("session1_id") String session1Id,
        @JsonProperty("session2_id") String session2Id,
        @JsonProperty("isDifferentInstance") boolean differentInstance
) {
}