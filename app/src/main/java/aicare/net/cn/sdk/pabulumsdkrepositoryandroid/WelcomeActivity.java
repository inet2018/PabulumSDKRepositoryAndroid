package aicare.net.cn.sdk.pabulumsdkrepositoryandroid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import aicare.net.cn.sdk.pabulumsdkrepositoryandroid.base.BaseActivity;
import aicare.net.cn.sdk.pabulumsdkrepositoryandroid.utils.AppUtils;
import aicare.net.cn.sdk.pabulumsdkrepositoryandroid.utils.T;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.net.aicare.pabulumlibrary.PabulumSDK;

/**
 * Created by Suzy on 2016/5/10.
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.tv_show_version)
    TextView tv_show_version;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initViews();
        PabulumSDK.getInstance().init(this,"8b834bfc14581ad3","00253970fb418e7b8e240f2869");
        if (ensureBLESupported()) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    openActivity(MainActivity.class);
                    WelcomeActivity.this.finish();
                }
            }, 3000);
        } else {
            T.showLong(this, R.string.not_support_ble);
            this.finish();
        }
    }

    protected void openActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private void initViews() {
        tv_show_version.setText(getString(R.string.current_version, AppUtils.getVersionName(this)));
    }
}
