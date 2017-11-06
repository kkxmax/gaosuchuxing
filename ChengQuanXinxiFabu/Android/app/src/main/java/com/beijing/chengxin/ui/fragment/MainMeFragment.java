package com.beijing.chengxin.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beijing.chengxin.R;
import com.beijing.chengxin.ui.activity.ChengxinLogActivity;
import com.beijing.chengxin.ui.activity.ChengxinReportActivity;
import com.beijing.chengxin.ui.activity.EvalMeActivity;
import com.beijing.chengxin.ui.activity.MyCollectActivity;
import com.beijing.chengxin.ui.activity.MyErrorCorrectActivity;
import com.beijing.chengxin.ui.activity.MyEvalActivity;
import com.beijing.chengxin.ui.activity.MyRealnameCertActivity;
import com.beijing.chengxin.ui.activity.MySettingActivity;
import com.beijing.chengxin.ui.activity.MyWriteActivity;
import com.beijing.chengxin.ui.activity.OpinionReturnActivity;

public class MainMeFragment extends Fragment {

	public final String TAG = MainMeFragment.class.getName();
    private View rootView;

    Button btnRealnameCert, btnReport, btnLog;
    TextView txtMyEval, txtEvalMe, txtMyError, txtMyWrite, txtMyCollect, txtOpinion, txtMySetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	rootView = inflater.inflate(R.layout.fragment_main_me, container, false);

        btnRealnameCert = (Button)rootView.findViewById(R.id.btn_realname_cert);
        btnReport = (Button)rootView.findViewById(R.id.btn_chengxin_report);
        btnLog = (Button)rootView.findViewById(R.id.btn_chengxin_log);

        txtMyEval = (TextView) rootView.findViewById(R.id.txt_my_eval);
        txtEvalMe = (TextView) rootView.findViewById(R.id.txt_eval_me);
        txtMyError = (TextView) rootView.findViewById(R.id.txt_my_error_correct);
        txtMyWrite = (TextView) rootView.findViewById(R.id.txt_my_write);
        txtMyCollect = (TextView) rootView.findViewById(R.id.txt_my_collect);
        txtOpinion = (TextView) rootView.findViewById(R.id.txt_opinion_return);
        txtMySetting = (TextView) rootView.findViewById(R.id.txt_setting);

        btnRealnameCert.setOnClickListener(mButtonClickListener);
        btnReport.setOnClickListener(mButtonClickListener);
        btnLog.setOnClickListener(mButtonClickListener);

        txtMyEval.setOnClickListener(mButtonClickListener);
        txtEvalMe.setOnClickListener(mButtonClickListener);
        txtMyError.setOnClickListener(mButtonClickListener);
        txtMyWrite.setOnClickListener(mButtonClickListener);
        txtMyCollect.setOnClickListener(mButtonClickListener);
        txtOpinion.setOnClickListener(mButtonClickListener);
        txtMySetting.setOnClickListener(mButtonClickListener);

        return rootView;
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_realname_cert:
                    intent = new Intent(getActivity(), MyRealnameCertActivity.class);
                    break;
                case R.id.btn_chengxin_report:
                    intent = new Intent(getActivity(), ChengxinReportActivity.class);
                    break;
                case R.id.btn_chengxin_log:
                    intent = new Intent(getActivity(), ChengxinLogActivity.class);
                    break;
                case R.id.txt_my_eval:
                    intent = new Intent(getActivity(), MyEvalActivity.class);
                    break;
                case R.id.txt_eval_me:
                    intent = new Intent(getActivity(), EvalMeActivity.class);
                    break;
                case R.id.txt_my_error_correct:
                    intent = new Intent(getActivity(), MyErrorCorrectActivity.class);
                    break;
                case R.id.txt_my_write:
                    intent = new Intent(getActivity(), MyWriteActivity.class);
                    break;
                case R.id.txt_my_collect:
                    intent = new Intent(getActivity(), MyCollectActivity.class);
                    break;
                case R.id.txt_opinion_return:
                    intent = new Intent(getActivity(), OpinionReturnActivity.class);
                    break;
                case R.id.txt_setting:
                    intent = new Intent(getActivity(), MySettingActivity.class);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };
}
