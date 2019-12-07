package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jms.InteractionJMS;

@WebServlet(name = "MessageServlet", urlPatterns = "/jms-controller")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int op = Integer.parseInt(req.getParameter("op")) ;	
		String sender = req.getParameter("sender") ;	
		InteractionJMS mq=new InteractionJMS();
		String msg = req.getParameter("message");

		if(op == 4) {	// send a offer to buyers
			mq.writeJMSToAllBuyers(msg,sender);
			RequestDispatcher miR=req.getRequestDispatcher("index.jsp");
			miR.forward(req, resp);
		}

		String correlationId = req.getParameter("correlationId");

		if (op==1){	// send a message to a seller
			mq.writeJMS(msg,correlationId,sender);
			RequestDispatcher miR=req.getRequestDispatcher("index.jsp");
			miR.forward(req, resp);

		}if (op==2) {	// read messages in my messages page
			req.setAttribute("messages", mq.readJMS(correlationId));
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);

		}else if(op == 3){	// answer a message
			mq.writeJMS(req.getParameter("message"),correlationId,sender);
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);
		}

	}

}