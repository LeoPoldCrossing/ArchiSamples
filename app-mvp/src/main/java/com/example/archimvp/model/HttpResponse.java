package com.example.archimvp.model;

/**
 * Created by LeoPoldCrossing on 2017/3/15.
 */

public class HttpResponse<T> {
    /**
     * message : {"code":0,"message":"操作成功"}
     * data : [{"category":"tabicon","client":"android","maxVersion":"","md5":"4a499faaf6f2c92b8a5eee3c39e0e080","miniVersion":"","targetVersion":"","timestamp":1488522214481,"url":"https://download.jinhui365.cn/group112/M00/AF/7B/CgAAcFi5C8rZtAlvAAHdxi7mTGo866.jpg","userInfo":{}},{"category":"tabicon","client":"android","maxVersion":"","md5":"4a499faaf6f2c92b8a5eee3c39e0e080","miniVersion":"","targetVersion":"","timestamp":1488522247249,"url":"https://download.jinhui365.cn/group112/M00/AF/7B/CgAAcFi5DAXDeU21AAHdxnjIpEI371.jpg","userInfo":{}},{"category":"gotoPage","client":"android","maxVersion":"","md5":"eaa72e14c7f0d3180e3bc03bf3aa4e36","miniVersion":"","targetVersion":"","timestamp":1488522583433,"url":"https://download.jinhui365.cn/group112/M00/AF/7B/CgAAcFi5C2Lx_NBzAAA0Lwt5vEM87.json","userInfo":{}},{"category":"tabicon","client":"all","maxVersion":"","md5":"08ecac30c13306c66a98804a109b161e","miniVersion":"","targetVersion":"","timestamp":1488525783000,"url":"https://download.jinhui365.cn/group112/M00/AF/7C/CgAAcFi5GdXYzX-_AACW7WQxFig521.zip","userInfo":{"activeTime":1488440100000,"expireTime":1488627000000}}]
     */
    private MessageBean message;

    public MessageBean getMessage() {
        return message;
    }

    private T data;

    public void setMessage(MessageBean message) {
        this.message = message;
    }


    public static class MessageBean {
        /**
         * code : 0
         * message : 操作成功
         */

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
