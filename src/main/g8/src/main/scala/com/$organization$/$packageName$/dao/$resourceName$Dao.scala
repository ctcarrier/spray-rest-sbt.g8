package com.$organization$.$resourceName$.dao

import _root_.akka.dispatch.Future._
import akka.dispatch.Future._
import com.mongodb.casbah.commons.MongoDBObject._
import com.mongodb.casbah.Imports._
import akka.actor.Actor._
import akka.dispatch.Future
import com.mongodb.casbah.commons.MongoDBObject
import java.util.Date
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.ServerAddress
import com.mongodb.DBObject
import com.mongodb.{ServerAddress, DBObject}
import com.mongodb.casbah.MongoConnection._
import com.mongodb.casbah.{MongoDB, MongoConnection}
import com.mongodb.casbah.TypeImports._
import com.mongodb.casbah.commons.TypeImports._
import com.mongodb.casbah.commons.MongoDBObject._
import com.novus.salat._
import com.mongodb.casbah.query.SetOp._
import com.mongodb.casbah.commons.MongoDBObject
import com.$organization$.$packageName$.model._

/**
 * @author chris carrier
 * // TODO need a better way to have this as a singleton "object" and use db as an implicit val.
 */

class $resourceName$DAO(mongoCollection: MongoCollection) extends Dao {

  def get(key: ObjectId) = {
    Future {
      val q = MongoDBObject("_id" -> key)
      val dbo = mongoCollection.findOne(q)
      dbo.map(f => grater[modelWrapper].asObject(f))
    }
  }

  def post(modelWrapper: $resourceName$Wrapper) = {
    Future {
      val dbo = grater[modelWrapper].asDBObject(modelWrapper)
      mongoCollection += dbo
      Some(modelWrapper.copy(_id = dbo.getAs[org.bson.types.ObjectId]("_id"))) // TODO grater was not working here. If this were an actor you would just do a "self.channel" as before.
    }
  }

  def put(key: ObjectId, model: $resourceName$) = {
    Future {
      val query = MongoDBObject("_id" -> key)
      val update = $addToSet("content" -> model)

      modelCollection.update(query, update, false, false, WriteConcern.Safe)

      val dbo = modelCollection.findOne(query)
      dbo.map(f => grater[modelWrapper].asObject(f))
    }
  }

  def search(searchObj: MongoDBObject) = {
    Future {
      val data = modelCollection.find(searchObj)
      val dataList = data.map(f => grater[modelWrapper].asObject(f).content).flatten.toList

      if (dataList.isEmpty) {
        None
      }
      else {
        Some(dataList)
      }
    }
  }
}
