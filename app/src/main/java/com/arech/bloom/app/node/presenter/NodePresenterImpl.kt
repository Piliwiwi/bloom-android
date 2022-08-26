package com.arech.bloom.app.node.presenter

import com.arech.bloom.app.node.NodeView
import com.arech.bloom.app.node.model.NodeInteractorImpl
import com.arech.bloom.models.Node

/**
 * Created by Pili Arancibia on 9/28/19
 */

class NodePresenterImpl(private val nodeView: NodeView): NodePresenter {
    private val nodeInteractor = NodeInteractorImpl(this)

    override fun getNodes(sectorId: String) {
        nodeInteractor.getNodes(sectorId)
    }

    override fun showNodes(nodes: List<Node>?) {
        nodeView.showNodes(nodes)
    }

    override fun manageErrors(error: Int) {
        nodeView.manageErrors(error)
    }
}