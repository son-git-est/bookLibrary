package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/BookBOServlet", "/OrderBOServlet", "/OrderDetailsBOServlet", "/StudentBOServlet" })
public class AdminAuthenticationFilter implements Filter {

	public AdminAuthenticationFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);

		boolean isLoggedIn = (session != null && session.getAttribute("username") != null);

		String loginURI = httpRequest.getContextPath() + "loginBO.jsp";

		boolean isLoginRequest = httpRequest.getRequestURI().endsWith(loginURI);

		boolean isLoginPage = httpRequest.getRequestURI().endsWith("loginBO.jsp");

		if (isLoggedIn && (isLoginRequest || isLoginPage)) {

			RequestDispatcher dispatcher = request.getRequestDispatcher("/StudentBOServlet");
			dispatcher.forward(httpRequest, response);

		} else if (isLoggedIn || isLoginRequest) {

			chain.doFilter(httpRequest, response);

		} else {

			RequestDispatcher dispatcher = request.getRequestDispatcher("loginBO.jsp");
			dispatcher.forward(httpRequest, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
