package cluedogame.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import main.TextClient;
import main.TextHelpers;

import org.junit.*;

import cluedogame.Board;
import cluedogame.GameOfCluedo;
import cluedogame.Player;
import cluedogame.cards.Card;
import cluedogame.cards.CharacterCard;
import cluedogame.cards.WeaponCard;
import cluedogame.sqaures.RoomSquare;
import cluedogame.sqaures.ShortcutSquare;
import cluedogame.sqaures.Square;
import static org.junit.Assert.*;

public class CluedoTests {

	@Test
	public void testPlayerSetup(){
		GameOfCluedo game = new GameOfCluedo();
		List<Player> players = makePlayers(6);
		game.setPlayers(players);
		assertEquals(players, game.getPlayers());
	}
	
	@Test
	public void testCardSetup(){
		GameOfCluedo game = new GameOfCluedo();
		List<Player> players = makePlayers(6);
		game.setPlayers(players);
		game.dealCards();
		game.printMurder();
		players = game.getPlayers();
		for (Player p : players){
			assertFalse(p.getHandStrings().isEmpty());
		}
	}
	
	@Test
	public void testBoardSetup(){
		GameOfCluedo game = new GameOfCluedo();
		Board board = game.getBoard();
		List<Player> players = makePlayers(3);
		game.drawBoard(players);
		assertTrue(board.squareAt(2, 2) instanceof RoomSquare);
	}
	
	@Test
	public void testFalseAccuse(){
		GameOfCluedo game = new GameOfCluedo();
		List<Player> players = makePlayers(4);
		game.setPlayers(players);
		game.dealCards();
		Card card = players.get(2).getHand().get(0);
		String[] accusation = new String[3];
		assertFalse(card == null);
		if (card instanceof CharacterCard){
			accusation[0] = card.getName();
			accusation[1] = GameOfCluedo.DAGGER;
			accusation[2] = GameOfCluedo.KITCHEN;
		} else if (card instanceof WeaponCard){
			accusation[0] = GameOfCluedo.MUSTARD;
			accusation[1] = card.getName();
			accusation[2] = GameOfCluedo.KITCHEN;
		} else {
			accusation[0] = GameOfCluedo.MUSTARD;
			accusation[1] = GameOfCluedo.DAGGER;
			accusation[2] = card.getName();
		}
		assertFalse(game.accuse(accusation));
	}
	
	@Test
	public void testTrueAccuse(){
		GameOfCluedo game = new GameOfCluedo();
		Card[] murderCards = game.getMurderCards();
		String[] accusation = new String[3];
		for(int i = 0; i < 3; i++){
			accusation[i] = murderCards[i].getName();
		}
		assertTrue(game.accuse(accusation));
	}
	
	@Test
	public void testPlayerSeenCards(){
		GameOfCluedo game = new GameOfCluedo();
		List<Player> players = makePlayers(3);
		game.setPlayers(players);
		game.dealCards();
		
		Player p = players.get(1);
		assertFalse(p.getHand().isEmpty());
		for (int i = 0; i < p.getHand().size(); i++){
			assertTrue(p.getHandStrings().get(i).equals(p.getCardsSeenStrings().get(i)));
		}
	}
	
	@Test
	public void testPlayerSeeNewCard(){
		GameOfCluedo game = new GameOfCluedo();
		List<Player> players = makePlayers(3);
		game.setPlayers(players);
		game.dealCards();
		
		Player p = players.get(1);
		Player p2 = players.get(2);
		Card card = p2.getHand().get(0);
		p.addCardSeen(card);
		assertTrue(p.hasSeenCard(card));
	}
	
	@Test
	public void testPlayerCanMove(){
		GameOfCluedo game = new GameOfCluedo();
		List<Player> players = makePlayers(2);
		game.setPlayers(players);
		Board board = game.getBoard();
		Player p = game.getPlayers().get(0);
		p.setPos(24, 7);
		assertTrue(p.canMoveUp(board));
		assertFalse(p.canMoveLeft(board));
		assertFalse(p.canMoveRight(board));
		assertFalse(p.canMoveDown(board));
		p.moveUp();
		assertTrue(p.canMoveDown(board));
	}
	
	@Test
	public void testPlayerMovement(){
		GameOfCluedo game = new GameOfCluedo();
		List<Player> players = makePlayers(2);
		game.setPlayers(players);
		Board board = game.getBoard();
		Player p = game.getPlayers().get(0);
		p.setPos(24, 7);
		p.moveUp();
		p.moveRight();
		p.moveUp();
		p.moveLeft();
		p.moveDown();
		assertTrue(p.column() == 7);
		assertTrue(p.row() == 23);
	}
	
	@Test
	public void testShortcutCreationFail(){
		GameOfCluedo game = new GameOfCluedo();
		Board board = game.getBoard();
		ShortcutSquare shortcut = new ShortcutSquare("Ball Room", board);
		assertTrue(shortcut.toRoom().equals("Ball Room"));
		assertTrue(shortcut.toCol() == -1);
		assertTrue(shortcut.toRow() == -1);
	}
	
	@Test
	public void testShortcutCreation(){
		GameOfCluedo game = new GameOfCluedo();
		Board board = game.getBoard();
		ShortcutSquare shortcut = new ShortcutSquare("Study", board);
		assertTrue(shortcut.toRoom().equals("Study"));
		assertTrue(shortcut.toCol() == 22);
		assertTrue(shortcut.toRow() == 21);
	}
	
//	@Test
//	public void testTextClient(){
//		TextClient client = new TextClient();
//		Method[] methods = client.getClass().getMethods();
//		// client.main(null);  // need to invoke interior methods
//	}
//	
//	@Test
//	
//	public void testTextHelpers(){
//		TextHelpers helper = new TextHelpers();
//		try {
//			Method[] methods = helper.getClass().getMethods();
//			methods[0].invoke(null);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e);
//			fail();
//		} 
//	}
	
	/**
	 * Creates an List of the specified number of players
	 * @param Number of players to create - defaults to two.
	 * @return The List of players
	 */
	public List<Player> makePlayers(int count){
		List<Player> players = new LinkedList();
		switch(count){
		case 6:
			players.add(new Player(GameOfCluedo.PLUM, '5'));
		case 5:
			players.add(new Player(GameOfCluedo.PEACOCK, '4'));
		case 4:
			players.add(new Player(GameOfCluedo.GREEN, '3'));
		case 3:
			players.add(new Player(GameOfCluedo.WHITE, '2'));
		default:
			players.add(new Player(GameOfCluedo.MUSTARD, '1'));
			players.add(new Player(GameOfCluedo.SCARLETT, '0'));
		}
		return players;
	}
}
