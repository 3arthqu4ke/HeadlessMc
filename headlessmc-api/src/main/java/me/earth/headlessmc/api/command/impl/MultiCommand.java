package me.earth.headlessmc.api.command.impl;

import me.earth.headlessmc.api.HeadlessMc;
import me.earth.headlessmc.api.command.AbstractCommand;
import me.earth.headlessmc.api.command.Command;
import me.earth.headlessmc.api.command.CommandException;
import me.earth.headlessmc.api.command.QuickExitCli;
import me.earth.headlessmc.api.command.line.CommandLine;

/**
 * A {@link Command} that treats its given arguments as commands and executes them.
 * This is useful when you want to start HeadlessMc with the {@link QuickExitCli}
 * from the command line; and you want to execute multiple commands that you know beforehand.
 */
public class MultiCommand extends AbstractCommand {
    /**
     * Constructs a new MultiCommand command.
     * The {@link CommandLine} of the given {@link HeadlessMc} instance
     * will be used to execute the given commands.
     *
     * @param ctx the HeadlessMc instance supplying the {@link CommandLine} to execute commands on.
     */
    public MultiCommand(HeadlessMc ctx) {
        super(ctx, "multi", "Run multiple commands together.");
        args.put("<command1 command2...>", "The commands to run.");
    }

    @Override
    public void execute(String line, String... args) throws CommandException {
        for (int i = 1; i < args.length; i++) {
            ctx.getCommandLine().getCommandContext().execute(args[i]);
        }
    }

}
