package org.limmen.hero.command;

import java.util.List;

public record ParsedCommand(Command command, List<String> arguments) {
}
