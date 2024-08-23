package me.earth.headlessmc.api.command.impl;

import me.earth.headlessmc.api.HeadlessMc;
import me.earth.headlessmc.api.command.AbstractCommand;
import me.earth.headlessmc.api.command.Command;
import me.earth.headlessmc.api.exit.ExitManager;

import java.util.Locale;

/**
 * A {@link Command} that calls HeadlessMc's {@link ExitManager} to quit the process.
 */
public class QuitCommand extends AbstractCommand {
    /**
     * Constructs a new QuitCommand.
     *
     * @param ctx the HeadlessMc instance to get the {@link ExitManager} from.
     */
    public QuitCommand(HeadlessMc ctx) {
        super(ctx, "quit", "Quits HeadlessMc.");
    }

    @Override
    public void execute(String line, String... args) {
        ctx.log("Bye!");
        ctx.getExitManager().exit(0);
    }

    @Override
    public boolean matches(String line, String... args) {
        if (args.length > 0) {
            String lower = args[0].toLowerCase(Locale.ENGLISH).trim();
            return lower.equalsIgnoreCase("quit")
                || lower.equalsIgnoreCase("exit")
                || lower.equalsIgnoreCase("stop");
        }

        return false;
    }

}
