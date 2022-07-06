package pl.muninn.markdown.common

import pl.muninn.markdown.common.Configuration

trait Configuration:
  def shouldEscapeLiterals: Boolean
  def safeInserting: Boolean
  def tableStrictPrinting: Boolean
  def withEscapeLiterals(shouldEscapeLiterals: Boolean): Configuration
  def withSafeInserting(safeInserting: Boolean): Configuration
  def withTableStrictPrinting(tableStrictPrinting: Boolean): Configuration

object Configuration:
  case class DefaultConfiguration(shouldEscapeLiterals: Boolean = false, safeInserting: Boolean = true, tableStrictPrinting: Boolean = false)
      extends Configuration:
    override def withEscapeLiterals(shouldEscapeLiterals: Boolean): Configuration     = copy(shouldEscapeLiterals = shouldEscapeLiterals)
    override def withSafeInserting(safeInserting: Boolean): Configuration             = copy(safeInserting = safeInserting)
    override def withTableStrictPrinting(tableStrictPrinting: Boolean): Configuration = copy(tableStrictPrinting = tableStrictPrinting)
