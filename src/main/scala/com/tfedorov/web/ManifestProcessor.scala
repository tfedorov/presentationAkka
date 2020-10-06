package com.tfedorov.web

import scala.io.Source

object ManifestProcessor {

  def manifestPath: String = Thread.currentThread().getContextClassLoader.getResource("META-INF/MANIFEST.MF").getFile

  def content: String = Source.fromResource("META-INF/MANIFEST.MF").getLines.mkString("\n")
}
