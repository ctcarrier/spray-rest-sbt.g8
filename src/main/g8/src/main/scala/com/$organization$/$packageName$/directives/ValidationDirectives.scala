package com.$organization$.$packageName$.directives

import cc.spray.directives.SprayRoute0
import cc.spray.Pass._
import cc.spray.Reject._
import cc.spray.ValidationRejection._
import net.liftweb.json.JsonParser._
import cc.spray.directives._
import cc.spray.{ValidationRejection, Reject, Pass, Directives}
import cc.spray
import net.liftweb.json.JsonParser._
import net.liftweb.json.Formats
import com.$organization$.$packageName$.util.ObjectIdSerializer
import net.liftweb.json.Serialization._
import com.$organization$.$packageName$.response.ErrorResponse._
import com.$organization$.$packageName$.response.ErrorResponse

/**
 * @author chris_carrier
 * @version 10/2/11
 */


trait ValidationDirectives extends Directives {

  implicit val formats: Formats

  def requiringStrings(fieldNames: List[String]): SprayRoute0 = filter {
    ctx =>
      ctx.request.content match {
        case Some(httpContent) => {
          val json = parse(new String(httpContent.buffer))
          fieldNames.map{ xs =>
            val field = json \ xs
            field.extractOpt[String] match {
              case Some(x) => //Do nothing
              case None => xs
            }
          }
          Pass
        }
        case _ => Reject(ValidationRejection("body.required"))
      }
  }


}
