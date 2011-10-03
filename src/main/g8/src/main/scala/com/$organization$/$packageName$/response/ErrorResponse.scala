package com.$organization$.$packageName$.response

/**
 * @author chris_carrier
 * @version 8/19/11
 */


case class ErrorResponse(version: Long, request: String, errors: List[String]) extends Response(version, request)