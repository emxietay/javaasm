package vn.funix.fx22541.asm04.dao;

import vn.funix.fx22541.asm04.model.Customer;
import vn.funix.fx22541.asm04.model.DigitalCustomer;
import vn.funix.fx22541.asm04.service.BinaryFileService;
import vn.funix.fx22541.asm04.service.TextFileService;

import java.util.List;

public class CustomerDAO <T extends Customer> {

    public static void main(String[] args) {
        importCustomers().stream().forEach(System.out::println);

    }

    //    private List<DigitalCustomer> customers;
//    public static List<DigitalCustomer> customers = readFile();
    private static final String DAT_FILE_PATH = "src/vn/funix/fx22541/asm04/store/customers.dat";
    private static final String TEXT_FILE_PATH = "src/vn/funix/fx22541/asm04/store/customers.txt";


    static public <T extends Customer> List<T> list() {
        return BinaryFileService.readFile(DAT_FILE_PATH);
    }


    public static <T extends Customer> List<T> importCustomers() {
        List<T> datFileList = list();

        List<List<String>> listString = TextFileService.readFile(TEXT_FILE_PATH);
        List<T> listFromTextFile = (List<T>) listString.stream().map(DigitalCustomer::new).toList();

        listFromTextFile.forEach(e -> {
            if (datFileList.contains(e)) {
                System.out.printf("DigitalCustomer existed: %-18s => cannot be imported!%n", e.getName());
            } else {
                System.out.printf("Importing new DigitalCustomer: %-18s  (id: %s)%n", e.getName(), e.getId());
                datFileList.add(e);
            }
        });
        System.out.println(datFileList.size());
        save(datFileList);
        return datFileList;
    }

    public static <T> void update(T customer) {
        List<T> customers = (List<T>) list();
        customers.add(customer);
        save(customers);
    }

    private static <T> void save(List<T> customers) {
        BinaryFileService.writeFile(DAT_FILE_PATH, customers);
    }

    public static boolean isCustomerExisted(String customerId) {
        List<Customer> customers = list();
        return customers.stream().anyMatch(e -> e.getId().equals(customerId));
    }
}
