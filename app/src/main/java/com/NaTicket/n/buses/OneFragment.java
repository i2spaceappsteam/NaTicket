package com.NaTicket.n.buses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.NaTicket.n.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ankit on 8/4/2016.
 */
public class OneFragment extends Fragment {

    ListView AminitesListView;
    ImageView AminitesImageVies;
    private ArrayList<String> postArrayList=null;
    String Amenities;
    JSONArray jsonArray;
    TextView NoAmenites;


    private MyAppAdapter mListAdapter;

    ArrayList<HashMap<String, String>> categoriesList = new ArrayList<HashMap<String, String>>();
    // Array of strings storing country names
    String[] countries = new String[] {
            "India",
            "Pakistan",
            "Sri Lanka",
            "China",
            "Bangladesh",
            "Nepal",
            "Afghanistan",
            "North Korea",
            "South Korea",
            "Japan"
    };

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{
            R.drawable.airconditioner,
            R.drawable.blanket,
            R.drawable.food,
            R.drawable.pillow,
            R.drawable.television,
            R.drawable.waterbottle,
            R.drawable.wifi,

    };

    public OneFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_one, container, false);


        AminitesListView = (ListView)v.findViewById(R.id.Aminites_Listview);
        NoAmenites= (TextView) v.findViewById(R.id.no_amenities);


      /*  mListAdapter     = new MyAppAdapter(getActivity(), categoriesList);
        AminitesListView.setAdapter(mListAdapter);*/

        SharedPreferences preference = getActivity().getSharedPreferences("JourneyDetails", Context.MODE_PRIVATE);
        Amenities= preference.getString("Amenities", null);

        System.out.println("Amenities: "+Amenities);
        try {

                 jsonArray = new JSONArray(Amenities);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Each row in the list stores country name, currency and flag

        HashMap<String, String> map = new HashMap<String, String>();
        // adding each child node to HashMap key => value




        categoriesList.add(map);
        //System.out.println("sasasasasasasasasas"+jsonobject.getString("restaurant_id"));

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<jsonArray.length();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            try {
                hm.put("txt",  jsonArray.getString(i));

             /*   if(jsonArray.getString(i).equals("AirConditioner")){
                    hm.put("flag", String.valueOf(R.drawable.airconditioner));
                }else if(jsonArray.getString(i).equals("ChargingPoint")){
                    hm.put("flag", String.valueOf(R.drawable.chargingpoint));
                }else if(jsonArray.getString(i).equals("FireExtinguisher")){
                    hm.put("flag", String.valueOf(R.drawable.fireextinguisher));
                }else if(jsonArray.getString(i).equals("Snacks")){
                    hm.put("flag", String.valueOf(R.drawable.snacks));
                }else if(jsonArray.getString(i).equals("Television")){
                    hm.put("flag", String.valueOf(R.drawable.television));
                }else if(jsonArray.getString(i).equals("WaterBottle")){
                    hm.put("flag", String.valueOf(R.drawable.waterbottle));
                }else if(jsonArray.getString(i).equals("Wifi")){
                    hm.put("flag", String.valueOf(R.drawable.wifi));
                }else if(jsonArray.getString(i).equals("Emergencyexit")){
                    hm.put("flag", String.valueOf(R.drawable.emergencyexit));
                }else if(jsonArray.getString(i).equals("FacialTissues")){
                    hm.put("flag", String.valueOf(R.drawable.facialtissues));
                }else if(jsonArray.getString(i).equals("Hammer")){
                    hm.put("flag", String.valueOf(R.drawable.hammer));
                }else if(jsonArray.getString(i).equals("Headsets")){
                    hm.put("flag", String.valueOf(R.drawable.headsets));
                }else if(jsonArray.getString(i).equals("Heater")){
                    hm.put("flag", String.valueOf(R.drawable.heater));
                }else if(jsonArray.getString(i).equals("Massagechair")){
                    hm.put("flag", String.valueOf(R.drawable.massagechair));
                }else if(jsonArray.getString(i).equals("Meal")){
                    hm.put("flag", String.valueOf(R.drawable.meal));
                }else if(jsonArray.getString(i).equals("Motorizedcalfsupport")){
                    hm.put("flag", String.valueOf(R.drawable.motorizedcalfsupport));
                }else if(jsonArray.getString(i).equals("Movie")){
                    hm.put("flag", String.valueOf(R.drawable.movie));
                }else if(jsonArray.getString(i).equals("Newspaper")){
                    hm.put("flag", String.valueOf(R.drawable.newspaper));
                }else if(jsonArray.getString(i).equals("PersonalTV")){
                    hm.put("flag", String.valueOf(R.drawable.personaltv));
                }else if(jsonArray.getString(i).equals("ReadingLight")){
                    hm.put("flag", String.valueOf(R.drawable.readinglight));
                }else if(jsonArray.getString(i).equals("Satellite")){
                    hm.put("flag", String.valueOf(R.drawable.satellite));
                }else if(jsonArray.getString(i).equals("Softdrink")){
                    hm.put("flag", String.valueOf(R.drawable.softdrink));
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }


            aList.add(hm);
        }
        // Keys used in Hashmap
        String[] from = { "flag","txt" };

        // Ids of views in listview_layout
        int[] to = {R.id.aminities_imageView, R.id.aminities_Item};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.aminities_list_item, from, to);

        // Getting a reference to listview of searchch.xml layout file
      //  ListView listView = ( ListView ) findViewById(R.id.listview);

        // Setting the adapter to the listView
        AminitesListView.setAdapter(adapter);

        return v;
    }

    public class MyAppAdapter extends BaseAdapter {


        float oldDist = 1f;
        static final int NONE = 0;
        static final int DRAG = 1;
        static final int ZOOM = 2;
        int mode = NONE;
        Context context;
        ArrayList<HashMap<String,String>> results = new ArrayList<HashMap<String,String>>();

        public MyAppAdapter(Context context , ArrayList<HashMap<String,String>> results)
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
                convertView = inflater.inflate(R.layout.aminities_list_item, parent, false);

                holder.pic = (ImageView)convertView.findViewById(R.id.aminities_imageView);
                holder.Catname = (TextView)convertView.findViewById(R.id.aminities_Item);

                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }

            Log.d("TAG","Image: "+results.get(position).get("Catimage"));


            holder.Catname.setText(results.get(position).get("Catname"));
            String aminity = results.get(position).get("Catname");

            int id = getContext().getResources().getIdentifier(aminity, "drawable", getContext().getPackageName());
            holder.pic.setImageResource(id);


            return convertView;
        }

        public class ViewHolder
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



}