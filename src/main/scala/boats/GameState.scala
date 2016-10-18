package boats

import boats.Grid._

object GameState {
  val empty = GameState(
    playerA = Player.empty,
    playerB = Player.empty
  )

  def hasPlayerLost(grid:ShipsGrid) =
    grid.forall(_.forall(c => c.isEmpty || c.isHit))

  def draw(gs: GameState): Unit = {
    drawPlayer(gs.playerA, "Player A")
    drawPlayer(gs.playerB, "Player B")
  }

  def drawPlayer(player: Player, msg: String): Unit = {
    println(s"$msg - Own Ships")
    drawShipsGrid(player.ownLocations)
    println(s"$msg - Targets")
    drawHitsGrid(player.enemyLocations)
  }

  private def draw(cell: ShipsCell): Char = cell match {
    case ShipsEmpty => ' '
    case ShipHealthy => 'S'
    case ShipHit => 'H'
  }

  private def draw(cell: TargetCell): Char = cell match {
    case TargetUnknown => ' '
    case TargetMiss => 'M'
    case TargetHit => 'H'
  }

  private def drawHitsGrid(grid:TargetGrid): Unit =
    grid.foreach { cols =>
      println(cols.map(draw).mkString(""))
    }

  def drawShips(grid:ShipsGrid): Seq[String] =
    grid.map(_.map(draw).mkString)

  def drawShipsGrid(grid:ShipsGrid): Unit = {
    val indexHeader = "   "+grid.head.indices.mkString
    val headerBar = "   "+grid.head.indices.map(_ => "_").mkString
    val drawnGrid = drawShips(grid)
    val indexedWorld = drawnGrid.zipWithIndex.map { case (world, idx) =>
        s"$idx |$world"
    }

    (Seq(indexHeader, headerBar) ++ indexedWorld).foreach(println)
  }


}

case class GameState(
                    playerA: Player,
                    playerB: Player)