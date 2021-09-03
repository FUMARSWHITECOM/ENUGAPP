package com.example.eungapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {
    private TextView welcomeText;
    private TextView signUpButton;
    private Button signInButton;
    private Button signOutButton;
    private EditText idInput;
    private EditText passwordInput;
    private LoginItem loginItem;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signInView = inflater.inflate(R.layout.fragment_signin, container, false);
        welcomeText = (TextView) signInView.findViewById(R.id.welcomeText);
        signUpButton = (TextView) signInView.findViewById(R.id.signUpButton);
        signInButton = (Button) signInView.findViewById(R.id.alarmButton);
        signOutButton = (Button) signInView.findViewById(R.id.signOutButton);
        idInput = (EditText) signInView.findViewById(R.id.signInIdInput);
        passwordInput = (EditText) signInView.findViewById(R.id.signInPasswordInput);
        signInView.findViewById(R.id.profile_image).setVisibility(View.GONE);

        if (MainActivity.CURRENT_USER != null) {
            signInView.findViewById(R.id.signInIdLayout).setVisibility(View.GONE);
            signInView.findViewById(R.id.signInPasswordLayout).setVisibility(View.GONE);
            signInView.findViewById(R.id.alarmButton).setVisibility(View.GONE);
            signInView.findViewById(R.id.signUpButton).setVisibility(View.GONE);
            signInView.findViewById(R.id.profile_image).setVisibility(View.VISIBLE);

            signOutButton.setVisibility(View.VISIBLE);
            welcomeText.setText("환영합니다,\n" + MainActivity.CURRENT_USER.getUserName() + "님!");
            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.CURRENT_USER = null;
                    welcomeText.setText("계정이\n있으신가요?");
                    refreshSignInFragment();
                }
            });
            return signInView;
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFragment signUpFragment = new SignUpFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, signUpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loginItem = new LoginItem(idInput.getText().toString(), passwordInput.getText().toString());
                if (loginItem.getEmail().equals("")) {
                    Toast myToast = Toast.makeText(container.getContext(),"아이디를 입력하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (loginItem.getPassword().equals("")) {
                    Toast myToast = Toast.makeText(container.getContext(),"비밀번호를 입력하세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    Call<AuthenticateItem> loginCall = MyAPICall.mMyAPI.login(loginItem.getEmail(), loginItem.getPassword());
                    loginCall.enqueue(new Callback<AuthenticateItem>() {
                        @Override
                        public void onResponse(Call<AuthenticateItem> call, Response<AuthenticateItem> response) {
                            AuthenticateItem authenticateItem = response.body();
                            if (authenticateItem.getMessage().equals("success")) {
                                Toast myToast = Toast.makeText(container.getContext(),"로그인 성공", Toast.LENGTH_SHORT);
                                myToast.show();
                                MainActivity.CURRENT_USER = new UserItem();
                                MainActivity.CURRENT_USER.setEmail(loginItem.getEmail());
                                MainActivity.CURRENT_USER.setUserName(authenticateItem.getUserName());
                                MainActivity.CURRENT_USER.setId(authenticateItem.getUserId());

                                refreshSignInFragment();
                            }
                            else {
                                Toast myToast = Toast.makeText(container.getContext(),"로그인 실패", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticateItem> call, Throwable t) {
                            Log.d("Login", "Fail msg : " + t.getMessage()); // 오류 메시지 출력
                        }
                    });
                }
            }
        });
        return signInView;
    }

    public void refreshSignInFragment() {
        SignInFragment signInFragment = new SignInFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, signInFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}