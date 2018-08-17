package com.example.asus.liangpinstore.bean;

import java.util.List;

/**
 * Created by asus on 2018/2/3.
 */

public class ListInfo {


    /**
     * error : 0
     * msg : 请求成功
     * result : {"banner":[{"goods_id":"0","id":"1","img":"14811829509103.jpg","product_id":"0","shop_id":"0"},{"goods_id":"0","id":"2","img":"14811832751166.jpg","product_id":"0","shop_id":"0"}],"cateList":{"result":"","total_num":"","total_page":""},"goodsList":{"result":[{"grade":"0","id":"76","img":"01.jpg","price":"0.00","title":"大葱","total_sale_num":"0"},{"grade":"0","id":"81","img":"11.jpg","price":"0.00","title":"黄龙果","total_sale_num":"0"},{"grade":"0","id":"83","img":"21.jpg","price":"0.00","title":"蓝莓","total_sale_num":"0"},{"grade":"0","id":"84","img":"31.jpg","price":"0.00","title":"芒果","total_sale_num":"0"},{"grade":"0","id":"85","img":"41.jpg","price":"0.00","title":"猕猴桃","total_sale_num":"0"},{"grade":"0","id":"86","img":"51.jpg","price":"0.00","title":"南瓜","total_sale_num":"0"},{"grade":"0","id":"87","img":"61.jpg","price":"0.00","title":"琵琶","total_sale_num":"0"},{"grade":"0","id":"88","img":"71.jpg","price":"0.00","title":"苹果","total_sale_num":"0"},{"grade":"0","id":"89","img":"81.jpg","price":"0.00","title":"石榴","total_sale_num":"0"},{"grade":"0","id":"90","img":"91.jpg","price":"0.00","title":"柚子","total_sale_num":"0"}],"total_num":"10","total_page":"1"}}
     */

    private String error;
    private String msg;
    private ResultBeanX result;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    public static class ResultBeanX {
        /**
         * banner : [{"goods_id":"0","id":"1","img":"14811829509103.jpg","product_id":"0","shop_id":"0"},{"goods_id":"0","id":"2","img":"14811832751166.jpg","product_id":"0","shop_id":"0"}]
         * cateList : {"result":"","total_num":"","total_page":""}
         * goodsList : {"result":[{"grade":"0","id":"76","img":"01.jpg","price":"0.00","title":"大葱","total_sale_num":"0"},{"grade":"0","id":"81","img":"11.jpg","price":"0.00","title":"黄龙果","total_sale_num":"0"},{"grade":"0","id":"83","img":"21.jpg","price":"0.00","title":"蓝莓","total_sale_num":"0"},{"grade":"0","id":"84","img":"31.jpg","price":"0.00","title":"芒果","total_sale_num":"0"},{"grade":"0","id":"85","img":"41.jpg","price":"0.00","title":"猕猴桃","total_sale_num":"0"},{"grade":"0","id":"86","img":"51.jpg","price":"0.00","title":"南瓜","total_sale_num":"0"},{"grade":"0","id":"87","img":"61.jpg","price":"0.00","title":"琵琶","total_sale_num":"0"},{"grade":"0","id":"88","img":"71.jpg","price":"0.00","title":"苹果","total_sale_num":"0"},{"grade":"0","id":"89","img":"81.jpg","price":"0.00","title":"石榴","total_sale_num":"0"},{"grade":"0","id":"90","img":"91.jpg","price":"0.00","title":"柚子","total_sale_num":"0"}],"total_num":"10","total_page":"1"}
         */

        private CateListBean cateList;
        private GoodsListBean goodsList;
        private List<BannerBean> banner;

        public CateListBean getCateList() {
            return cateList;
        }

        public void setCateList(CateListBean cateList) {
            this.cateList = cateList;
        }

        public GoodsListBean getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(GoodsListBean goodsList) {
            this.goodsList = goodsList;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class CateListBean {
            /**
             * result :
             * total_num :
             * total_page :
             */

            private String result;
            private String total_num;
            private String total_page;

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String getTotal_num() {
                return total_num;
            }

            public void setTotal_num(String total_num) {
                this.total_num = total_num;
            }

            public String getTotal_page() {
                return total_page;
            }

            public void setTotal_page(String total_page) {
                this.total_page = total_page;
            }
        }

        public static class GoodsListBean {
            /**
             * result : [{"grade":"0","id":"76","img":"01.jpg","price":"0.00","title":"大葱","total_sale_num":"0"},{"grade":"0","id":"81","img":"11.jpg","price":"0.00","title":"黄龙果","total_sale_num":"0"},{"grade":"0","id":"83","img":"21.jpg","price":"0.00","title":"蓝莓","total_sale_num":"0"},{"grade":"0","id":"84","img":"31.jpg","price":"0.00","title":"芒果","total_sale_num":"0"},{"grade":"0","id":"85","img":"41.jpg","price":"0.00","title":"猕猴桃","total_sale_num":"0"},{"grade":"0","id":"86","img":"51.jpg","price":"0.00","title":"南瓜","total_sale_num":"0"},{"grade":"0","id":"87","img":"61.jpg","price":"0.00","title":"琵琶","total_sale_num":"0"},{"grade":"0","id":"88","img":"71.jpg","price":"0.00","title":"苹果","total_sale_num":"0"},{"grade":"0","id":"89","img":"81.jpg","price":"0.00","title":"石榴","total_sale_num":"0"},{"grade":"0","id":"90","img":"91.jpg","price":"0.00","title":"柚子","total_sale_num":"0"}]
             * total_num : 10
             * total_page : 1
             */

            private String total_num;
            private String total_page;
            private List<ResultBean> result;

            public String getTotal_num() {
                return total_num;
            }

            public void setTotal_num(String total_num) {
                this.total_num = total_num;
            }

            public String getTotal_page() {
                return total_page;
            }

            public void setTotal_page(String total_page) {
                this.total_page = total_page;
            }

            public List<ResultBean> getResult() {
                return result;
            }

            public void setResult(List<ResultBean> result) {
                this.result = result;
            }

            public static class ResultBean {
                /**
                 * grade : 0
                 * id : 76
                 * img : 01.jpg
                 * price : 0.00
                 * title : 大葱
                 * total_sale_num : 0
                 */

                private String grade;
                private String id;
                private String img;
                private String price;
                private String title;
                private String total_sale_num;

                public String getGrade() {
                    return grade;
                }

                public void setGrade(String grade) {
                    this.grade = grade;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getTotal_sale_num() {
                    return total_sale_num;
                }

                public void setTotal_sale_num(String total_sale_num) {
                    this.total_sale_num = total_sale_num;
                }
            }
        }

        public static class BannerBean {
            /**
             * goods_id : 0
             * id : 1
             * img : 14811829509103.jpg
             * product_id : 0
             * shop_id : 0
             */

            private String goods_id;
            private String id;
            private String img;
            private String product_id;
            private String shop_id;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }
        }
    }
}
