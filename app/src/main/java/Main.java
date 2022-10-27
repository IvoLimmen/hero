import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.limmen.hero.command.CommandFactory;
import org.limmen.hero.domain.Direction;
import org.limmen.hero.domain.Enemy;
import org.limmen.hero.domain.Link;
import org.limmen.hero.domain.Location;
import org.limmen.hero.domain.PromptProvider;
import org.limmen.hero.domain.World;
import org.limmen.hero.domain.factory.EnemyFactory;
import org.limmen.hero.domain.factory.LocationFactory;

public class Main {

  public Main() {
    loop();
  }

  public static void main(String[] args) {
    new Main();
  }

  private List<Location> createLocations() {
    var enemies = new ArrayList<Enemy>();
    enemies.add(EnemyFactory.get().byName("borg").get());

    return List.of(
      new Location("start", "Big docking station. All metal walls except for the bay doors",
        List.of(new Link(Direction.DOWN, "space")),
        List.of()),
      new Location("space", "Vast space. Nothing to see here, except for a million stars",
        List.of(new Link(Direction.UP, "start")),
        enemies));
  }

  private void loop() {
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
