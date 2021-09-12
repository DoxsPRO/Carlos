/* C.A.R.L.O.S.
 * Computer Advanced Random Logic Operating System
 * 
 * Creato da
 * Capone Orazio Andrea	MAT 0000987364
 * Ciancio Domenico 	MAT 0000987367
 */

package mnkgame;

import java.util.Random;

public class CARLOS implements MNKPlayer{
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;
	long start;
	
	public CARLOS(){
	}
	
	public String playerName() {
		return "C.A.R.L.O.S.";
	}
	
	public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
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

		MNKCell c = BestMove(B, FC); //Select best move
		
		B.markCell(c.i, c.j);

		return c;
	}
	
	
	public MNKCell BestMove(MNKBoard board, MNKCell[] FC)
	{
		start = System.currentTimeMillis();
		int bestScore=Integer.MIN_VALUE;
		MNKCell bestMove=FC[0];
		
		
		//Per ogni cella libera verifica la mossa migliore tramite minimax
		for(MNKCell c : board.getFreeCells())
		{
			board.markCell(c.i, c.j);
			int score=MiniMax(board, 3, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
			
			if(score>bestScore)
			{
				bestScore=score;
				bestMove=c;
			}
			board.unmarkCell();
			
			//se il tempo sta finendo interrompe il ciclo
			if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0))
				break;
		}
		
		System.out.println();
		return bestMove;
	}
	
	public int MiniMax(MNKBoard board, int depth, boolean isMax, int alpha, int beta)
	{
		//se il tempo sta finendo ritorna 0
		if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(90.0/100.0))
			return 0;
		
		if(depth<=0)
			return 0;
		
		if(board.gameState()==myWin)
		{
			return 10;
		}
		else if(board.gameState()==yourWin)
		{
			return -10;
		}
		else if(board.gameState()==MNKGameState.DRAW)
		{
			return 0;
		}
		
		if(isMax)
		{
			int maxScore=Integer.MIN_VALUE;
			
			for(MNKCell c : board.getFreeCells())
			{
				board.markCell(c.i, c.j);
				maxScore=Math.max(maxScore, MiniMax(board, depth-1, false, alpha, beta));
				board.unmarkCell();
				
				alpha = Math.max( alpha, maxScore);
			    
				if(beta <= alpha)
					break;
			}
			return maxScore;			
		}
		else
		{
			int minScore=Integer.MAX_VALUE;
			
			for(MNKCell c : board.getFreeCells())
			{
				board.markCell(c.i, c.j);
				minScore=Math.min(minScore, MiniMax(board, depth-1, true, alpha, beta));
				board.unmarkCell();
				
				beta = Math.min( beta, minScore);
			    if(beta <= alpha)
			    	break;
			}
			return minScore;
		}
	}
}
