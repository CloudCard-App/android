package com.example.marc.materialtabviews.misc_fragments;

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

import com.example.marc.materialtabviews.R;

public class EnterCodeFragment extends IntermediateFragment {

    // The correct code to approve the deck for access
    private String correctCode;
    // The title of the deck
    private String title;
    // The place where correct/incorrect is shown
    // TODO: Make sure this is working!
    // The place where the user can type in the code
    private EditText codeForm;

    /**
     * Default constructor that doesn't setup the next or previous fragments.
     */
    public EnterCodeFragment() {
        super(null, null);
        correctCode = "";
        title = "";
        codeForm = null;
    }

    public EnterCodeFragment(Fragment previous, Fragment next, String correctCode, String title) {
        super(previous, next);
        this.correctCode = correctCode;
        this.title = title;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.enter_code_fragment, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button backButton = (Button) getView().findViewById(R.id.cancelButton);
        Button submitButton = (Button) getView().findViewById(R.id.submitButton);
        codeForm = (EditText) getView().findViewById(R.id.enterCode);
        TextView titleDisplay = (TextView) getView().findViewById(R.id.titleText);

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
                    codeForm.setText(R.string.correctCode);
                    toggleKeyboard(); // Close upon exiting
                    gotoNext();
                } else {
                    codeForm.setText(R.string.incorrectCode);
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
