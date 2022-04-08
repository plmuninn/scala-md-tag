package pl.muninn.markdown.common

import pl.muninn.markdown.common.Configuration

trait Configuration:
  val shouldEscapeLiterals: Boolean = true
  val safeInserting: Boolean        = true

object Configuration:
  extension (conf: Configuration)
    def notSafeInserting: Configuration = new Configuration {
      override val shouldEscapeLiterals: Boolean = conf.shouldEscapeLiterals
      override val safeInserting: Boolean        = false
    }

    def notEscapingLiterals: Configuration = new Configuration {
      override val shouldEscapeLiterals: Boolean = false
      override val safeInserting: Boolean        = conf.safeInserting
    }

  case object DefaultConfiguration extends Configuration
