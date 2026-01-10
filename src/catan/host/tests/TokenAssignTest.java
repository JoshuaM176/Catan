package catan.host.tests;

import catan.host.game.board.tile.numbertokenassigner.DefaultNumberTokenAssigner;
import catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;

import java.util.ArrayList;

import catan.host.game.board.Board;
import catan.host.game.board.resources.Resource;

public class TokenAssignTest {
    public static void main(String[] args) {
        int[][] tilePattern = new int[][] {
            {0,1,1,1,0},
            {1,1,1,1,0},
            {1,1,1,1,1},
            {1,1,1,1,0},
            {0,1,1,1,0}
        };
        int[] numberTokens = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        NumberTokenAssigner numberTokenAssigner = new DefaultNumberTokenAssigner();
        ArrayList<Resource> resources = new ArrayList<>();
        Board.addResources(resources, Resource.STONE, 3);
        Board.addResources(resources, Resource.BRICK, 3);
        Board.addResources(resources, Resource.WHEAT, 4);
        Board.addResources(resources, Resource.LOGS, 4);
        Board.addResources(resources, Resource.SHEEP, 4);
        Board.addResources(resources, Resource.DESERT, 1);
        Board board = new Board(tilePattern, numberTokens, numberTokenAssigner, resources);
        System.out.println(board);
    }
}
