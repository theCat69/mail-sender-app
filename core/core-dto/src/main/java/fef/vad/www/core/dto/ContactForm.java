package fef.vad.www.core.dto;

import java.util.List;

public record ContactForm(String name, String email, String message, List<FileDomain> files) {}