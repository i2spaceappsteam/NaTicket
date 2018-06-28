package com.NaTicket.n.recharges;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.recharges.Bean.ReportsBean;
import com.NaTicket.n.recharges.Enums.RechargeStatus;
import com.NaTicket.n.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 09-03-2016.
 */
public class RechargeReportsAdapter extends ArrayAdapter<ReportsBean> {


    private static  String date1;
    ArrayList<ReportsBean> repots=new ArrayList<ReportsBean>();
    Context ctx;
    SharedPreferences pref1;
    SharedPreferences.Editor editor1;
    LayoutInflater inflator;
    int deviceWidth,deviceHeight,textHeight;
    int textwidth,amt;

    ViewHolder holder=new ViewHolder();
    public RechargeReportsAdapter(MyReports myReports,
                                  ArrayList<ReportsBean> reportsBeanArrayList) {
        super(myReports, R.layout.recharge_myreports,reportsBeanArrayList);
        this.ctx=myReports;
        this.repots=reportsBeanArrayList;
        this.inflator=LayoutInflater.from(ctx);
        Display deviceDisplay=((WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        deviceWidth = deviceDisplay.getWidth();//480
        deviceHeight = deviceDisplay.getHeight();//800
        textHeight=(int)(deviceHeight*10/133.3333);//60
        textwidth=(deviceWidth/2);
        amt=(int)(deviceWidth/1.25);

        pref1=ctx.getSharedPreferences("Android",0);
        editor1=pref1.edit();




    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return repots.size();
    }

    @Override
    public ReportsBean getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public class ViewHolder{
        TextView refno,psaname,amount,bookingsstatus,src,dest;
        View seperator;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if(convertView==null){

            convertView=inflator.inflate(R.layout.recharge_myreports, null);
            holder.refno=(TextView)convertView.findViewById(R.id.refno);
            holder.psaname=(TextView)convertView.findViewById(R.id.psaname);
            holder.amount=(TextView)convertView.findViewById(R.id.bcadapteramount);
            holder.src=(TextView)convertView.findViewById(R.id.bcadaptersrc);
            holder.dest=(TextView)convertView.findViewById(R.id.bcadapterdest);
            holder.bookingsstatus=(TextView)convertView.findViewById(R.id.bcadapterbookingstatus);
            holder.seperator=(View)convertView.findViewById(R.id.seperator);

            LinearLayout.LayoutParams refparams = new LinearLayout.LayoutParams(
                    textwidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            refparams.setMargins(0, 0, 0, 0);
//			holder.text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Constants.textSize);
            holder.refno.setTextColor(Color.BLACK);


            holder.refno.setLayoutParams(refparams);

            LinearLayout.LayoutParams psaParams = new LinearLayout.LayoutParams(
                    textwidth, ViewGroup.LayoutParams.WRAP_CONTENT);
//			holder.syncText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            psaParams.setMargins(10, 0, 0, 0);
            holder.psaname.setTextColor(Color.GRAY);


            holder.psaname.setLayoutParams(psaParams);



            LinearLayout.LayoutParams amountParams = new LinearLayout.LayoutParams(
                    textwidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            amountParams.setMargins(10, 0, 0, 0);
//			holder.text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Constants.textSize);
            holder.amount.setTextColor(Color.BLACK);


            holder.amount.setLayoutParams(amountParams);

            LinearLayout.LayoutParams srcParams = new LinearLayout.LayoutParams(
                    textwidth, ViewGroup.LayoutParams.WRAP_CONTENT);
//			holder.syncText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            srcParams.setMargins(0, 0, 0, 0);
            holder.src.setTextColor(Color.BLUE);


            holder.src.setLayoutParams(srcParams);
            LinearLayout.LayoutParams destpParams = new LinearLayout.LayoutParams(
                    textwidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            destpParams.setMargins(10, 0, 0, 0);
//			holder.text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Constants.textSize);
            holder.dest.setTextColor(Color.BLACK);


            holder.dest.setLayoutParams(destpParams);

            LinearLayout.LayoutParams bookingsstatuspaParams = new LinearLayout.LayoutParams(
                    textwidth, ViewGroup.LayoutParams.WRAP_CONTENT);
//			holder.syncText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            bookingsstatuspaParams.setMargins(10, 0, 0, 0);
            holder.bookingsstatus.setTextColor(Color.GRAY);


            holder.bookingsstatus.setLayoutParams(bookingsstatuspaParams);

            LinearLayout.LayoutParams Viewarams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 5);
            Viewarams.setMargins(0, 0, 0, 0);
            holder.seperator.setLayoutParams(Viewarams);
            holder.seperator.setBackgroundColor(Color.GRAY);
            convertView.setTag(holder);


        }else{
            holder=(ViewHolder)convertView.getTag();
        }
         date1=repots.get(position).getModifiedDate();

        String output = date1.substring(0, 10);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date testDate = null;

        try {

            testDate = sdf.parse(output);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        String newFormat = formatter.format(testDate);
        System.out.println(".....Date..."+newFormat);






        String str=repots.get(position).getRechargeStatus();
        int i=Integer.parseInt(str);
        holder.refno.setText("REFNo " +repots.get(position).getBookingRefNo());
        //holder.psaname.setText("Date "+newFormat);
        holder.amount.setText("Recharge Amount : Rs"+repots.get(position).getRechargeAmount());
        holder.src.setText("Mobile: "+repots.get(position).getRechargeNumber());
        holder.dest.setText("Operator: "+repots.get(position).getOperatorName());
        for (RechargeStatus cb: RechargeStatus.values()
                ) {

            str =""+ RechargeStatus.values()[i];
        }

        holder.bookingsstatus.setText("Status: "+str);
        holder.psaname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        return convertView;
    }

}
