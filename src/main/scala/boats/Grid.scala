package boats

object Grid {
  type ShipsGrid = Seq[Seq[ShipsCell]]
  type TargetGrid = Seq[Seq[TargetCell]]

  sealed trait TargetCell {
    def isHit: Boolean
    def isMiss: Boolean
  }
  case object TargetHit extends TargetCell {
    val isHit = true
    val isMiss = false
  }
  case object TargetMiss extends TargetCell {
    val isHit = false
    val isMiss = true

  }
  case object TargetUnknown extends TargetCell {
    val isHit = false
    val isMiss = false
  }

  sealed trait ShipsCell {
    def isHit: Boolean
    def isHealthy: Boolean
    def isEmpty: Boolean = !isHit && !isHealthy
  }
  case object ShipHit extends ShipsCell {
    val isHit = true
    val isHealthy = false
  }
  case object ShipHealthy extends ShipsCell {
    val isHit = false
    val isHealthy = true
  }
  case object ShipsEmpty extends ShipsCell {
    val isHit = false
    val isHealthy = false
  }
}
