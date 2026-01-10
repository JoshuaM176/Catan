package catan.host.game.board.tile.numbertokenassigner;

import catan.host.game.board.tile.Tile;

public interface NumberTokenAssigner {
   public void assignNumberTokens(Tile[][] tiles, int[][] tilePattern, int[] numberTokens);

}
