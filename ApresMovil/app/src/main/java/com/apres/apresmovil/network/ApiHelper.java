package com.apres.apresmovil.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.apres.apresmovil.HealthInsuranceApplication;
import com.apres.apresmovil.models.HealthCenter;
import com.apres.apresmovil.models.News;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

/**
 * Created by javierlara on 11/12/16.
 */
public class ApiHelper {

    HealthInsuranceApplication helper = HealthInsuranceApplication.getInstance();

    public interface ApiHelperCallback {
        void onSuccess(List list);

        void onError(Exception error);
    }

    private void getRequest(String endpoint, final ApiHelperCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, endpoint, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
//
//                            String minTemp, maxTemp, atmo;
//                            int avgTemp;
//
//                            response = response.getJSONObject("report");
//
//                            minTemp = response.getString("min_temp"); minTemp = minTemp.substring(0, minTemp.indexOf("."));
//                            maxTemp = response.getString("max_temp"); maxTemp = maxTemp.substring(0, maxTemp.indexOf("."));
//
//                            avgTemp = (Integer.parseInt(minTemp)+Integer.parseInt(maxTemp))/2;
//
//                            atmo = response.getString("atmo_opacity");


//                            callback.onSuccess(response);

                        } catch (Exception e) {
                            callback.onError(e);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });

        helper.add(request);
    }


    /***
     * TODO: MOVE THIS TO ANOTHER CLASS
     */
    final static String HEALTH_CENTERS_ENDPOINT = "https://health-insurance-stage.herokuapp.com/api/health_centers";
    final static String NEWS_ENDPOINT = "https://health-insurance-stage.herokuapp.com/api/news";

    public void getHealthCenters(final ApiHelperCallback callback) {
//        getRequest(HEALTH_CENTERS_ENDPOINT, callback);

        GsonRequest<HealthCenter[]> request =
                new GsonRequest<HealthCenter[]>(HEALTH_CENTERS_ENDPOINT, HealthCenter[].class,
                        new Response.Listener<HealthCenter[]>() {
                            @Override
                            public void onResponse(HealthCenter[] response) {
                                List<HealthCenter> healthCenters = Arrays.asList(response);
                                callback.onSuccess(healthCenters);
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                            }
                        }
                );

        helper.add(request);

    }

    public void getNewses(final ApiHelperCallback callback) {
        GsonRequest<News[]> request =
                new GsonRequest<News[]>(NEWS_ENDPOINT, News[].class,
                        new Response.Listener<News[]>() {
                            @Override
                            public void onResponse(News[] response) {
                                List<News> news = Arrays.asList(response);
                                callback.onSuccess(news);
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                            }
                        }
                );

        helper.add(request);

    }
}
