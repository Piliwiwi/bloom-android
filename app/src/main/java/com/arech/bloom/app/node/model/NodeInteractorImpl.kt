package com.arech.bloom.app.node.model

import com.arech.bloom.app.node.presenter.NodePresenter
import com.arech.bloom.core.crud.NodeDB

/**
 * Created by Pili Arancibia on 9/28/19
 */

class NodeInteractorImpl(nodePresenter: NodePresenter): NodeInteractor {
    private val nodeRepository = NodeRepositoryImpl(nodePresenter)

    override fun getNodes(sectorId: String) {
        nodeRepository.getNodesAPI(sectorId)
    }
}