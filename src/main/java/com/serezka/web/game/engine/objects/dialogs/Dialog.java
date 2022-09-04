package com.serezka.web.game.engine.objects.dialogs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor @Getter
public class Dialog {
    String question;
    String answer;
    List<Dialog> options;

    public Dialog(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.options = Collections.emptyList();
    }
}
