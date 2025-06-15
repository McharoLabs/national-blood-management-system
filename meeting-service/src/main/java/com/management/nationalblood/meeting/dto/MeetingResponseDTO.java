package com.management.nationalblood.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingResponseDTO<T> {
    private Instant timestamp;
    private int status;
    private boolean success;
    private String message;
    private T data;
    private String path;

    public static <T> MeetingResponseDTO<T> ok(T data, String message, String path) {
        return new MeetingResponseDTO<>(Instant.now(), 200, true, message, data, path);
    }

    public static <T> MeetingResponseDTO<T> error(int status, String message, String path) {
        return new MeetingResponseDTO<>(Instant.now(), status, false, message, null, path);
    }
}
