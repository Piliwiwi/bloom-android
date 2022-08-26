package com.arech.bloom.app.node.model

import com.arech.bloom.R
import com.arech.bloom.app.node.presenter.NodePresenter
import com.arech.bloom.core.crud.NodeDB
import com.arech.bloom.models.Node
import com.arech.bloom.network.call.NodeCall
import com.arech.bloom.network.callbacks.BloomCallback

/**
 * Created by Pili Arancibia on 9/28/19
 */

class NodeRepositoryImpl(private val nodePresenter: NodePresenter): NodeRepository {

    override fun getNodesAPI(sectorId: String) {
        NodeCall.getAllInFromNetwork(sectorId, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                if (value is List<*>) {
                    val response: List<Node> = value as List<Node>
                    NodeDB.add(response)
                    nodePresenter.showNodes(NodeDB.getAll(sectorId))
                }
            }

            override fun onServerError(status: Int) {
                getNodesDB(sectorId)
                nodePresenter.manageErrors(R.string.net_error)
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }
        })
    }

    override fun getNodesDB(sectorId: String) {
        nodePresenter.showNodes(NodeDB.getAll(sectorId))
    }
}