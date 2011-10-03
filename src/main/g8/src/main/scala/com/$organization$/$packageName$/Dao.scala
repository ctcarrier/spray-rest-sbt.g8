package com.$organization$.$packageName$

import _root_.akka.dispatch.Future
import akka.dispatch.Future
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject
import com.$organization$.$packageName$.model._

trait Dao {
  def get$resourceName$(key: ObjectId): Future[Option[$resourceName$Wrapper]]
  def create$resourceName$(modelWrapper: $resourceName$Wrapper): Future[Option[$resourceName$Wrapper]]
  def update$resourceName$(key: ObjectId, model: $resourceName$): Future[Option[$resourceName$Wrapper]]
  def search$resourceName$(searchObj: MongoDBObject): Future[Option[List[$resourceName$]]]
}