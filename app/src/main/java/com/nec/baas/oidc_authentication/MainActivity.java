package com.nec.baas.oidc_authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * アプリ起動画面のActivity
 */
public class MainActivity extends AppCompatActivity  implements OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttonを取得
        Button btnLogin = (Button) findViewById(R.id.BtnMainActivitylogin);
        btnLogin.setOnClickListener(this);

        TextView outputView = (TextView) findViewById(R.id.TvMainActivityMessage);

        // 入力パラメータの表示
        StringBuffer paramInfo = new StringBuffer("");
        paramInfo.append("-BaaS Access Setting --------" + System.getProperty("line.separator"));

        String formatString = "%s : %s " + System.getProperty("line.separator");

        paramInfo.append(String.format(formatString, "EndpointUri    " , getString(R.string.EndpointUri)));
        paramInfo.append(String.format(formatString, "TenantId       " , getString(R.string.TenantId)));
        paramInfo.append(String.format(formatString, "AppId          " , getString(R.string.AppId)));
        paramInfo.append(String.format(formatString, "AppKey         " , getString(R.string.AppKey)));
        paramInfo.append(String.format(formatString, "Scheme         " , getString(R.string.Scheme)));
        paramInfo.append(String.format(formatString, "Host           " , getString(R.string.Host)));
        paramInfo.append(String.format(formatString, "OP             " , getString(R.string.OP)));
        paramInfo.append(String.format(formatString, "Scope          " , getString(R.string.Scope)));

        paramInfo.append("-App Setting ----------" + System.getProperty("line.separator"));
        paramInfo.append(String.format(formatString, "SSL_Self_Signed" , getString(R.string.SSL_Self_Signed)));
        paramInfo.append(String.format(formatString, "DebugMode      " , getString(R.string.DebugMode)));

        outputView.setText(paramInfo.toString()) ;

    }

    /**
     * ボタン押下時のイベントメソッド
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.BtnMainActivitylogin:
                // ブラウザを起動してOpenIDConnect認証開始のREST-APIを実行する
                final String redirectUri = String.format("%s://%s",
                        getString(R.string.Scheme), getString(R.string.Host));
                final String endpointUri = getString(R.string.EndpointUri);
                final String tenantId    = getString(R.string.TenantId);

                try {
                    StringBuffer sbRequestUri = new StringBuffer();

                    // Pass
                    sbRequestUri.append(String.format("%s/1/%s/auth/oidc/start",
                            endpointUri, tenantId));
                    // redirectUri
                    sbRequestUri.append(String.format("?redirect=%s",
                            URLEncoder.encode(redirectUri, "UTF-8")));

                    // op
                    sbRequestUri.append(String.format("&op=%s",getString(R.string.OP)));

                    // createUser
                    sbRequestUri.append("&createUser=true");

                    // scope
                    if(getString(R.string.Scope).compareTo("") != 0) {
                        sbRequestUri.append(String.format("&scope=%s",
                            URLEncoder.encode(getString(R.string.Scope), "UTF-8")));
                    }

                    Log.d(TAG,sbRequestUri.toString());

                    Uri uri = Uri.parse(sbRequestUri.toString());

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivity(intent);

                    Toast.makeText(this, "Request OpenID Connect Authentication", Toast.LENGTH_LONG).show();

                } catch (UnsupportedEncodingException e) {
                    TextView outputView = (TextView) findViewById(R.id.TvMainActivityMessage);
                    outputView.append("Error CreateUrl:" + e.toString());
                    Log.e(TAG,e.toString());
                } catch (Exception e) {
                    TextView outputView = (TextView) findViewById(R.id.TvMainActivityMessage);
                    outputView.append("Error Other Exception: :" + e.toString());
                    Log.e(TAG,e.toString());
                }
                break;
            default:
                break;
        }
    }
}
