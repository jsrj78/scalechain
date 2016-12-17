package io.scalechain.blockchain.net

import java.net.InetSocketAddress

import io.netty.channel.ChannelFuture
import io.scalechain.blockchain.net.p2p.RetryingConnector
import io.scalechain.blockchain.proto.IPv6Address
import io.scalechain.blockchain.proto.NetworkAddress
import io.scalechain.blockchain.proto.Version
import io.scalechain.util.HexUtil
import io.scalechain.util.PeerAddress

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

object PeerToPeerNetworking {
  lateinit private var thePeerCommunicator : PeerCommunicator
  fun createPeerCommunicator(inboundPort : Int, peerAddresses : List<PeerAddress> ) : PeerCommunicator {

    // The peer set that keeps multiple PeerNode(s).
    val peerSet = PeerSet.create()

    // TODO : BUGBUG : Need to call nodeServer.shutdown before the process finishes ?
    val nodeServer = NodeServer(peerSet)
    val bindChannelFuture : ChannelFuture = nodeServer.listen(inboundPort)
    // Wait until the inbound port is bound.
    bindChannelFuture.sync()

    peerAddresses.map { peer ->
      RetryingConnector(peerSet, retryIntervalSeconds=1).connect(peer.address, peer.port)
    }

    thePeerCommunicator = PeerCommunicator(peerSet)
    return thePeerCommunicator
  }

  fun getPeerCommunicator() : PeerCommunicator = thePeerCommunicator
}
