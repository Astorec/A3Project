package Functions;

import Classes.PermitHolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

public class FilterData {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String areaCode;
    private LocalDate startDate;
    private LocalDate expiryDate;

    public void setID(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Stream<Entry<String, PermitHolder>> filter(Set<Entry<String, PermitHolder>> entries) {
        return entries.stream().filter(entry -> {
            PermitHolder permit = entry.getValue();
            boolean match = id == null || id.isEmpty() || permit.getId().contains(id);
            if (firstName != null && !firstName.isEmpty() && !permit.getFirst_name().contains(firstName)) {
                match = false;
            }
            if (lastName != null && !lastName.isEmpty() && !permit.getLast_name().contains(lastName)) {
                match = false;
            }
            if (address != null && !address.isEmpty() && !permit.getAddress().toString().contains(address)) {
                match = false;
            }
            if (areaCode != null && !areaCode.isEmpty() && !permit.getPermit().sendAreaCodes().contains(areaCode)) {
                match = false;
            }
            if (startDate != null && !startDate.toString().isEmpty() && !permit.getPermit().sendStartDate().equals(startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
                match = false;
            }
            if (expiryDate != null && !expiryDate.toString().isEmpty() && !permit.getPermit().sendExpiryDate().contains(expiryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
                match = false;
            }
            return match;
        });
    }
}
