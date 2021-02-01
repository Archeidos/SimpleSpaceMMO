package com.codestallions.spacemmo.ui.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.SpaceMMO;
import com.codestallions.spacemmo.databinding.FragmentSystemBinding;
import com.codestallions.spacemmo.ui.adapter.GeneralAdapter;
import com.codestallions.spacemmo.ui.viewmodel.SystemViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SystemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SystemFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SystemViewModel systemViewModel;

    public SystemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SystemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SystemFragment newInstance(String param1, String param2) {
        SystemFragment fragment = new SystemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSystemBinding systemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_system, container, false);
        systemViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SystemViewModel.class);
        systemBinding.setSystemViewModel(systemViewModel);

        RecyclerView systemRecycler = systemBinding.getRoot().findViewById(R.id.system_recycler_view);
        GeneralAdapter generalAdapter = new GeneralAdapter(getContext(), getViewLifecycleOwner(), systemViewModel);
        systemRecycler.setAdapter(generalAdapter);
        systemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        systemViewModel.getLocalPlanetList(SpaceMMO.getSession().getPlayerSystemLocation())
                .observe(getViewLifecycleOwner(), planets -> {
            generalAdapter.setPlanetList(planets);
            generalAdapter.notifyDataSetChanged();
            systemRecycler.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {

                        @Override
                        public boolean onPreDraw() {
                            systemRecycler.getViewTreeObserver().removeOnPreDrawListener(this);

                            for (int i = 0; i < systemRecycler.getChildCount(); i++) {
                                View v = systemRecycler.getChildAt(i);
                                v.setAlpha(0.0f);
                                v.animate().alpha(1.0f)
                                        .setDuration(300)
                                        .setStartDelay(i * 50)
                                        .start();
                            }

                            return true;
                        }
                    });
        });

        return systemBinding.getRoot();
    }
}