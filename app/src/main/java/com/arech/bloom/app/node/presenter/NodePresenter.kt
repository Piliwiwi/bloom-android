package com.arech.bloom.app.node.presenter

import com.arech.bloom.models.Node

/**
 * Created by Pili Arancibia on 9/28/19
 */

interface NodePresenter {
    fun getNodes(sectorId: String)
    fun showNodes(nodes: List<Node>?)
    fun manageErrors(error: Int)
}