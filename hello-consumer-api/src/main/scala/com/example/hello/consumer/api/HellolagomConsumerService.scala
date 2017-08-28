package com.example.hello.consumer.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.lightbend.lagom.scaladsl.api.transport.Method

trait HellolagomConsumerService extends Service {

  override def descriptor: Descriptor = {
    import Service._

    named("consumer").withCalls(
      restCall(Method.GET, "/api/printMessage", printMessage _)
    ).withAutoAcl(true)
  }

  def printMessage(): ServiceCall[NotUsed, String]

}
