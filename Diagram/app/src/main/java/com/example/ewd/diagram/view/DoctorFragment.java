package com.example.ewd.diagram.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.database.UserDatabase;
import com.example.ewd.diagram.model.local.entities.User;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;
import com.example.ewd.diagram.viewmodel.UserViewModel;
import com.example.ewd.diagram.viewmodel.UserViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link DoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorFragment extends android.support.v4.app.Fragment {
    // The fragment initialization parameters
    private static final String USER_ID = "USER_ID";
    private static final String SESSION_KEY = "SESSION_KEY";

    @BindView(R.id.user_type_image_view)
    ImageView userTypeImageView;

    @BindView(R.id.username)
    TextView userNameTextView;

    @BindView(R.id.name)
    TextView nameTextView;

    private String userId;
    private String sessionKey;

    // Member variable for the Database
    private UserDatabase mDb;

    private int[] colors = {R.color.colorPrimary, R.color.colorAmber, R.color.colorYellow};

    public DoctorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId     Parameter 1.
     * @param sessionKey Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    public static DoctorFragment newInstance(String userId, String sessionKey) {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        args.putString(SESSION_KEY, sessionKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID);
            sessionKey = getArguments().getString(SESSION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);

        ButterKnife.bind(this, view);

        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Get Database instance
        mDb = UserDatabase.getInstance(getActivity());

        UserViewModelFactory factory = new UserViewModelFactory(mDb, userId);
        final UserViewModel viewModel
                = ViewModelProviders.of(this, factory).get(UserViewModel.class);

        // Observe the LiveData object in the ViewModel. Use it also when removing the observer
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User userEntry) {

                populateUI(userEntry);
            }
        });



    }


    /**
     * populateUI would be called to populate the UI w
     *
     * @param user the taskEntry to populate the UI
     */
    private void populateUI(User user) {
        if (user == null) {
            return;

        }
        //Circular Icon
        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect( "D",
                        getActivity().getResources().getColor(colors[1]), 150);
        userTypeImageView.setImageDrawable(drawable);

        userNameTextView.setText(user.getUsername());
        nameTextView.setText(user.getFirstName() + " " + user.getLastName());


    }







}
