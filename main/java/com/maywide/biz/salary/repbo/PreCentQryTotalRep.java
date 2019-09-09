package com.maywide.biz.salary.repbo;

import java.util.List;

public class PreCentQryTotalRep {

   private String rtcode;
    private String message;
    private List<Detail> scenelist;  //数据明细

    public static class Detail{
        private String scene;  //业务场景
        private String sceneName;  //业务场景
        private String totalcents;  //总积分
        private String srccents; //个人积分
        private String sharecents; //分享积分

        public String getScene() {
            return scene;
        }

        public void setScene(String scene) {
            this.scene = scene;
        }

        public String getTotalcents() {
            return totalcents;
        }

        public void setTotalcents(String totalcents) {
            this.totalcents = totalcents;
        }

        public String getSrccents() {
            return srccents;
        }

        public void setSrccents(String srccents) {
            this.srccents = srccents;
        }

        public String getSharecents() {
            return sharecents;
        }

        public void setSharecents(String sharecents) {
            this.sharecents = sharecents;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }
    }

    public String getRtcode() {
        return rtcode;
    }

    public void setRtcode(String rtcode) {
        this.rtcode = rtcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Detail> getScenelist() {
        return scenelist;
    }

    public void setScenelist(List<Detail> scenelist) {
        this.scenelist = scenelist;
    }
}
