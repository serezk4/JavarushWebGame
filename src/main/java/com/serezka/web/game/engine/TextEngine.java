package com.serezka.web.game.engine;

import com.serezka.web.game.engine.objects.dialogs.Dialog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor @Getter
public class TextEngine {
    Dialog rootDialog;
    String backstory;

}
