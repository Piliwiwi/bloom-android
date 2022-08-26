package com.arech.bloom.network.call;

import com.arech.bloom.models.Node;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;
import com.arech.bloom.network.req.NodeModificationRequest;

import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;

/**
 * Created by Pili Arancibia on 9/28/19
 */

public class NodeCall {
    public static void getAllInFromNetwork(String sectorId, @Nullable final BloomCallback callback) {
        Call<List<Node>> call = BloomCallInterceptor.service.getAllInNodes(sectorId);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void getAllFromNetwork(@Nullable final BloomCallback callback) {
        Call<List<Node>> call = BloomCallInterceptor.service.getAllNodes();

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void getByIdFromNetwork(String nodeId, @Nullable final BloomCallback callback) {
        Call<Node> call = BloomCallInterceptor.service.getNodeById(nodeId);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void setModification(String nodeId, NodeModificationRequest request, @Nullable final BloomCallback callback) {
        Call<Void> call = BloomCallInterceptor.service.nodeModification(nodeId, request);

        BloomCallInterceptor.processCallList(call, callback);
    }
}
