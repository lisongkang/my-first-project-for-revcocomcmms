package com.maywide.biz.inter.pojo.usingProduct;

import java.util.List;

/**
 * @author wzy
 * 查询在用产品boss响应
 */
public class UsingProductResp {


    /**
     * currentPage : 1
     * pagesize : 5
     * prods : [{"etime":"2014-11-30T00:00:00","flag":"0","ispostpone":"1","pcode":"C013136","pcodeStr":null,"permark":"1","pname":"互动\u202245","price":"26.5","prodarrears":"0","prodfees":"0","prodstatus":"00","salespkgname":null,"stime":"2014-11-04T00:00:00","fees":"0"},{"etime":"2014-12-18T10:14:56","flag":"0","ispostpone":"0","pcode":"B000303","pcodeStr":null,"permark":"3","pname":"U点播\u2022华数","price":"0","prodarrears":"0","prodfees":"0","prodstatus":"00","salespkgname":null,"stime":"2014-11-04T00:00:00","fees":"0"},{"etime":"2014-12-18T10:14:56","flag":"0","ispostpone":"0","pcode":"B000304","pcodeStr":null,"permark":"3","pname":"U点播\u2022天华","price":"0","prodarrears":"0","prodfees":"0","prodstatus":"00","salespkgname":null,"stime":"2014-11-04T00:00:00","fees":"0"},{"etime":"2014-12-18T10:14:56","flag":"0","ispostpone":"0","pcode":"B000305","pcodeStr":null,"permark":"3","pname":"U点播\u2022粤语","price":"0","prodarrears":"0","prodfees":"0","prodstatus":"00","salespkgname":null,"stime":"2014-11-04T00:00:00","fees":"0"},{"etime":"2014-12-18T10:14:56","flag":"0","ispostpone":"0","pcode":"B000306","pcodeStr":null,"permark":"3","pname":"U点播\u2022欧美","price":"0","prodarrears":"0","prodfees":"0","prodstatus":"00","salespkgname":null,"stime":"2014-11-04T00:00:00","fees":"0"}]
     * totalRecords : 8
     */

//    private int currentPage;
//    private int pagesize;
//    private int totalRecords;
    private List<ProdsBean> prods;

//    public int getCurrentPage() {
//        return currentPage;
//    }
//
//    public void setCurrentPage(int currentPage) {
//        this.currentPage = currentPage;
//    }
//
//    public int getPagesize() {
//        return pagesize;
//    }
//
//    public void setPagesize(int pagesize) {
//        this.pagesize = pagesize;
//    }
//
//    public int getTotalRecords() {
//        return totalRecords;
//    }
//
//    public void setTotalRecords(int totalRecords) {
//        this.totalRecords = totalRecords;
//    }

    public List<ProdsBean> getProds() {
        return prods;
    }

    public void setProds(List<ProdsBean> prods) {
        this.prods = prods;
    }

    public static class ProdsBean {

        private String servid;
        private String keyno;
        private String pcode;
        private String pname;
        private String prodtype;
        private String permark;
        private String prodarrears;
        private String prodfees;
        private String stime;
        private String etime;

        private String pstatus;
        private String statusdate;
        private String payway;
        private String ispostpone;
        private String fbtime;
        private String stoptime;
        private String salesid;
        private List<Mixprod> mixprods;
        private String mindate;
        private String pid;

        private String isbase;
        private String salespkgname;

        public String getSalespkgname() {
            return salespkgname;
        }

        public void setSalespkgname(String salespkgname) {
            this.salespkgname = salespkgname;
        }

        public String getServid() {
            return servid;
        }

        public void setServid(String servid) {
            this.servid = servid;
        }

        public String getKeyno() {
            return keyno;
        }

        public void setKeyno(String keyno) {
            this.keyno = keyno;
        }

        public String getPcode() {
            return pcode;
        }

