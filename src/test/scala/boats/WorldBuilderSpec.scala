package boats

import Grid.ShipsEmpty
import org.specs2.mutable.Specification

class WorldBuilderSpec extends Specification {
  "WorldBuilder" should {
    "give all places that we could place one ship" in {
      val startGrid = Seq.fill(2,2)(ShipsEmpty)
      val worlds = WorldBuilder.streamOfAllPlacesToPlaceThatOneShip(startGrid, 2)
      val rendered = worlds.toList.map(GameState.drawShips(_).mkString("|"))
      val expected = Seq(
        "SS|  ",
        "  |SS",
        "S |S ",
        " S| S"
      )
      rendered.toSet === expected.toSet
    }

    "give all locations in the grid" in {
      val grid = Seq.fill(2,2)(ShipsEmpty)
      val loc = WorldBuilder.allLocations(grid)
      Seq(
        0 -> 0,
        0 -> 1,
        1 -> 0,
        1 -> 1
      ).toSet === loc.toSet
    }

    "place two ships in the grid" in {
      val startGrid = Seq.fill(2,2)(ShipsEmpty)
      val worlds = WorldBuilder.randomShipsGrid(Seq(2,2), startGrid)

      val rendered = worlds.map(GameState.drawShips(_).mkString("|"))

      rendered.toList.size === 4 // there are duplicates, these could be removed as a optimization
      rendered.toList.distinct.size === 1

      rendered.head === "SS|SS"
    }

    "place one ship in the grid" in {
      val startGrid = Seq.fill(2,2)(ShipsEmpty)
      val worlds = WorldBuilder.randomShipsGrid(Seq(2), startGrid)

      val rendered = worlds.map(GameState.drawShips(_).mkString("|"))

      rendered.forall(_.count(_ == 'S') == 2)
    }

    "place a few ships in the grid" in {
      val startGrid = Seq.fill(5,5)(ShipsEmpty)
      val worlds = WorldBuilder.randomShipsGrid(Seq(5,3,2), startGrid)

      val rendered = worlds.map(GameState.drawShips(_).mkString("\n"))

      //print(rendered.head)
      /*
      should be like ..

           SS
        SSSSS
         SSS
       */

      rendered.head.count(_ == 'S') === 10
    }
  }
}
