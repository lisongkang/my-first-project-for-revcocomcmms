package com.maywide.biz.pay.unify.pojo;

import java.io.Serializable;
import java.util.List;

public class UnifyPayBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private OrderInfo orderInfo;

    private CustInfo custInfo;

    private String totalFee;
    private String payments;
    private String redirectUrl;
    private String noticeAction;
    private String orderNum;
    private String isOrder;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public CustInfo getCustInfo() {
        return custInfo;
    }

    public void setCustInfo(CustInfo custInfo) {
        this.custInfo = custInfo;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getNoticeAction() {
        return noticeAction;
    }

    public void setNoticeAction(String noticeAction) {
        this.noticeAction = noticeAction;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(String isOrder) {
        this.isOrder = isOrder;
    }

    public static class OrderInfo {
        private String orderNo;

        private List<Product> productList;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public List<Product> getProductList() {
            return productList;
        }

        public void setProductList(List<Product> productList) {
            this.productList = productList;
        }

        public static class Product {
            private String keyno;
            private String productName;
            private String count;
            private String fee;

            public String getKeyno() {
                return keyno;
            }

            public void setKeyno(String keyno) {
                this.keyno = keyno;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }
        }
    }

    public static class CustInfo {
        private String custname;
        private String custid;
        private String gdNo;
        private String city;
        private String area;
        private String cardNo;
        private String servid;

        public String getCustname() {
            return custname;
        }

        public void setCustname(String custname) {
            this.custname = custname;
        }

        public String getCustid() {
            return custid;
        }

        public void setCustid(String custid) {
            this.custid = custid;
        }

        public String getGdNo() {
            return gdNo;
        }

        public void setGdNo(String gdNo) {
            this.gdNo = gdNo;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getServid() {
            return servid;
        }

        public void setServid(String servid) {
            this.servid = servid;
        }
    }
}
