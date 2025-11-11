package com.mohsenko.e_commerce_mohsenko.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // excludes the field from JSON output when its value is null (e.g., omit validationErrors if not present)
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime timestamp; // ex: "2025-11-10T10:30:45+02:00"
    private Integer status;
    private String error;
    private String message;
    private String path;
    private List<ValidationError> errors;

    /* ValidationError doesn't need access to ErrorResponse fields. So, why we declared it as a nested and static?
    as a nested: for logical grouping as it is used only with ErrorResponse, and avoiding creating a separate small class.
    as a static: it doesn’t need access to ErrorResponse fields, so we don't have to declare it as non-static.
    and as dealing with non-static inner classes is complex and has bad performance, static is preferred.
    search "non-static inner classes drawbacks" to find more details. */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
    }
}