        public void setPcode(String pcode) {
            this.pcode = pcode;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getProdtype() {
            return prodtype;
        }

        public void setProdtype(String prodtype) {
            this.prodtype = prodtype;
        }

        public String getPermark() {
            return permark;
        }

        public void setPermark(String permark) {
            this.permark = permark;
        }

        public String getProdarrears() {
            return prodarrears;
        }

        public void setProdarrears(String prodarrears) {
            this.prodarrears = prodarrears;
        }

        public String getProdfees() {
            return prodfees;
        }

        public void setProdfees(String prodfees) {
            this.prodfees = prodfees;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getPstatus() {
            return pstatus;
        }

        public void setPstatus(String pstatus) {
            this.pstatus = pstatus;
        }

        public String getStatusdate() {
            return statusdate;
        }

        public void setStatusdate(String statusdate) {
            this.statusdate = statusdate;
        }

        public String getPayway() {
            return payway;
        }

        public void setPayway(String payway) {
            this.payway = payway;
        }

        public String getIspostpone() {
            return ispostpone;
        }

        public void setIspostpone(String ispostpone) {
            this.ispostpone = ispostpone;
        }

        public String getFbtime() {
            return fbtime;
        }

        public void setFbtime(String fbtime) {
            this.fbtime = fbtime;
        }

        public String getStoptime() {
            return stoptime;
        }

        public void setStoptime(String stoptime) {
            this.stoptime = stoptime;
        }

        public String getSalesid() {
            return salesid;
        }

        public void setSalesid(String salesid) {
            this.salesid = salesid;
        }

        public List<Mixprod> getMixprods() {
            return mixprods;
        }

        public void setMixprods(List<Mixprod> mixprods) {
            this.mixprods = mixprods;
        }

        public String getMindate() {
            return mindate;
        }

        public void setMindate(String mindate) {
            this.mindate = mindate;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getIsbase() {
            return isbase;
        }

        public void setIsbase(String isbase) {
            this.isbase = isbase;
        }

        public static class Mixprod{
            private String spid;
            private String spname;
            private String permark;
            private String devno;

            public String getSpid() {
                return spid;
            }

            public void setSpid(String spid) {
                this.spid = spid;
            }

            public String getSpname() {
                return spname;
            }

            public void setSpname(String spname) {
                this.spname = spname;
            }

            public String getPermark() {
                return permark;
            }

            public void setPermark(String permark) {
                this.permark = permark;
            }

            public String getDevno() {
                return devno;
            }

            public void setDevno(String devno) {
                this.devno = devno;
            }
        }
//        private String etime;
//        private String flag;
//        private String ispostpone;
//        private String pcode;
//        private Object pcodeStr;
//        private String permark;
//        private String pname;
//        private String price;
//        private String prodarrears;
//        private String prodfees;
//        private String prodstatus;
//        private Object salespkgname;
//        private String stime;
//        private String fees;
//
//        public String getEtime() {
//            return etime;
//        }
//
//        public void setEtime(String etime) {
//            this.etime = etime;
//        }
//
//        public String getFlag() {
//            return flag;
//        }
//
//        public void setFlag(String flag) {
//            this.flag = flag;
//        }
//
//        public String getIspostpone() {
//            return ispostpone;
//        }
//
//        public void setIspostpone(String ispostpone) {
//            this.ispostpone = ispostpone;
//        }
//
//        public String getPcode() {
//            return pcode;
//        }
//
//        public void setPcode(String pcode) {
//            this.pcode = pcode;
//        }
//
//        public Object getPcodeStr() {
//            return pcodeStr;
//        }
//
//        public void setPcodeStr(Object pcodeStr) {
//            this.pcodeStr = pcodeStr;
//        }
//
//        public String getPermark() {
//            return permark;
//        }
//
//        public void setPermark(String permark) {
//            this.permark = permark;
//        }
//
//        public String getPname() {
//            return pname;
//        }
//
//        public void setPname(String pname) {
//            this.pname = pname;
//        }
//
//        public String getPrice() {
//            return price;
//        }
//
//        public void setPrice(String price) {
//            this.price = price;
//        }
//
//        public String getProdarrears() {
//            return prodarrears;
//        }
//
//        public void setProdarrears(String prodarrears) {
//            this.prodarrears = prodarrears;
//        }
//
//        public String getProdfees() {
//            return prodfees;
//        }
//
//        public void setProdfees(String prodfees) {
//            this.prodfees = prodfees;
//        }
//
//        public String getProdstatus() {
//            return prodstatus;
//        }
//
//        public void setProdstatus(String prodstatus) {
//            this.prodstatus = prodstatus;
//        }
//
//        public Object getSalespkgname() {
//            return salespkgname;
//        }
//
//        public void setSalespkgname(Object salespkgname) {
//            this.salespkgname = salespkgname;
//        }
//
//        public String getStime() {
//            return stime;
//        }
//
//        public void setStime(String stime) {
//            this.stime = stime;
//        }
//
//        public String getFees() {
//            return fees;
//        }
//
//        public void setFees(String fees) {
//            this.fees = fees;
//        }
    }
}
