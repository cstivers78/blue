# Blue Soup -- DEFUNCT

Blue Soup is a templating library based on JSoup. It allows you to bind an HTML document to a tranformation functions to generate a final document.


HTML: `hello.html`

	<html>
	  <head>
	    <title>Greeting</title>
	  </head>
	  <body>
	    <div class="test"></div>
	    <h1>Greeting</h1>
	    <div class="test"></div>
	  </body>
	</html>

View: `hello.scala`

	object hello extends View {
	  def render(doc: Document, attrs: Map[String,Any]) = {
			attrs.get("title").foreach(doc.select("> head > title").text)
			attrs.get("message").foreach(doc.select("> body > h1").text)
			doc
	  }
	}

Filter: `removeTests.scala`

	object removeTests extends Filter {
	  def render(doc: Document) = {
			doc.select(".test").remove
			doc
	  }
	}

Render:

	val resolver = ResourceResolver()
	
	val mappings = Map (
	  "hello.html" -> (removeTests andThen hello)
	)
	
	val render = SoupRenderer(resolver,mappings)

	render("hello.html", Map(
		"title"		-> "Hi",
		"message"	-> "Hello"
	))

Output:

	<html>
	  <head>
	    <title>Hi</title>
	  </head>
	  <body>
	    <h1>Hello</h1>
	  </body>
	</html>
