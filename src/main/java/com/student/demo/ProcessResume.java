package com.student.demo;

import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ProcessResume {
    public String extractName(String content) {
        String[] lines = content.split("\\r?\\n");

        for (int i = 0; i < Math.min(5, lines.length); i++) {
            if (lines[i].matches("[A-Za-z ]+")) {
                return lines[i].trim();
            }
        }
        return "Not Found";
    }
    public String extractEmail(String content) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}");
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : "Not Found";
    }

}
