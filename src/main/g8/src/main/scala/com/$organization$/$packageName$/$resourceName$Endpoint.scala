package com.$organization$.$packageName$

import cc.spray.Directives
import model.{$resourceName$SearchParams, $resourceName$Wrapper, $resourceName$}
import net.liftweb.json.JsonParser._
import cc.spray.directives.LongNumber
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization._
import org.bson.types.ObjectId
import akka.event.EventHandler
import com.recursivity.commons.validator.{NotNullOrNone, ValidationGroup, ClasspathMessageResolver}
import service.$resourceName$Service
import cc.spray.http._
import HttpHeaders._
import HttpMethods._
import StatusCodes._
import MediaTypes._
import com.$organization$.$packageName$.model

/**
 * @author chris carrier
 */

trait $resourceName$EndPoint extends Directives {
  implicit val formats = DefaultFormats

  final val NOT_FOUND_MESSAGE = "resource.notFound"
  final val INTERNAL_ERROR_MESSAGE = "error"

  def JsonContent(content: String) = HttpContent(ContentType(`application/json`), content)

  EventHandler.info(this, "Starting actor.")
  val service: Dao

  def validateRequestBody(requestBody: String): List[(String, String)] = {
    EventHandler.info(this, "Class= " + this.getClass)
    EventHandler.info(this, "EnclosingClass= " + this.getClass.getEnclosingClass)
    val group = ValidationGroup(new ClasspathMessageResolver(this.getClass.getEnclosingClass))
    val json = parse(requestBody)

    List(("name"), ("description")).map {
      xs =>
        NotNullOrNone[Option[String]](xs, {
          (json \ xs).extractOpt[String]
        })
    }.foreach {
      xs => group.add(xs)
    }

    group.validateAndReturnErrorMessages
  }

  val restService = {
    // Debugging: /ping -> pong
    path("ping") {
      get {
        _.complete("pong")
      }
    } ~
      // Service implementation.
      pathPrefix("partners" / LongNumber / "customers" / LongNumber / "resources") {
        (partnerId, customerId) =>
          path("^[a-f0-9]+$".r) {
            configId =>
              get {
                ctx =>
                  try {
                    configService.get(new ObjectId(configId)).onComplete(f => {
                      f.result.get match {
                        case Some($resourceName$Wrapper(oid, version, content)) => ctx.complete(write(SuccessResponse[$resourceName$](version, ctx.request.path, 1, None, content.map(x => x.copy(id = oid)))))
                        case None => ctx.fail(StatusCodes.NotFound, write(ErrorResponse(1l, ctx.request.path, List(NOT_FOUND_MESSAGE))))
                      }
                    })
                  }
                  catch {
                    case e: IllegalArgumentException => {
                      ctx.fail(StatusCodes.NotFound, write(ErrorResponse(1l, ctx.request.path, List(NOT_FOUND_MESSAGE))))
                    }
                  }
              } ~
              put {
                ctx =>
                  try {
                    val content = new String(ctx.request.content.get.buffer)

                    val failures = validateRequestBody(content)
                    if (!failures.isEmpty) {
                      ctx.fail(StatusCodes.NotFound, write(ErrorResponse(1, ctx.request.path, failures.map(xs => xs._2))))
                    }
                    else {
                      val config = parse(content).extract[$resourceName$]

                      configService.put(new ObjectId(configId), customerId, partnerId, config).onTimeout(f => {
                        ctx.fail(StatusCodes.InternalServerError, write(ErrorResponse(1, ctx.request.path, List(INTERNAL_ERROR_MESSAGE))))
                      }).onComplete(f => {
                        f.result.get match {
                          case Some($resourceName$Wrapper(oid, version, content)) => ctx.complete(write(SuccessResponse[$resourceName$](version, ctx.request.path, 1, None, content.map(x => x.copy(id = oid)))))
                          case None => ctx.fail(StatusCodes.NotFound, write(ErrorResponse(1, ctx.request.path, List(NOT_FOUND_MESSAGE))))
                        }
                      }).onException {
                        case e => {
                          ctx.fail(StatusCodes.InternalServerError, write(ErrorResponse(1, ctx.request.path, List(e.getMessage))))
                        }
                      }
                    }
                  }
                  catch {
                    case e: IllegalArgumentException => {
                      ctx.fail(StatusCodes.NotFound, write(ErrorResponse(1l, ctx.request.path, List(NOT_FOUND_MESSAGE))))
                    }
                  }
              }
          } ~
            path("") {
              post {
                ctx =>
                  val content = new String(ctx.request.content.get.buffer)

                  val failures = validateRequestBody(content)
                  if (!failures.isEmpty) {
                    ctx.fail(StatusCodes.BadRequest, write(ErrorResponse(1, ctx.request.path, failures.map(xs => xs._2))))
                  }
                  else {
                    val config = parse(content).extract[$resourceName$]
                    val configWrapper = $resourceName$Wrapper(None, customerId, partnerId, 1, List(config))

                    configService.post(configWrapper).onTimeout(f => {
                      ctx.fail(StatusCodes.InternalServerError, write(ErrorResponse(1, ctx.request.path, List(INTERNAL_ERROR_MESSAGE))))
                      EventHandler.info(this, "Timed out")
                    }).onComplete(f => {
                      f.result.get match {
                        case Some($resourceName$Wrapper(oid, version, content)) => ctx.complete(HttpResponse(StatusCodes.Created, JsonContent(write(SuccessResponse[$resourceName$](version, ctx.request.path, 1, None, content.map(x => x.copy(id = oid)))))))
                        case None => ctx.fail(StatusCodes.BadRequest, write(ErrorResponse(1, ctx.request.path, List(NOT_FOUND_MESSAGE))))
                      }
                    }).onException {
                      case e => {
                        EventHandler.info(this, "Excepted: " + e)
                        ctx.fail(StatusCodes.InternalServerError, write(ErrorResponse(1, ctx.request.path, List(e.getMessage))))
                      }
                    }
                  }
              } ~
              parameters('name?, 'description?) { (name, description) =>
                get { ctx =>

                  configService.search($resourceName$SearchParams(name, description)).onTimeout(f => {
                    ctx.fail(StatusCodes.InternalServerError, write(ErrorResponse(1, ctx.request.path, List(INTERNAL_ERROR_MESSAGE))))
                            }).onComplete(f => {
                              f.result.get match {
                                case content: Some[List[$resourceName$]] => ctx.complete(write(SuccessResponse[$resourceName$](1, ctx.request.path, content.get.length, None, content.get)))
                                case None => ctx.fail(StatusCodes.NotFound, write(ErrorResponse(1, ctx.request.path, List(NOT_FOUND_MESSAGE))))
                              }
                            }).onException {
                              case e => {
                                e.printStackTrace()
                                ctx.fail(StatusCodes.InternalServerError, write(ErrorResponse(1, ctx.request.path, List(e.getMessage))))
                              }
                            }
                }
              }
            }
      }
  }

}