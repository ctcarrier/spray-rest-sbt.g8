package com.$organization$.$packageName$.boot

import akka.actor.{Supervisor}
import akka.config.Supervision
import Supervision._
import akka.actor.Actor._
import cc.spray.utils.ActorHelpers._
import cc.spray.HttpService._
import com.mongodb.ServerAddress
import com.mongodb.casbah.{MongoDB, MongoConnection}
import cc.spray.{HttpService, RootService}
import cc.spray.utils.Logging
import com.$organization$.$packageName$._
import com.$organization$.$packageName$.dao._

/**
 * @author chris_carrier
 */


object ServiceInitializer extends App with Logging {
  
  log.info("Running Initializer")

  val akkaConfig = akka.config.Config.config

  val mongoUrl = akkaConfig.getString("mongodb.mongoUrl", "localhost")
  val mongoDbName = akkaConfig.getString("mongodb.database", "$organizationName$")
  val collection = akkaConfig.getString("mongodb.collection", "$resourceName$s")

  val urlList = mongoUrl.split(",").toList.map(new ServerAddress(_))
  val db = urlList match {
    case List(s) => MongoConnection(s)(mongoDbName)
    case s: List[String] => MongoConnection(s)(mongoDbName)
  }

  // ///////////// INDEXES for collections go here (include all lookup fields)
  //  configsCollection.ensureIndex(MongoDBObject("customerId" -> 1), "idx_customerId")
  val mainModule = new $resourceName$Endpoint {
    val service = new $resourceName$Dao(db(collection))
  }

  val httpService = actorOf(new HttpService(mainModule.restService))
  val rootService = actorOf(new RootService(httpService))

  // Start all actors that need supervision, including the root service actor.
  Supervisor(
    SupervisorConfig(
      OneForOneStrategy(List(classOf[Exception]), 3, 100),
      List(
        Supervise(httpService, Permanent),
        Supervise(rootService, Permanent)
      )
    )
  )

}
