package com.login.mobi.loginapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabsDynamicallyFragment extends Fragment  {

    View parentLayout;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;

    public TabsDynamicallyFragment(){}

    public static TabsDynamicallyFragment newInstance(int sectionNumber) {
        TabsDynamicallyFragment fragment = new TabsDynamicallyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabs_dynamically_fragment, container, false);

        parentLayout = getActivity().findViewById(android.R.id.content);

        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        TextView textView = (TextView) rootView.findViewById(R.id.txtTabItemNumber);
        textView.setText("TAB " + sectionNumber);

        return rootView;
    }









}
