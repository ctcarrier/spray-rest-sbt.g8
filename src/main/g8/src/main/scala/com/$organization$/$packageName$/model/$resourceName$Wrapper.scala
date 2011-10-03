package com.$organization$.$packageName$.model

import com.novus.salat.annotations.raw.Ignore
import com.novus.salat.annotations.raw.Salat
import com.zub.ss.rest.mongo.MongoWrapper
import org.bson.types.ObjectId
import com.novus.salat.annotations.raw.{Ignore, Salat}
import org.bson.types.ObjectId

case class $resourceName$Wrapper(_id: Option[ObjectId],
                                       version: Long,
                                       content: List[Immutable$resourceName$])