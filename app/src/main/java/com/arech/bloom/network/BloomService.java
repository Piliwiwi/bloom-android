package com.arech.bloom.network;

import com.arech.bloom.app.zone.data.remote.model.RemoteZone;
import com.arech.bloom.app.zone.data.remote.model.ZoneEditRequest;
import com.arech.bloom.models.Company;
import com.arech.bloom.models.Field;
import com.arech.bloom.models.Node;
import com.arech.bloom.models.Sector;
import com.arech.bloom.models.Sensor;
import com.arech.bloom.models.Switch;
import com.arech.bloom.models.User;
import com.arech.bloom.models.Zone;
import com.arech.bloom.network.req.DeviceRegistrationRequest;
import com.arech.bloom.network.req.LoginRequest;
import com.arech.bloom.network.req.NodeModificationRequest;
import com.arech.bloom.network.req.SwitchModificationRequest;
import com.arech.bloom.network.req.ZoneModificationRequest;
import com.arech.bloom.network.res.BloomServerListResponse;
import com.arech.bloom.network.res.GreenhouseListResponse;
import com.arech.bloom.network.res.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Pili Arancibia on 5/10/19
 */
public interface BloomService {

    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest loginReq);

    @POST("api/logout")
    Call<Void> logout();

    @GET("api/user/me")
    Call<User> getMe();

    @GET("api/my/company")
    Call<Company> getMyCompany();

    @GET("api/field/all")
    Call<List<Field>> getAllFields();

    @GET("api/greenhouse/all/mobile")
    Call<GreenhouseListResponse> getAllGreenhouses();

    @GET("api/sector/all/{greenhouse_id}")
    Call<List<Sector>> getAllInSectors(@Path("greenhouse_id") String greenhouseId);

    @GET("api/sector/all/{greenhouse_id}/mobile")
    Call<BloomServerListResponse> getAllSectorFromGreenhouseBFF(@Path("greenhouse_id") String greenhouseId);

    @GET("api/node/all/{sector_id}")
    Call<List<Node>> getAllInNodes(@Path("sector_id") String sectorId);

    @GET("api/sensor/all/{node_id}")
    Call<List<Sensor>> getAllInSensors(@Path("node_id") String nodeId);

    @GET("api/zone/{node_id}/mobile/control")
    Call<List<Switch>> getAllInSwitches(@Path("node_id") String nodeId);

    @GET("api/sector/all")
    Call<List<Sector>> getAllSectors();

    @GET("api/node/all")
    Call<List<Node>> getAllNodes();

    @GET("api/node/{node_id}")
    Call<Node> getNodeById(@Path("node_id") String nodeId);

    @GET("api/sensor/all")
    Call<List<Sensor>> getAllSensors();

    @GET("api/switch/all")
    Call<List<Switch>> getAllSwitches();

    @GET("api/zone/all/{sector_id}")
    Call<List<Zone>> getAllZonesInSector(@Path("sector_id") String sectorId);

    @Deprecated
    @GET("api/sensor/sector/{sector_id}/zone/{zone_number}")
    Call<List<Sensor>> getAllSensorsInZone(@Path("sector_id") String sectorId, @Path("zone_number") int zoneNumber);

    @GET("api/sector/{sector_id}/zones")
    Call<List<RemoteZone>> getAllZonesFromSector(@Path("sector_id") String sectorId);

    @GET("api/sensor/zone/{zone_id}")
    Call<List<Sensor>> getAllSensorsByZoneId(@Path("zone_id") String zoneId);

    @GET("api/zone/{sector_id}/mobile/control")
    Call<List<Switch>> getAllSwitchesInZone(@Path("sector_id") String sectorId);

    @PATCH("api/switch/{switch_id}")
    Call<Void> switchModification(@Path("switch_id") String switchId, @Body SwitchModificationRequest request);

    @PATCH("api/zone/{zone_id}")
    Call<Void> zoneModification(@Path("zone_id") String zoneId, @Body ZoneModificationRequest request);

    @PATCH("api/zone/{zone_id}")
    Call<Void> editZone(@Path("zone_id") String zoneId, @Body ZoneEditRequest request);

    @PATCH("api/node/{node_id}")
    Call<Void> nodeModification(@Path("node_id") String nodeId, @Body NodeModificationRequest request);

    @PATCH("api/user/device/registration")
    Call<Void> deviceRegistration(@Body DeviceRegistrationRequest request);

}
