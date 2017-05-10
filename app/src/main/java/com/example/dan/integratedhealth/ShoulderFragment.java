package com.example.dan.integratedhealth;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by twinniss on 4/1/2017.
 */
public class ShoulderFragment extends Fragment {
    private View view;
    final String[] shoulder_list = {"Overhead Press", "Push Press"};
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shoulder, container, false);
        ListView lv = (ListView) view.findViewById(R.id.shoulders);
        final List<String> exercise = new ArrayList<String>(Arrays.asList(shoulder_list));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.mylist, R.id.Itemname, exercise);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataEntryFragment dataEntryFragment = new DataEntryFragment();
                switch(i){
                    case 0:
                        FragmentViewer fv = (FragmentViewer) getActivity();
                        fv.current_exercise = "Overhead Press";
                        getFragmentManager().beginTransaction().replace(R.id.content_id,dataEntryFragment).addToBackStack("Overhead Press").commit();
                        break;
                    case 1:
                        getFragmentManager().beginTransaction().replace(R.id.content_id,dataEntryFragment).addToBackStack("Push Press").commit();
                        break;
                }
            }
        });
        return view;
    }
    public void onBackPress(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}