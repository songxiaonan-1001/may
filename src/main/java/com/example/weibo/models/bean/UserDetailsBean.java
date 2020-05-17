package com.example.weibo.models.bean;

public class UserDetailsBean {

    /**
     * err : 200
     * errmsg :
     * data : {"username":"yun","nickname":null,"sex":0,"avater":"http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1589201615&t=4abf9992c4d95bad33c05eaf370b257e","level":null,"phone":null,"sign":null,"age":null}
     */

    private int err;
    private String errmsg;
    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * username : yun
         * nickname : null
         * sex : 0
         * avater : http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1589201615&t=4abf9992c4d95bad33c05eaf370b257e
         * level : null
         * phone : null
         * sign : null
         * age : null
         */

        private String username;
        private Object nickname;
        private int sex;
        private String avater;
        private Object level;
        private Object phone;
        private Object sign;
        private Object age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getSign() {
            return sign;
        }

        public void setSign(Object sign) {
            this.sign = sign;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }
    }
}
