package controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req,
			             ServletResponse resp,
			             FilterChain filterChain)
			throws IOException, ServletException {

		var httpReq = (HttpServletRequest) req;
		var httpResp = (HttpServletResponse) resp;
		System.out.println("Filtro: " + httpReq.getRequestURI());

		boolean userIsLogged =
				httpReq.getSession().getAttribute("user_logged") != null;

		String path = httpReq.getRequestURI()
				.substring(httpReq.getContextPath().length());

		boolean isPublicResource =
				path.endsWith(".css") ||
				path.endsWith(".js");

		boolean isLogin = path.equals("/login") || path.equals("/login.jsp");

		if (userIsLogged || isLogin || isPublicResource) {
			filterChain.doFilter(httpReq, httpResp);
		} else {
			httpResp.sendRedirect(httpReq.getContextPath() + "/login.jsp");
		}
	}
}