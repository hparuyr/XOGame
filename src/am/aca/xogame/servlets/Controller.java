package am.aca.xogame.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import am.aca.xogame.beans.GameBean;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/game")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("gameId") == null)
			response.sendRedirect("index.jsp");

		Integer cell = Integer.parseInt(request.getParameter("cell_id"));
		Integer gameId = (Integer) session.getAttribute("gameId");

		//From application context we are getting game for this player
		ServletContext application = getServletConfig().getServletContext();
		GameBean bean = (GameBean) application.getAttribute("Game_" + gameId);

		String result = "success";
		// Pass turn to next player and populate table with appropriate char
		if (bean.getTurn().equals(bean.getPlayer1())) {

			// if try to change already chosen cell error should be shown
			if (bean.changeCell(cell, 'X'))
				bean.setTurn(bean.getPlayer2());
			else
				result = "Wrong move";
		} else {

			// if try to change already chosen cell error should be shown
			if (bean.changeCell(cell, 'O'))
				bean.setTurn(bean.getPlayer1());
			else
				result = "Wrong move";
		}

		if (bean.checkFinish())
			response.sendRedirect("win.jsp");
		else {
			response.setContentType("application/json");
			response.getWriter().print("{\"result\": \"" + result + "\", \"id\": \"" + cell + "\"}");
		}
	}

}
