package android.jun.birdwatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class BirdFragment extends Fragment {
    private static final String ARG_BIRD_ID  = "bird_id";
    private static final String DIALOG_DATE  = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private Bird mBird;
    private EditText mNameField;
    private EditText mDescriptionField;
    private Button mDateButton;
    private Button mRemoveButton;
    private Fragment currentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID birdId = (UUID) getArguments().getSerializable(ARG_BIRD_ID);
        mBird = BirdList.get(getActivity()).getBird(birdId);
        currentFragment = this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBird.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mBird.getDate().toString());
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
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getParentFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mBird.getDate());
                dialog.setTargetFragment(BirdFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mRemoveButton = (Button) v.findViewById(R.id.bird_remove);
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog Interface
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case DialogInterface.BUTTON_POSITIVE:
                                BirdList.get(getActivity()).removeBird(mBird.getID());
                                getActivity().onBackPressed();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you really sure?")
                        .setPositiveButton("Yes", dialogListener)
                        .setNegativeButton("No", dialogListener)
                        .show();
            }
        });
        return v;
    }
}

