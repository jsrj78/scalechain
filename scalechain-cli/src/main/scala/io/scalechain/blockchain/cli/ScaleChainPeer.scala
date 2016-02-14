package io.scalechain.blockchain.cli

import java.net.InetSocketAddress

import akka.actor.{Props, ActorSystem}
import akka.stream.ActorMaterializer
import akka.util
import com.typesafe.config.{ConfigFactory, Config}
import io.scalechain.blockchain.cli.api.{RpcInvoker, Parameters}
import io.scalechain.blockchain.net._
import io.scalechain.blockchain.proto.{ProtocolMessage, IPv6Address, NetworkAddress, Version}
import io.scalechain.util.HexUtil._
import scala.collection.JavaConverters._

/** A ScaleChainPeer that connects to other peers and accepts connection from other peers.
  */
object ScaleChainPeer {
  case class Parameters(
    inboundPort : Int = io.scalechain.util.Config.scalechain.getInt("scalechain.p2p.inbound_port")
  )

  def main(args: Array[String]) = {
    val parser = new scopt.OptionParser[Parameters]("scalechain") {
      head("scalechain", "1.0")
      opt[Int]('p', "port") action { (x, c) =>
        c.copy(inboundPort = x) } text("The inbound port to use to accept connection from other peers.")
    }

    // parser.parse returns Option[C]
    parser.parse(args, Parameters()) match {
      case Some(params) => {
        startPeer(params.inboundPort)
      }

      case None =>
      // arguments are bad, error message will have been displayed
    }
  }


  def startPeer(inboundPort : Int): Unit = {
    case class PeerAddress(address : String, port : Int) {
      def isMyself() = (address == "localhost" || address == "127.0.0.1") && (port == inboundPort )
    }

    /**
      * Read list of peers from scalechain.conf
      * It contains list of peer address and port.
      *
      * scalechain {
      *   p2p {
      *     peers = [
      *       { address:"127.0.0.1", port:"7643" },
      *       { address:"127.0.0.1", port:"7644" },
      *       { address:"127.0.0.1", port:"7645" }
      *     ]
      *   }
      * }
      */
    val peers = io.scalechain.util.Config.scalechain.getConfigList("scalechain.p2p.peers").asScala.toList.map { peer =>
      PeerAddress( peer.getString("address"), peer.getInt("port") )
    }

    /** Create the actor system.
      */
    implicit val system = ActorSystem("ScaleChainPeer", ConfigFactory.load.getConfig("server"))

    val materializer = ActorMaterializer()

    /** The peer broker that keeps multiple PeerNode(s) and routes messages based on the origin address of the message.
      */
    //val peerBroker = system.actorOf(Props[PeerBroker], "peerBroker")

    /** The consumer that opens an inbound port, and waits for connections from other peers.
      */
    val server = StreamServerLogic(system, materializer, new InetSocketAddress("127.0.0.1", inboundPort))

    /** The mediator that creates outbound connections to other peers listed in the scalechain.p2p.peers configuration.
      */
    peers.map { peer =>
      if (!peer.isMyself()) {
        val peerAddress = new InetSocketAddress(peer.address, peer.port)
        val client = StreamClientLogic(system, materializer, peerAddress)

        // Send StartPeer message to the peer, so that it can initiate the node start-up process.
        //peerBroker ! (clientProducer /*connected peer*/, peerAddress, Some(StartPeer) /* No message to send to the peer node */  )
      }
    }
  }
}