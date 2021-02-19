package com.transsnet.string;

/**
 * @author yinqi
 * @date 2021/2/5
 */
public class Student implements Cloneable {

        private int number;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public Object clone() {
            Student stu = null;
            try{
                stu = (Student)super.clone();
            }catch(CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return stu;
        }
    }

