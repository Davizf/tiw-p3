package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.ChatController;
import model.Message;

@WebServlet(name = "MessageServlet", urlPatterns = "/MessageServlet")
public class ChatServlet extends HttpServlet {
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
			ChatController.sendMessage(message);
			RequestDispatcher miR=req.getRequestDispatcher("index.jsp");
			miR.forward(req, resp);

		}else if (op==2) {	// read messages in my messages page
			List <Message> messages = ChatController.getUserMessages(receiver);
			req.setAttribute("messages", messages);
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);
			

		}else if(op == 3){	// answer a message
			Message message = new Message(sender, receiver, msg);
			ChatController.sendMessage(message);
			
			List <Message> messages = ChatController.getUserMessages(sender);
			req.setAttribute("messages", messages);
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);
		}else if(op == 4) {	// send a offer to buyers
			ChatController.sendMessageToAllBuyers(msg, sender);
			RequestDispatcher miR=req.getRequestDispatcher("index.jsp");
			miR.forward(req, resp);
		}else if(op == 5) {	// remove a message
			ChatController.deleteMessage(req.getParameter("msgid"));
			List <Message> messages = ChatController.getUserMessages(sender);
			req.setAttribute("messages", messages);
			RequestDispatcher miR=req.getRequestDispatcher("mymessages-page.jsp");
			miR.forward(req, resp);
		}
	
	}

}