package Week3;

class CompanyEmployeeRecord {

    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId,
                          Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;

        totalRecords++;
    }

    String fullProfile() {

        String parking;

        if (slot == null) {
            parking = "no parking assigned";
        } else {
            parking = slot.slotNo;
        }

        double pay;

        if (employee instanceof ManagerEmployee) {
            pay = ((ManagerEmployee) employee).effectiveSalary();
        } else if (employee instanceof InternEmployee) {
            pay = ((InternEmployee) employee).effectiveSalary();
        } else {
            pay = employee.getSalary();
        }

        return name + " | Pay: Rs " + pay
                + " | Slot: " + parking;
    }
}

public class Problem5 {

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {

        for (int i = 0; i < slots.length; i++) {
            if (slots[i].occupiedCount < slots[i].capacity) {
                return slots[i];
            }
        }

        return null;
    }

    static ParkingSlot allotSlot(ParkingSlot[] slots, String vehicleNo) {

        ParkingSlot slot = findAvailableSlot(slots);

        if (slot != null) {
            slot.allot(vehicleNo);
        }

        return slot;
    }

    public static void main(String[] args) {

        ManagerEmployee manager =
                new ManagerEmployee(101, "Divya", 70000, 8000);

        Employee employee =
                new Employee(102, "Karan", 40000);

        InternEmployee intern =
                new InternEmployee(103, "Meera", 12000, 10000);

        ParkingSlot[] slots = {
                new ParkingSlot("A1", 1, 0),
                new ParkingSlot("A2", 1, 0)
        };

        ParkingSlot slot1 =
                allotSlot(slots, "TN01AA1111");

        ParkingSlot slot2 =
                allotSlot(slots, "TN02BB2222");

        CompanyEmployeeRecord record1 =
                new CompanyEmployeeRecord(
                        "Divya",
                        "101",
                        manager,
                        slot1
                );

        CompanyEmployeeRecord record2 =
                new CompanyEmployeeRecord(
                        "Karan",
                        "102",
                        employee,
                        slot2
                );

        CompanyEmployeeRecord record3 =
                new CompanyEmployeeRecord(
                        "Meera",
                        "103",
                        intern,
                        null
                );

        System.out.println(record1.fullProfile());
        System.out.println(record2.fullProfile());
        System.out.println(record3.fullProfile());

        System.out.println("Total records: "
                + CompanyEmployeeRecord.totalRecords);
    }
}