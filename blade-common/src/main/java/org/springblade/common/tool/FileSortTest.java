/**
 * Copyright (C), 2015-2021
 * FileName: FileSortTest
 * Author:   呵呵哒
 * Date:     2021/12/23 15:06
 * Description:
 */
package org.springblade.common.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @创建人 hyp
 * @创建时间 2021/12/23
 * @描述
 */
public class FileSortTest {

    public static final String PATH = "D:\\tmp\\";

    public static void main(String[] args) {
        List<File> files = new ArrayList<File>();
        files.add(new File(PATH + "test4.jpg"));
        files.add(new File(PATH + "test1.jpg"));
        files.add(new File(PATH + "test5.jpg"));
        files.add(new File(PATH + "test2.jpg"));
        files.add(new File(PATH + "test10.jpg"));
        files.add(new File(PATH + "test3.jpg"));
        files = sortFileByName(files, "asc");
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    /**
     *
     * @param files
     * @param orderStr 排序:asc,des,不区分大小写
     * @return
     */
    public static List<File> sortFileByName(List<File> files, final String orderStr) {
        if (!orderStr.equalsIgnoreCase("asc") && orderStr.equalsIgnoreCase("desc")) {
            return files;
        }
        File[] files1 = files.toArray(new File[0]);
        Arrays.sort(files1, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int n1 = extractNumber(o1.getName());
                int n2 = extractNumber(o2.getName());
                if(orderStr == null || orderStr.length() < 1 || orderStr.equalsIgnoreCase("asc")) {
                    return n1 - n2;
                } else {
                    //降序
                    return n2 - n1;
                }
            }
        });
        return new ArrayList<File>(Arrays.asList(files1));
    }

    private static int extractNumber(String name) {
        int i;
        try {
            String number = name.replaceAll("[^\\d]", "");
            i = Integer.parseInt(number);
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }
}
