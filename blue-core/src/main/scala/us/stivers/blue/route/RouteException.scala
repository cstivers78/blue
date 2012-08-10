package us.stivers.blue.route

import us.stivers.blue.http.{Request,Status}

/**
 * Used when Route could not be matched with the given Request. It also specifies 
 * the appropriate HTTP Status code to use in response to the routing failure.
 */
case class RouteException(req: Request, status: Status) extends Exception