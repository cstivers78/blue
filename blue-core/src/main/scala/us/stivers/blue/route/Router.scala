package us.stivers.blue.route

import scalax.util.{Try,Success,Failure}
import us.stivers.blue.http.{Request}

/**
 * Finds the best match route for a given request.
 */
trait Router extends (Request=>Try[Route]) {
  def routes: Iterable[Route]
}
