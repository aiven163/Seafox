# Seafox
this is a basic app framework

这是一个简单的App框架，主要实现功能如下：

1、HTTP  主要集合volley框架
2、View  通过注解对Activity和Fragment中的View赋值，不用再通过findViewById去获取
3、Command  命令，主要用于Activity，Fragment各自之间的通信
4、Event 在下面讲解


Event 任务，这里的任务主要是耗时任务，主要分为普通耗时任务和网络耗时任务，均在子线程中执行，执行完毕后
通知View层


1、网络任务：
   编写一个网络任务类，继承自HttpEvent。
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
    public ArrayList<EventParam> paramNextEventParam(ArrayList<EventParam> arrayList) {
        return null;
    }

}

```

  任务类编写完成了，那么怎么在Activity中使用呢？
  我们需要编写一个Activity继承自BaseActivity，实现父类的方法，然后在需要执行任务的地方，实例化该任务，并且添加任务参数，最后调用该任务的excute()放假，既可以开始执行任务。<br>
  我们可以看到activity从父类继承下来了很多方法。下面先介绍一下吧
  
  
  
   * Activity、Fragment入口方法
  
   ```Java
  	@Override
    public void initView(View view, Bundle bundle) {}
    
  ```
  
   * 数据初始化方法
   ```Java
   @Override
    public void initData(Bundle bundle) {
      
    }  
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
    public void onHandCmd(int cmdId, Object data) {
    }
    ```
    
    * 任务接收方法
    ```Java
    @Override
    public void eventHandle(int taskId, Object data) {
    }
    ```
