package io.scalechain.wallet

import java.io.File

import io.scalechain.blockchain.storage.Storage
import io.scalechain.blockchain.transaction.ChainTestTrait
import java.util.*

import scala.util.Random

class WalletStoreSpec : WalletTestTrait(),
  WalletStoreAccountTestTrait,
  WalletStoreOutPointTestTrait,
  WalletStoreTransactionHashTestTrait,
  WalletStoreWalletOutputTestTrait, // Need to fix the protocol codec exception.
  WalletStoreWalletTransactionTestTrait {

  override val testPath = File("./target/unittests-WalletStoreSpec-${Random().nextLong()}")

  lateinit var store : WalletStore

  init {
    if (!Storage.isInitialized)
      Storage.initialize()

    testWalletStoreAccount(store, db)
    testWalletStoreOutPoint(store, db)
    testWalletStoreTransactionHash(store, db)
    testWalletStoreWalletOutput(store, db)
    testWalletStoreWalletTransaction(store, db)
  }


  override fun beforeEach() {
    super.beforeEach()

    store = WalletStore()
  }

  override fun afterEach() {
    super.afterEach()
  }
}
