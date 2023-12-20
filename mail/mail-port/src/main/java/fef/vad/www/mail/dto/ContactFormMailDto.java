package fef.vad.www.mail.dto;

import java.util.List;

public record ContactFormMailDto(String name, String email, String message, List<String> files) {}
