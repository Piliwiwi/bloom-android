package com.arech.bloom.network;

import android.content.Context;

import com.arech.bloom.app.main.MainActivity;
import com.arech.bloom.app.zone.presenter.model.AttrsEventSensor;
import com.arech.bloom.config.Config;
import com.arech.bloom.config.Resources;
import com.arech.bloom.core.crud.GreenhouseDB;
import com.arech.bloom.core.crud.NodeDB;
import com.arech.bloom.core.crud.SectorDB;
import com.arech.bloom.core.crud.SensorDB;
import com.arech.bloom.core.crud.SwitchDB;
import com.arech.bloom.core.crud.UserDB;
import com.arech.bloom.event.GreenhouseCenter;
import com.arech.bloom.event.NodeCenter;
import com.arech.bloom.event.SectorCenter;
import com.arech.bloom.event.SensorCenter;
import com.arech.bloom.event.SwitchCenter;
import com.arech.bloom.models.Field;
import com.arech.bloom.models.User;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by Pili Arancibia on 5/16/19
 */

public class BloomSocket {
    private static Socket socket;

    public static void connect(final Context context) {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = true;
            opts.secure = false;

            socket = IO.socket(Resources.BASE_URL, opts);
            socket.connect();

            //EVENTO AL CONECTARSE
            socket.on(Socket.EVENT_CONNECT, args -> {
                if(context instanceof MainActivity) {
                    MainActivity activity = (MainActivity) context;
                    activity.setSocketSyncResource(true);
                }
            });

            //EVENTO AL DESCONECTARSE
            socket.on(Socket.EVENT_DISCONNECT, args -> {
                if(context instanceof MainActivity) {
                    MainActivity activity = (MainActivity) context;
                    activity.setSocketSyncResource(false);
                }
            });

            User me = UserDB.getMe();
            socket.emit("login", me.get_id());

            socket.on("field", args -> {
                System.out.println("FIELD!!!");
                if(args[0] instanceof JSONObject) {
                    Field field = new Gson().fromJson(args[0].toString(), Field.class);
                    System.out.println("EL PRIMER PARAMETRO ES FIELD : " + field.get_id());
                } else {
                    System.out.println("ES OTRA WEA: " + args);
                }
            });

            socket.on("greenhouse", args -> {
                if(args[0] instanceof JSONObject) {
                    JSONObject greenhouse = (JSONObject) args[0];
                    GreenhouseDB.update(greenhouse);
                    EventBus.getDefault().post(new GreenhouseCenter.GreenhouseEvent(true, true));
                }
            });

            socket.on("sector", args -> {
                if(args[0] instanceof JSONObject) {
                    JSONObject sector = (JSONObject) args[0];
                    SectorDB.update(sector);
                    EventBus.getDefault().post(new SectorCenter.SectorEvent(true, true));
                }
            });

            socket.on("node", args -> {
                if(args[0] instanceof JSONObject) {
                    JSONObject node = (JSONObject) args[0];

                    ArrayList<String> zonesIdList = new ArrayList<String>();
                    String lastSync = null;
                    try {
                        JSONArray zonesId = node.getJSONArray("zonesId");
                        JSONArray jsonArray = (JSONArray) zonesId;
                        if (jsonArray != null) {
                            int len = jsonArray.length();
                            for (int i=0;i<len;i++){
                                zonesIdList.add(jsonArray.get(i).toString());
                            }
                        }
                        lastSync = node.getString("lastSync");
                    } catch (JSONException e) {
                        //e.printStackTrace();
                    }
                    EventBus.getDefault().post(new NodeCenter.NodeEvent(zonesIdList, lastSync));
                }
            });

            socket.on("sensor", args -> {
                if(args[0] instanceof JSONObject) {
                    JSONObject sensor = (JSONObject) args[0];

                    String sensorId = null;
                    String zoneId = null;
                    String sensorData = null;
                    String sensorStat = null;

                    try {
                        sensorId = sensor.getString("_id");
                        zoneId = sensor.getString("zoneId");
                        sensorData = sensor.getString("formattedData");
                        sensorStat = sensor.getString("stat");
                    } catch (JSONException e) {
                        //e.printStackTrace();
                    }

                    if (sensorId != null) {
                        AttrsEventSensor event = new AttrsEventSensor(sensorId, zoneId, sensorData, sensorStat);
                        EventBus.getDefault().post(new SensorCenter.SensorEvent(event));
                    }
                }
            });

            socket.on("switch", args -> {
                if(args[0] instanceof JSONObject) {
                    JSONObject maSwitch = (JSONObject) args[0];
                    SwitchDB.update(maSwitch);
                    String id = null;
                    try {
                        id = maSwitch.getString("_id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(new SwitchCenter.SwitchEvent(id));
                }
            });

        } catch (URISyntaxException e) {}
    }

    public void disconnect(Context context) {
            if(socket != null) socket.disconnect();
    }

}
