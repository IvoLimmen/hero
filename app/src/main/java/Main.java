import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.limmen.hero.command.CommandFactory;
import org.limmen.hero.domain.Direction;
import org.limmen.hero.domain.Enemy;
import org.limmen.hero.domain.Item;
import org.limmen.hero.domain.Link;
import org.limmen.hero.domain.Location;
import org.limmen.hero.domain.PromptProvider;
import org.limmen.hero.domain.World;
import org.limmen.hero.domain.factory.EnemyFactory;
import org.limmen.hero.domain.factory.LocationFactory;
import org.limmen.hero.domain.factory.WeaponFactory;

public class Main {

  public Main() {
    boot();
  }

  public static void main(String[] args) {
    new Main();
  }

  private List<Location> createLocations() {    
    var enemies = new ArrayList<Enemy>();
    enemies.add(EnemyFactory.get().byName("borg").get());
    var items = new ArrayList<Item>();
    items.add(WeaponFactory.get().byName("Lazergun"));

    return List.of(
      new Location("start", 
        "Big docking station. All metal walls except for the bay doors",
        List.of(new Link(Direction.DOWN, "space")),
        new ArrayList<>(),
        items),
      new Location("space", 
        "Vast space. Nothing to see here, except for a million stars",
        List.of(new Link(Direction.UP, "start")),
        enemies, 
        new ArrayList<>()));
  }

  private void boot() {
    AnsiConsole.systemInstall();
    LineReader reader = LineReaderBuilder.builder()
        .appName("Hero")
        .completer(new StringsCompleter(CommandFactory.get().listOfNamesAndAlliases().stream().map(Candidate::new).toList()))
        .build();

    LocationFactory.get().set(createLocations());
    
    PromptProvider prompt = str -> { 
      return reader.readLine(str); 
    };

    var writer = new PrintWriter(System.out);
    var location = LocationFactory.get().byName("start");
    var world = new World(writer, prompt, location);

    world.start();
  }
}
