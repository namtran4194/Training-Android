package com.namtran.lazada.presenter.signup;

import com.namtran.lazada.model.signin_signup.SignUpModel;
import com.namtran.lazada.model.objectclass.Staff;
import com.namtran.lazada.view.signin_signup.SignUpView;

/**
 * Created by namtr on 05/05/2017.
 */

public class SignUpPresenter implements ISignUp {
    private SignUpView signUpView;
    private SignUpModel signUpModel;

    public SignUpPresenter(SignUpView signUpView) {
        this.signUpView = signUpView;
        signUpModel = new SignUpModel();
    }

    @Override
    public void doSigningUp(Staff staff) {
        boolean isSuccessed = signUpModel.signUp(staff);
        if (isSuccessed)
            signUpView.signUpSuccess();
        else
            signUpView.signUpFailure();
    }
}
