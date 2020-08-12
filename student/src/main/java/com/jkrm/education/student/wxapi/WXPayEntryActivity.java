package com.jkrm.education.student.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.rx.RxPayType;
import com.jkrm.education.constants.Extras;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, MyApp.WX_APP_ID);
        api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}



	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		PayResp res = (PayResp) resp;
		switch (res.extData){
			//订单
			case Extras.ORDER_PAY:
				if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
					switch (resp.errCode) {
						case 0://支付成功
							Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
							EventBus.getDefault().post(new RxPayType(RxPayType.WECHAT_PAY,RxPayType.PAY_SUCCESS));
							break;
						case -1://错误，可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
							Toast.makeText(this, "支付错误", Toast.LENGTH_SHORT).show();
							EventBus.getDefault().post(new RxPayType(RxPayType.WECHAT_PAY,RxPayType.PAY_FAIL));
							break;
						case -2://用户取消，无需处理。发生场景：用户不支付了，点击取消，返回APP。
							Toast.makeText(WXPayEntryActivity.this, "用户取消支付", Toast.LENGTH_SHORT).show();
							EventBus.getDefault().post(new RxPayType(RxPayType.WECHAT_PAY,RxPayType.PAY_CANCEL));
							break;

					}
				}
				break;
				//充值
			case Extras.RECHARGE_PAY:
				if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
					switch (resp.errCode) {
						case 0://支付成功
							Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
							EventBus.getDefault().post(new RxPayType(RxPayType.WECHAT_RECHARGE,RxPayType.PAY_SUCCESS));
							break;
						case -1://错误，可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
							Toast.makeText(this, "支付错误", Toast.LENGTH_SHORT).show();
							EventBus.getDefault().post(new RxPayType(RxPayType.WECHAT_RECHARGE,RxPayType.PAY_FAIL));
							break;
						case -2://用户取消，无需处理。发生场景：用户不支付了，点击取消，返回APP。
							Toast.makeText(WXPayEntryActivity.this, "用户取消支付", Toast.LENGTH_SHORT).show();
							EventBus.getDefault().post(new RxPayType(RxPayType.WECHAT_RECHARGE,RxPayType.PAY_CANCEL));
							break;

					}
				}
				break;
		}

		finish();
    }
}