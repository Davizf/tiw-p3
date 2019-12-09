package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.MessageController;
import model.Message;

@WebServlet(name = "MessageServlet", urlPatterns = "/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String sender = req.getParameter("sender") ;	
		String msg = req.getParameter("message");
		String receiver = req.getParameter("correlationId");
		
		int op = Integer.parseInt(req.getParameter("op")) ;	
		
		if (op == 1){	// send a message to a seller			
			Message message = new Message(sender, receiver, msg);
			MessageController.sendMessage(message);
			RequestDispatcher miR=req.getRequestDispatcher("index.jsp");
			miR.forward(req, resp);

		}if (op==2) {	// read messages in my messages page
			List <Message> messages = MessageController.getUserMessages(receiver);
			req.setAttribute("messages", messages);
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);
			

		}else if(op == 3){	// answer a message
			Message message = new Message(sender, receiver, msg);
			MessageController.sendMessage(message);
			
			List <Message> messages = MessageController.getUserMessages(sender);
			req.setAttribute("messages", messages);
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);
		}else if(op == 4) {	// send a offer to buyers
			MessageController.sendMessageToAllBuyers(msg, sender);
			RequestDispatcher miR=req.getRequestDispatcher("index.jsp");
			miR.forward(req, resp);
		}else if(op == 5) {	// remove a message
			MessageController.deleteMessage(req.getParameter("msgid"));
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);
		}
	
	}

}