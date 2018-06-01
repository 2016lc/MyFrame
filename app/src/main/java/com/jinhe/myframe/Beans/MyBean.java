package com.jinhe.myframe.Beans;

import com.jinhe.myframe.base.BaseBean;

/**
 * Created by LC on 2018/5/26.
 */
public class MyBean extends BaseBean{

    /**
     * para : {"user_id":38,"username":"user","nickname":"莱昂纳多·狗剩","mobile":"18725788213","level":"普通用户","invite":"123456","invitenum":"12","redpacket":"2425.00","headpic":null,"token":"e33c710fb7dfad6f232ba159a381a9bd","bankname":"刘旭","bankcard":"123456789"}
     */

    private ParaBean para;

    public ParaBean getPara() {
        return para;
    }

    public void setPara(ParaBean para) {
        this.para = para;
    }

    public static class ParaBean {
        /**
         * user_id : 38
         * username : user
         * nickname : 莱昂纳多·狗剩
         * mobile : 18725788213
         * level : 普通用户
         * invite : 123456
         * invitenum : 12
         * redpacket : 2425.00
         * headpic : null
         * token : e33c710fb7dfad6f232ba159a381a9bd
         * bankname : 刘旭
         * bankcard : 123456789
         */

        private int user_id;
        private String username;
        private String nickname;
        private String mobile;
        private String level;
        private String invite;
        private String invitenum;
        private String redpacket;
        private Object headpic;
        private String token;
        private String bankname;
        private String bankcard;
        private boolean istbs;
        private int tuisong;
        private String unti;
        private String isadmin;
        private String isjx;
        private String chaturl;


        public String getChaturl() {
            return chaturl;
        }

        public void setChaturl(String chaturl) {
            this.chaturl = chaturl;
        }

        public String getIsjx() {
            return isjx;
        }

        public void setIsjx(String isjx) {
            this.isjx = isjx;
        }

        public String getIsadmin() {
            return isadmin;
        }

        public void setIsadmin(String isadmin) {
            this.isadmin = isadmin;
        }

        public String getUnti() {
            return unti;
        }

        public void setUnti(String unti) {
            this.unti = unti;
        }

        public int getTuisong() {
            return tuisong;
        }

        public void setTuisong(int tuisong) {
            this.tuisong = tuisong;
        }

        public boolean isIstbs() {
            return istbs;
        }

        public void setIstbs(boolean istbs) {
            this.istbs = istbs;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getInvite() {
            return invite;
        }

        public void setInvite(String invite) {
            this.invite = invite;
        }

        public String getInvitenum() {
            return invitenum;
        }

        public void setInvitenum(String invitenum) {
            this.invitenum = invitenum;
        }

        public String getRedpacket() {
            return redpacket;
        }

        public void setRedpacket(String redpacket) {
            this.redpacket = redpacket;
        }

        public Object getHeadpic() {
            return headpic;
        }

        public void setHeadpic(Object headpic) {
            this.headpic = headpic;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getBankcard() {
            return bankcard;
        }

        public void setBankcard(String bankcard) {
            this.bankcard = bankcard;
        }
    }
}
