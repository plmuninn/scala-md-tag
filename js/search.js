// When the user clicks on the search box, we want to toggle the search dropdown
function displayToggleSearch(e) {
  e.preventDefault();
  e.stopPropagation();

  closeDropdownSearch(e);
  
  if (idx === null) {
    console.log("Building search index...");
    prepareIdxAndDocMap();
    console.log("Search index built.");
  }
  const dropdown = document.querySelector("#search-dropdown-content");
  if (dropdown) {
    if (!dropdown.classList.contains("show")) {
      dropdown.classList.add("show");
    }
    document.addEventListener("click", closeDropdownSearch);
    document.addEventListener("keydown", searchOnKeyDown);
    document.addEventListener("keyup", searchOnKeyUp);
  }
}

//We want to prepare the index only after clicking the search bar
var idx = null
const docMap = new Map()

function prepareIdxAndDocMap() {
  const docs = [  
    {
      "title": "Home",
      "url": "/scala-md-tag/",
      "content": "Simple library to generate Markdown Tags - inspired by scalatags It uses plain scala ( no cats, shapeless etc. ) Overview Library tries to guarantee: Simple html like API Properly displayed document Simple way to generate and compose markdown Getting started Add to yours build.sbt : resolvers ++= Seq( Resolver.sonatypeRepo(\"releases\"), Resolver.sonatypeRepo(\"snapshots\") ) libraryDependencies += \"pl.muninn\" %% \"scala-md-tag\" % \"0.2.3\" Then you need to only add in your code: import pl.muninn.scalamdtag._ And you are good to go. Supported tags Tag name Description / function Function name Text Representation of text fragment text Horizontal line Generate horizontal line --- hr Break line Symbol of breaking line br Fragment Represent concatenation of elements frag , :: Header Header like # Header h h1 h2 etc. Italic Italic _italic_ i Bold Bold **bold** b Code Cod `some code` code Paragraph / Markdown Paragraph like html p. p , markdown Image Generate markdown image img Link Generate markdown link a Code block Generate code block codeBlock Unordered list Generate unsorted list ul Ordered list Generate ordered list ol Table Generate markdown table table Concatenation Markdown tags provides three way of concatenation of tags / strings. :: was added to fix problem with + during concatenation with strings. Use it if you want to concatenate string with tag + will add tags to each other and creat new fragment ++ is to merge two fragments Example import pl.muninn.scalamdtag._ markdown( h1(\"Scala markdown tags\"), p( \"Simple library to generate Markdown Tags - inspired by\", a(\"scalatags\", \"https://github.com/lihaoyi/scalatags\"), br, \"It uses plain scala (\" :: b(\"no cats, shapeless etc.\") :: \")\", ), h1(\"Overview\"), p(\"Library tries to guarantee:\", ul( \"Simple html like API\", \"Properly displayed document\", \"Simple way to generate and compose markdown\" ) ), h1(\"Getting started\"), p( frag(\"Add to yours\", code(\"build.sbt\"), \":\"), codeBlock( \"\"\"resolvers ++= Seq( | Resolver.sonatypeRepo(\"releases\"), | Resolver.sonatypeRepo(\"snapshots\") |) | |libraryDependencies += \"pl.muninn\" %% \"scala-md-tag\" % \"0.2\"\"\"\".stripMargin), \"Then you need to only add in your code:\", codeBlock(\"scala\", \"import pl.muninn.scalamdtag._\"), \"And you are good to go.\" ), h1(\"Example\"), p( \"Example of usage, you can find \", a(\"here.\", \"./src/Readme.sc\") ), p( \"Supported tags:\", table( (\"Tag name\", \"Description / function\", \"Function name\"), List( (\"Text\", \"Representation of text fragment\", code(\"text\")), (\"Horizontal line\", \"Generate horizontal line\" :: code(\"---\"), code(\"hr\")), (\"Break line\", \"Symbol of breaking line\", code(\"br\")), (\"Fragment\", \"Represent concatenation of elements\", code(\"frag\") + \",\" + code(\"::\")), (\"Header\", \"Header like\" :: code(\"# Header\"), code(\"h\") + code(\"h1\") + code(\"h2\") + \"etc.\"), (\"Italic\", \"Italic\" :: code(\"_italic_\"), code(\"i\")), (\"Bold\", \"Bold\" :: code(\"**bold**\"), code(\"b\")), (\"Code\", \"Cod \\\\`some code\\\\`\", code(\"code\")), (\"Paragraph / Markdown\", \"Paragraph like html p.\", code(\"p\") + \",\" + code(\"markdown\")), (\"Image\", \"Generate markdown image\", code(\"img\")), (\"Link\", \"Generate markdown link\", code(\"a\")), (\"Code block\", \"Generate code block\", code(\"codeBlock\")), (\"Unordered list\", \"Generate unsorted list\", code(\"ul\")), (\"Ordered list\", \"Generate ordered list\", code(\"ol\")), (\"Table\", \"Generate markdown table\", code(\"table\")), ) ) ), h1(\"Concatenation\"), p( \"Markdown tags provides three way of concatenation of tags / strings.\", br, ul( frag(code(\"::\"), \"was added to fix problem with\", code(\"+\"), \"during concatenation with strings. Use it if you want to concatenate string with tag\"), frag(code(\"+\"), \"will add tags to each other and creat new fragment\"), frag(code(\"++\"), \"is to merge two fragments\") ) ), p( h1(\"What is in plan to 1.0.0\"), taskList( task(\"Create decoding markdown text to scala-md-structures\"), ) ) ).md // res0: String = \"\"\"# Scala markdown tags // // Simple library to generate Markdown Tags - inspired by [scalatags](https://github.com/lihaoyi/scalatags) // It uses plain scala ( **no cats, shapeless etc.** ) // # Overview // // Library tries to guarantee: // * Simple html like API // * Properly displayed document // * Simple way to generate and compose markdown // // # Getting started // // Add to yours `build.sbt` : // ``` // resolvers ++= Seq( // Resolver.sonatypeRepo(\"releases\"), // Resolver.sonatypeRepo(\"snapshots\") // ) // // libraryDependencies += \"pl.muninn\" %% \"scala-md-tag\" % \"0.2\" // ``` // Then you need to only add in your code: // ```scala // import pl.muninn.scalamdtag._ // ``` // And you are good to go. // # Example // // Example of usage, you can find [here.](./src/Readme.sc) // // Supported tags: // // | Tag name | Description / function | Function name | // | -------------------- | ----------------------------------- | ------------------ | // | Text | Representation of text fragment | `text` | // | Horizontal line | Generate horizontal line `---` | `hr` | // | Break line | Symbol of breaking line | `br` | // | Fragment | Represent concatenation of elements | `frag` , `::` | // | Header | Header like `# Header` | `h` `h1` `h2` etc. | // | Italic | Italic `_italic_` | `i` ... What is in plan to 1.0.0 Create decoding markdown text to scala-md-structures Handle different markdown like formats ex. Jira"
    } ,        
  ];

  idx = lunr(function () {
    this.ref("title");
    this.field("content");

    docs.forEach(function (doc) {
      this.add(doc);
    }, this);
  });

  docs.forEach(function (doc) {
    docMap.set(doc.title, doc.url);
  });
}

