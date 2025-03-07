package view.model;


public class UserReportDTO {
    private String username;
    private int soldBooks;
    private int totalSum;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSoldBooks() {
        return soldBooks;
    }

    public void setSoldBooks(int soldBooks) {
        this.soldBooks = soldBooks;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public String toString() {
        return "UserReport [username=" + username + ", soldBooks=" + soldBooks + ", totalSum=" + totalSum + "]";
    }
}
