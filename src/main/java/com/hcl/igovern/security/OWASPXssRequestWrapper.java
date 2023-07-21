package com.hcl.igovern.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

public class OWASPXssRequestWrapper extends HttpServletRequestWrapper {

	Logger logger = LoggerFactory.getLogger(OWASPXssRequestWrapper.class);

	public OWASPXssRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return values;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			String value = decryptParamValues(values[i]);
			encodedValues[i] = stripXSS(value);
		}

		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		value = decryptParamValues(value);
		return stripXSS(value);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}
		return stripXSS(value);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> raw = super.getParameterMap();
		if (raw == null)
			return null;

		Iterator<String> ksItr = raw.keySet().iterator();
		Map<String, String[]> encodedMap = new HashMap<>();
		while (ksItr.hasNext()) {
			String key = ksItr.next();
			String[] rawVals = raw.get(key);
			String[] snzVals = new String[rawVals.length];
			for (int i = 0; i < rawVals.length; i++) {
				snzVals[i] = stripXSS(rawVals[i]);
			}
			encodedMap.put(key, snzVals);
		}
		return encodedMap;
	}

	private String stripXSS(String value) {
		if (value != null) {
			try {
				// Avoid script/encoded attacks
				value = ESAPI.encoder().canonicalize(value);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				value = "";
			}

			// Avoid null characters
			value = value.replace("\0", "");

			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// avoid onXX= expression
			scriptPattern = Pattern.compile("on.*(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Sanitize HTML special characters
			value = SafeHtml.sanitize(value);

			// Strip double quotes from the string
			if (value.contains("\"")) {
				value = value.replace("\"", "");
			}
		}
		return value;
	}

	private String decryptParamValues(String value) {
		String paramVal = null;
		try {
			if (value != null && !value.isEmpty()) {
				paramVal = HtmlUtils.htmlUnescape(value);
				if (paramVal.contains(" ")) {
					paramVal = paramVal.replace(" ", "+");
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return paramVal;
	}
}
