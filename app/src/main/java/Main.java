import java.util.ArrayList;
import java.util.List;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.limmen.hero.domain.Dice;
import org.limmen.hero.domain.Direction;
import org.limmen.hero.domain.Enemy;
import org.limmen.hero.domain.Hero;
import org.limmen.hero.domain.Link;
import org.limmen.hero.domain.Location;
import org.limmen.hero.domain.World;
import org.limmen.hero.domain.factory.EnemyFactory;
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

  private Hero start(LineReader reader) {

    System.out.println("Welcome to space station X5-Y.");

    var name = reader.readLine("What is your name?");

    var hero = new Hero();
    hero.setName(name);
    hero.setHealth(20);
    hero.setWeapon(WeaponFactory.get().byName("Lazer gun"));
    hero.setArmour(10 + Dice.d10(0).value());
    return hero;        
  }

  private void loop() {
    LineReader reader = LineReaderBuilder.builder()
        .appName("Hero")
        .build();

    LocationFactory.get().set(createLocations());
    
    var location = LocationFactory.get().byName("start");
    var world = new World(start(reader), location);

    world.start(prompt -> { 
      return reader.readLine(prompt); 
    });
  }
}
