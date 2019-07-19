package am.aca.xogame.beans;

public class GameBean {
	private Integer id;
	private String player1;
	private String player2;
	private String turn;
	private Character[] table = { '_', '_', '_', '_', '_', '_', '_', '_', '_' };
	private String winner;
	
	public GameBean() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public Character[] getTable() {
		return table;
	}

	public void setTable(Character[] table) {
		this.table = table;
	}

	public boolean changeCell(Integer cellId, char value) {
		if(table[cellId] != '_')
			return false;
		table[cellId] = value;
		return true;
	}
	
	public boolean checkFinish() {
		// Here should be added win check logic
		// if someone win put player name into winner field
		return false;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
}
