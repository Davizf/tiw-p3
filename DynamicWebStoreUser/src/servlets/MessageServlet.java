package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.MessageController;
import model.Messages;

@WebServlet(name = "MessageServlet", urlPatterns = "/jms-controller")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int op = Integer.parseInt(req.getParameter("op")) ;	
		String sender = req.getParameter("sender") ;	
		
		String msg = req.getParameter("message");

		if(op == 4) {	// send a offer to buyers
			MessageController.sendMessageToAllBuyers(msg, sender);
			RequestDispatcher miR=req.getRequestDispatcher("index.jsp");
			miR.forward(req, resp);
		}

		String receiver = req.getParameter("correlationId");

		if (op==1){	// send a message to a seller			
			Messages message = new Messages();
			message.setSender(sender);
			message.setReceiver(receiver);
			message.setMessage(msg);
			MessageController.sendMessage(message);
			RequestDispatcher miR=req.getRequestDispatcher("index.jsp");
			miR.forward(req, resp);

		}if (op==2) {	// read messages in my messages page
			req.setAttribute("messages", MessageController.getUserMessages(receiver));
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);

		}else if(op == 3){	// answer a message
			Messages message = new Messages();
			message.setSender(sender);
			message.setReceiver(receiver);
			message.setMessage(msg);
			MessageController.sendMessage(message);
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);
		}

	}

}