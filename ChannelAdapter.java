package com.RSPL.MEDIA.Doc990;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.RSPL.MEDIA.R;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.MyViewHolder> {
    Context context;
    ArrayList<DoctorPojo> doctor;
    ArrayList<DoctorPojo.Hospital>hospitals;
    ArrayList<DoctorPojo.Hospital.Specialization> specializations;
    String docsearch,hospsearch,specsearch;
    String fromDate,toDate;
    public ChannelAdapter(Context context, ArrayList<DoctorPojo> doctor,String fromDate,String toDate) {
        this.context = context;
        this.doctor = doctor;
        this.fromDate=fromDate;
        this.toDate=toDate;
    }
    AppCompatActivity activity;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_channel_layout,parent,false);
        activity = (AppCompatActivity) view.getContext();
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DoctorPojo doctors = doctor.get(position);
        /* final DoctorPojo.Hospital hospitals = doctors.hospitals.get(position); /*  final DoctorPojo.Hospital.Specialization specializatio =*/
        holder.docchannel.setText(doctors.getName());
        // holder.spechannel.setText(specializatio.getName());
        holder.channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DoctorPojo.Hospital>  hossss =doctors.getHospitals();
                final ArrayList<DoctorPojo.Hospital.Specialization>specializations = new ArrayList<DoctorPojo.Hospital.Specialization>();
                for(int i=0;i<hossss.size();i++) {
                    Log.d("%%%%RC%%%%", String.valueOf(doctors.getHospitals().get(i).getSpecialization()).replace("[","").replace("]",""));
                    specializations.addAll(doctors.getHospitals().get(i).getSpecialization());
                }
             /*   String doctor, hospital1, specialization,specializationid;
                doctor =doctors.getName();
                hospital1 = hospitals.getName();
                specialization = specializatio.getName();
                specializationid= specializatio.getId();
                Log.d("*********", doctor + " " + hospital1 + " " + specialization);
                Bundle intent = new Bundle();
                intent.putString("DOCTOR", doctor);
                intent.putString("SPECIALIZATION", specialization);
                intent.putString("SPECIALIZATION_ID", specializationid);
                intent.putString("HOSPITAL", hospital1);
                intent.putString("DOCTOR_ID", doctors.getId());
                intent.putString("HOSPITAL_ID", hospitals.getId());
                intent.putString("FROM_DATE", fromDate);
                intent.putString("TO_DATE",toDate);
                Log.d("***&&(())******", doctors.getId() + " " + hospitals.getId());
                DoctorSessions docSessions = new DoctorSessions();
                docSessions.setArguments(intent);
                activity.getFragmentManager().beginTransaction().replace(R.id.linearlayouts, docSessions).addToBackStack("my_fragment").commit();*/
            }
        });
    }
    @Override
    public int getItemCount() {
        return doctor.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView docchannel,spechannel;
        Button channel;
        public MyViewHolder(View itemView) {
            super(itemView);
            docchannel = (TextView)itemView.findViewById(R.id.docchannel);
            spechannel = (TextView)itemView.findViewById(R.id.specchannel);
            channel = (Button)itemView.findViewById(R.id.channelbutton);
        }
    }
}
