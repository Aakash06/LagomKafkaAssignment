package com.example.hello.consumer.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.hello.api.HellolagomService
import com.example.hello.consumer.api.HellolagomConsumerService
import com.lightbend.lagom.scaladsl.broker.kafka.{LagomKafkaClientComponents, LagomKafkaComponents}
import com.softwaremill.macwire._

class HellolagomConsumerLoader extends LagomApplicationLoader {
/**
  * Created by knoldus on 24/8/17.
  */
  override def load(context: LagomApplicationContext): LagomApplication =
    new HellolagomConsumerApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new HellolagomConsumerApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[HellolagomService])
}

abstract class HellolagomConsumerApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    //with CassandraPersistenceComponents
    with LagomKafkaClientComponents
    with AhcWSComponents {
  /*applicationLifecycle.addStopHook { () =>
    persistentEntityRegistry.gracefulShutdown(15.seconds)
  }*/

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[HellolagomConsumerService](wire[HellolagomConsumerServiceImpl])

  lazy val helloService = serviceClient.implement[HellolagomService]

  // Register the JSON serializer registry
  //override lazy val jsonSerializerRegistry = HellolagomSerializerRegistry

  // Register the hello-lagom persistent entity
  //persistentEntityRegistry.register(wire[HellolagomEntity])
}
