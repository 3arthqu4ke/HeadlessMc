package me.earth.headlessmc.jline;

import me.earth.headlessmc.api.HeadlessMc;
import me.earth.headlessmc.api.HeadlessMcImpl;
import me.earth.headlessmc.api.command.CommandContextImpl;
import me.earth.headlessmc.api.command.impl.HelpCommand;
import me.earth.headlessmc.api.command.line.CommandLine;
import me.earth.headlessmc.api.config.ConfigImpl;
import me.earth.headlessmc.api.exit.ExitManager;
import me.earth.headlessmc.logging.LoggingService;
import org.jline.reader.Candidate;
import org.jline.reader.ParsedLine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandCompleterTest {
    @Test
    public void testCommandCompleter() {
        HeadlessMc hmc = new HeadlessMcImpl(ConfigImpl::empty, new CommandLine(), new ExitManager(), new LoggingService());
        HelpCommand helpCommand = new HelpCommand(hmc);
        CommandContextImpl commands = new CommandContextImpl(hmc) {{
            add(helpCommand);
        }};

        hmc.getCommandLine().setCommandContext(commands);
        List<Candidate> candidates = new ArrayList<>();
        new CommandCompleter(hmc).complete(null, new ParsedLine() {
            @Override
            public String word() {
                return null;
            }

            @Override
            public int wordCursor() {
                return 0;
            }

            @Override
            public int wordIndex() {
                return 0;
            }

            @Override
            public List<String> words() {
                return null;
            }

            @Override
            public String line() {
                return "";
            }

            @Override
            public int cursor() {
                return 0;
            }
        }, candidates);

        assertEquals(1, candidates.size());
        assertEquals(helpCommand.getName(), candidates.get(0).value());
        assertEquals(helpCommand.getName(), candidates.get(0).displ());
        assertEquals(helpCommand.getDescription(), candidates.get(0).descr());
    }

}