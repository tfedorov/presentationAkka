package com.epam.maf

import com.epam.maf.Table._

object NightKillMaker extends RandomListUtils {

  def killInNight(): Unit = {
    val nightVictim = randomFromList(availableNoMaf.filterNot(_.yourSelf))
    removePlayer(nightVictim)

  }
}

