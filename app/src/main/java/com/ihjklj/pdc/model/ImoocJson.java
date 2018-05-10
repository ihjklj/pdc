package com.ihjklj.pdc.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8.
 */

public class ImoocJson {

    private List<ImoocCourse> data;

    public List<ImoocCourse> getData() {
        return data;
    }

    public void setData(List<ImoocCourse> data) {
        this.data = data;
    }

    public static class ImoocCourse implements Parcelable {
        /**
         * category : 动效动画
         * level : 入门
         * url : http://imooc.com/learn/985
         * title : MAYA-材质基础入门
         * introduction : Maya材质制作基础命令详解，并快速入门Maya材质制作
         * image_url : http://img2.mukewang.com/5ae4458000016dfe05400300-240-135.jpg
         * student : 5
         * atime : 2018-05-09
         */

        private String category;
        private String level;
        private String url;
        private String title;
        private String introduction;
        private String image_url;
        private int student;
        private String atime;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getStudent() {
            return student;
        }

        public void setStudent(int student) {
            this.student = student;
        }

        public String getAtime() {
            return atime;
        }

        public void setAtime(String atime) {
            this.atime = atime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.category);
            dest.writeString(this.level);
            dest.writeString(this.url);
            dest.writeString(this.title);
            dest.writeString(this.introduction);
            dest.writeString(this.image_url);
            dest.writeInt(this.student);
            dest.writeString(this.atime);
        }

        public ImoocCourse() {
        }

        protected ImoocCourse(Parcel in) {
            this.category = in.readString();
            this.level = in.readString();
            this.url = in.readString();
            this.title = in.readString();
            this.introduction = in.readString();
            this.image_url = in.readString();
            this.student = in.readInt();
            this.atime = in.readString();
        }

        public static final Parcelable.Creator<ImoocCourse> CREATOR = new Parcelable.Creator<ImoocCourse>() {
            @Override
            public ImoocCourse createFromParcel(Parcel source) {
                return new ImoocCourse(source);
            }

            @Override
            public ImoocCourse[] newArray(int size) {
                return new ImoocCourse[size];
            }
        };
    }
}
