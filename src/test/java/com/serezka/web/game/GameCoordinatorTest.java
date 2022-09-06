package com.serezka.web.game;

import com.serezka.web.game.engine.objects.dialogs.Dialog;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        long userId = (long) (Math.random() * 10000);
        String username = "BiBi";

        GameCoordinator.addUser(userId, username);

        assertTrue(checkHistory(GameCoordinator.getUserById(userId).getUserDialog()));
        assertTrue(checksGameCoordinator(GameCoordinator.getUserById(userId).getUserDialog(), userId));
    }

    private boolean checkHistory(Dialog dialog) {
        if (dialog == null) return false;
        if (dialog.getOptions().isEmpty()) return true;

        boolean res = true;
        for (Dialog tempDialog : dialog.getOptions()) {
            res = res && checkHistory(tempDialog);
        }

        return res;
    }

    private boolean checksGameCoordinator(Dialog root, long userId) {
        assertNotNull(root);
        if (root.getOptions().isEmpty()) return true;

        boolean res = true;
        GameCoordinator.getUserById(userId).setUserDialog(root);
        for (Dialog rootOption : root.getOptions()) {
            Map.Entry<String, List<Dialog>> newOptions = GameCoordinator.getNextDialog(GameCoordinator.getUserById(userId), rootOption.getQuestion(), false);
            assertNotNull(newOptions);

            System.out.println("ROOT: " + rootOption.getQuestion());

            for (Dialog newOption : newOptions.getValue()) {
                System.out.println(" -> NEW: " + newOption.getQuestion());

                res = res && checksGameCoordinator(newOption, userId);
            }

        }

        return res;
    }

}