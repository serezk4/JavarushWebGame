package com.serezka.web.game;

import com.serezka.web.game.engine.TextEngine;
import com.serezka.web.game.engine.objects.dialogs.Dialog;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameCoordinator {
    static final Map<Long, User> users = new HashMap<>();
    static final TextEngine textEngine;

    //
    static {
        textEngine = new TextEngine(new Dialog("", "You've lost your memory. Accept the UFO challenge?",
                List.of(new Dialog("Reject a call?", "You rejected the call. Defeat."), new Dialog("Take the challenge", "You accepted the challenge. Are you going up to the captain's bridge?",
                        List.of(new Dialog("Refuse to climb the bridge", "You didn't negotiate. Defeat."),
                                new Dialog("Climb to the bridge", "You have climbed the bridge. - \"Who are you?\"",
                                        List.of(new Dialog("Lie about yourself", "Your lies have been exposed. Defeat."), new Dialog("Tell the truth about yourself", "You have been returned home. Victory."))))))),

                "You decide to walk to the store late at night for groceries.<br/>" +
                        "You bought groceries and go home, but suddenly your eyes suddenly get dark...");
    }

    // user logic


    public static void addUser(long userId, String username) {
        if (!users.containsKey(userId)) users.put(userId, new User(username, userId, textEngine.getRootDialog()));
    }

    public static boolean containsUser(long userId) {
        return users.containsKey(userId);
    }

    public static String getUsers() {
        StringBuilder sb = new StringBuilder();
        users.forEach((key, val) -> sb.append(" > <strong>").append(key).append("</strong> - <strong>").append(val.getUsername()).append("</strong><br/>"));
        return sb.toString();
    }

    public static User getUserById(long id) {
        return users.get(id);
    }

    // game logic
    public static String getBackstory() {
        return textEngine.getBackstory();
    }

    public static Map.Entry<String, List<Dialog>> getNextDialog(User user, String predAnsw, boolean updateUserDialog) {
        if (predAnsw.equals("new")) {
            if (updateUserDialog) user.setUserDialog(textEngine.getRootDialog());
            return new AbstractMap.SimpleEntry<>(user.getUserDialog().getAnswer(), user.getUserDialog().getOptions());
        }

        if (user.getUserDialog().getOptions().isEmpty()) return new AbstractMap.SimpleEntry<>(user.getUserDialog().getAnswer(), Collections.emptyList());
        if (predAnsw.isBlank()) return new AbstractMap.SimpleEntry<>(user.getUserDialog().getAnswer(), user.getUserDialog().getOptions());

        Optional<Dialog> selectedDialog = user.getUserDialog().getOptions().stream().filter(dialog -> dialog.getQuestion().equalsIgnoreCase(predAnsw)).collect(Collectors.toList()).stream().findFirst();

        if (selectedDialog.isEmpty()) return null;

        if (updateUserDialog) user.setUserDialog(selectedDialog.get());
        return new AbstractMap.SimpleEntry<>(selectedDialog.get().getAnswer(), selectedDialog.get().getOptions());
    }
}
