package santander_tec.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.lang.String.format;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private final HttpStatus statusErrorResponse;

	public JwtAuthenticationFailureHandler(HttpStatus statusErrorResponse) {
		this.statusErrorResponse = statusErrorResponse;
	}

	public JwtAuthenticationFailureHandler() {
		this.statusErrorResponse = HttpStatus.UNAUTHORIZED;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException {
		response.setStatus(statusErrorResponse.value());
		response.setContentType("application/json");
		response.getWriter().append(jsonResponse());
	}

	private String jsonResponse() {
		long date = new Date().getTime();
		return format("{\"timestamp\": %s, \"status\": %s, \"error\": \"Unauthorized\", \"message\": \"Authentication failed: bad credentials\", \"path\": \"/login\"}", date, statusErrorResponse.value());
	}
}