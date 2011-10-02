package com.zub.ss.prototype.model

import com.mongodb.casbah.Imports._

/**
 * @author chris_carrier
 * @version 9/30/11
 */

object $resourceName$SearchParams {

  implicit def toDbo(p: $resourceName$SearchParams): MongoDBObject = {

    val query = MongoDBObject()
    
    if (p.name != None) {
      query += "content.name" -> p.name.get
    }


    if (p.description != None) {
      query += "content.description" -> p.description.get
    }

    query
  }
}

case class $resourceName$SearchParams(name: Option[String], description: Option[String])