# blue-core

Provides the data structures and values for working with HTTP. 

The `http` package provides classes and values for HTTP headers, status codes, method, and versions. The idea is that you would use the classes and values while working with the protocol, rather than strings. This can reduce potential errors in your code, while also providing a consistent interface to the protocol.

The `http` package also provides classes for HTTP request and response. These classes are defined as case classes with default constructor arguments, making it easy to create and copy instances.

The `http` package provides the `Content` class, for encapsulating the body of the request and response. The content wraps a `java.nio.ByteBuffer`, and provides a `Content-Type` label for specifying the type of media stored in the buffer.

The `codec` package provides encoder and decoder type definitions, as well as definitions for `Content` codecs. 

The `uri` package provides data types for working with [URI][]. The `URI` class is a case class with default constructor arguments, making it easy to create and copy instances. The `URI` class uses `Path` and `Query` classed for representing the `path` and `query` parts of a URI. The `Path` class makes it easier to work with path, such as providing pre-split path segments, accessors for dirname, basename and extname. The `Query` class makes it easier to work with query-string, by providing easy access to the parameters as key-value pairs.

The `resolve` package provides facility for resolving URIs. This is provided in core, as URI resolution is a commonly used function.

The `respond` package provides facilities for creating HTTP responses. The `Respond` object provides ability to create responses using positional arguments, rather than the named arguments of the `Response` class. The `Responder` is able to generate a `Response` from other data types.


[URI]: http://en.wikipedia.org/wiki/Uniform_resource_identifier