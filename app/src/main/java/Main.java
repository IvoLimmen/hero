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
import org.limmen.hero.domain.Location;
import org.limmen.hero.domain.PromptProvider;
import org.limmen.hero.domain.World;
import org.limmen.hero.domain.factory.EnemyFactory;
import org.limmen.hero.domain.factory.LocationFactory;
import org.limmen.hero.domain.factory.WeaponFactory;
import org.limmen.hero.util.JSON;

public class Main {

  public Main() {
    boot();
  }

  public static void main(String[] args) {
    new Main();
  }

  private List<Location> createLocations() {    
    LocationFactory.get().set(JSON.loadList("/locations.json", Location.class));
    
    var enemies = new ArrayList<Enemy>();
    enemies.add(EnemyFactory.get().byName("Droid").get());

    var loc1 = LocationFactory.get().byName("Dockingstation");
    loc1.addNode(Direction.EAST, "Hallway");
    loc1.addItem(WeaponFactory.get().byName("Lazergun"));

    var loc2 = LocationFactory.get().byName("Hallway");
    loc2.addNode(Direction.EAST, "Controlroom");
    loc2.addNode(Direction.SOUTH, "Dockingstation");

    var loc3 = LocationFactory.get().byName("Controlroom");
    loc3.addNode(Direction.SOUTH, "Hallway");
    loc3.addEnemy(EnemyFactory.get().byName("Droid").get());

    return List.of(loc1, loc2, loc3);
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
    var location = LocationFactory.get().byName("Dockingstation");
    var world = new World(writer, prompt, location);

    world.start();
  }
}
