package vn.funix.fx22541.asm04.test.dao;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
class AccountDAOTest {
    @Test
    public void canReadFile() {
        String fileName = "src/vn/funix/fx22541/asm04/store/customers.txt";
        File file = new File(fileName);
//        List<IAccount> list = CustomerDAO.readFile(fileName);
//        assertFalse(list.isEmpty());
        assertThat(fileName).hasSizeGreaterThanOrEqualTo(fileName.length());
        assertThat(file)
                .isFile()
                .hasExtension("txt")
                .isFile()
                .as("Hello")
                .isNull()
        ;
    }
}