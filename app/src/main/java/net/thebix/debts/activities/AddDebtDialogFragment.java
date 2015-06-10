package net.thebix.debts.activities;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.thebix.debts.R;
import net.thebix.debts.enums.Constants;

public class AddDebtDialogFragment
                extends DialogFragment {

    // region ������������
    public static AddDebtDialogFragment newInstance() { //TODO: �������� ���������� �������������� ���������� �������� � ������
        AddDebtDialogFragment fragment = new AddDebtDialogFragment();
//        Bundle args = new Bundle();
//        args.putLong(Constants.KEY_DEBITOR_ID, debitorId);
//        fragment.setArguments(args);
        return fragment;
    }

    public AddDebtDialogFragment() {
        // Required empty public constructor
    }
    // endregion

    // region �������
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getDialog().setTitle(getString(R.string.title_dialog_fragment_add_debt));
        View v = inflater.inflate(R.layout.dialog_fragment_add_debt, null);
        v.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        v.findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: �������� ���� addDebt(false);
                dismiss();
            }
        });
        return v;
    }

//    // ����� onDismiss �����������, ����� ������ �����������
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
//    }
//
//    // ����� onCancel �����������, ����� ������ �������� ������� �����
//    @Override
//    public void onCancel(DialogInterface dialog) {
//        super.onCancel(dialog);
//    }

    // endregion

    // region �������� ������

    // endregion
}
