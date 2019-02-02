package com.programmer.jgallos.ma_s;

public class AttendanceRecords {
        private String signin_date, signin_time, signout_time, uid;

        public AttendanceRecords(String signin_date, String signin_time, String signout_time, String uid) {
            this.signin_date = signin_date;
            this.signin_time = signin_time;
            this.signout_time = signout_time;
            this.uid = uid;
        }

        public AttendanceRecords() {

        }

        public void setDate(String signin_date) {
            this.signin_date=signin_date;
        }
        /*public  String getUsername() {
            return username;
        }*/

        /*public void setUsername(String username) {
            this.username = username;
        }*/

        public void setSignin(String signin_time) {
            this.signin_time = signin_time;
        }

        public void setSignout(String signout_time) {
            this.signout_time = signout_time;
        }
        public void setUid(String uid) {
        this.uid = uid;
    }

        public String getDate() {
        return signin_date;
    }
        public String getSignin() {
        return signin_time;
    }
        public String getSignout() {
        return signout_time;
    }
        public String getUid() {
        return uid;
    }
}

