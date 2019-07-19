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
import am.aca.xogame.db.DBAccess;

/**
 * Servlet implementation class Create
 */
@WebServlet("/join")
public class Join extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Join() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String player = request.getParameter("player");
		GameBean game = DBAccess.getAvailableGames();
		
		//Create game if there is no game created
		if(game == null) {
			Integer gameId = DBAccess.createGame(player);
			game = new GameBean();
			game.setId(gameId);
			game.setPlayer1(player);
			session.setAttribute("gameId", game.getId());
			session.setAttribute("player", player);
		}
		// There is already created game join
		else {
			DBAccess.joinGame(game.getId(), player);
			game.setPlayer2(player);
			session.setAttribute("gameId", game.getId());
			session.setAttribute("player", player);
			game.setTurn(game.getPlayer1());
			
			// Put game in application context for quick access
			ServletContext application = getServletConfig().getServletContext();
			application.setAttribute("Game_"+game.getId(), game);
		}
		response.sendRedirect("main.jsp");
	}

}
