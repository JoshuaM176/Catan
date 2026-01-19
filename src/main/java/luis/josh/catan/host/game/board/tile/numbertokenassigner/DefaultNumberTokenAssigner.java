package luis.josh.catan.host.game.board.tile.numbertokenassigner;

import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.ResourceTile;

public class DefaultNumberTokenAssigner implements NumberTokenAssigner{

    Tile[][] tiles;
    int[][] tilePattern;
    int[] numberTokens;
    int tokenIndex = 0;

    private void assignNumberToken(int y, int x) {
        Tile tile = tiles[y][x];
        if(tile.resource != Resource.DESERT) {
            ResourceTile rsrcTile = (ResourceTile)tile;
            rsrcTile.assignNumberToken(numberTokens[tokenIndex]);
            tokenIndex++;
            tilePattern[y][x] = 0;
        }
        else {
            tilePattern[y][x] = 0;
        }
    }

    @Override
    public void assignNumberTokens(Tile[][] tiles, int[][] tilePattern, int[] numberTokens) {
        this.tiles = tiles;
        this.tilePattern = tilePattern;
        this.numberTokens = numberTokens;
        tokenIndex = 0;
        for(int step = 0; step < tilePattern.length / 2 + 1; step++) {
            for(int x = step; x < tilePattern[0].length - step; x++) {
                int y = step;
                if(tilePattern[y][x] == 1) {
                    assignNumberToken(y, x);
                }
            }
            for(int y = step + 1; y < tilePattern.length - step; y++) {
                int x = tilePattern.length - step - 1;
                if(tilePattern[y][x] == 1) {
                    assignNumberToken(y, x);
                }
                else if(x > 0) {
                    if(tilePattern[y][x-1] == 1) {
                        assignNumberToken(y, x-1);
                    }
                }
            }
            for(int x = tilePattern[0].length - step - 2; x > step - 1; x--) {
                int y = tilePattern.length - step - 1;
                if(tilePattern[y][x] == 1) {
                    assignNumberToken(y, x);
                }
            }
            for(int y = tilePattern.length - step - 2; y > step; y--) {
                int x = step;
                if(tilePattern[y][x] == 1) {
                    assignNumberToken(y, x);
                }
            }
        }
    }
}