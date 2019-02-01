package com.programmer.jgallos.ma_s;

public class AttendanceRecords {
        private String atdate, signin_time, atsignout, uid;

        public AttendanceRecords(String atdate, String signin_time, String atsignout, String uid) {
            this.atdate = atdate;
            this.signin_time = signin_time;
            this.atsignout = atsignout;
            this.uid = uid;
        }

        public AttendanceRecords() {

        }

        public void setDate(String atdate) {
            this.atdate=atdate;
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

        public void setSignout(String atsignout) {
            this.atsignout = atsignout;
        }
        public void setUid(String uid) {
        this.uid = uid;
    }

        public String getDate() {
        return atdate;
    }
        public String getSignin() {
        return signin_time;
    }
        public String getSignout() {
        return atsignout;
    }
        public String getUid() {
        return uid;
    }
}

