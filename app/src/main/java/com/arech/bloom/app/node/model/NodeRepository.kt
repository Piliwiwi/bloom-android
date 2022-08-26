package com.arech.bloom.app.node.model

/**
 * Created by Pili Arancibia on 9/28/19
 */

interface NodeRepository {
    fun getNodesAPI(sectorId: String)
    fun getNodesDB(sectorId: String)
}