package com.example.oryossipof.alphahotal;

        import android.app.Activity;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
        import android.widget.Toast;


public class MaintenanceActivity extends Activity {
    private String department = "Maintenance";
    private BroadcastReceiver receiver;
    private String roomNum;
    private ListView mListView ;
    private Context context;
    private int[] darwablename = { R.drawable.safe2,R.drawable.volte,R.drawable.plum,R.drawable.door2,R.drawable.phone,R.drawable.call3};
    String maintenance[] = {"Fix Safe","Power failure","Plumbing fault","Fix door","Fix phone","Call"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maintenance);



        mListView = (ListView) findViewById(R.id.lv_housekeeping);
        final CutomAdapter2 adapter = new CutomAdapter2();
        mListView.setAdapter(adapter);
        context=this;
        roomNum = getIntent().getStringExtra("roomNum");


    }

    class CutomAdapter2 extends BaseAdapter
    {


        @Override
        public int getCount() {
            return darwablename.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int index = i;
            view = getLayoutInflater().inflate(R.layout.listview_layout, null);
            ImageView iv = (ImageView) view.findViewById(R.id.imageviewlayout);
            TextView textview = (TextView) view.findViewById(R.id.textviewLayout);
            iv.setImageResource(darwablename[i]);
            textview.setText(maintenance[i]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    BackgroundWorker bg = new BackgroundWorker(MaintenanceActivity.this);
                    bg.execute("insertNewRequest", roomNum, department, maintenance[index], "");


                    registerReceiver(receiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            String result = (String) intent.getExtras().getString("result");

                            //alertDialog.show();
                            if (result.equals("New requests accepted successfully")) {
                                Toast.makeText(MaintenanceActivity.this, "New request accepted successfully", Toast.LENGTH_SHORT).show();

                                unregisterReceiver(receiver);

                            } else {
                                Toast.makeText(MaintenanceActivity.this, "connection error! try again later", Toast.LENGTH_SHORT).show();
                                unregisterReceiver(receiver);
                            }
                        }

                    }, new IntentFilter("resultIntent4"));
                }

            });

            return view;
        }
        }
}
