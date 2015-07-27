# Seafox
this is a basic app framework

����һ���򵥵�App��ܣ���Ҫʵ�ֹ������£�

1��HTTP  ��Ҫ����volley���
2��View  ͨ��ע���Activity��Fragment�е�View��ֵ��������ͨ��findViewByIdȥ��ȡ
3��Command  �����Ҫ����Activity��Fragment����֮���ͨ��
4��Event �����潲��


Event ���������������Ҫ�Ǻ�ʱ������Ҫ��Ϊ��ͨ��ʱ����������ʱ���񣬾������߳���ִ�У�ִ����Ϻ�
֪ͨView��


1����������
   ��дһ�����������࣬�̳���HttpEvent��
   ʵ�����£�
   public class ImgGetEvent extends HttpEvent {

    private int cid;
    private int pageIndex;

    public ImgGetEvent(int id, String PanelName, int cid, int pageIndex) {
        super(id, PanelName);
        this.cid = cid;
        this.pageIndex = pageIndex;
    }

    /**
     * �����������
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
     * ���ݽ���
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
     * ��һ������ִ�в���
     * @param arrayList
     * @return
     */
    @Override
    public ArrayList<EventParam> paramNextEventParam(ArrayList<EventParam> arrayList) {
        return null;
    }

}
