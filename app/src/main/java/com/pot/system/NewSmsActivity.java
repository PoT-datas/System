package com.pot.system;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import api.pot.system.phone.XPhoning;
import api.pot.system.sms.Message;
import api.pot.system.sms.SmsCallback;
import api.pot.system.sms.SmsLink;
import api.pot.system.sms.XSms;

public class NewSmsActivity extends AppCompatActivity {
    TextView phone;
    TextView msg;
    TextView submit;
    TextView data;
    TextView simId;

    Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sms);

        phone = (TextView) findViewById(R.id.phone);
        msg = (TextView) findViewById(R.id.msg);
        submit = (Button) findViewById(R.id.submit);
        data = (TextView) findViewById(R.id.data);
        simId = (TextView) findViewById(R.id.simId);


        QuoteBank mQuoteBank = new QuoteBank(NewSmsActivity.this);
        List<String> mLines = mQuoteBank.readLine("datas.txt");
        for (String string : mLines) {
            data.setText(data.getText() + "\n" + string);
        }

        data.setVisibility(View.INVISIBLE);
        submit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (data.getVisibility() == View.VISIBLE)
                    data.setVisibility(View.INVISIBLE);
                else
                    data.setVisibility(View.VISIBLE);
                return true;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sId = 0;
                try {
                    sId = getSim();
                    /*sId = Integer.valueOf(simId.getText().toString());
                    sId %= 2;*/
                } catch (Exception e) {
                }
                simId.setText((sId+1) + "");
                //String data = msg.getText().toString();
                String data = "smslink://" + phone.getText().toString() + "/api/data?id=" + msg.getText().toString();
                if (!XSms.phoneValide(phone.getText().toString()) && !XSms.linkValide(data)) {
                    Toast.makeText(NewSmsActivity.this, "Not Valid Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (XSms.linkValide(data)) message = new Message(data);
                else message = new Message(phone.getText().toString(), data);
                ///
                //message.sendSms();
                ///
                Toast.makeText(NewSmsActivity.this,
                        "Sending : " + XSms.with(NewSmsActivity.this)
                                .sendSms(sId, message),
                        Toast.LENGTH_SHORT).show();
            }
        });

        XSms.with(this)
                .listener(new SmsCallback() {
                    @Override
                    public Message onReceivedCmd(Message message) {
                        String[] data;
                        if(message.getBody().length()==2) {
                            message.setBody("smslink://"+message.getAddress()+"/api/user?id="+message.getBody());
                        }else if( (data=message.getBody().split(" ")).length==2 ){
                            message.setBody("smslink://"+message.getAddress()+"/api/"+data[0].toLowerCase()+"?id="+data[1].toLowerCase());
                        }
                        return message;
                    }

                    @Override
                    public void onReceivedSms(Message message) {
                        Toast.makeText(NewSmsActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReceivedLink(SmsLink link) {
                        Message response = new Message(link.message.getAddress(), "");
                        ///
                        int sId = 0;
                        try {
                            sId = getSim();
                        } catch (Exception e) {}
                        simId.setText((sId+1) + "");
                        ///
                        if (link.checkForAPI("api/etat")) {
                            if (link.checkParamsAvailable("id")) {
                                String id = link.getParam("id");
                                //
                                QuoteBank mQuoteBank = new QuoteBank(NewSmsActivity.this);
                                List<String> mLines = mQuoteBank.readLine("user.txt");
                                response.setBody("api/data : \nNo Match Id");
                                for (String string : mLines) {
                                    if (string.substring(0, string.indexOf(":")).equals(id)) {
                                        response.setBody("api/data : \n" +
                                                "id = " + id + "\n" +
                                                "Noms = " + string.substring(string.indexOf(":") + 1));
                                        break;
                                    }
                                }
                            } else {
                                response.setBody("api/data : \nMissing Params");
                            }
                        } else if (link.checkForAPI("api/méteo")) {
                            if (link.checkParamsAvailable("id")) {
                                String id = link.getParam("id");
                                //
                                QuoteBank mQuoteBank = new QuoteBank(NewSmsActivity.this);
                                List<String> mLines = mQuoteBank.readLine("user.txt");
                                response.setBody("api/data : \nNo Match Id");
                                for (String string : mLines) {
                                    if (string.substring(0, string.indexOf(":")).equals(id)) {
                                        response.setBody("api/data : \n" +
                                                "id = " + id + "\n" +
                                                "Noms = " + string.substring(string.indexOf(":") + 1));
                                        break;
                                    }
                                }
                            } else {
                                response.setBody("api/data : \nMissing Params");
                            }
                        } else if (link.checkForAPI("api/présence")) {
                            if (link.checkParamsAvailable("id")) {
                                String id = link.getParam("id");
                                //
                                QuoteBank mQuoteBank = new QuoteBank(NewSmsActivity.this);
                                List<String> mLines = mQuoteBank.readLine("user.txt");
                                response.setBody("api/data : \nNo Match Id");
                                for (String string : mLines) {
                                    if (string.substring(0, string.indexOf(":")).equals(id)) {
                                        response.setBody("api/data : \n" +
                                                "id = " + id + "\n" +
                                                "Noms = " + string.substring(string.indexOf(":") + 1));
                                        break;
                                    }
                                }
                            } else {
                                response.setBody("api/data : \nMissing Params");
                            }
                        } else if (link.checkForAPI("api/user")) {
                            if (link.checkParamsAvailable("id")) {
                                String id = link.getParam("id");
                                //
                                QuoteBank mQuoteBank = new QuoteBank(NewSmsActivity.this);
                                List<String> mLines = mQuoteBank.readLine("user.txt");
                                response.setBody("api/data : \nNo Match Id");
                                for (String string : mLines) {
                                    if (string.substring(0, string.indexOf(":")).equals(id)) {
                                        response.setBody("api/data : \n" +
                                                "id = " + id + "\n" +
                                                "Noms = " + string.substring(string.indexOf(":") + 1));
                                        break;
                                    }
                                }
                            } else {
                                response.setBody("api/data : \nMissing Params");
                            }
                        } else if (link.checkForAPI("api/somme")) {
                            if (link.params.size() > 1 && link.checkParamsAvailable("a", "b")) {
                                int some = 0;
                                try {
                                    some = Integer.valueOf(link.getParam("a")) + Integer.valueOf(link.getParam("b"));
                                } catch (Exception e) {
                                }
                                response.setBody("api/somme : \n" +
                                        link.getParam("a") + " + " + link.getParam("b") + " = " + some);
                            } else {
                                response.setBody("api/somme : \nMissing Params");
                            }
                        }else {
                            response.setBody("api/data : \nNo Match API");
                        }
                        ///
                        XSms.with(NewSmsActivity.this)
                                .sendSms(sId, response);
                    }
                });
    }

    @SuppressLint({"MissingPermission", "NewApi"})
    private int getSim() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int n=manager.getPhoneCount();
        ///
        //int n = XPhoning.getSIMInfo(NewSmsActivity.this).size();
        int i = Integer.valueOf(simId.getText().toString());
        i -= 1;
        if(i<0) i=0;
        if( (i/n)!=0 ) i%=n;
        return i;
    }
}
