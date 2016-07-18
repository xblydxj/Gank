package xblydxj.gank.bean;

import java.util.List;

/**
 * Created by 46321 on 2016/7/17/017.
 */
public class Data {

    /**
     * error : false
     * results : [{"_id":"57878d15421aa90dea11e9f3","createdAt":"2016-07-14T21:01:09.982Z",
     * "desc":"Android消息机制的原理剖析\u2014闭环总结 ","publishedAt":"2016-07-15T11:56:07.907Z","source":"web","type":"Data",
     * "url":"http://blog.csdn.net/u012164786/article/details/51638753","used":true,"who":"ayanamist"},
     * {"_id":"57875f1e421aa90de83c1b8e","createdAt":"2016-07-14T17:45:02.387Z","desc":"Data N (6.X) 开发者预览指南",
     * "publishedAt":"2016-07-15T11:56:07.907Z","source":"web","type":"Data","url":"http://www.jianshu
     * .com/p/a269f06433b3","used":true,"who":"单刀土豆"},{"_id":"57874364421aa90df33fe79e",
     * "createdAt":"2016-07-14T15:46:44.532Z","desc":"MIUI8 悬浮球","publishedAt":"2016-07-15T11:56:07.907Z",
     * "source":"chrome","type":"Data","url":"https://github.com/hanbaokun/FloatingViewService","used":true,
     * "who":"蒋朋"},{"_id":"5786f961421aa90df638bb2b","createdAt":"2016-07-14T10:30:57.387Z","desc":"Google Play
     * Top200 应用分析报告","publishedAt":"2016-07-14T11:39:49.710Z","source":"web","type":"Data","url":"http://mp
     * .weixin.qq.com/s?__biz=MzA5OTMxMjQzMw==&mid=2648112540&idx=1&sn=c2dc3d17f561337ce9b0fe93537d769f&scene=0
     * #wechat_redirect","used":true,"who":null},{"_id":"5786f616421aa90de83c1b86","createdAt":"2016-07-14T10:16:54
     * .844Z","desc":"基于 Constraints 布局方式实现的一个 Demo ，可以跟着学习下 Constraints 的用法。","publishedAt":"2016-07-14T11:39:49
     * .710Z","source":"chrome","type":"Data","url":"https://github.com/hitherejoe/Constraints","used":true,
     * "who":"代码家"},{"_id":"5786f59e421aa90dea11e9ea","createdAt":"2016-07-14T10:14:54.225Z","desc":"用 ImageView
     * 实现头像","publishedAt":"2016-07-14T11:39:49.710Z","source":"chrome","type":"Data","url":"https://github
     * .com/Carbs0126/AvatarImageView","used":true,"who":"代码家"},{"_id":"5786c69a421aa90df33fe794",
     * "createdAt":"2016-07-14T06:54:18.230Z","desc":"自定义ActionProvider，ToolBar 自定义Menu，Menu添加角标小红点",
     * "publishedAt":"2016-07-14T11:39:49.710Z","source":"web","type":"Data","url":"http://blog.csdn
     * .net/yanzhenjie1003/article/details/51902796","used":true,"who":"严振杰"},{"_id":"57865e09421aa90dea11e9e8",
     * "createdAt":"2016-07-13T23:28:09.5Z","desc":"深入了解View之事件分发和处理","publishedAt":"2016-07-14T11:39:49.710Z",
     * "source":"web","type":"Data","url":"http://shaohui
     * .xyz/2016/07/12/Data%E4%B8%AD%E7%9A%84%E4%BA%8B%E4%BB%B6%E5%88%86%E5%8F%91%E5%92%8C%E5%A4%84%E7%90%86/",
     * "used":true,"who":"邵辉Vista"},{"_id":"57862b76421aa90df33fe78e","createdAt":"2016-07-13T19:52:22.313Z",
     * "desc":"HermesEventBus-饿了么开源的Android跨进程事件分发框架","publishedAt":"2016-07-15T11:56:07.907Z","source":"web",
     * "type":"Data","url":"https://elelogistics.github
     * .io/2016/07/13/HermesEventBus-%E4%B8%80%E7%A7%8D%E6%96%B0%E7%9A%84Android%E8%B7%A8%E8%BF%9B%E7%A8%8B%E4%BA%8B
     * %E4%BB%B6%E5%88%86%E5%8F%91%E6%A1%86%E6%9E%B6/","used":true,"who":"xujinyang"},
     * {"_id":"5785d4f6421aa90df638bb1f","createdAt":"2016-07-13T13:43:18.660Z",
     * "desc":"这篇文章通过模仿知乎介绍了自定义Behavior，通过模仿百度地图介绍了BottomSheetBehavior的使用。","publishedAt":"2016-07-14T11:39:49.710Z",
     * "source":"web","type":"Data","url":"http://www.jianshu.com/p/488283f74e69","used":true,"who":null}]
     */

    private boolean error;
    /**
     * _id : 57878d15421aa90dea11e9f3
     * createdAt : 2016-07-14T21:01:09.982Z
     * desc : Android消息机制的原理剖析—闭环总结
     * publishedAt : 2016-07-15T11:56:07.907Z
     * source : web
     * type : Data
     * url : http://blog.csdn.net/u012164786/article/details/51638753
     * used : true
     * who : ayanamist
     */

    private List<ResultsBean> results;

    public boolean isError() { return error;}

    public void setError(boolean error) { this.error = error;}

    public List<ResultsBean> getResults() { return results;}

    public void setResults(List<ResultsBean> results) { this.results = results;}

    public static class ResultsBean {
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() { return _id;}

        public void set_id(String _id) { this._id = _id;}

        public String getCreatedAt() { return createdAt;}

        public void setCreatedAt(String createdAt) { this.createdAt = createdAt;}

        public String getDesc() { return desc;}

        public void setDesc(String desc) { this.desc = desc;}

        public String getPublishedAt() { return publishedAt;}

        public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt;}

        public String getSource() { return source;}

        public void setSource(String source) { this.source = source;}

        public String getType() { return type;}

        public void setType(String type) { this.type = type;}

        public String getUrl() { return url;}

        public void setUrl(String url) { this.url = url;}

        public boolean isUsed() { return used;}

        public void setUsed(boolean used) { this.used = used;}

        public String getWho() { return who;}

        public void setWho(String who) { this.who = who;}
    }
}
