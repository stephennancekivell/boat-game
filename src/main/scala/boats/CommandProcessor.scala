package boats

import boats.Grid._
import boats.Input.{Attack, Command, Exit}
import com.softwaremill.quicklens._

object CommandProcessor {
  def process(prevState: GameState, command: Command, isPlayerA: Boolean): GameState = {
    command match {
      case Exit =>
        System.exit(0)
        prevState
      case a: Attack =>
        process(prevState, a, isPlayerA)
    }
  }

  def process(prevState: GameState, command: Attack, isPlayerA: Boolean): GameState = {
    isPlayerA match {
      case true =>
        if (isHit(prevState.playerB.ownLocations, command)) {
          setHit(command, true, prevState)
        } else {
          prevState
            .modify(_.playerA.enemyLocations).using(setMiss(command, _))
        }

      case false =>
        if (isHit(prevState.playerB.ownLocations, command)) {
          setHit(command, false, prevState)
        } else {
          prevState
            .modify(_.playerB.enemyLocations).using(setMiss(command, _))
        }
    }
  }

  def isHit(shipsLocations: ShipsGrid, attack: Attack): Boolean =
    shipsLocations(attack.x)(attack.y).isHealthy

  def areAllShipsHits(shipsGrid: ShipsGrid): Boolean = {
    shipsGrid.forall(_.forall(!_.isHealthy))
  }

  def setHit(attack: Attack, isPlayerA: Boolean, gameState: GameState): GameState = {
    isPlayerA match {
      case true =>
        gameState
          .modify(_.playerA.enemyLocations).using(setHitTarget(attack,_))
          .modify(_.playerB.ownLocations).using(setHitShips(attack, _))
      case false =>
        gameState
          .modify(_.playerB.enemyLocations).using(setHitTarget(attack, _))
          .modify(_.playerA.ownLocations).using(setHitShips(attack,_))
    }
  }

  def setHitShips(attack: Attack, grid: ShipsGrid): ShipsGrid =
    set(attack.x, attack.y, grid, ShipHit)

  def setHitTarget(attack: Attack, grid: TargetGrid): TargetGrid =
    set(attack.x, attack.y, grid, TargetHit)

  def setMiss(attack: Attack, grid: TargetGrid): TargetGrid =
    set(attack.x, attack.y, grid, TargetMiss)

  def set[T](x: Int, y: Int, grid: Seq[Seq[T]], value: T): Seq[Seq[T]] = {
    val newY = grid(x).updated(y, value)
    grid.updated(x, newY)
  }
}
