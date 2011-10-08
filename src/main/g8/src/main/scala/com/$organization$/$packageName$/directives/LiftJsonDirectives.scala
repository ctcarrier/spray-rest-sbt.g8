package com.$organization$.$packageName$.directives

import cc.spray.directives._
import cc.spray.{ValidationRejection, Reject, Pass, Directives}
import cc.spray

/**
 * @author chris_carrier
 * @version 10/2/11
 */


trait LiftJsonDirectives extends Directives {

  def contentBody = filter1 { ctx =>
    ctx.request.content match {
      case Some(httpContent) => Pass(new String(httpContent.buffer))
      case _ => Reject(ValidationRejection("body.required"))
    }
  }
 

}