package io.scalechain.blockchain.api.command.rawtx.p2

import io.scalechain.blockchain.api.command.RpcCommand
import io.scalechain.blockchain.api.domain.{RpcRequest, RpcResult}

/*
  CLI command :
    bitcoin-cli -testnet getrawtransaction \
      ef7c0cbf6ba5af68d2ea239bba709b26ff7b0b669839a63bb01c2cb8e8de481e

  CLI output(wrapped) :
    0100000001268a9ad7bfb21d3c086f0ff28f73a064964aa069ebb69a9e437da8\
    5c7e55c7d7000000006b483045022100ee69171016b7dd218491faf6e13f53d4\
    0d64f4b40123a2de52560feb95de63b902206f23a0919471eaa1e45a0982ed28\
    8d374397d30dff541b2dd45a4c3d0041acc0012103a7c1fd1fdec50e1cf3f0cc\
    8cb4378cd8e9a2cee8ca9b3118f3db16cbbcf8f326ffffffff0350ac60020000\
    00001976a91456847befbd2360df0e35b4e3b77bae48585ae06888ac80969800\
    000000001976a9142b14950b8d31620c6cc923c5408a701b1ec0a02088ac002d\
    3101000000001976a9140dfc8bafc8419853b34d5e072ad37d1a5159f58488ac\
    00000000

  CLI command :
    bitcoin-cli -testnet getrawtransaction \
      ef7c0cbf6ba5af68d2ea239bba709b26ff7b0b669839a63bb01c2cb8e8de481e 1

  CLI output :
    {
        "hex" : "0100000001268a9ad7bfb21d3c086f0ff28f73a064964aa069ebb69a9e437da85c7e55c7d7000000006b483045022100ee69171016b7dd218491faf6e13f53d40d64f4b40123a2de52560feb95de63b902206f23a0919471eaa1e45a0982ed288d374397d30dff541b2dd45a4c3d0041acc0012103a7c1fd1fdec50e1cf3f0cc8cb4378cd8e9a2cee8ca9b3118f3db16cbbcf8f326ffffffff0350ac6002000000001976a91456847befbd2360df0e35b4e3b77bae48585ae06888ac80969800000000001976a9142b14950b8d31620c6cc923c5408a701b1ec0a02088ac002d3101000000001976a9140dfc8bafc8419853b34d5e072ad37d1a5159f58488ac00000000",
        "txid" : "ef7c0cbf6ba5af68d2ea239bba709b26ff7b0b669839a63bb01c2cb8e8de481e",
        "version" : 1,
        "locktime" : 0,
        "vin" : [
            {
                "txid" : "d7c7557e5ca87d439e9ab6eb69a04a9664a0738ff20f6f083c1db2bfd79a8a26",
                "vout" : 0,
                "scriptSig" : {
                    "asm" : "3045022100ee69171016b7dd218491faf6e13f53d40d64f4b40123a2de52560feb95de63b902206f23a0919471eaa1e45a0982ed288d374397d30dff541b2dd45a4c3d0041acc001 03a7c1fd1fdec50e1cf3f0cc8cb4378cd8e9a2cee8ca9b3118f3db16cbbcf8f326",
                    "hex" : "483045022100ee69171016b7dd218491faf6e13f53d40d64f4b40123a2de52560feb95de63b902206f23a0919471eaa1e45a0982ed288d374397d30dff541b2dd45a4c3d0041acc0012103a7c1fd1fdec50e1cf3f0cc8cb4378cd8e9a2cee8ca9b3118f3db16cbbcf8f326"
                },
                "sequence" : 4294967295
            }
        ],
        "vout" : [
            {
                "value" : 0.39890000,
                "n" : 0,
                "scriptPubKey" : {
                    "asm" : "OP_DUP OP_HASH160 56847befbd2360df0e35b4e3b77bae48585ae068 OP_EQUALVERIFY OP_CHECKSIG",
                    "hex" : "76a91456847befbd2360df0e35b4e3b77bae48585ae06888ac",
                    "reqSigs" : 1,
                    "type" : "pubkeyhash",
                    "addresses" : [
                        "moQR7i8XM4rSGoNwEsw3h4YEuduuP6mxw7"
                    ]
                }
            },
            {
                "value" : 0.10000000,
                "n" : 1,
                "scriptPubKey" : {
                    "asm" : "OP_DUP OP_HASH160 2b14950b8d31620c6cc923c5408a701b1ec0a020 OP_EQUALVERIFY OP_CHECKSIG",
                    "hex" : "76a9142b14950b8d31620c6cc923c5408a701b1ec0a02088ac",
                    "reqSigs" : 1,
                    "type" : "pubkeyhash",
                    "addresses" : [
                        "mjSk1Ny9spzU2fouzYgLqGUD8U41iR35QN"
                    ]
                }
            },
            {
                "value" : 0.20000000,
                "n" : 2,
                "scriptPubKey" : {
                    "asm" : "OP_DUP OP_HASH160 0dfc8bafc8419853b34d5e072ad37d1a5159f584 OP_EQUALVERIFY OP_CHECKSIG",
                    "hex" : "76a9140dfc8bafc8419853b34d5e072ad37d1a5159f58488ac",
                    "reqSigs" : 1,
                    "type" : "pubkeyhash",
                    "addresses" : [
                        "mgnucj8nYqdrPFh2JfZSB1NmUThUGnmsqe"
                    ]
                }
            }
        ],
        "blockhash" : "00000000103e0091b7d27e5dc744a305108f0c752be249893c749e19c1c82317",
        "confirmations" : 88192,
        "time" : 1398734825,
        "blocktime" : 1398734825
    }

  Json-RPC request :
    {"jsonrpc": "1.0", "id":"curltest", "method": "getrawtransaction", "params": [] }

  Json-RPC response :
    {
      "result": << Same to CLI Output >> ,
      "error": null,
      "id": "curltest"
    }
*/

/** GetRawTransaction: gets a hex-encoded serialized transaction or a JSON object describing the transaction.
  * By default, Bitcoin Core only stores complete transaction data for UTXOs and your own transactions,
  * so the RPC may fail on historic transactions unless you use the non-default txindex=1 in your Bitcoin Core startup settings.
  *
  * https://bitcoin.org/en/developer-reference#getrawtransaction
  */
object GetRawTransaction extends RpcCommand {
  def invoke(request : RpcRequest ) : RpcResult = {
    // TODO : Implement
    assert(false)
    null
  }
}


