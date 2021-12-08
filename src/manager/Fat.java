package com.filemanager.manager;

import java.util.Arrays;

/**
 * FAT表类
 * 提供封装的FAT表
 * 并且把FAT表分配和释放的方法封装在此
 */
public class Fat {
    int[] fatList;
    int fatSize;

    public Fat(int fatSize) {
        this.fatSize = fatSize;
        this.fatList=this.initFat();
    }

    /**
     * 生成并对FAT表进行初始化
     * @return  生成的FAT表
     */
    private int[] initFat() {
        int[] ints = new int[fatSize + 10];
        for (int i = 1; i <= fatSize; i++) {
            ints[i] = -1;
        }
        ints[1]=1;
        return ints;
    }

    /**
     * 在FAT表中查找可以用于分配的位置
     * @return  可以分配的位置，如果是-1则表示FAT表无法分配
     */
    public int getFatLocation() {
        for (int i=1;i<=this.fatSize;i++) {
            if (fatList[i]==-1) {
                return fatList[i]=i;
            }
        }
        return -1;
    }

    /**
     * 回收FAT表中的某个位置
     * @param x
     */
    public void releaseFat(int x) {
        this.fatList[x]=-1;
    }

    public int[] getFatList() {
        return fatList;
    }

    public void setFatList(int[] fatList) {
        this.fatList = fatList;
    }

    public int getFatSize() {
        return fatSize;
    }

    public void setFatSize(int fatSize) {
        this.fatSize = fatSize;
    }

    public void showFat() {
        System.out.println("FatSize-->" + this.fatSize);
        int counter=0;
        int x=1;
        while (x<=this.fatSize) {
            System.out.print(this.fatList[x++]+" ");
            counter++;
            if (counter>=10) {
                System.out.println();
                counter=0;
            }
        }
    }
}
