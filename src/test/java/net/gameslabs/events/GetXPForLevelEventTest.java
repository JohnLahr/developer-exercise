package net.gameslabs.events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetXPForLevelEventTest {
    private GetXPForLevelEvent sut  ;

    @Test
    void getXPForLevelEventShouldReturnCorrectLevel() {
        sut = new GetXPForLevelEvent(40);

        assertEquals(40, sut.getLevel(), "Get level should return correct level");
    }

    @Test
    void getXPForLevelEventDefaultsTo0XP() {
        sut = new GetXPForLevelEvent(71);

        assertEquals(0, sut.getXp(), "Initial XP should be 0");
    }

    @Test
    void getXPForLevelEventReturnsCorrectXP() {
        sut = new GetXPForLevelEvent(28);

        sut.setXp(5000);

        assertEquals(5000, sut.getXp(), "XP getter and setter should function correctly");
    }
}
