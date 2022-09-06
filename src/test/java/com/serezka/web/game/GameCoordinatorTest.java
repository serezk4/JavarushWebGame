package com.serezka.web.game;

import com.serezka.web.game.engine.objects.dialogs.Dialog;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameCoordinatorTest {

    @Test
    void addUser() {
        long userId = (long) (Math.random() * 10000);
        String username = "BiBi";

        GameCoordinator.addUser(userId, username);

        assertEquals(GameCoordinator.getUserById(userId).getUsername(), username);
    }

    @Test
    void containsUser() {
        long userId = (long) (Math.random() * 10000);
        GameCoordinator.addUser(userId, "test");

        assertTrue(GameCoordinator.containsUser(userId));
    }

    @Test
    void getBackstory() {
        assertNotNull(GameCoordinator.getBackstory());
    }

    @Test
    void getNextDialog() {
        for (int i = 0; i < 1000; i++) {
            long userId = (long) (Math.random() * 10000);
            String username = "BiBi";

            GameCoordinator.addUser(userId, username);

            Dialog dialog;
            while (!(dialog = GameCoordinator.getUserById(userId).getUserDialog()).getOptions().isEmpty()) {
                assertNotNull(GameCoordinator.getNextDialog(GameCoordinator.getUserById(userId), dialog.getOptions().get(new Random().nextInt(dialog.getOptions().size())).getQuestion()));
            }
        }


    }
}