<%@page import="am.aca.xogame.beans.GameBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	if (session.getAttribute("gameId") == null) {
		response.sendRedirect("index.jsp");
		return;
	}
	Integer gameId = (Integer) session.getAttribute("gameId");
	String player = (String) session.getAttribute("player");
	GameBean game = (GameBean) application.getAttribute("Game_" + gameId);
%>
<html>
<head>
<meta charset="UTF-8">
<title>Let's Play</title>
</head>
<body>
	<% if (game == null || game.getPlayer2() == null) { %>
		<p>GameId:<%= gameId %></p>
		<p>Player:<%= player %></p>
		<p>Waiting...</p>
		<img alt="Loading.." src="https://media0.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy.gif">
		<script type="text/javascript">
			// reloading page each 2 seconds to see if oponent found
			setTimeout(function() { location.reload(); }, 2000);
		</script>
	<% } else { Character[] table = game.getTable(); %>
		<script type="text/javascript">
			var yourTourn = <%=player.equals(game.getTurn())%>
			if(!yourTourn){
				// if it's not your tourn reloading the page to get oponent's move
				setTimeout(function() { location.reload(); }, 2000);
			}
			function send(td) {
				if(yourTourn){
					var id = td.getAttribute("id");
					var ajax = new XMLHttpRequest();
					ajax.onreadystatechange = processResult;
			
					ajax.open("POST", "game", true);
					ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
					ajax.send("cell_id=" + id);
				}
				else{
					alert("It's not your tourn");
				}
			}
			function processResult() {
				if (this.readyState == 4 && this.status == 200) {
					var val = JSON.parse(this.responseText);
					if(val.result == "success")
						location.reload();
					else{
						alert(val.result);
					}
				}		
			}
		</script>
		<p>GameId:<%= game.getId() %></p>
		<table border="1">
			<tr><td colspan="3">Player:<%= game.getPlayer1() %></td></tr>
			<tr>
				<td id="0" style="width: 30px; height: 30px" onclick="send(this)"><%=table[0]%></td>
				<td id="1" style="width: 30px; height: 30px" onclick="send(this)"><%=table[1]%></td>
				<td id="2" style="width: 30px; height: 30px" onclick="send(this)"><%=table[2]%></td>
			</tr>
			<tr>
				<td id="3" style="width: 30px; height: 30px" onclick="send(this)"><%=table[3]%></td>
				<td id="4" style="width: 30px; height: 30px" onclick="send(this)"><%=table[4]%></td>
				<td id="5" style="width: 30px; height: 30px" onclick="send(this)"><%=table[5]%></td>
			</tr>
			<tr>
				<td id="6" style="width: 30px; height: 30px" onclick="send(this)"><%=table[6]%></td>
				<td id="7" style="width: 30px; height: 30px" onclick="send(this)"><%=table[7]%></td>
				<td id="8" style="width: 30px; height: 30px" onclick="send(this)"><%=table[8]%></td>
			</tr>
			<tr><td colspan="3">Player:<%= game.getPlayer2() %></td></tr>
		</table>
	<% } %>
</body>
</html>
