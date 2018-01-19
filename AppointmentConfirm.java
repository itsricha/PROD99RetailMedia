package com.RSPL.MEDIA.Doc990;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.serialport.api.SerialPortHelper;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.RSPL.MEDIA.MediaMainScreen;
import com.RSPL.MEDIA.R;
import com.ngx.USBPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.RSPL.MEDIA.R.color.adapter;

public class AppointmentConfirm extends Fragment {
    String referenceapp, username, phonenumber, nicapp, total, next, namehosp, doc, spec, datee, timee, email, pass,title;
    Button continueforpayment ;
    TextView passport, hospitalappoint, doctor, speciality, specialnote, patientno, patientname, phone, nic, reference, amount, time, date, emailapp,pattitle;
    ImageButton error;
     String doccharge,docvatcharge,hospitalcharge,bookingcharge;
    TextView doctorcharges,docchargesvat,hospitalcharges,bookingcharges;
    ProgressDialog progressBar;
    private String st;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_confirm, container, false);


        doctorcharges=(TextView)view.findViewById(R.id.doctorcharges);
        docchargesvat=(TextView)view.findViewById(R.id.doctorchargesvat);
        hospitalcharges=(TextView)view.findViewById(R.id.hospitalcharges);
        bookingcharges=(TextView)view.findViewById(R.id.bookingcharges);

        error = (ImageButton) view.findViewById(R.id.error);
        continueforpayment = (Button) view.findViewById(R.id.continueforpay);
        hospitalappoint = (TextView) view.findViewById(R.id.appointhospital);
        doctor = (TextView) view.findViewById(R.id.drNameappoint);
        speciality = (TextView) view.findViewById(R.id.specializationappoint);
        specialnote = (TextView) view.findViewById(R.id.specialnote);
        time = (TextView) view.findViewById(R.id.timesessionappoint);
        date = (TextView) view.findViewById(R.id.datesessionappoint);
        patientno = (TextView) view.findViewById(R.id.patientnoappoint);
        patientname = (TextView) view.findViewById(R.id.patientname);
        phone = (TextView) view.findViewById(R.id.phoneappoint);
        reference = (TextView) view.findViewById(R.id.reference);
        amount = (TextView) view.findViewById(R.id.totalcharges);
        nic = (TextView) view.findViewById(R.id.nicappoint);
        emailapp = (TextView) view.findViewById(R.id.email);
        passport = (TextView) view.findViewById(R.id.passportno);
        pattitle=(TextView) view.findViewById(R.id.patienttitle);
        Bundle intent = this.getArguments();

        if (intent != null) {
            doccharge = intent.getString("doctorcharges");
            docvatcharge = intent.getString("doctorvat");
            hospitalcharge = intent.getString("hospitalcharge");
            bookingcharge = intent.getString("bookingcharges");

            referenceapp = intent.getString("reference");
            username = intent.getString("name");
            phonenumber = intent.getString("phone");
            nicapp = intent.getString("nic");
            total = intent.getString("total");
            next = intent.getString("nextPatient");
            namehosp = intent.getString("hospitals");
            doc = intent.getString("doctorName");
            spec = intent.getString("specialization");
            datee = intent.getString("date");
            timee = intent.getString("time");
            email = intent.getString("email");
            pass = intent.getString("passport");
            title=intent.getString("title");

            patientname.setText(username);
            patientno.setText(next);
            phone.setText(phonenumber);
            nic.setText(nicapp);

            reference.setText(referenceapp);
            hospitalappoint.setText(namehosp);
            doctor.setText(doc);
            speciality.setText(spec);
            time.setText(timee);
            date.setText(datee);
            emailapp.setText(email);
            passport.setText(pass);
            pattitle.setText(title);

            doctorcharges.setText(doccharge);
            docchargesvat.setText(docvatcharge);
            hospitalcharges.setText(hospitalcharge);
            bookingcharges.setText(bookingcharge);
            amount.setText(String.valueOf(Double.valueOf(doccharge)+Double.valueOf(docvatcharge)+Double.valueOf(hospitalcharge)+Double.valueOf(bookingcharge)));

            writeData();

        }
        continueforpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack("my_fragmen");
                Successful successful = new Successful();
                transaction.replace(R.id.linearlayouts, successful, "Doc");
                transaction.commit();
            }
        });

        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorfragment();

            }
        });

        return view;
    }

    public void writeData() {
        try {
            SerialPortHelper UssPrinter = MediaMainScreen.mSerialPortHelper;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Reference", referenceapp);
            jsonObject.put("Name", username);
            jsonObject.put("Nic", nicapp);
            jsonObject.put("Total", total);
            jsonObject.put("Hospital", namehosp);
            jsonObject.put("NextPatient", next);
            jsonObject.put("Phone", phonenumber);
            jsonObject.put("DoctorName", doc);
            jsonObject.put("specialization", spec);
            jsonObject.put("Date", datee);
            jsonObject.put("Time", timee);
            jsonObject.put("Email", email);
            jsonObject.put("PaymentStatus", "Pending");
            jsonObject.put("passport", pass);

            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void errorfragment() {
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }


    public void PaymentProcess(final String userTyped) {
        class WaitingforResponse extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d(">>>>>>>>", userTyped);
                progressBar = new ProgressDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                progressBar.setTitle("Loading");
                progressBar.setMessage("Continue for Payment Gateway");
                progressBar.show();
            }

            @Override
            public String doInBackground(String... params) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(1000, TimeUnit.SECONDS)
                        .writeTimeout(1000, TimeUnit.SECONDS)
                        .readTimeout(6000, TimeUnit.SECONDS)
                        .build();
                MediaType mediaType = MediaType.parse("application/json");

                String json = "";
                RequestBody body = RequestBody.create(mediaType, json);
                Request request = new Request.Builder()
                        .url("https://ideabiz.lk/apicall/docs/V2.0/payment-methods" )

                        .addHeader("content-type", "application/json")
                        .addHeader("accept", "application/json")
                        .addHeader("access", "99R")
                        .addHeader("authorization", String.format("Bearer %s", st))
                        .get()
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String test = response.body().string();
                    if (response.isSuccessful()) {
                        int success = response.code();
                        if(success==401){
                            MediaMainScreen mediaMainScreen = new MediaMainScreen();
                            mediaMainScreen.TokenGenerationProcess();
                        }

                        Headers responseHeaders = response.headers();
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                        System.out.println(test);
                        JSONObject js = new JSONObject(test);
                        String res = js.get("response").toString();

                        JSONObject jsonObjectpay = new JSONObject(res);
                        name = jsonObjectpay.getString("name");

                        Log.d("payment****method",name);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.dismiss();

                }
            }
        WaitingforResponse WaitingforResponse = new WaitingforResponse();
        WaitingforResponse.execute(userTyped);

        }


    }



