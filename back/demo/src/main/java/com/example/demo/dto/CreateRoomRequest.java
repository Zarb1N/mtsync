package com.example.demo.dto;

import lombok.Data;

@Data
public class CreateRoomRequest {
    String sessionId;
    String name;
    String previewUrl;
    Long playlistId;
}
