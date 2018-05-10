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
         * category : Python
         * index_sign : 0.0
         * level : 入门
         * url : http://imooc.com/learn/988
         * title : 区块链技术核心概念与原理讲解
         * introduction : 登链科技首席技术官(Tiny熊），为你讲解区块链技术核心概念与原理
         * image_url : http://img3.mukewang.com/5ae3e5b80001818c06000338-240-135.jpg
         * student : 123
         * atime : 2018-05-10
         * id : 1
         */

        private String category;
        private double index_sign;
        private String level;
        private String url;
        private String title;
        private String introduction;
        private String image_url;
        private int student;
        private String atime;
        private int id;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getIndex_sign() {
            return index_sign;
        }

        public void setIndex_sign(double index_sign) {
            this.index_sign = index_sign;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.category);
            dest.writeDouble(this.index_sign);
            dest.writeString(this.level);
            dest.writeString(this.url);
            dest.writeString(this.title);
            dest.writeString(this.introduction);
            dest.writeString(this.image_url);
            dest.writeInt(this.student);
            dest.writeString(this.atime);
            dest.writeInt(this.id);
        }

        public ImoocCourse() {
        }

        protected ImoocCourse(Parcel in) {
            this.category = in.readString();
            this.index_sign = in.readDouble();
            this.level = in.readString();
            this.url = in.readString();
            this.title = in.readString();
            this.introduction = in.readString();
            this.image_url = in.readString();
            this.student = in.readInt();
            this.atime = in.readString();
            this.id = in.readInt();
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
