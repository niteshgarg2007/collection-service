package com.hcl.igovern.security;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.springframework.stereotype.Component;

@Component
public class SafeHtml {
	
	private SafeHtml() {	
		
	}

	public static String sanitize(String raw) {
		if (raw == null || raw.length() == 0)
			return raw;

		return escapeHtml(raw);
	}
	
	public static String escapeHtml(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(
                aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;"); 
            } else if (character == '(') {
                result.append("&#40;");
            } else if (character == ')') {
                result.append("&#41;");
            } 
            else if (character == '&') {
                result.append("&amp;");
            } else if (character == '+') {
                result.append("&#43;");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }
}
