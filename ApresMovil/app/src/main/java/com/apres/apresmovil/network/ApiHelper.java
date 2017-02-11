package com.apres.apresmovil.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.apres.apresmovil.HealthInsuranceApplication;
import com.apres.apresmovil.models.Doctor;
import com.apres.apresmovil.models.HealthCenter;
import com.apres.apresmovil.models.News;
import com.apres.apresmovil.models.Plan;
import com.apres.apresmovil.models.Speciality;

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
    final static String PLANS_ENDPOINT = "https://health-insurance-stage.herokuapp.com/api/plans";
    final static String DOCTORS_ENDPOINT = "https://health-insurance-stage.herokuapp.com/api/doctors";
    final static String SPECIALITIES_ENDPOINT = "https://health-insurance-stage.herokuapp.com/api/specialities";
    final static String CARTILLA_ENDPOINT = "https://health-insurance-stage.herokuapp.com/api/cartilla";

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

    public void getPlans(final ApiHelperCallback callback) {
        GsonRequest<Plan[]> request =
                new GsonRequest<Plan[]>(PLANS_ENDPOINT, Plan[].class,
                        new Response.Listener<Plan[]>() {
                            @Override
                            public void onResponse(Plan[] response) {
                                List<Plan> plans = Arrays.asList(response);
                                callback.onSuccess(plans);
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

    public void getDoctors(final ApiHelperCallback callback) {
        GsonRequest<Doctor[]> request =
                new GsonRequest<Doctor[]>(DOCTORS_ENDPOINT, Doctor[].class,
                        new Response.Listener<Doctor[]>() {
                            @Override
                            public void onResponse(Doctor[] response) {
                                List<Doctor> doctors = Arrays.asList(response);
                                callback.onSuccess(doctors);
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

    public void getCartilla(String plan_id, String speciality_id, String location, final ApiHelperCallback callback) {
        String endpoint = CARTILLA_ENDPOINT + "?";
        if(plan_id != "") {
            endpoint = endpoint + "plan_id=" + plan_id + "&";
        }
        if(speciality_id != "") {
            endpoint = endpoint + "speciality_id=" + speciality_id + "&";
        }
        if(location != "") {
            endpoint = endpoint + "location=" + location + "&";
        }
        GsonRequest<Doctor[]> request =
                new GsonRequest<Doctor[]>(endpoint, Doctor[].class,
                        new Response.Listener<Doctor[]>() {
                            @Override
                            public void onResponse(Doctor[] response) {
                                List<Doctor> doctors = Arrays.asList(response);
                                callback.onSuccess(doctors);
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

    public void getSpecialitites(final ApiHelperCallback callback) {
        GsonRequest<Speciality[]> request =
                new GsonRequest<Speciality[]>(SPECIALITIES_ENDPOINT, Speciality[].class,
                        new Response.Listener<Speciality[]>() {
                            @Override
                            public void onResponse(Speciality[] response) {
                                List<Speciality> specialities = Arrays.asList(response);
                                callback.onSuccess(specialities);
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
