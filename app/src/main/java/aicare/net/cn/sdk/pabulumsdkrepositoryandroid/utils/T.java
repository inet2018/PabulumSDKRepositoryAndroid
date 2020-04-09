package aicare.net.cn.sdk.pabulumsdkrepositoryandroid.utils;

import android.content.Context;
import android.widget.Toast;

public class T {

	private T() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	private static Toast mToast;


	public static void showShort(Context context, CharSequence message) {
		if (mToast == null) {
			mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void showShort(Context context, int message) {
		if (mToast == null) {
			mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}


	public static void showLong(Context context, CharSequence message) {
		if (mToast == null) {
			mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		} else {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		mToast.show();
	}


	public static void showLong(Context context, int message) {
		if (mToast == null) {
			mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		} else {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		mToast.show();
	}


	public static void show(Context context, CharSequence message, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, message, duration);
		} else {
			mToast.setText(message);
			mToast.setDuration(duration);
		}
		mToast.show();
	}


	public static void show(Context context, int message, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, message, duration);
		} else {
			mToast.setText(message);
			mToast.setDuration(duration);
		}
		mToast.show();
	}

}