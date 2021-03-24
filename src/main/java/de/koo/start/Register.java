package de.koo.start;

import de.koo.cmd.extra.Music;
import de.koo.cmd.general.C_Help;
import de.koo.cmd.moderation.C_Delete;

/**
 * @author Shyqu
 * @created 24/03/2021 - 21:14
 * @project HololiveBot
 */
public class Register {

    public static void __init__() {
        Hololive.api.addMessageCreateListener(new C_Help());
        Hololive.api.addMessageCreateListener(new C_Delete());
        Hololive.api.addListener(new Music());
    }

}
