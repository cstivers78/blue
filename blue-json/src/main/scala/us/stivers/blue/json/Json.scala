package us.stivers.blue.json

import com.fasterxml.jackson.databind.{MappingJsonFactory, Module, ObjectMapper}
import com.fasterxml.jackson.core.{JsonFactory, JsonGenerator, JsonParser}
import com.codahale.jerkson.{Generator, Parser, ScalaModule}
import com.fasterxml.jackson.datatype.joda.{JodaModule}

class Json(val mapper: ObjectMapper, val factory: JsonFactory) extends Parser with Generator

object Json {

  def apply(mapper: ObjectMapper, factory: JsonFactory): Json = new Json(mapper, factory)

  def apply(modules: Seq[Module]): Json = {
    val classLoader = Thread.currentThread().getContextClassLoader
    
    val mapper = new ObjectMapper
    mapper.registerModule(new ScalaModule(classLoader))
    mapper.registerModule(new JodaModule)
    modules.foreach(mapper.registerModule)

    val factory = new MappingJsonFactory(mapper)
    factory.enable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)
    factory.enable(JsonGenerator.Feature.AUTO_CLOSE_TARGET)
    factory.enable(JsonGenerator.Feature.QUOTE_FIELD_NAMES)
    factory.enable(JsonParser.Feature.ALLOW_COMMENTS)
    factory.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE)

    new Json(mapper, factory)
  }

  def apply(): Json = apply(Seq.empty)

}