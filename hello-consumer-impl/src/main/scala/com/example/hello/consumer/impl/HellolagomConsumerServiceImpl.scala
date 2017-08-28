package com.example.hello.consumer.impl

import akka.{Done, NotUsed}
import akka.stream.scaladsl.Flow
import com.example.hello.api.{GreetingMessage, GreetingMessageChanged, HellolagomService}
import com.example.hello.consumer.api.HellolagomConsumerService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import scala.concurrent.Future

class HellolagomConsumerServiceImpl(hellolagomService: HellolagomService) extends HellolagomConsumerService {

  hellolagomService.greetingsTopic()
    .subscribe
    .atLeastOnce(
      Flow[GreetingMessageChanged].map{ msg =>
        putGreetingMessage(msg)
        Done
      }
    )

  private def putGreetingMessage(message: GreetingMessageChanged) = {
    println(message.message)
  }

  override def printMessage(): ServiceCall[NotUsed, String] = {
    ServiceCall{ _ =>
      Future.successful("Consumer Output")
    }
  }

}
