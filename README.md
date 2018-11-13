oidc-sample-android: OpenID Connect 認証サンプル（Android アプリケーション）
==========================================================================
AndroidアプリケーションでOpenID Connect 認証を行うためのサンプルです。

BaaS サーバ 事前準備
----------------------
先に BaaS サーバ上に OpenID Connect を設定したテナントを作成しておく必要があります。  
手順は以下の通りです。

1. BaaSコンソールでテナントを新規作成する。認証方式に 通常認証+OIDC認証 を指定すること。
2. OpenID Connect 設定画面の"OPリスト"から使用するOP種別の設定を行う。
3. OpenID Connect 設定画面の"許可するリダイレクトURI(オプション)"に以下を登録する。  
   [Scheme（任意）]://[Host（任意）] （例：sampleapp1://oidc_callback）

アプリケーション 事前設定
-------------------------
app/src/main/res/values/settings.sample.xml を同ディレクトリに settings.xml としてコピーし、  
settings.xml 内の以下変数にBaaSのAPIベースURL、テナントID、アプリID、  
アプリキー、スキーム名、ホスト名、OP設定などを設定してください。

### 設定対象
* EndpointUrl
* TenantId
* AppId
* AppKey
* Scheme
* Host
* OP  
  設定可能な値は以下の通りです。  
    * google: Google
    * other: OpenAM
    * adfs: ADFS (Windows Server 2016)  
* Scope (オプション)

サーバ自己署名証明書を許可する場合やSDKをデバッグモードで動作させたい場合は
以下の設定をtrueとしてください。

###  設定対象
* SSL_Self_Signed  
  trueの場合、サーバ自己署名証明書を許可します。
* DebugMode  
  trueの場合、デバッグモード動作となりデバッグログが出力されます。

利用手順
--------
サンプルアプリケーションを起動し、"LOGIN"ボタンをタップしてください。  
システムデフォルトのブラウザを起動しBaaSサーバへ認証開始要求を行います。  
認証操作が成功すると、アプリケーションが再表示されログイン処理が行われます。  

尚、処理結果状況に応じ、以下のメッセージがアプリケーション上に表示されます。

* 認証成功時  
  BaaSサーバからのレスポンス情報。
* ログイン成功時  
  処理が成功である旨のメッセージ。
* ログイン失敗時  
  処理が失敗である旨のメッセージおよびエラー内容。
