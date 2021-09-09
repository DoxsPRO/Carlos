package mnkgame;

import java.util.Random;

public class CARLOS implements MNKPlayer{
	private Random rand;
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;
	
	public CARLOS(){
		
	}
	
	public String playerName() {
		return "C.A.R.L.O.S.";
	}
	
	public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		// New random seed for each game
		rand    = new Random(System.currentTimeMillis()); 
		B       = new MNKBoard(M,N,K);
		myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
		TIMEOUT = timeout_in_secs;	
	}
	
	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
		long start = System.currentTimeMillis();
		if(MC.length > 0) {
			MNKCell c = MC[MC.length-1]; // Recover the last move from MC
			B.markCell(c.i,c.j);         // Save the last move in the local MNKBoard
		}
		// If there is just one possible move, return immediately
		if(FC.length == 1)
			return FC[0];
		 
		int pos   = rand.nextInt(FC.length); 
		MNKCell c = FC[pos]; // random move
		
		//MiniMax
		
		// No win or loss, return the randomly selected move
		return c;
	}
	
	public int MiniMax()
	{
		return 0;
	}
	
	
}
