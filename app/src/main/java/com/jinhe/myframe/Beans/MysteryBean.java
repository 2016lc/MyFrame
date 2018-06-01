package com.jinhe.myframe.Beans;



import com.jinhe.myframe.base.BaseBean;

import java.util.List;

/**
 * Created by LC on 2018/3/8.
 */

public class MysteryBean extends BaseBean {

    /**
     * total : 0
     * count : 0
     * data : [{"identity":2,"title":"测试测试","dateline":"19小时前","actionNo":25},{"identity":3,"title":"旦撒旦撒sa","dateline":"19小时前","actionNo":25},{"identity":4,"title":"测试测试cs","dateline":"19小时前","actionNo":25},{"identity":5,"title":"测试测试测试测试13213213","dateline":"19小时前","actionNo":25}]
     */

    private int total;
    private int count;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * identity : 2
         * title : 测试测试
         * dateline : 19小时前
         * actionNo : 25
         */

        private int identity;
        private String title;
        private String dateline;
        private int actionNo;
        private String content;


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public int getActionNo() {
            return actionNo;
        }

        public void setActionNo(int actionNo) {
            this.actionNo = actionNo;
        }
    }
}