// The onkeypress handler for search functionality
function searchOnKeyDown(e) {
  const keyCode = e.keyCode;
  const parent = e.target.parentElement;
  const isSearchBar = e.target.id === "search-bar";
  const isSearchResult = parent ? parent.id.startsWith("result-") : false;
  const isSearchBarOrResult = isSearchBar || isSearchResult;

  if (keyCode === 40 && isSearchBarOrResult) {
    // On 'down', try to navigate down the search results
    e.preventDefault();
    e.stopPropagation();
    selectDown(e);
  } else if (keyCode === 38 && isSearchBarOrResult) {
    // On 'up', try to navigate up the search results
    e.preventDefault();
    e.stopPropagation();
    selectUp(e);
  } else if (keyCode === 27 && isSearchBarOrResult) {
    // On 'ESC', close the search dropdown
    e.preventDefault();
    e.stopPropagation();
    closeDropdownSearch(e);
  }
}

// Search is only done on key-up so that the search terms are properly propagated
function searchOnKeyUp(e) {
  // Filter out up, down, esc keys
  const keyCode = e.keyCode;
  const cannotBe = [40, 38, 27];
  const isSearchBar = e.target.id === "search-bar";
  const keyIsNotWrong = !cannotBe.includes(keyCode);
  if (isSearchBar && keyIsNotWrong) {
    // Try to run a search
    runSearch(e);
  }
}

