package luis.josh.catan.host.game.board.tile.numbertokenassigner;

import luis.josh.catan.host.game.board.tile.Tile;

public interface NumberTokenAssigner {
   
   public void assignNumberTokens(Tile[][] tiles, int[][] tilePattern, int[] numberTokens);

}
