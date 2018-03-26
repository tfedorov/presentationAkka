package com.epam.maf

import com.epam.maf.EmulatorApp.random
import com.epam.maf.Table.{availablePlayers, removePlayer}

object NightKillMaker {
  def makeNightStep(): Unit = {

    val peaceResidents = availablePlayers.filterNot(_.isMafia)
    val nightVictim = peaceResidents(random.nextInt(peaceResidents.size))
    removePlayer(nightVictim)

  }
}
