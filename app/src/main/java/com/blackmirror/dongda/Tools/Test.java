package com.blackmirror.dongda.Tools;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ruge on 2018-04-20 下午5:06
 */
public class Test {
    public static void main(String[] args) {
        List<A> set = new ArrayList<>();

        set.add(new A(1, "a"));
        set.add(new A(4, "d"));
        set.add(new A(3, "c"));
        set.add(new A(2, "f"));
        set.add(new A(2, "b"));


        Collections.sort(set);

        for (A a : set) {
            System.out.println(a);
        }


    }

    static class A implements Comparable<A> {
        public int i;
        public String s;

        public A(int i, String s) {
            this.i = i;
            this.s = s;
        }

        @Override
        public String toString() {
            return "A{" +
                    "i=" + i +
                    ", s='" + s + '\'' +
                    '}';
        }

        @Override
        public int compareTo(@NonNull A o) {
            if (this.i >= o.i) {
                return 1;
            } else {
                return -1;

            }

        }
    }

}
