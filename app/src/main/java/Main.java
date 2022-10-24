import java.util.List;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.limmen.hero.domain.Dice;
import org.limmen.hero.domain.Direction;
import org.limmen.hero.domain.Hero;
import org.limmen.hero.domain.Link;
import org.limmen.hero.domain.Location;
import org.limmen.hero.domain.World;
import org.limmen.hero.domain.factory.LocationFactory;
import org.limmen.hero.domain.factory.WeaponFactory;

public class Main {

  public Main() {
    loop();
  }

  public static void main(String[] args) {
    new Main();
  }

  private List<Location> createLocations() {
    return List.of(
      new Location("start", "Big docking station. All metal walls except for the bay doors",
        List.of(new Link(Direction.DOWN, "space"))),
      new Location("space", "Vast space. Nothing to see here.",
        List.of(new Link(Direction.UP, "start"))));
  }

  private Hero start() {
    LineReader reader = LineReaderBuilder.builder()
        .appName("Hero")
        .build();

    System.out.println("Welcome to space station X5-Y.");

    var name = reader.readLine("What is your name?");

    return Hero.builder()
        .name(name)
        .armour(Dice.d20(0).value())
        .health(20)
        .weapon(WeaponFactory.get().byName("Hands"))
        .build();
  }

  private void loop() {
    LineReader reader = LineReaderBuilder.builder()
        .appName("Hero")
        .build();

    LocationFactory.get().set(createLocations());
    
    var location = LocationFactory.get().byName("start");
    var world = new World(start(), location);

    world.start(prompt -> { 
      return reader.readLine(prompt); 
    });
  }
}
