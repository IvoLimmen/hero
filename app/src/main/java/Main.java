import java.util.List;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.limmen.hero.domain.Dice;
import org.limmen.hero.domain.Direction;
import org.limmen.hero.domain.Hero;
import org.limmen.hero.domain.Link;
import org.limmen.hero.domain.Location;
import org.limmen.hero.domain.LocationFactory;
import org.limmen.hero.domain.WeaponFactory;
import org.limmen.hero.domain.World;

public class Main {

  public Main() {
    loop();
  }

  public static void main(String[] args) {
    new Main();
  }

  private List<Location> createLocations() {
    return List.of(
      new Location("start", 
        List.of(new Link(Direction.DOWN, "space"))),
      new Location("space", 
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
