package vn.funix.fx22541.asm04.dao;

import vn.funix.fx22541.asm04.model.Account;
import vn.funix.fx22541.asm04.service.BinaryFileService;

import java.util.List;

public interface DAO <T> {
    //    <T> void update(List<? super T> list);
    String fileName = "";

    void save();

    enum ListFileName {
        ACCOUNT("aaaaa") {
        },
        CUSTOMER("abc"),
        TRANSACTION( "this");


        private static final String Transaction = "abc";

        ListFileName(String s) {

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }



    default <T extends Account> void save(List<T> list) {
        BinaryFileService.writeFile("", list);
    }

    public static void main(String[] args) {
        System.out.println(ListFileName.CUSTOMER.ordinal());
    }

//    List<T> list();
//
//    void update();
//
//    List<T> readTextFile();



//    void update();
//    void read();
//
//    void list();

}
