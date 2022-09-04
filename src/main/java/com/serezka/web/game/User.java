package com.serezka.web.game;

import com.serezka.web.game.engine.objects.dialogs.Dialog;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter @Setter
public class User {
    final String username;
    final long id;
    Dialog userDialog;
}
