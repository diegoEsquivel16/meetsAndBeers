package santander_tec.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private static final String HEADER_AUTHORIZATION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer";
	private final UserDetailsService userDetailsService;

	public JwtAuthorizationFilter(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = httpServletRequest.getHeader(HEADER_AUTHORIZATION_KEY);

		if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader
				.startsWith(TOKEN_BEARER_PREFIX)) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		final String token = authorizationHeader.replace(TOKEN_BEARER_PREFIX + " ", "");

		String userName = TokenProvider.getUserName(token);
		UserDetails user = userDetailsService.loadUserByUsername(userName);

		UsernamePasswordAuthenticationToken authenticationToken = TokenProvider.getAuthentication(token, user);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}