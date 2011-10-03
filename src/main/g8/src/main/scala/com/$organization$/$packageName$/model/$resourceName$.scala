package com.$organization$.$packageName$.model

import org.bson.types.ObjectId
import com.novus.salat.annotations.raw.Ignore
import com.novus.salat.annotations.raw.Salat
import com.novus.salat.annotations.raw.{Ignore, Salat}

case class $resourceName$(@Ignore id: Option[ObjectId], name: String, description: String, nestedObject: Option[NestedObject], enabled: Boolean)