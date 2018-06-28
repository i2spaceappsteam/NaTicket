package com.NaTicket.n.buses.adapters;

/**
 * Created by Ankit on 8/17/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.NaTicket.n.R;

import java.util.ArrayList;
import java.util.HashMap;


public class CustomGridtAdapter extends BaseAdapter {

    AlertDialog dialog;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF();
    float oldDist = 1f;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    Context context;
    ArrayList<HashMap<String,String>> results = new ArrayList<HashMap<String,String>>();

    public CustomGridtAdapter(Context context , ArrayList<HashMap<String,String>> results)
    {
        this.context = context;
        this.results = results;
    }



    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_buses, parent, false);

            holder.pic = (ImageView)convertView.findViewById(R.id.imageView1);
            //holder.Catname = (TextView)convertView.findViewById(R.id.textView1);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Log.d("TAG","Image: "+results.get(position).get("Catimage"));
        if(null!=results.get(position).get("Catimage")) {
            Glide.with(context).load(results.get(position).get("Catimage")).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.pic);
        }
        else{
            holder.pic.setImageResource(R.mipmap.ic_launcher);
        }

        //holder.Catname.setText(results.get(position).get("Catname"));
        holder.pic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

				/*AlertDialog.Builder builder = new AlertDialog.Builder(context);

				builder.setTitle((results.get(position).get("Catname")));
				LinearLayout ll   = new LinearLayout(context);
				ImageView image   = new ImageView(context);

				Glide.with(context).load(results.get(position).get("Catimage")).placeholder(R.drawable.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(image);

				Button close      = new Button(context);
				ll.setOrientation(LinearLayout.VERTICAL);

				close.setText("Close");
				close.setTextColor(colorPrimary);
				close.setBackgroundColor(Color.parseColor("#FFFFFF"));
				close.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
				});
				ll.addView(image);
				ll.addView(close);


				builder.setView(ll);
				dialog = builder.create();
				dialog.setCancelable(false);
				dialog.show();
*/

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.alert_busimage);
                dialog.show();

                ImageView about = (ImageView) dialog.findViewById(R.id.alert_imageview);
                Glide.with(context).load(results.get(position).get("Catimage")).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(about);

                Button closeabout = (Button) dialog.findViewById(R.id.alert_cancel);
                closeabout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });


        return convertView;
    }

    private static class ViewHolder
    {
        public TextView Catname;
        public TextView Catid;

        public ImageView pic;
    }


    public  void updateResults(ArrayList<HashMap<String,String>> results)
    {
        this.results = results;
        notifyDataSetChanged();
    }

}
