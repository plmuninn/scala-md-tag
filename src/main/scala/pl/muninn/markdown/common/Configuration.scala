package pl.muninn.markdown.common

import pl.muninn.markdown.common.Configuration

trait Configuration:
  def shouldEscapeLiterals: Boolean
  def safeInserting: Boolean
  def tableStrictPrinting: Boolean

object Configuration:
  extension (conf: DefaultConfiguration)
    def withEscapeLiterals(shouldEscapeLiterals: Boolean)                              = conf.copy(shouldEscapeLiterals = shouldEscapeLiterals)
  extension (conf: DefaultConfiguration) def withSafeInserting(safeInserting: Boolean) = conf.copy(safeInserting = safeInserting)
  extension (conf: DefaultConfiguration)
    def withTableStrictPrinting(tableStrictPrinting: Boolean) = conf.copy(tableStrictPrinting = tableStrictPrinting)

  case class DefaultConfiguration(shouldEscapeLiterals: Boolean = true, safeInserting: Boolean = true, tableStrictPrinting: Boolean = false)
      extends Configuration
