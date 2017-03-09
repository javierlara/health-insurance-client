package com.apres.apresmovil.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.apres.apresmovil.HealthInsuranceApplication;
import com.apres.apresmovil.R;
import com.apres.apresmovil.models.AppointmentContainer;
import com.apres.apresmovil.models.Doctor;
import com.apres.apresmovil.models.HealthCenter;
import com.apres.apresmovil.models.Member;
import com.apres.apresmovil.models.News;
import com.apres.apresmovil.models.Plan;
import com.apres.apresmovil.models.ScheduleContainer;
import com.apres.apresmovil.models.Speciality;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by javierlara on 11/12/16.
 */
public class ApiHelper {

    public ApiHelper(Context context) {
        mProgress = new ProgressDialog(context, R.style.ProgressTheme);
        mProgress.setCancelable(false);
        mProgress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
    }

    HealthInsuranceApplication helper = HealthInsuranceApplication.getInstance();

    public interface ApiHelperCallback {
        void onSuccess(List list);

        void onError(Exception error);
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
    final static String MEMBER_ENDPOINT= "https://health-insurance-stage.herokuapp.com/api/members";
    final static String APPOINTMENT_ENDPOINT = "https://health-insurance-stage.herokuapp.com/api/appointment";
    final static String GET_MEMBER_APPOINTMENTS_ENDPOINT = "http://health-insurance-stage.herokuapp.com/api/appointments/filter?member_id=";

    ProgressDialog mProgress;

    public void getHealthCenters(final ApiHelperCallback callback) {
//        getRequest(HEALTH_CENTERS_ENDPOINT, callback);
        mProgress.show();
        GsonRequest<HealthCenter[]> request =
                new GsonRequest<HealthCenter[]>(Request.Method.GET, HEALTH_CENTERS_ENDPOINT,
                        HealthCenter[].class,
                        new Response.Listener<HealthCenter[]>() {
                            @Override
                            public void onResponse(HealthCenter[] response) {
                                List<HealthCenter> healthCenters = Arrays.asList(response);
                                callback.onSuccess(healthCenters);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                                mProgress.dismiss();
                            }
                        });

        helper.add(request);

    }

    public void getNewses(final ApiHelperCallback callback) {
        mProgress.show();
        GsonRequest<News[]> request =
                new GsonRequest<News[]>(Request.Method.GET, NEWS_ENDPOINT,
                        News[].class,
                        new Response.Listener<News[]>() {
                            @Override
                            public void onResponse(News[] response) {
                                List<News> news = Arrays.asList(response);
                                callback.onSuccess(news);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                                mProgress.dismiss();
                            }
                        });

        helper.add(request);

    }

    public void getPlans(final ApiHelperCallback callback) {
        mProgress.show();
        GsonRequest<Plan[]> request =
                new GsonRequest<Plan[]>(Request.Method.GET, PLANS_ENDPOINT,
                        Plan[].class,
                        new Response.Listener<Plan[]>() {
                            @Override
                            public void onResponse(Plan[] response) {
                                List<Plan> plans = Arrays.asList(response);
                                callback.onSuccess(plans);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                                mProgress.dismiss();
                            }
                        });

        helper.add(request);

    }

    public void getDoctors(final ApiHelperCallback callback) {
        mProgress.show();
        GsonRequest<Doctor[]> request =
                new GsonRequest<Doctor[]>(Request.Method.GET, DOCTORS_ENDPOINT,
                        Doctor[].class,
                        new Response.Listener<Doctor[]>() {
                            @Override
                            public void onResponse(Doctor[] response) {
                                List<Doctor> doctors = Arrays.asList(response);
                                callback.onSuccess(doctors);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                                mProgress.dismiss();
                            }
                        });

        helper.add(request);

    }

    public void getCartilla(String plan_id, String speciality_id, String latitude, String longitude, final ApiHelperCallback callback) {
        String endpoint = CARTILLA_ENDPOINT + "?";
        if(plan_id != "") {
            endpoint = endpoint + "plan_id=" + plan_id + "&";
        }
        if(speciality_id != "") {
            endpoint = endpoint + "speciality_id=" + speciality_id + "&";
        }
        if(latitude != "") {
            endpoint = endpoint + "lat=" + latitude + "&";
        }
        if(longitude != "") {
            endpoint = endpoint + "long=" + longitude + "&";
        }

        mProgress.show();
        GsonRequest<Doctor[]> request =
                new GsonRequest<Doctor[]>(Request.Method.GET, endpoint,
                        Doctor[].class,
                        new Response.Listener<Doctor[]>() {
                            @Override
                            public void onResponse(Doctor[] response) {
                                List<Doctor> doctors = Arrays.asList(response);
                                callback.onSuccess(doctors);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                                mProgress.dismiss();
                            }
                        });

        helper.add(request);

    }

