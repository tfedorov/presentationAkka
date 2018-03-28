package com.epam.maf

import com.epam.maf.Table.{availablePlayers, removePlayer}

object NightKillMaker extends RandomListUtils {

  def killInNight(): Unit = {
    val nightVictim = randomFromList(availablePlayers.filterNot(_.isMafia))
    removePlayer(nightVictim)

  }
}
