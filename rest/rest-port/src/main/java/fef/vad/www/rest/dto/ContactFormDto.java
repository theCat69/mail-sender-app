package fef.vad.www.rest.dto;

import java.util.List;

public record ContactFormDto(String name, String email, String message, List<String> files) {}
