package com.leekien.shipfoodfinal.bo;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.leekien.shipfoodfinal.bo.Directions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User on 12/20/2016.
 */

public class GetDirectionsTask {
    private String mRequest;
    InputStreamReader reader;
    Map map;

    public GetDirectionsTask(String _mRequest) {
        this.mRequest = _mRequest;
    }

    public Map testDirection() {
        ArrayList<String> points = new ArrayList<String>();
        try {
            URL url;
            url = new URL(mRequest);

            reader = new InputStreamReader(url.openStream(), "UTF-8");
            Directions results = new Gson().fromJson(reader, Directions.class);
            Directions.Route[] routes = results.getRoutes();
            Directions.Leg[] leg = routes[0].getLegs();
            Directions.Leg.Step[] steps = leg[0].getSteps();
            Directions.Leg.Distance distance=  leg[0].getDistance();
            String text = distance.getText();
            for (Directions.Leg.Step step : steps) {
                points.add(step.getPolyline().points);
            }
             map= new Map(text,points);

            return map;

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
