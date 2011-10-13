package com.$organization$.$packageName$.dao

import com.mongodb.casbah.Imports._
import akka.dispatch.Future
import java.util.Date
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.ServerAddress
import com.mongodb.DBObject
import com.mongodb.{ServerAddress, DBObject}
import com.mongodb.casbah.{MongoDB, MongoConnection}
import com.mongodb.casbah.commons.MongoDBObject
import com.$organization$.$packageName$._
import com.$organization$.$packageName$.model._

/**
 * @author chris carrier
 */

class $resourceName$Dao(mongoCollection: MongoCollection) extends Dao {

  def get$resourceName$(key: ObjectId) = {
    Future {
      val q = MongoDBObject("_id" -> key)
      val dbo = mongoCollection.findOne(q)
      dbo.map(f => grater[$resourceName$Wrapper].asObject(f))
    }
  }

  def create$resourceName$(modelWrapper: $resourceName$Wrapper) = {
    Future {
      val dbo = grater[$resourceName$Wrapper].asDBObject(modelWrapper)
      mongoCollection += dbo
      Some(modelWrapper.copy(_id = dbo.getAs[org.bson.types.ObjectId]("_id"))) 
    }
  }

  def update$resourceName$(key: ObjectId, model: $resourceName$) = {
    Future {
      val query = MongoDBObject("_id" -> key)
      val update = \$addToSet("content" -> model)

      mongoCollection.update(query, update, false, false, WriteConcern.Safe)

      val dbo = mongoCollection.findOne(query)
      dbo.map(f => grater[$resourceName$Wrapper].asObject(f))
    }
  }

  def search$resourceName$(searchObj: MongoDBObject) = {
    Future {
      val data = mongoCollection.find(searchObj)
      val dataList = data.map(f => grater[$resourceName$Wrapper].asObject(f).content).flatten.toList

      if (dataList.isEmpty) {
        None
      }
      else {
        Some(dataList)
      }
    }
  }
}
