package com.digicon_valley.listviewcontextualactionmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List data_provider=new ArrayList();
    ArrayAdapter<String> adapter;
    List selection=new ArrayList();
    int count=0;
    String[] android_versions={"Cupcake","Donut","Eclair"
            ,"Froyo","Gingerbread","Honeycomb",
            "Ice Cream Sandwich","Jelly Bean","KitKat"
            ,"Lollipop","Marshmallow","Oreo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.list_view);
        for (String item:android_versions){
            data_provider.add(item);
        }
        adapter=new ArrayAdapter<String>(this,R.layout.row_layout,R.id.list_item,data_provider);
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                if (checked){
                    selection.add(data_provider.get(position));
                    count++;
                    mode.setTitle(count+" Selected");
                }else {
                    selection.remove(data_provider.get(position));
                    count--;
                    mode.setTitle(count+" Selected");
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                MenuInflater menuInflater =getMenuInflater();
                menuInflater.inflate(R.menu.my_menu,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                if (item.getItemId()==R.id.id_delete){

                    for (Object items :selection) {
                        String ITEM = item.toString();
                        data_provider.remove(ITEM);
                    }
                        adapter.notifyDataSetChanged();
                        mode.finish();
                    return true;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

                count=0;
                selection.clear();

            }
        });
    }
}
