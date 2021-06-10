package android.jun.birdwatch;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BirdFragment extends Fragment {
    private static final String ARG_BIRD_ID  = "bird_id";
    private Bird mBird;
    private EditText mNameField;
    private EditText mDescriptionField;
    private Button mDateButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID birdId = (UUID) getArguments().getSerializable(ARG_BIRD_ID);
        mBird = BirdList.get(getActivity()).getBird(birdId);
    }

    public static BirdFragment newInstance(UUID birdId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BIRD_ID, birdId);
        BirdFragment fragment = new BirdFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bird, container, false);

        //Linking up widgets
        mNameField = (EditText) v.findViewById(R.id.bird_name);
        if(mBird.getName() != null){
            mNameField.setText(mBird.getName());
        }
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Not used
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBird.setName(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                //Not used
            }
        });

        mDescriptionField = (EditText) v.findViewById(R.id.bird_description);
        if(mBird.getDescription() != null){
            mDescriptionField.setText(mBird.getDescription());
        }
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Not used
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBird.setDescription(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                //Not used
            }
        });

        mDateButton = (Button) v.findViewById(R.id.bird_date);
        mDateButton.setText(mBird.getDate().toString());
        mDateButton.setEnabled(false);

        return v;
    }
}

