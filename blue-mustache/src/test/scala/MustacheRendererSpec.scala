// package us.stivers.blue.mustache

// import us.stivers.blue.{Result,Success,Failure}
// import us.stivers.blue.http.{Response}
// import us.stivers.blue.resolve.{Resolver,FallbackResolver,ResourceResolver,FileResolver}
// import org.specs2._
// import specification._

// class MustacheRendererSpec extends Specification { def is =
  
//   "MustacheRenderer"                       ^
//     "Given a MustacheRenderer instance"    ^ renderer ^
//     "When I compile: ${hello.mustache}"    ^ render ^
//     "Then I should get: ${Hello Bob!}"     ^ verify ^
//                                            end ^
//   "MustacheRenderer"                        ^
//     "Given a MustacheRenderer instance"     ^ renderer ^
//     "When I compile: ${layouts/page1.mustache}" ^ render ^
//     "Then I should get: ${Hello Bob!}"      ^ verify ^
//                                             end ^
//   "MustacheRenderer"                        ^
//     "Given a MustacheRenderer instance"     ^ renderer ^
//     "When I compile: ${layouts/sub/page2.mustache}" ^ render ^
//     "Then I should get: ${Hello Bob!}"      ^ verify ^
//                                             end


//   val resolver = FallbackResolver(
//     ResourceResolver(),
//     FileResolver("blue-mustache/src/test/resources/"),
//     FileResolver("src/test/resources/")
//   )

//   val mustache = MustacheRenderer(resolver)

//   object renderer extends Given[MustacheRenderer] {
//     def extract(text: String) = mustache
//   }

//   object render extends When[MustacheRenderer,Result[String]] {
//     def extract(render: MustacheRenderer, text: String) = render(extract1(text),Map("name"->"Bob"))
//   }

//   object verify extends Then[Result[String]] {
//     def extract(result: Result[String], text: String) = {
//       result match {
//         case Success(s) if s == extract1(text) => ok
//       }
//     }
//   }

// }