package com.example.marc.materialtabviews;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EnterCodeFragment extends IntermediateFragment {

    private String correctCode;
    private String title;
    private Button backButton;
    private Button submitButton;
    private TextView titleDisplay;
    private TextView codeCorrectness;
    private EditText codeForm;

    public EnterCodeFragment() {
        super(null, null);
    }

    public EnterCodeFragment(Fragment previous, Fragment next, String correctCode, String title) {
        super(previous, next);
        this.correctCode = correctCode;
        this.title = title;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.enter_code_fragment, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        backButton = (Button) getView().findViewById(R.id.cancelButton);
        submitButton = (Button) getView().findViewById(R.id.submitButton);
        codeForm = (EditText) getView().findViewById(R.id.enterCode);
        codeCorrectness = (TextView) getView().findViewById(R.id.codeCorrectness);
        titleDisplay = (TextView) getView().findViewById(R.id.titleText);

        titleDisplay.setText(title);

        toggleKeyboard(); // Open keyboard for code

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleKeyboard(); // Close upon exiting
                gotoPrevious();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeText = codeForm.getText().toString();
                if (codeText.equals(correctCode)) {
                    codeCorrectness.setText("Correct code");
                    toggleKeyboard(); // Close upon exiting
                    gotoNext();
                } else {
                    codeCorrectness.setText("Incorrect code");
                }
            }
        });

    }

    private void toggleKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // Should hide it since it was open
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

}