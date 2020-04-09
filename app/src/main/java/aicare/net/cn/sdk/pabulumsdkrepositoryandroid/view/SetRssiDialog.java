package aicare.net.cn.sdk.pabulumsdkrepositoryandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import aicare.net.cn.sdk.pabulumsdkrepositoryandroid.R;
import aicare.net.cn.sdk.pabulumsdkrepositoryandroid.utils.Config;
import aicare.net.cn.sdk.pabulumsdkrepositoryandroid.utils.SPUtils;
import aicare.net.cn.sdk.pabulumsdkrepositoryandroid.utils.T;


/**
 * Created by Suzy on 2016/6/22.
 */
public class SetRssiDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private int rssi;
    private OnQueryListener onQueryListener;

    private Button btn_cancel, btn_query;
    private EditText et_input_rssi;

    public SetRssiDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public SetRssiDialog(Context context, int rssi, OnQueryListener onQueryListener) {
        this(context);
        this.context = context;
        this.rssi = rssi;
        this.onQueryListener = onQueryListener;
    }

    public interface OnQueryListener {
        void query(int rssi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_set_rssi);
        setDialogWidth();
        initViews();
        setEvents();
    }


    private void setDialogWidth() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    private void initViews() {
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_query = (Button) findViewById(R.id.btn_query);
        et_input_rssi = (EditText) findViewById(R.id.et_input_rssi);
        et_input_rssi.setText(String.valueOf(Math.abs(rssi)));
        et_input_rssi.setSelection(String.valueOf(Math.abs(rssi)).length());
    }

    private void setEvents() {
        btn_cancel.setOnClickListener(this);
        btn_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cancel:
                T.showShort(context, R.string.user_cancel);
                break;
            case R.id.btn_query:
                String rssiStr = et_input_rssi.getText().toString();
                if (TextUtils.isEmpty(rssiStr)) {
                    T.showShort(context, R.string.rssi_not_null);
                }else {
                    int customRssi = Integer.valueOf(rssiStr);
                    if (customRssi < 30 || customRssi > 120) {
                        T.showShort(context, R.string.rssi_error);
                    }else {
                        SPUtils.put(context, Config.DEFAULT_RSSI, 0 - customRssi);
                        if (onQueryListener != null) {
                            onQueryListener.query(0 - customRssi);
                        }
                        T.showShort(context, R.string.rssi_set_success);
                    }
                }
                break;
        }
        dismiss();
    }
}
