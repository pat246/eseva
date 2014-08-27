package components;

import database.Company;

public enum CompanyDataComponents {
    NAME("Company name") {
        @Override
        public String getValue() {
            return company.getName();
        }
    },
    ADDRESS("Address") {
        @Override
        public String getValue() {
            return "";
        }
    },
    EMAIL("Email") {
        @Override
        public String getValue() {
            return company.getEmail();
        }
    },
    PHONE("Phone no.") {
        @Override
        public String getValue() {
            return company.getPhone();
        }
    },
    CONTACT_PERSON("Contact Person") {
        @Override
        public String getValue() {
            return company.getContactPerson();
        }
    };

    CompanyDataComponents(String label) {
        this.label = label;
    }

    public abstract String getValue();

    public String         label;
    public static Company company;
}
