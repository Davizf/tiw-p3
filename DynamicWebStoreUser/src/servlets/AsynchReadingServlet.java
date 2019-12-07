package servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import jms.AsynchConsumer;

@WebServlet(name = "AsynchReadingServlet", urlPatterns = "/AsynchReadingServlet", loadOnStartup = 1)
public class AsynchReadingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("AsynchReadServlet-->Listener rising....");
		AsynchConsumer miAc=new AsynchConsumer();
		miAc.asynchRead();

	}

}