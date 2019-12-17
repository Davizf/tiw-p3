package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.UserController;

@WebFilter(
		urlPatterns = "/catalogue.jsp",
		filterName = "CatalogueFilter"
		)
public class CatalogueFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;

		String email = (String) ((HttpServletRequest) request).getSession().getAttribute("user");

		if (email == null) {
			res.sendRedirect("index.jsp");
			return;
		} else {
			if (UserController.getUser(email).getType() != UserController.USER_TYPE_SELLER) {
				res.sendRedirect("products.jsp");
				return;
			}

			chain.doFilter(request, response);
		}
	}

}