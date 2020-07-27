package com.sobot.chat.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.sobot.chat.activity.WebViewActivity;
import com.sobot.chat.api.model.SobotMultiDiaRespInfo;
import com.sobot.chat.api.model.ZhiChiMessageBase;
import com.sobot.chat.utils.ChatUtils;
import com.sobot.chat.utils.HtmlTools;
import com.sobot.chat.utils.ResourceUtils;
import com.sobot.chat.utils.SobotOption;
import com.sobot.chat.viewHolder.base.MessageHolderBase;

import java.util.List;
import java.util.Map;

public class RobotTemplateMessageHolder6 extends MessageHolderBase {

    private TextView sobot_template6_title;
    private TextView sobot_template6_msg;

    public RobotTemplateMessageHolder6(Context context, View convertView) {
        super(context, convertView);
        sobot_template6_msg = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id", "sobot_template6_msg"));
        sobot_template6_title = (TextView) convertView.findViewById(ResourceUtils.getIdByName(context, "id", "sobot_template6_title"));
    }

    @Override
    public void bindData(final Context context, final ZhiChiMessageBase message) {
        if (message.getAnswer() != null && message.getAnswer().getMultiDiaRespInfo() != null) {
            final SobotMultiDiaRespInfo multiDiaRespInfo = message.getAnswer().getMultiDiaRespInfo();
            sobot_template6_msg.setText(ChatUtils.getMultiMsgTitle(multiDiaRespInfo));
            applyTextViewUIConfig(sobot_template6_msg);
            final List<Map<String, String>> interfaceRetList = multiDiaRespInfo.getInterfaceRetList();
            if ("000000".equals(multiDiaRespInfo.getRetCode()) && interfaceRetList != null && interfaceRetList.size() > 0) {
                final Map<String, String> interfaceRet = interfaceRetList.get(0);
                if (interfaceRet != null && interfaceRet.size() > 0) {
                    setSuccessView();
                    HtmlTools.getInstance(context).setRichText(sobot_template6_title, interfaceRet.get("tempStr").replaceAll("\n", "<br/>"), getLinkTextColor());

                }
            } else {
                setFailureView();
            }
        }
    }


    private void setSuccessView() {
        sobot_template6_msg.setVisibility(View.VISIBLE);
        sobot_template6_title.setVisibility(View.VISIBLE);
    }

    private void setFailureView() {
        sobot_template6_msg.setVisibility(View.VISIBLE);
        sobot_template6_title.setVisibility(View.GONE);
    }
}