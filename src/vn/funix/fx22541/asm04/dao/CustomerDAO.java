package vn.funix.fx22541.asm04.dao;

import vn.funix.fx22541.asm04.model.Customer;
import vn.funix.fx22541.asm04.model.DigitalCustomer;
import vn.funix.fx22541.asm04.service.BinaryFileService;
import vn.funix.fx22541.asm04.service.TextFileService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomerDAO <T extends Customer> {
    private static <V> Predicate<V>
    distinctByKeys(final Function<? super V, ?>... keyExtractors)
    {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());
            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    private static final String DAT_FILE_PATH = "src/vn/funix/fx22541/asm04/store/customers.dat";

    static public <T extends Customer> List<T> list() {
        return BinaryFileService.readFile(DAT_FILE_PATH);
    }


    public static <T extends Customer> List<T> importCustomers(String path) {
        List<T> datFileList = list();
        List<List<String>> listString = TextFileService.readFile(path);
        List<List<String>> list = listString.stream().filter(distinctByKeys(e -> e.get(0))).toList();
        List<T> listFromTextFile = (List<T>) list.stream().map(DigitalCustomer::new).toList();

        listFromTextFile.forEach(e -> {
            if (datFileList.contains(e)) {
                System.out.printf("Customer existed: %-18s, cannot be imported!%n", e.getName());
            } else {
                System.out.printf("Importing new DigitalCustomer: %-18s  (id: %s)%n", e.getName(), e.getId());
                datFileList.add(e);
            }
        });
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

    public static <T extends Customer> boolean isCustomerExisted(String customerId) {
        List<T> customers = list();
        return customers.stream().anyMatch(e -> e.getId().equals(customerId));
    }
}
