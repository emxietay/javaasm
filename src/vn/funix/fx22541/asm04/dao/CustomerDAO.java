package vn.funix.fx22541.asm04.dao;

import vn.funix.fx22541.asm04.model.Customer;
import vn.funix.fx22541.asm04.service.BinaryFileService;
import vn.funix.fx22541.asm04.service.TextFileService;

import java.util.List;

public class CustomerDAO {

    public static void main(String[] args) {
        importCustomers().stream().forEach(System.out::println);

    }

    //    private List<DigitalCustomer> customers;
//    public static List<Customer> customers = readFile();
    private static final String DAT_FILE_PATH = "src/vn/funix/fx22541/asm04/store/customers.dat";
    private static final String TEXT_FILE_PATH = "src/vn/funix/fx22541/asm04/store/customers.txt";

    static public List<Customer> readFile() {
        return BinaryFileService.readFile(DAT_FILE_PATH);
    }

    static public void save(List<Customer> customers) {
        BinaryFileService.writeFile(DAT_FILE_PATH, customers);
    }

//    static public List<Customer> list() {
//        customers = readFile();
//        return customers;
//    }

    public static List<Customer> importCustomers() {
        List<Customer> datFileList = readFile();

        List<List<String>> listString = TextFileService.readFile(TEXT_FILE_PATH);
        List<Customer> listFromTextFile = listString.stream().map(Customer::new).toList();

        listFromTextFile.forEach(e -> {
            if (datFileList.contains(e)) {
                System.out.printf("Customer existed: %-18s => cannot be imported!%n", e.getName());
            } else {
                System.out.printf("Importing new Customer: %-18s  (id: %s)%n", e.getName(), e.getId());
                datFileList.add(e);
            }
        });
        System.out.println(datFileList.size());
        save(datFileList);
        return datFileList;

    }
}
