package com.example.projectmad.ui.notifications;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.projectmad.AccountSettings;
import com.example.projectmad.TransactionLimit;
import com.example.projectmad.Notifications;
import com.example.projectmad.TwoFactor;
import com.example.projectmad.language_region;
import com.example.projectmad.start;
import com.example.projectmad.PasswordManagement;
import com.example.projectmad.BiometricSecurity;
import com.example.projectmad.PaymentMethods;
import com.example.projectmad.R;
import com.example.projectmad.databinding.FragmentNotificationsBinding;


public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //code here
        TextView TwoFactor=root.findViewById(R.id.fa);
        TwoFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),TwoFactor.class);
                startActivity(intent);
            }
        });
        TextView AccountSettings=root.findViewById(R.id.user);
        AccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AccountSettings.class);
                startActivity(intent);
            }
        });
        TextView BiometricSecurity=root.findViewById(R.id.biometric);
        BiometricSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BiometricSecurity.class);
                startActivity(intent);
            }
        });
        TextView TransactionLimit=root.findViewById(R.id.limits);
        TransactionLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),TransactionLimit.class);
                startActivity(intent);
            }
        });
        TextView PasswordManagement=root.findViewById(R.id.password);
        PasswordManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PasswordManagement.class);
                startActivity(intent);
            }
        });
        TextView methods=root.findViewById(R.id.methods);
        methods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PaymentMethods.class);
                startActivity(intent);
            }
        });
        TextView SignOut=root.findViewById(R.id.signOut);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), start.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        TextView Notifications=root.findViewById(R.id.notify);
        Notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Notifications.class);
                startActivity(intent);
            }
        });
        TextView Region=root.findViewById(R.id.region);
        Region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), language_region.class);
                startActivity(intent);
            }
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}