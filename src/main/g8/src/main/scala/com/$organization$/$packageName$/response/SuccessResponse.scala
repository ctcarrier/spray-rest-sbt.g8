package com.$organization$.$packageName$.model

/**
 * @author chris_carrier
 * @version 8/19/11
 */


case class SuccessResponse[T](version: Long,
                                    request: String,
                                    size: Long,
                                    requestParams: Option[Map[String, _]],
                                    content: List[T]) extends Response(version, request)