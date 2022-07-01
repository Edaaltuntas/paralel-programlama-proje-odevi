package com.edaaltuntas;

public class Group {
    String customer_city;
    String product_category_name;
    int sum;

    public int getSum() {
        return sum;
    }

    public Group(String customer_city, String product_category_name, int sum) {
        this.customer_city = customer_city;
        this.product_category_name = product_category_name;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Group{" +
                "customer_city='" + customer_city + '\'' +
                ", product_category_name='" + product_category_name + '\'' +
                ", sum=" + sum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!customer_city.equals(group.customer_city)) return false;
        return product_category_name.equals(group.product_category_name);
    }

    @Override
    public int hashCode() {
        int result = customer_city.hashCode();
        result = 31 * result + product_category_name.hashCode();
        return result;
    }


}
