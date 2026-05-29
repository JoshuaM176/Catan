package luis.josh.catan.host.game.gamepieces.cards;

import luis.josh.catan.host.game.board.resources.Resource;

public class ResourceCard extends Card{

    Resource resource;

    public ResourceCard(Resource resource) {
        this.resource = resource;
    }

    public Resource resource() {
        return resource;
    }

    @Override
    public String getName() {
        return resource.name();
    }

}