    public void getSpecialitites(final ApiHelperCallback callback) {
        mProgress.show();
        GsonRequest<Speciality[]> request =
                new GsonRequest<Speciality[]>(Request.Method.GET, SPECIALITIES_ENDPOINT,
                        Speciality[].class,
                        new Response.Listener<Speciality[]>() {
                            @Override
                            public void onResponse(Speciality[] response) {
                                List<Speciality> specialities = Arrays.asList(response);
                                callback.onSuccess(specialities);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                                mProgress.dismiss();
                            }
                        });

        helper.add(request);

    }

    public void getSchedule(String doctorId, String month, String year, final ApiHelperCallback callback) {
        mProgress.show();

        String endpoint = DOCTORS_ENDPOINT + "/" + doctorId + "/schedule/" + month + "/" + year;

        GsonRequest<ScheduleContainer> request =
                new GsonRequest<ScheduleContainer>(Request.Method.GET, endpoint,
                        ScheduleContainer.class,
                        new Response.Listener<ScheduleContainer>() {
                            @Override
                            public void onResponse(ScheduleContainer response) {
                                List<ScheduleContainer> scheduleContainers = new ArrayList<ScheduleContainer>();
                                scheduleContainers.add(response);
                                callback.onSuccess(scheduleContainers);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                                mProgress.dismiss();
                            }
                        });

        helper.add(request);

    }

    public void getMemberByNumber(String memberNumber, final ApiHelperCallback callback) {
//        mProgress.show();

        String endpoint = MEMBER_ENDPOINT + "/number/" + memberNumber;

        GsonRequest<Member> request =
                new GsonRequest<Member>(Request.Method.GET, endpoint,
                        Member.class,
                        new Response.Listener<Member>() {
                            @Override
                            public void onResponse(Member response) {
                                List<Member> members = new ArrayList<Member>();
                                members.add(response);
                                callback.onSuccess(members);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
//                                mProgress.dismiss();
                            }
                        });

        helper.add(request);

    }

    public void getAppointments(String memberId, final ApiHelperCallback callback) {
        mProgress.show();

        String endpoint = GET_MEMBER_APPOINTMENTS_ENDPOINT + memberId;

        GsonRequest<AppointmentContainer> request =
                new GsonRequest<AppointmentContainer>(Request.Method.GET, endpoint,
                        AppointmentContainer.class,
                        new Response.Listener<AppointmentContainer>() {
                            @Override
                            public void onResponse(AppointmentContainer response) {
                                List<AppointmentContainer> appointmentContainers = new ArrayList<AppointmentContainer>();
                                appointmentContainers.add(response);
                                callback.onSuccess(appointmentContainers);
                                mProgress.dismiss();
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onError(error);
                                mProgress.dismiss();
                            }
                        });

        helper.add(request);
    }

    public void postAppointment(String doctorId, String memberId, String start, ApiHelperCallback apiHelperCallback) {
        mProgress.show();

        String endpoint = APPOINTMENT_ENDPOINT;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("doctor_id", doctorId);
            jsonBody.put("member_id", memberId);
            jsonBody.put("start", start);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request
                = new JsonObjectRequest(Request.Method.POST, endpoint, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("APPOINTMENTPOST", response.toString());
                mProgress.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("APPOINTMENTPOST", error.toString());
                mProgress.hide();
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        helper.add(request);
    }

    public void deleteAppointment(String appointmentId, ApiHelperCallback apiHelperCallback) {
        mProgress.show();

        String endpoint = APPOINTMENT_ENDPOINT + '/' + appointmentId;

        JSONObject jsonBody = new JSONObject();

        JsonObjectRequest request
                = new JsonObjectRequest(Request.Method.DELETE, endpoint, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("APPOINTMENTDELETE", response.toString());
                mProgress.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("APPOINTMENTDELETE", error.toString());
                mProgress.hide();
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        helper.add(request);
    }
}
