# Seafox

作者：Aiven <br>
联系方式：aiven163@sina.com  <br>

# Description

这是一个简单的App框架，主要实现功能如下：<br/>

1、HTTP  主要集合volley框架<br/>
2、View  通过注解对Activity和Fragment中的View赋值，不用再通过findViewById去获取<br/>
3、Command  命令，主要用于Activity，Fragment各自之间的通信<br/>
4、Event 在下面讲解<br/>

### 使用方式

* 如果使用的是Eclipse，请下载作为一个library 工程使用 <br/>
* 如果你使用的是Android Studio开发项目，可以直接在你app中的build.gradle的dependencies{}中添加代码:<br/>
  
    `compile 'com.aiven.seafox:seafox:1.0.0'`


# 具体介绍

### 任务

Event 任务，这里的任务主要是耗时任务，主要分为普通耗时任务和网络耗时任务，均在子线程中执行，执行完毕后
通知View层。任务支持连续任务，可以给一个任务设置下一个执行任务，当当前任务执行完毕后，自动执行设置好的nextEvent <br/>

1、网络任务：<br/>
   编写一个网络任务类，继承自HttpEvent。<br/>
   实例如下：
   
   ```Java 
   
   public class ImgGetEvent extends HttpEvent {
    private int cid;
    private int pageIndex;

    public ImgGetEvent(int id, String PanelName, int cid, int pageIndex) {
        super(id, PanelName);
        this.cid = cid;
        this.pageIndex = pageIndex;
    }

    /**
     * 设置请求参数
     * @param arrayList
     * @return
     */
    @Override
    public Request paramRequest(ArrayList<EventParam> arrayList) {
        Request request = new Request();
        request.setUrl(Constants.HTTP_BASE + cid + Constants.PAGE_ADRR + pageIndex);
        Logs.logPrint("HTTP", request.getUrl());
        return request;
    }

    /**
     * 数据解析
     * @param s
     * @return
     * @throws EventException
     */
    @Override
    public Response analysisData(String s) throws EventException {
        Logs.logPrint("HTTP", s);
        Response response = new Response();
        List<ImgBase> imgListBase = HtmlGetImgUtil.htmlGetImgs(s);
        response.setDataObj(imgListBase);
        PageInfo page = new PageInfo();
        page.currentPageIndex = pageIndex;
        response.setPageInfo(page);
        return response;
    }

    /**
     * 下一个任务执行参数
     * @param arrayList
     * @return
     */
    @Override
    public ArrayList<EventParam> nextEventParam(ArrayList<EventParam> arrayList) {
        return null;
    }

}

```

  任务类编写完成了，那么怎么在Activity中使用呢？<br/>
  我们需要编写一个Activity继承自BaseActivity，实现父类的方法，然后在需要执行任务的地方，实例化该任务，并且添加任务参数，最后调用该任务的excute()放假，既可以开始执行任务。这里注意，如果是Fragment则继承自BaseFragment<br>
  我们可以看到activity从父类继承下来了很多方法。下面先介绍一下吧<br/>
  
  
  
   * Activity、Fragment入口方法<br/>
    ```Java
  	 @Override
     public void initView(View view, Bundle bundle) {}
    ```
  
   * 数据初始化方法
    ```Java
    @Override
    public void initData(Bundle bundle) {}  
    ```
   * 指定接收命令方法，用于指定该界面只接收哪些命令
   
   ```Java
    @Override
    public int[] registReceiveCmdIds() {
        return new int[0];
    }
    ```
   * 命令接收方法
    ```Java
    @Override
    public void onHandCmd(int cmdId, Object data) {}
    ```
    
   * 任务接收方法
    ```Java
    @Override
    public void eventHandle(int taskId, Object data) {  //在此更新UI  }
    ```
   回到上面说的HTTP任务，任务完成后回到了activity中，Activity源码附上：
   
   ```Java
   @ELayout(Layout = R.layout.activity_main)
   public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,               MeiZAdapter.OnTapClickListener {

    private static final int TASK_ID = 100;

    @EWidget(id = R.id.tmpIv)
    private ImageView mTmpIv;

    @EWidget(id = R.id.appBarLayout)
    private AppBarLayout mAppBar;

    @EWidget(id = R.id.toolBar)
    private Toolbar mToolBar;

    @EWidget(id = R.id.swip)
    private SwipeRefreshLayout mRefreshView;

    @EWidget(id = R.id.recyView)
    private RecyclerView mRecyclerView;

    private ImgGetEvent event;
    private List<ImgBase> dataList;
    private MeiZAdapter mAdapter;

    private boolean mIsFirstTimeTouchBottom = true;

    private int mCurrentType = 1;
    private int mCurrentIndex = 1;
    private boolean noMore = false;

    @Override
    public void initView(View view, Bundle bundle) {
        setSupportActionBar(mToolBar);
        LogConfig.Debug = true;
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
        initUI();
    }

    @Override
    public void initData(Bundle bundle) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestData(mCurrentType, mCurrentIndex);
            }
        }, 100);
    }


    private void initUI() {
        mRefreshView.setColorSchemeColors(
                getResources().getColor(R.color.md_red_100),
                getResources().getColor(R.color.md_red_300),
                getResources().getColor(R.color.md_red_500),
                getResources().getColor(R.color.md_red_800)
        );
        mRefreshView.setOnRefreshListener(this);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
        );
        mRecyclerView.setLayoutManager(layoutManager);
        dataList = new ArrayList<>();
        mAdapter = new MeiZAdapter(dataList, this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!mRefreshView.isRefreshing() && layoutManager.findLastCompletelyVisibleItemPositions(
                        new int[2]
                )[1] >= mAdapter.getItemCount() - 2) {
                    if (noMore) {
                        return;
                    }
                    if (!mIsFirstTimeTouchBottom) {
                        mRefreshView.setRefreshing(true);
                        requestData(mCurrentType, mCurrentIndex);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        });
    }

    @Override
    public int[] registReceiveCmdIds() {
        return new int[0];
    }

    @Override
    public void onHandCmd(int i, Object o) {
    }

   //任务完成接收的地方，这里跟任务关联起来了
    @Override
    public void eventHandle(int i, Object o) {
        if (i == TASK_ID) {
            Response response = (Response) o;
            List<ImgBase> tmpList = (List<ImgBase>) response.getDataObj();
            if (response.getPageInfo().currentPageIndex == 1) {
                dataList.clear();
            }
            dataList.addAll(tmpList);
            mAdapter.update(dataList);
            mRefreshView.setRefreshing(false);
            if (tmpList.size() == 0) {
                noMore = true;
                showToast("全部加载完毕");
            }
            mCurrentIndex = response.getPageInfo().currentPageIndex + 1;
        }
    }

   //任务失败，或者任务错误回调
    @Override
    public void eventError(int i, int i1, String s) {
        if (i == TASK_ID) {
            mRefreshView.setRefreshing(false);
            showToast("请求失败");
        }
    }

    @Override
    public void onRefresh() {
        mCurrentIndex = 1;
        noMore = false;
        requestData(mCurrentType, mCurrentIndex);
    }

    private void requestData(final int type, final int pageIndex) {
        mRefreshView.setRefreshing(true);
        event = new ImgGetEvent(TASK_ID, panelName, type, pageIndex);
        event.excute();
    }

    @Override
    public void onClick(final ImgBase base, final View view) {
        Picasso.with(MainActivity.this).load(base.getNomalImgUrl()).into(
                mTmpIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        Intent i = new Intent(MainActivity.this, ImgShowAct.class);
                        Bundle bd = new Bundle();
                        bd.putSerializable("data", base);
                        i.putExtras(bd);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                MainActivity.this, view, ImgShowAct.TRANSIT_PIC
                        );
                        ActivityCompat.startActivity(
                                MainActivity.this, i, optionsCompat.toBundle()
                        );
                    }

                    @Override
                    public void onError() {
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int type = mCurrentType;
        switch (item.getItemId()) {
            case R.id.item_meinv://美女
                mCurrentType = 3;
                break;
            case R.id.item_huahui://花卉
                mCurrentType = 4;
                break;
            case R.id.item_fenjing://风景
                mCurrentType = 2;
                break;
            case R.id.item_meishi://美食
                mCurrentType = 1546;
                break;
        }
        if (type != mCurrentType) {
            mRecyclerView.smoothScrollToPosition(0);
            mCurrentIndex = 1;
            requestData(mCurrentType, mCurrentIndex);
        }
        return true;
    }
  }
  ```
