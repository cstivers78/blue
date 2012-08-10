package us.stivers.blue.route

import scala.collection.immutable.{Set,SetProxy}

/**
 * Set of Routes.
 * Provides a low priority operator '|', for concatenating Routes.
 */
trait RouteSet extends Set[Route] with SetProxy[Route] {
  def routes: Set[Route]
  def self: Set[Route] = routes
  def |(route: Route): RouteSet = RouteSet(self + route)
  def |(routes: Set[Route]): RouteSet = RouteSet(self ++ routes)
}

object RouteSet {
  def apply(r: Route): RouteSet = new RouteSet {
    val routes: Set[Route] = Set(r)
  }
  def apply(r: Set[Route]): RouteSet = new RouteSet {
    val routes: Set[Route] = r
  }
}