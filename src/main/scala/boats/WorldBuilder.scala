package boats

import scala.util.Random
import Grid._

object WorldBuilder {
  def oneRandomShipsGrid(shipsToPlace: Seq[Int], grid: ShipsGrid = Seq.fill(10,10)(ShipsEmpty)): ShipsGrid = {
    randomShipsGrid(shipsToPlace, grid).head
  }

  def randomShipsGrid(shipsToPlace: Seq[Int], grid: ShipsGrid): Stream[ShipsGrid] = {
    shipsToPlace match {
      case Nil => Stream(grid)
      case head :: Nil =>
        streamOfAllPlacesToPlaceThatOneShip(grid, head)
      case head :: tail =>
        streamOfAllPlacesToPlaceThatOneShip(grid, head)
          .flatMap { gridWithPlaced =>
            randomShipsGrid(tail, gridWithPlaced)
        }
    }
  }

  def streamOfAllPlacesToPlaceThatOneShip(grid: ShipsGrid, shipSize: Int): Stream[ShipsGrid] = {
    val all = allLocations(grid)
    val validish: Seq[(Int,Int)] =
      all.filter{ case (x,y) => couldFit(x,y,true, grid, shipSize) || couldFit(x,y,false,grid,shipSize)}

    Random.shuffle(validish)
      .toStream
      .flatMap { case (x,y) =>
        val h =
          if (couldFit(x,y,true, grid, shipSize)) {
            Stream(place(grid, x,y,shipSize,true))
          } else
            Stream.empty

        val v =
          if (couldFit(x,y,false, grid, shipSize)) {
            Stream(place(grid, x,y,shipSize,false))
          } else
            Stream.empty

          v ++ h
      }

    // this is stream of all the first taken ships.
    // after this flatMap to apply all the next size to the grid.

  }

  def place(grid: ShipsGrid, x:Int, y:Int, shipSize: Int, isHorizontal: Boolean): ShipsGrid = {
    val ys = if (!isHorizontal)
        y until y+shipSize
      else
        Seq.fill(shipSize)(y)

    val xs = if (isHorizontal)
      x until x+shipSize
    else
      Seq.fill(shipSize)(x)

    val i = for {xx <- xs; yy <- ys} yield xx -> yy

    i.foldLeft(grid){ case (acc, (xx,yy)) =>
      CommandProcessor.set(xx, yy, acc, ShipHealthy)
    }
  }

  def allEmptyLocations(grid: ShipsGrid): Seq[(Int,Int)] = {
    grid.zipWithIndex.flatMap { case (cols ,x) =>
      cols.zipWithIndex.flatMap {
        case (cell, y) if cell.isEmpty =>
          Seq(x -> y)
        case _ =>
          Nil
      }
    }
  }

  def allLocations(grid: ShipsGrid): Seq[(Int,Int)] = for {
    x <- grid.indices
    y <- grid.head.indices
  } yield x -> y

  case class PossiblePosition(x: Int, y: Int, isHorizontal: Boolean)

  def couldFit(x: Int, y: Int, isHorizontal: Boolean, shipsGrid: ShipsGrid, shipSize: Int): Boolean = {
    if (isHorizontal) {
      shipsGrid.size >= x + shipSize &&
      Range(x, x+shipSize).forall(shipsGrid(_)(y).isEmpty)
    } else {
      shipsGrid(x).size >= y + shipSize &&
      Range(y, y+shipSize).forall(shipsGrid(x)(_).isEmpty)
    }
  }
}