2、普通任务，<br/>
   普通任务继承自CommonEvent，方法基本和HTTP任务相似，具体操作不再给出实例了。
   

### 命令

* 命令的发送<br/>
  首先构造一个命令，指定他的命令ID
   ```Java
   Cmd cmd=new Cmd(1001);
   cmd.setData("我是命令携带数据");
   ```
   这样构造好了一个命令之后接下来就是把这个命令发送出去：<br/>
   a 如果在Activity或者Fragment中直接调用此方法：
   ```Java
     sendCommand(cmd);
   ```
   b 如果不是在activity或者fragment中，则没有该方法的实现，需要从中介者中去调用方法，具体发送方法如下：
   ```Java
     Mediator.getInstance().sendCommand(cmd);
   ```
   这样就将命令发送出去了.
   
* 命令的接收<br/>
   命令的接收只能在Activity和Fragment中，这个是前提：<br/>
   首先注册接收命令：
   ```Java
   @Override
    public int[] registReceiveCmdIds() {
        return new int[]{1001};
    }
   ```
   这里注册了接收的命令ID为1001，当然可以接收很多个命令<br/><br/>
   
   只要注册后，命令在其他地方已发送，这个界面将会收到命令，接收命令的入口：
   ```Java
   @Override
    public void onHandCmd(int cmdId, Object o) {
        if(cmdId==1001){
            showToast(o.toString());
        }
    }
   ``` 
   可以测试在这里成功收到了命令，当然同一个界面可以自己给自己发送命令，但是不建议，因为没必要，直接调方法就可以。命令主要用在不同界面或者非UI任务通知UI的时候用到。
   
### View注解

  注解这里就不再多说了，上面给出的Activity源码已经很好地给出了注解的应用，参照应用就可以了
  
  
  
