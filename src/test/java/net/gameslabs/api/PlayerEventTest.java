package net.gameslabs.api;

import net.gameslabs.implem.PlayerImplem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerEventTest {
    private PlayerEvent sut;

    protected Player player;

    @BeforeEach
    public void setup() throws Exception {
        player = PlayerImplem.newPlayer("Bewgs");
    }

    @Test
    void playerEventReturnsPlayerCorrectly() {
        sut = new PlayerEvent(player);

        assertSame(player, sut.getPlayer(), "Player event should return correct player");
    }
}
