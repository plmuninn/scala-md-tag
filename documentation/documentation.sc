//> using scala "3.1"
//> using lib "com.lihaoyi::pprint:0.7.3"

import docs.readme
import utils.files.*

val pages = Map(
  "readme.md" -> readme.markdown
)

pprint.pprintln("Starting gescala-cli . -- scalanerating files")
generateFiles(pages)
pprint.pprintln("Files generated")
