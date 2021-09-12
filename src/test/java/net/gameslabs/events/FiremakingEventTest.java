package net.gameslabs.events;

import net.gameslabs.api.Player;
import net.gameslabs.implem.PlayerImplem;
import net.gameslabs.model.Logs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FiremakingEventTest {
    private FiremakingEvent sut;

    protected Player player;

    @BeforeEach
    public void setup() throws Exception {
        player = PlayerImplem.newPlayer("Bewgs");
    }

    @Test
    void firemakingEventLogsShouldBeReturnedCorrectly() {
        sut = new FiremakingEvent(player, Logs.OAK);

        assertEquals(Logs.OAK, sut.getLogs(), "FiremakingEvent logs should be returned correctly");
    }

    @Test
    void testStandardLogsBurnDuration() {
        int max = 45;
        int min = 30;
        int iterations = (max - min) * 1000;

        for (int i = 0; i < iterations; i++) {
            sut = new FiremakingEvent(player, Logs.STANDARD);

            assertTrue(sut.getBurnDuration() >= min, "Standard logs should burn for at least 30 seconds");
            assertTrue(sut.getBurnDuration() <= max, "Standard logs should burn for no more than 45 seconds");
        }
    }

    @Test
    void testOakLogsBurnDuration() {
        int max = 90;
        int min = 60;
        int iterations = (max - min) * 1000;

        for (int i = 0; i < iterations; i++) {
            sut = new FiremakingEvent(player, Logs.OAK);

            assertTrue(sut.getBurnDuration() >= min, "Oak logs should burn for at least 60 seconds");
            assertTrue(sut.getBurnDuration() <= max, "Oak logs should burn for no more than 90 seconds");
        }
    }

    @Test
    void testWillowLogsBurnDuration() {
        int max = 135;
        int min = 90;
        int iterations = (max - min) * 1000;

        for (int i = 0; i < iterations; i++) {
            sut = new FiremakingEvent(player, Logs.WILLOW);

            assertTrue(sut.getBurnDuration() >= min, "Willow logs should burn for at least 90 seconds");
            assertTrue(sut.getBurnDuration() <= max, "Willow logs should burn for no more than 135 seconds");
        }
    }

    @Test
    void firemakingEventShouldReturnCorrectPlayer() {
        sut = new FiremakingEvent(player, Logs.STANDARD);

        assertSame(player, sut.getPlayer(), "Get player should return correct player");
    }
}