// Move the cursor up the search list
function selectUp(e) {
  if (e.target.parentElement.id.startsWith("result-")) {
    const index = parseInt(e.target.parentElement.id.substring(7));
    if (!isNaN(index) && (index > 0)) {
      const nextIndexStr = "result-" + (index - 1);
      const querySel = "li[id$='" + nextIndexStr + "'";
      const nextResult = document.querySelector(querySel);
      if (nextResult) {
        nextResult.firstChild.focus();
      }
    }
  }
}

// Move the cursor down the search list
function selectDown(e) {
  if (e.target.id === "search-bar") {
    const firstResult = document.querySelector("li[id$='result-0']");
    if (firstResult) {
      firstResult.firstChild.focus();
    }
  } else if (e.target.parentElement.id.startsWith("result-")) {
    const index = parseInt(e.target.parentElement.id.substring(7));
    if (!isNaN(index)) {
      const nextIndexStr = "result-" + (index + 1);
      const querySel = "li[id$='" + nextIndexStr + "'";
      const nextResult = document.querySelector(querySel);
      if (nextResult) {
        nextResult.firstChild.focus();
      }
    }
  }
}

// Search for whatever the user has typed so far
function runSearch(e) {
  if (e.target.value === "") {
    // On empty string, remove all search results
    // Otherwise this may show all results as everything is a "match"
    applySearchResults([]);
  } else {
    const tokens = e.target.value.split(" ");
    const moddedTokens = tokens.map(function (token) {
      // "*" + token + "*"
      return token;
    })
    const searchTerm = moddedTokens.join(" ");
    const searchResults = idx.search(searchTerm);
    const mapResults = searchResults.map(function (result) {
      const resultUrl = docMap.get(result.ref);
      return { name: result.ref, url: resultUrl };
    })

    applySearchResults(mapResults);
  }

}

// After a search, modify the search dropdown to contain the search results
function applySearchResults(results) {
  const dropdown = document.querySelector("div[id$='search-dropdown'] > .dropdown-content.show");
  if (dropdown) {
    //Remove each child
    while (dropdown.firstChild) {
      dropdown.removeChild(dropdown.firstChild);
    }

    //Add each result as an element in the list
    results.forEach(function (result, i) {
      const elem = document.createElement("li");
      elem.setAttribute("class", "dropdown-item");
      elem.setAttribute("id", "result-" + i);

      const elemLink = document.createElement("a");
      elemLink.setAttribute("title", result.name);
      elemLink.setAttribute("href", result.url);
      elemLink.setAttribute("class", "dropdown-item-link");

      const elemLinkText = document.createElement("span");
      elemLinkText.setAttribute("class", "dropdown-item-link-text");
      elemLinkText.innerHTML = result.name;

      elemLink.appendChild(elemLinkText);
      elem.appendChild(elemLink);
      dropdown.appendChild(elem);
    });
  }
}

// Close the dropdown if the user clicks (only) outside of it
function closeDropdownSearch(e) {
  // Check if where we're clicking is the search dropdown
  if (e.target.id !== "search-bar") {
    const dropdown = document.querySelector("div[id$='search-dropdown'] > .dropdown-content.show");
    if (dropdown) {
      dropdown.classList.remove("show");
      document.documentElement.removeEventListener("click", closeDropdownSearch);
    }
  }
}
