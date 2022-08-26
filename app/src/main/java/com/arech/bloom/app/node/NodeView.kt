package com.arech.bloom.app.node

import com.arech.bloom.models.Node

/**
 * Created by Pili Arancibia on 9/28/19
 */

interface NodeView {
    fun getNodes(sectorId: String?)
    fun showNodes(nodes: List<Node>?)
    fun manageErrors(error: Int)
}